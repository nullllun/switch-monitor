package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.PortStatus;
import cn.albumenj.switchmonitor.dao.PortStatusMapper;
import cn.albumenj.switchmonitor.dto.DevicePortDto;
import cn.albumenj.switchmonitor.dto.PortStatusDto;
import cn.albumenj.switchmonitor.dto.VlanDto;
import cn.albumenj.switchmonitor.service.PortStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Albumen
 */
@Service
public class PortStatusServiceImpl implements PortStatusService{
    private static List<PortStatus> portStatusRest = new LinkedList<>();

    @Autowired
    private PortStatusMapper portStatusMapper;

    @Override
    public int insert(PortStatus portStatus){
        return portStatusMapper.insert(portStatus);
    }

    @Override
    public int update(PortStatus portStatus){
        if(portStatusMapper.update(portStatus)==0){
            return portStatusMapper.insert(portStatus);
        } else{
            return 1;
        }
    }

    @Override
    public int updateList(List<PortStatus> portStatuses) {
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
        portStatus.setSwitchPort("s"+portStatus.getSwitchId()+"p"+portStatus.getPortIndex());
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

        for(PortStatusDto portStatusDto:portStatusDtos){
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
        return devicePortDto;
    }

    @Override
    public List<PortStatus> select() {
        return portStatusMapper.select();
    }


    @Override
    public void updateVlan() {
        List<VlanDto> vlanDtoList = portStatusMapper.selectVlanOrigin();
        int c = 100;
        for(VlanDto vlanDto:vlanDtoList){
            PortStatus portStatus = new PortStatus();
            portStatus.setId(vlanDto.getId());
            String []ips = vlanDto.getIp().split("\\.");
            if("1".equals(ips[3]) && vlanDto.getPortId() == 1){
                c = 100;
            }
            if(vlanDto.getPortId() == 1){
                c++;
            }
            portStatus.setCvlan(c++);
            portStatus.setPvlan(Integer.parseInt(ips[2]));
            portStatusMapper.updateVlan(portStatus);
        }
    }

    @Override
    public String fetchPort(String ip, String port) {
        return portStatusMapper.fetchPort(ip, port);
    }
}
