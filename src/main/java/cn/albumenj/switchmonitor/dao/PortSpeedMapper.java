package cn.albumenj.switchmonitor.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import cn.albumenj.switchmonitor.bean.PortSpeed;

@Mapper
public interface PortSpeedMapper {
    int insert(@Param("portSpeed") PortSpeed portSpeed);

    int insertList(@Param("portSpeeds") List<PortSpeed> portSpeeds);

    int update(@Param("portSpeed") PortSpeed portSpeed);

    int updateList(@Param("portSpeeds") List<PortSpeed> portSpeed);

    List<PortSpeed> selectBySwitch(@Param("portSpeed") PortSpeed portSpeed);
}