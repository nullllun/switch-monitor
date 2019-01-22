package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.SwitchesStatusHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface SwitchesStatusHistoryMapper {
    /**
     * 插入单条数据
     *
     * @param switchesStatusHistory
     * @return
     */
    int insert(@Param("switchesStatusHistory") SwitchesStatusHistory switchesStatusHistory);

    /**
     * 插入多条数据
     *
     * @param switchesStatusHistorys
     * @return
     */
    int insertList(@Param("switchesStatusHistorys") List<SwitchesStatusHistory> switchesStatusHistorys);

    /**
     * 获取当前所有数据
     *
     * @return
     */
    List<SwitchesStatusHistory> select();

    /**
     * 通过交换机获取数据
     *
     * @param ip
     * @return
     */
    List<SwitchesStatusHistory> selectBySwitch(@Param("ip") String ip);

    /**
     * 删除过期数据
     *
     * @param switchesStatusHistory
     * @return
     */
    int delete(@Param("switchesStatusHistory") SwitchesStatusHistory switchesStatusHistory);
}
