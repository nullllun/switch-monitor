package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.PortSpeedHistoryBlank;

import java.util.List;

/**
 * 端口流量速率（历史，空）
 *
 * @author Albumen
 */
public interface PortSpeedHistoryBlankService {

    /**
     * 插入单条数据
     *
     * @param portSpeedHistoryBlank
     * @return
     */
    int insert(PortSpeedHistoryBlank portSpeedHistoryBlank);

    /**
     * 修改单条数据
     *
     * @param portSpeedHistoryBlank
     * @return
     */
    int update(PortSpeedHistoryBlank portSpeedHistoryBlank);

    /**
     * 插入多条数据
     *
     * @param portSpeedHistoryBlanks 不允许存在空值
     * @return
     */
    int insertList(List<PortSpeedHistoryBlank> portSpeedHistoryBlanks);

    /**
     * 修改多条数据
     * 批量修改失败则转单条
     * 不会转增加
     *
     * @param portSpeedHistoryBlanks
     * @return
     */
    int updateList(List<PortSpeedHistoryBlank> portSpeedHistoryBlanks);

    /**
     * 通过端口获取信息
     *
     * @param switchPort
     * @return
     */
    List<PortSpeedHistoryBlank> selectByPort(String switchPort);

    /**
     * 获取所有最新数据
     * 相对于间断点之前的
     *
     * @return
     */
    List<PortSpeedHistoryBlank> select();

    /**
     * 删除过期数据
     *
     * @return
     */
    int delete();
}
