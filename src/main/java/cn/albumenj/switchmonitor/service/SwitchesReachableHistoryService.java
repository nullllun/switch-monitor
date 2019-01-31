package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.SwitchesReachableHistory;

import java.util.List;

/**
 * 交换机在线转态（历史）
 *
 * @author Albumen
 */
public interface SwitchesReachableHistoryService {

    /**
     * 插入单条数据
     *
     * @param switchesReachableHistory
     * @return
     */
    int insert(SwitchesReachableHistory switchesReachableHistory);

    /**
     * 删除过期数据
     *
     * @return
     */
    int delete();

    /**
     * 插入多条数据
     * 插入转单条插入
     *
     * @param switchesReachableHistorys
     * @return
     */
    int insertList(List<SwitchesReachableHistory> switchesReachableHistorys);

    /**
     * 获取历史数据
     *
     * @return
     */
    List<SwitchesReachableHistory> select();

    /**
     * 通过交换机获取数据
     *
     * @param switchesReachableHistory
     * @return
     */
    List<SwitchesReachableHistory> selectBySwitch(SwitchesReachableHistory switchesReachableHistory);
}
