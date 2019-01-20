package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.bean.*;
import cn.albumenj.switchmonitor.service.*;
import cn.albumenj.switchmonitor.util.OidList;
import cn.albumenj.switchmonitor.util.SnmpUtil;
import com.alibaba.druid.support.spring.stat.annotation.Stat;
import org.snmp4j.mp.StatusInformation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    @Value("${commit.switchesStatusHistories-insert}")
    Integer switchesStatusHistoriesLimit;
    @Value("${commit.switchesReachables-update}")
    Integer switchesReachablesLimit;

    private static List<SwitchesStatusHistory> switchesStatusHistories = new LinkedList<>();
    private static List<SwitchesStatus> switchesStatuses = new LinkedList<>();
    private static ConcurrentHashMap<String,PortStatus> portStatusConcurrentHashMap = new ConcurrentHashMap<>();

    public static List<SwitchesStatusHistory> getSwitchesStatusHistories() {
        return switchesStatusHistories;
    }

    public static List<SwitchesStatus> getSwitchesStatuses() {
        return switchesStatuses;
    }

    public boolean submit(SwitchesList s) {
        boolean ret = true;
        SwitchesStatus switchesStatus = new SwitchesStatus();
        SwitchesStatusHistory switchesStatusHistory = new SwitchesStatusHistory();

        OidList oidList = new OidList(s.getModel());

        switchesStatus.setSwitchId(s.getId());
        switchesStatus.setUpTime(getStringData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getUpTime())));
        switchesStatus.setIp(getStringData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfIp())));
        switchesStatus.setIpIndex(getIntegerData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfIpindex())));
        switchesStatus.setIpMask(getStringData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfIpmask())));
        switchesStatus.setName(getStringData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getNAME())));
        switchesStatus.setCpuLoad(getIntegerData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getCpuLoad())));
        switchesStatus.setMemoryUsed(getIntegerData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getMemUsed())));
        switchesStatus.setTemp(getIntegerData(snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getTEMP())));
        switchesStatus.setTimeStamp(new Date());

        submitPort(s,oidList);

        BeanUtils.copyProperties(switchesStatus, switchesStatusHistory);
        synchronized (switchesStatusHistories){
            switchesStatusHistories.add(switchesStatusHistory);
            if(switchesStatusHistories.size()>switchesStatusHistoriesLimit){
                ret = ret && switchesStatusHistoryService.insertList(switchesStatusHistories) > 0;
                switchesStatusHistories.clear();
            }
        }
        synchronized (switchesStatuses){
            switchesStatuses.add(switchesStatus);
            if(switchesStatuses.size()>switchesReachablesLimit){
                ret = ret && switchesStatusService.updateList(switchesStatuses)>0;
                switchesStatuses.clear();
            }
        }
        return ret;
    }

    private boolean submitPort(SwitchesList s,OidList oidList){
        boolean ret = true;

        Map<Integer, String> portName = snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfName());
        Map<Integer, String> portIndex = snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfIndex());
        Map<Integer, String> portStatusG = snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfStatus());
        Map<Integer, String> portIn = snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfIn());
        Map<Integer, String> portOut = snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfOut());
        Map<Integer, String> portUptime = snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfUptime());
        Map<Integer, String> portDesc = snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfDescr());
        Map<Integer, String> portSpeed = snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getIfSpeed());

        Set<Map.Entry<Integer, String>> portNameEntries = portName.entrySet();
        List<PortSpeedHistory> portSpeedHistories = new LinkedList<>();
        List<PortSpeed> portSpeeds = new LinkedList<>();
        List<PortStatus> portStatuses = new LinkedList<>();

        for (Map.Entry<Integer, String> entry : portNameEntries) {
            if(portIndex.get(entry.getKey()) == null){
                continue;
            }
            PortStatus portStatus = new PortStatus();

            portStatus.setSwitchId(s.getId());
            portStatus.setPortIndex((Integer)getData(Integer.class,portIndex.get(entry.getKey())));
            portStatus.setName((String)getData(String.class,entry.getValue()));
            portStatus.setInData((Long)getData(Long.class,portIn.get(entry.getKey())));
            portStatus.setOutData((Long)getData(Long.class,portOut.get(entry.getKey())));
            portStatus.setUpTime((String)getData(String.class,portUptime.get(entry.getKey())));
            portStatus.setDes((String)getData(String.class,portDesc.get(entry.getKey())));
            portStatus.setSpeed((Integer)getData(Integer.class,portSpeed.get(entry.getKey())));
            portStatus.setStatus((Integer)getData(Integer.class,portStatusG.get(entry.getKey())));
            portStatus.setTimeStamp(new Date());
            portStatus.setSwitchPort("s"+portStatus.getSwitchId()+"p"+portStatus.getPortIndex());
            String []portId = portStatus.getName().split("Ethernet[0-9]/[0-9]/");
            if(portId.length == 2 && portStatus.getName().split("bitEthernet[0-9]/[0-9]/").length != 2) {
                portStatus.setPortId(Integer.parseInt(portId[1]));
            }

            submitPortSpeed(portSpeeds,portSpeedHistories,portStatus);

            portStatuses.add(portStatus);
        }
        if(portSpeeds.size()>0){
            ret = ret && portSpeedService.updateList(portSpeeds) >0;
        }
        if(portSpeedHistories.size()>0) {
            ret = ret && portSpeedHistoryService.insertList(portSpeedHistories) > 0;
        }
        if(portStatuses.size()>0){
            ret = ret && portStatusService.updateList(portStatuses) > 0;
        }
        return ret;
    }

    private void submitPortSpeed(List<PortSpeed> portSpeeds,List<PortSpeedHistory> portSpeedHistories,PortStatus portStatus){
        PortStatus portStatusOld = portStatusConcurrentHashMap.get(portStatus.getSwitchPort());
        boolean npeCheck = portStatusOld != null && portStatusOld.getInData() != null
                && portStatus.getInData() != null && portStatusOld.getOutData() != null
                && portStatus.getOutData() != null && portStatus.getSpeed() != null
                && !"".equals(portStatusOld.getInData())
                && !"".equals(portStatusOld.getOutData())
                && !"".equals(portStatus.getInData())
                && !"".equals(portStatus.getOutData());
        if (npeCheck) {
            Long inOld = Long.valueOf(portStatusOld.getInData());
            Long inNow = Long.valueOf(portStatus.getInData());
            Long outOld = Long.valueOf(portStatusOld.getOutData());
            Long outNow = Long.valueOf(portStatus.getOutData());

            Long in = inNow - inOld;
            Long out = outNow - outOld;
            Long time = (portStatus.getTimeStamp().getTime() - portStatusOld.getTimeStamp().getTime()) / 1000;
            Long speedMax = time * (Long.valueOf(portStatus.getSpeed()) * 125000);

            boolean valid;
            valid = in <= speedMax;
            valid = valid && out <= speedMax;
            valid = valid && in >= 0;
            valid = valid && out >= 0;

            if (valid) {
                Long divide = time;
                Long inB = in / divide;
                Long outB = out / divide;

                PortSpeed portSpeedSubmit = new PortSpeed();
                portSpeedSubmit.setInSpeed(inB);
                portSpeedSubmit.setOutSpeed(outB);
                portSpeedSubmit.setSwitchId(portStatus.getSwitchId());
                portSpeedSubmit.setPortIndex(portStatus.getPortIndex());
                portSpeedSubmit.setSwitchPort("s"+portSpeedSubmit.getSwitchId()+"p"+portSpeedSubmit.getPortIndex());

                Integer up = 2;
                if(up.equals(portStatus.getStatus())){
                    portSpeedSubmit.setInSpeed(0L);
                    portSpeedSubmit.setOutSpeed(0L);
                }

                portSpeeds.add(portSpeedSubmit);
                PortSpeedHistory portSpeedHistory = new PortSpeedHistory();
                BeanUtils.copyProperties(portSpeedSubmit, portSpeedHistory);
                portSpeedHistories.add(portSpeedHistory);
                PortSpeedHistoryMongo portSpeedHistoryMongo = new PortSpeedHistoryMongo();
                BeanUtils.copyProperties(portSpeedHistory,portSpeedHistoryMongo);
                //mongoTemplate.save(portSpeedHistoryMongo);
            }
        }
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

    private Object getData(Class c,String data) {
        Object result;
        if(data != null){
            if(c == String.class){
                result = data;
            }
            else if(c == Long.class){
                result = Long.parseLong(data);
            }
            else{
                result = Integer.parseInt(data);
            }
        }
        else{
            if(c == String.class){
                result = "-1";
            }
            else if(c == Long.class){
                result = -1L;
            }
            else{
                result = -1;
            }
        }
        return result;
    }

    public void updatePortStatusMap(){
        List<PortStatus> portStatusOld = portStatusService.selectOld();
        portStatusConcurrentHashMap.clear();
        for(PortStatus portStatus:portStatusOld){
            portStatusConcurrentHashMap.put(portStatus.getSwitchPort(),portStatus);
        }
    }
}