package cn.albumenj.switchmonitor.service;

import java.util.List;
import cn.albumenj.switchmonitor.bean.PortSpeed;
import org.apache.ibatis.annotations.Param;

public interface PortSpeedService{

    int insert(PortSpeed portSpeed);

    int insertList(List<PortSpeed> portSpeeds);

    int update(PortSpeed portSpeed);

    int updateList(List<PortSpeed> portSpeeds);

    List<PortSpeed> selectBySwitch(PortSpeed portSpeed);
}
