package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.PortStatusHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface PortStatusHistoryMapper {
    /**
     * 插入单条数据
     *
     * @param portStatusHistory
     * @return
     */
    int insert(@Param("portStatusHistory") PortStatusHistory portStatusHistory);

    /**
     * 插入多条数据
     *
     * @param portStatusHistorys
     * @return
     */
    int insertList(@Param("portStatusHistorys") List<PortStatusHistory> portStatusHistorys);

    /**
     * 删除过期数据
     *
     * @param portStatusHistory
     * @return
     */
    int delete(@Param("portStatusHistory") PortStatusHistory portStatusHistory);

    /**
     * 通过交换机获取数据
     *
     * @param portStatusHistory
     * @return
     */
    List<PortStatusHistory> selectBySwitch(@Param("portStatusHistory") PortStatusHistory portStatusHistory);

    /**
     * 通过端口获取数据
     *
     * @param portStatusHistory
     * @return
     */
    List<PortStatusHistory> selectByPort(@Param("portStatusHistory") PortStatusHistory portStatusHistory);

}
