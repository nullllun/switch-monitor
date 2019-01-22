package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.PortStatus;
import cn.albumenj.switchmonitor.dto.DevicePortDto;

import java.util.List;

public interface PortStatusService{

    int insert(PortStatus portStatus);

    int update(PortStatus portStatus);

    int updateList(List<PortStatus> portStatuses);

    int delete(PortStatus portStatus);

    List<PortStatus> selectBySwitch(PortStatus portStatus);

    PortStatus selectByPort(PortStatus portStatus);

    DevicePortDto selectByIP(String ip);

    List<PortStatus> selectOld();

    void updateVlan();

    String fetchPort(String ip, String port);
}
