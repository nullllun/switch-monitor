package cn.albumenj.switchmonitor.service;

import java.util.List;
import cn.albumenj.switchmonitor.bean.PortStatus;
import cn.albumenj.switchmonitor.dto.DevicePortDto;
import cn.albumenj.switchmonitor.dto.PortStatusDto;
import org.apache.ibatis.annotations.Param;

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
}
