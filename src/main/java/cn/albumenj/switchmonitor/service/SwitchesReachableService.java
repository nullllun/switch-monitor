package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.SwitchesReachable;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;

import java.util.List;

/**
 * 交换机在线状态
 *
 * @author Albumen
 */
public interface SwitchesReachableService{

    /**
     * 插入单条数据
     *
     * @param switchesReachable
     * @return
     */
    int insert(SwitchesReachable switchesReachable);

    /**
     * 插入多条数据
     *
     * @param switchesReachables
     * @return
     */
    int insertList(List<SwitchesReachable> switchesReachables);

    /**
     * 修改单条数据
     * 修改失败转插入
     *
     * @param switchesReachable
     * @return
     */
    int update(SwitchesReachable switchesReachable);

    /**
     * 修改多条数据
     * 修改失败转单条
     *
     * @param switchesReachables
     * @return
     */
    int updateList(List<SwitchesReachable> switchesReachables);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<SwitchesReachable> select();

    /**
     * 通过交换机获取数据
     *
     * @param switchesReachable
     * @return
     */
    SwitchesReachable selectBySwitch(SwitchesReachable switchesReachable);

    /**
     * 获取当前概况
     *
     * @return
     */
    BriefStatusDto fetchBrief();
}
