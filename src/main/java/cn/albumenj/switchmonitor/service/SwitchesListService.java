package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.SwitchesList;

import java.util.List;

/**
 * 交换机基本数据
 *
 * @author Albumen
 */
public interface SwitchesListService{

    /**
     * 插入单条数据
     *
     * @param switchesList
     * @return
     */
    int insert(SwitchesList switchesList);

    /**
     * 插入多条数据
     *
     * @param switchesLists
     * @return
     */
    int insertList(List<SwitchesList> switchesLists);

    /**
     * 修改单条数据
     *
     * @param switchesList
     * @return
     */
    int update(SwitchesList switchesList);

    /**
     * 获取数据
     * 可自定义查询条件
     * 通过传入非空判断
     *
     * @param switchesList
     * @return
     */
    List<SwitchesList> select(SwitchesList switchesList);

    /**
     * 获取数据
     * 可自定义查询条件
     * 通过传入非空判断
     * 设备在线
     *
     * @param switchesList
     * @return
     */
    List<SwitchesList> selectOnline(SwitchesList switchesList);

    /**
     * 删除数据
     *
     * @param switchesList
     * @return
     */
    int delete(SwitchesList switchesList);

    /**
     * 通过楼栋获取数据
     *
     * @return
     */
    List<String> selectBuilding();
}
