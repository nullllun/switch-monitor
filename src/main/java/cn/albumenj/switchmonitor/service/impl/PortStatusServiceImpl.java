package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.PortStatus;
import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.dao.PortStatusMapper;
import cn.albumenj.switchmonitor.dto.DevicePortDto;
import cn.albumenj.switchmonitor.dto.PortStatusDto;
import cn.albumenj.switchmonitor.service.PortStatusService;
import cn.albumenj.switchmonitor.service.SwitchesListService;
import cn.albumenj.switchmonitor.util.CustomThreadFactory;
import cn.albumenj.switchmonitor.util.OidList;
import cn.albumenj.switchmonitor.util.SnmpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Albumen
 */
@Service
public class PortStatusServiceImpl implements PortStatusService {
    private static List<PortStatus> portStatusRest = new LinkedList<>();

    @Autowired
    private PortStatusMapper portStatusMapper;

    @Autowired
    SwitchesListService switchesListService;

    @Autowired
    SnmpUtil snmpUtil;

    @Override
    public int insert(PortStatus portStatus) {
        return portStatusMapper.insert(portStatus);
    }

    @Override
    public int update(PortStatus portStatus) {
        if (portStatusMapper.update(portStatus) == 0) {
            return portStatusMapper.insert(portStatus);
        } else {
            return 1;
        }
    }

    @Override
    public int updateList(List<PortStatus> portStatuses) {
        if (portStatuses.size() == 0) {
            return 1;
        }
        int row = portStatusMapper.updateList(portStatuses);
        if (row != portStatuses.size()) {
            for (PortStatus portStatus : portStatuses) {
                row += update(portStatus);
            }
        }
        return row;
    }

    @Override
    public int delete(PortStatus portStatus) {
        portStatus.setSwitchPort("s" + portStatus.getSwitchId() + "p" + portStatus.getPortIndex());
        return portStatusMapper.delete(portStatus);
    }

    @Override
    public List<PortStatus> selectBySwitch(PortStatus portStatus) {
        return portStatusMapper.selectBySwitch(portStatus);
    }

    @Override
    public PortStatus selectByPort(PortStatus portStatus) {
        return portStatusMapper.selectByPort(portStatus);
    }

    @Override
    public DevicePortDto selectByIP(String ip) {
        List<PortStatusDto> portStatusDtos = portStatusMapper.selectByIP(ip);
        DevicePortDto devicePortDto = new DevicePortDto();
        List<String> ifDescr = new LinkedList<>();
        List<String> ifIn = new LinkedList<>();
        List<Integer> ifInSpeed = new LinkedList<>();
        List<String> ifIp = new LinkedList<>();
        List<String> ifName = new LinkedList<>();
        List<String> ifOut = new LinkedList<>();
        List<Integer> ifOutSpeed = new LinkedList<>();
        List<String> ifSpeed = new LinkedList<>();
        List<String> ifStatus = new LinkedList<>();
        List<String> uptime = new LinkedList<>();
        List<Integer> cvlan = new LinkedList<>();
        List<Integer> pvlan = new LinkedList<>();

        for (PortStatusDto portStatusDto : portStatusDtos) {
            ifDescr.add(portStatusDto.getDes());
            ifIn.add(portStatusDto.getInData().toString());
            ifInSpeed.add(Integer.parseInt(portStatusDto.getInSpeed().toString()));
            ifIp.add("");
            ifName.add(portStatusDto.getName());
            ifOut.add(portStatusDto.getOutData().toString());
            ifOutSpeed.add(Integer.parseInt(portStatusDto.getOutSpeed().toString()));
            ifSpeed.add(portStatusDto.getSpeed().toString());
            ifStatus.add(portStatusDto.getStatus());
            uptime.add(portStatusDto.getUpTime());
            cvlan.add(portStatusDto.getCvlan());
            pvlan.add(portStatusDto.getPvlan());
        }
        devicePortDto.setIfDescr(ifDescr);
        devicePortDto.setIfIn(ifIn);
        devicePortDto.setIfInSpeed(ifInSpeed);
        devicePortDto.setIfIp(ifIp);
        devicePortDto.setIfName(ifName);
        devicePortDto.setIfOut(ifOut);
        devicePortDto.setIfOutSpeed(ifOutSpeed);
        devicePortDto.setIfSpeed(ifSpeed);
        devicePortDto.setIfStatus(ifStatus);
        devicePortDto.setIfUptime(uptime);
        devicePortDto.setPvlan(pvlan);
        devicePortDto.setCvlan(cvlan);
        return devicePortDto;
    }

    @Override
    public List<PortStatus> select() {
        return portStatusMapper.select();
    }


    @Override
    public void updateVlan() {
        List<SwitchesList> switchesLists = switchesListService.select(new SwitchesList());
        ExecutorService executorService = new ThreadPoolExecutor(1000, 1000, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new CustomThreadFactory());

        for (SwitchesList s : switchesLists) {
            executorService.execute(() -> {
                OidList oidList = new OidList(s.getModel());
                PortStatus portStatus = new PortStatus();
                portStatus.setSwitchId(s.getId());
                List<PortStatus> portStatusList = portStatusMapper.selectBySwitch(portStatus);
                Map<Integer, String> pvid = snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getPVID());
                Map<Integer, String> pvidName = snmpUtil.walk(s.getIp(), s.getReadKey(), oidList.getVlanName());
                Map<String, String> vlan = new LinkedHashMap<>();
                Set<Map.Entry<Integer, String>> entries = pvid.entrySet();
                for (Map.Entry<Integer, String> entry : entries) {
                    vlan.put(stringEncoding(pvidName.get(entry.getKey())), entry.getValue());
                }

                for (PortStatus port : portStatusList) {
                    String[] ips = s.getIp().split("\\.");

                    String cvlan = vlan.get(port.getName());
                    if (cvlan == null) {
                        port.setCvlan(-1);
                    } else {
                        port.setCvlan(Integer.parseInt(cvlan));
                    }
                    port.setPvlan(Integer.parseInt(ips[2]));
                    portStatusMapper.updateVlan(port);
                }
            });
        }
        executorService.shutdown();
        try {//等待直到所有任务完成
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String fetchPort(String ip, String port) {
        return portStatusMapper.fetchPort(ip, port);
    }

    /**
     * S2700 VLAN对应端口名字获取结果转换
     * "45:74:68:65:72:6e:65:74:30:2f:30:2f:31:00"
     *
     * @param str
     * @return
     */
    private String stringEncoding(String str) {
        if (str == null) {
            return str;
        }
        String[] strings = str.split(":");
        if (strings.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : strings) {
                if (s.equals("00")) {
                    continue;
                }
                stringBuilder.append((char) Integer.parseInt(s, 16));
            }
            return stringBuilder.toString();
        } else {
            return str;
        }
    }
}
