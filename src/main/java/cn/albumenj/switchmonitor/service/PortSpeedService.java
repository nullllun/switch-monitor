package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.PortSpeed;

import java.util.List;

public interface PortSpeedService{

    int insert(PortSpeed portSpeed);

    int insertList(List<PortSpeed> portSpeeds);

    int update(PortSpeed portSpeed);

    int updateList(List<PortSpeed> portSpeeds);

    List<PortSpeed> selectBySwitch(PortSpeed portSpeed);

    List<PortSpeed> selectOld();
}
