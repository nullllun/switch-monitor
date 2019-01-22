package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.PortStatusHistory;

import java.util.List;

/**
 * 端口状态（历史）
 *
 * @author Albumen
 */
public interface PortStatusHistoryService{

    /**
     * 插入单条数据
     *
     * @param portStatusHistory
     * @return
     */
    int insert(PortStatusHistory portStatusHistory);

    /**
     * 删除过期数据
     *
     * @return
     */
    int delete();

    /**
     * 通过交换机获取数据
     *
     * @param portStatusHistory
     * @return
     */
    List<PortStatusHistory> selectBySwitch(PortStatusHistory portStatusHistory);

    /**
     * 通过端口获取数据
     *
     * @param portStatusHistory
     * @return
     */
    List<PortStatusHistory> selectByPort(PortStatusHistory portStatusHistory);

}
