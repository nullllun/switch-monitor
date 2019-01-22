package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.SwitchesReachableHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface SwitchesReachableHistoryMapper {
    /**
     * 插入单条数据
     *
     * @param switchesReachableHistory
     * @return
     */
    int insert(@Param("switchesReachableHistory") SwitchesReachableHistory switchesReachableHistory);

    /**
     * 删除过期数据
     *
     * @param switchesReachableHistory
     * @return
     */
    int delete(@Param("switchesReachableHistory") SwitchesReachableHistory switchesReachableHistory);

    /**
     * 插入多条数据
     *
     * @param switchesReachableHistorys
     * @return
     */
    int insertList(@Param("switchesReachableHistorys") List<SwitchesReachableHistory> switchesReachableHistorys);

    /**
     * 修改单条数据
     *
     * @param switchesReachableHistory
     * @return
     */
    int update(@Param("switchesReachableHistory") SwitchesReachableHistory switchesReachableHistory);

    /**
     * 通过交换机获取数据
     *
     * @param switchesReachableHistory
     * @return
     */
    List<SwitchesReachableHistory> selectBySwitch(@Param("switchesReachableHistory") SwitchesReachableHistory switchesReachableHistory);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<SwitchesReachableHistory> select();
}
