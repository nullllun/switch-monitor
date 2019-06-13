package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.bean.*;
import cn.albumenj.switchmonitor.service.*;
import cn.albumenj.switchmonitor.util.DateUtil;
import cn.albumenj.switchmonitor.util.OidList;
import cn.albumenj.switchmonitor.util.PortConvert;
import cn.albumenj.switchmonitor.util.SnmpUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 交换机刷新（SNMP）
 *
 * @author Albumen
 */
@Component
public class SwitchUpdate {
    @Autowired
    PortStatusService portStatusService;
    @Autowired
    PortSpeedHistoryService portSpeedHistoryService;
    @Autowired
    SwitchesStatusService switchesStatusService;
    @Autowired
    SwitchesStatusHistoryService switchesStatusHistoryService;
    @Autowired
    PortSpeedService portSpeedService;
    @Autowired
    SnmpUtil snmpUtil;
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    PortSpeedHistoryBlankService portSpeedHistoryBlankService;

    @Value("${commit.switchesStatusHistories-insert}")
    Integer switchesStatusHistoriesLimit;
    @Value("${commit.switchesReachables-update}")
    Integer switchesReachablesLimit;

    private static List<SwitchesStatusHistory> switchesStatusHistories = new LinkedList<>();
    private static List<SwitchesStatus> switchesStatuses = new LinkedList<>();
    private static ConcurrentHashMap<String, PortStatus> portStatusConcurrentHashMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, PortSpeed> portSpeedConcurrentHashMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, PortSpeedHistoryBlank> portSpeedHistoryBlankConcurrentHashMap = new ConcurrentHashMap<>();

    public static List<SwitchesStatusHistory> getSwitchesStatusHistories() {
        return switchesStatusHistories;
    }

    public static List<SwitchesStatus> getSwitchesStatuses() {
        return switchesStatuses;
    }

    public boolean submit(SwitchesList s) {
        Long time = (System.currentTimeMillis());
        boolean ret = true;
        SwitchesStatus switchesStatus = new SwitchesStatus();
        SwitchesStatusHistory switchesStatusHistory = new SwitchesStatusHistory();

        OidList oidList = new OidList(s.getModel());

        switchesStatus.setSwitchId(s.getId());

        switchesStatus.setUpTime(DateUtil.getTillTime(Long.valueOf(getStringData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getUpTime()))) * 10));
        switchesStatus.setName(getStringData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getNAME())));
        switchesStatus.setCpuLoad(getIntegerData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getCpuLoad())));
        switchesStatus.setMemoryUsed(getIntegerData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getMemUsed())));
        switchesStatus.setTemp(getIntegerData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getTEMP())));

        submitPort(s, oidList);

        switchesStatus.setTimeStamp(new Date());

        BeanUtils.copyProperties(switchesStatus, switchesStatusHistory);
        synchronized (switchesStatusHistories) {
            switchesStatusHistories.add(switchesStatusHistory);
            if (switchesStatusHistories.size() > switchesStatusHistoriesLimit) {
                ret = ret && switchesStatusHistoryService.insertList(switchesStatusHistories) > 0;
                switchesStatusHistories.clear();
            }
        }
        synchronized (switchesStatuses) {
            switchesStatuses.add(switchesStatus);
            if (switchesStatuses.size() > switchesReachablesLimit) {
                ret = ret && switchesStatusService.updateList(switchesStatuses) > 0;
                switchesStatuses.clear();
            }
        }
        return ret;
    }

    private boolean submitPort(SwitchesList s, OidList oidList) {
        boolean ret = true;

        Map<Integer, String> portName = new LinkedHashMap<>();
        Map<Integer, String> portIndex = new LinkedHashMap<>();
        Map<Integer, String> portStatusG = new LinkedHashMap<>();
        Map<Integer, String> portIn = new LinkedHashMap<>();
        Map<Integer, String> portOut = new LinkedHashMap<>();
        Map<Integer, String> portUptime = new LinkedHashMap<>();
        Map<Integer, String> portDesc = new LinkedHashMap<>();
        Map<Integer, String> portSpeed = new LinkedHashMap<>();

        snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfName(), portName);
        snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfIndex(), portIndex);
        snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfStatus(), portStatusG);
        snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfIn(), portIn);
        snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfOut(), portOut);
        snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfUptime(), portUptime);
        snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfDescr(), portDesc);
        snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfSpeed(), portSpeed);

        Set<Map.Entry<Integer, String>> portNameEntries = portName.entrySet();
        List<PortSpeedHistory> portSpeedHistories = new LinkedList<>();
        List<PortSpeed> portSpeeds = new LinkedList<>();
        List<PortStatus> portStatuses = new LinkedList<>();
        List<PortSpeedHistoryBlank> portSpeedBlankUpdate = new LinkedList<>();
        List<PortSpeedHistoryBlank> portSpeedBlankInsert = new LinkedList<>();

        for (Map.Entry<Integer, String> entry : portNameEntries) {
            boolean ret1 = portIndex.get(entry.getKey()) == null ||
                    (Integer.parseInt(portIndex.get(entry.getKey())) > 1000
                            &&
                            portIndex.get(entry.getKey()).equals(entry.getKey()));
            if (ret1) {
                continue;
            }
            PortStatus portStatus = new PortStatus();

            portStatus.setSwitchId(s.getId());
            portStatus.setPortIndex((Integer) getData(Integer.class, portIndex.get(entry.getKey())));
            portStatus.setName((String) getData(String.class, entry.getValue()));
            portStatus.setInData((Long) getData(Long.class, portIn.get(entry.getKey())));
            portStatus.setOutData((Long) getData(Long.class, portOut.get(entry.getKey())));
            portStatus.setUpTime(DateUtil.getTillTime(Long.valueOf((String) getData(String.class, portUptime.get(entry.getKey()))) * 10));
            portStatus.setDes((String) getData(String.class, portDesc.get(entry.getKey())));
            portStatus.setSpeed((Integer) getData(Integer.class, portSpeed.get(entry.getKey())));
            portStatus.setStatus((Integer) getData(Integer.class, portStatusG.get(entry.getKey())));
            portStatus.setTimeStamp(new Date());
            portStatus.setSwitchPort("s" + portStatus.getSwitchId() + "p" + portStatus.getPortIndex());

            submitPortSpeed(portSpeeds, portSpeedHistories, portStatus, portSpeedBlankUpdate, portSpeedBlankInsert);

            portStatuses.add(portStatus);
        }
        ret = ret && portSpeedService.updateList(portSpeeds) > 0;
        ret = ret && portSpeedHistoryService.insertList(portSpeedHistories) > 0;
        ret = ret && portStatusService.updateList(portStatuses) > 0;
        ret = ret && portSpeedHistoryBlankService.updateList(portSpeedBlankUpdate) > 0;
        ret = ret && portSpeedHistoryBlankService.insertList(portSpeedBlankInsert) > 0;

        portName = null;
        portIndex = null;
        portStatusG = null;
        portIn = null;
        portOut = null;
        portUptime = null;
        portDesc = null;
        portSpeed = null;

        return ret;
    }

    private void submitPortSpeed(List<PortSpeed> portSpeeds,
                                 List<PortSpeedHistory> portSpeedHistories,
                                 PortStatus portStatus,
                                 List<PortSpeedHistoryBlank> portSpeedBlankUpdate,
                                 List<PortSpeedHistoryBlank> portSpeedBlankInsert) {
        PortStatus portStatusOld = portStatusConcurrentHashMap.get(portStatus.getSwitchPort());
        boolean npeCheck = portStatusOld != null && portStatusOld.getInData() != null
                && portStatus.getInData() != null && portStatusOld.getOutData() != null
                && portStatus.getOutData() != null && portStatus.getSpeed() != null;
        if (npeCheck) {

            Long in = portStatus.getInData() - portStatusOld.getInData();
            Long out = portStatus.getOutData() - portStatusOld.getOutData();
            Long time = (portStatus.getTimeStamp().getTime() - portStatusOld.getTimeStamp().getTime()) / 1000;
            Long speedMax = time * (portStatus.getSpeed() * 125000L);

            boolean valid = in <= speedMax && out <= speedMax && in >= 0L && out >= 0L;

            if (valid) {
                PortSpeed portSpeedSubmit = new PortSpeed();

                Integer up = 2;
                if (up.equals(portStatus.getStatus())) {
                    portSpeedSubmit.setInSpeed(0L);
                    portSpeedSubmit.setOutSpeed(0L);
                } else {
                    Long inB = in / time;
                    Long outB = out / time;
                    portSpeedSubmit.setInSpeed(inB);
                    portSpeedSubmit.setOutSpeed(outB);
                }

                portSpeedSubmit.setSwitchId(portStatus.getSwitchId());
                portSpeedSubmit.setPortIndex(portStatus.getPortIndex());
                portSpeedSubmit.setSwitchPort(portStatus.getSwitchPort());

                portSpeeds.add(portSpeedSubmit);
                submitSpeedHistory(portSpeedSubmit, portSpeedHistories, portSpeedBlankUpdate, portSpeedBlankInsert);
            }
        }
    }

    private void submitSpeedHistory(PortSpeed portSpeedSubmit,
                                    List<PortSpeedHistory> portSpeedHistories,
                                    List<PortSpeedHistoryBlank> portSpeedBlankUpdate,
                                    List<PortSpeedHistoryBlank> portSpeedBlankInsert) {
        if (!portSpeedSubmit.getInSpeed().equals(0L) || !portSpeedSubmit.getOutSpeed().equals(0L)) {
            // 当前有流量
            PortSpeedHistory portSpeedHistory = new PortSpeedHistory();
            BeanUtils.copyProperties(portSpeedSubmit, portSpeedHistory);
            portSpeedHistories.add(portSpeedHistory);

            // 若以前有空流量则置无效
            PortSpeedHistoryBlank portSpeedHistoryBlankOld = portSpeedHistoryBlankConcurrentHashMap.get(portSpeedSubmit.getSwitchPort());
            if (portSpeedHistoryBlankOld != null) {
                portSpeedHistoryBlankOld.setLatest(0);
                portSpeedBlankUpdate.add(portSpeedHistoryBlankOld);
            }
        } else {
            // 当前无流量
            PortSpeedHistoryBlank portSpeedHistoryBlank = portSpeedHistoryBlankConcurrentHashMap.get(portSpeedSubmit.getSwitchPort());
            if (portSpeedHistoryBlank != null) {
                portSpeedHistoryBlank.setTimeEnd(new Date());
                portSpeedBlankUpdate.add(portSpeedHistoryBlank);
            } else {
                portSpeedHistoryBlank = initBlank(portSpeedSubmit.getSwitchPort());
                portSpeedBlankInsert.add(portSpeedHistoryBlank);
            }
        }
    }

    private PortSpeedHistoryBlank initBlank(String switchPort) {
        PortSpeedHistoryBlank portSpeedHistoryBlank = new PortSpeedHistoryBlank();
        portSpeedHistoryBlank.setSwitchId(PortConvert.getSwitchId(switchPort));
        portSpeedHistoryBlank.setPortIndex(PortConvert.getPortIndex(switchPort));
        portSpeedHistoryBlank.setTimeStart(new Date());
        portSpeedHistoryBlank.setTimeEnd(new Date());
        portSpeedHistoryBlank.setLatest(1);
        return portSpeedHistoryBlank;
    }

    private String getStringData(Map<Integer, String> data) {
        Set<Map.Entry<Integer, String>> entries = data.entrySet();
        for (Map.Entry<Integer, String> entry : entries) {
            if (entry.getValue().compareTo("0") != 0) {
                return entry.getValue();
            }
        }
        return "-1";
    }

    private Integer getIntegerData(Map<Integer, String> data) {
        Set<Map.Entry<Integer, String>> entries = data.entrySet();
        for (Map.Entry<Integer, String> entry : entries) {
            if (entry.getValue().compareTo("0") != 0) {
                return Integer.parseInt(entry.getValue());
            }
        }
        return -1;
    }

    private Object getData(Class c, String data) {
        Object result;
        if (data != null) {
            if (c == String.class) {
                result = data;
            } else if (c == Long.class) {
                result = Long.parseLong(data);
            } else {
                result = Integer.parseInt(data);
            }
        } else {
            if (c == String.class) {
                result = "-1";
            } else if (c == Long.class) {
                result = -1L;
            } else {
                result = -1;
            }
        }
        return result;
    }

    public void updatePortStatusMap() {
        List<PortStatus> portStatusOld = portStatusService.select();
        portStatusConcurrentHashMap.clear();
        for (PortStatus portStatus : portStatusOld) {
            portStatusConcurrentHashMap.put(portStatus.getSwitchPort(), portStatus);
        }
    }

    public void updatePortSpeedMap() {
        List<PortSpeed> portSpeedOld = portSpeedService.select();
        portSpeedConcurrentHashMap.clear();
        for (PortSpeed portSpeed : portSpeedOld) {
            portSpeedConcurrentHashMap.put(portSpeed.getSwitchPort(), portSpeed);
        }
    }

    public void updateSpeedBlankMap() {
        List<PortSpeedHistoryBlank> portSpeedHistoryBlankOld = portSpeedHistoryBlankService.select();
        portSpeedHistoryBlankConcurrentHashMap.clear();
        for (PortSpeedHistoryBlank portSpeedHistoryBlank : portSpeedHistoryBlankOld) {
            portSpeedHistoryBlankConcurrentHashMap.put(
                    PortConvert.complex(portSpeedHistoryBlank.getSwitchId(), portSpeedHistoryBlank.getPortIndex()),
                    portSpeedHistoryBlank);
        }
    }

}
