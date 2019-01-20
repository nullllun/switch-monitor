package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.dto.PortFlowOriginDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import cn.albumenj.switchmonitor.bean.PortSpeedHistory;

@Mapper
public interface PortSpeedHistoryMapper {
    int insert(@Param("portSpeedHistory") PortSpeedHistory portSpeedHistory);

    int insertList(@Param("portSpeedHistorys") List<PortSpeedHistory> portSpeedHistorys);

    int delete(@Param("portSpeedHistory") PortSpeedHistory portSpeedHistory);

    List<PortFlowOriginDto> selectFlow(@Param("ip") String ip, @Param("name") String name);
}
