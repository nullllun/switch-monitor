package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.PortSpeedHistory;
import cn.albumenj.switchmonitor.dto.PortFlowOriginDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface PortSpeedHistoryMapper {
    /**
     * 插入单条记录
     *
     * @param portSpeedHistory
     * @return
     */
    int insert(@Param("portSpeedHistory") PortSpeedHistory portSpeedHistory);

    /**
     * 插入多条数据
     *
     * @param portSpeedHistorys
     * @return
     */
    int insertList(@Param("portSpeedHistorys") List<PortSpeedHistory> portSpeedHistorys);

    /**
     * 删除过期数据
     *
     * @param portSpeedHistory
     * @return
     */
    int delete(@Param("portSpeedHistory") PortSpeedHistory portSpeedHistory);

    /**
     * 获取端口数据
     *
     * @param switchPort
     * @return
     */
    List<PortFlowOriginDto> select(@Param("switchPort") String switchPort);
}
