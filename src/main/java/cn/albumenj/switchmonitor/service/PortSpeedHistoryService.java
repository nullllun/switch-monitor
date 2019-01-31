package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.PortSpeedHistory;
import cn.albumenj.switchmonitor.dto.PortFlowDto;

import java.util.List;

/**
 * 端口流量速率（历史，非空）
 *
 * @author Albumen
 */
public interface PortSpeedHistoryService {

    /**
     * 插入单条数据
     *
     * @param portSpeedHistory
     * @return
     */
    int insert(PortSpeedHistory portSpeedHistory);

    /**
     * 删除过期数据
     *
     * @return
     */
    int delete();

    /**
     * 插入多条数据
     * 错误转单条插入
     *
     * @param portSpeedHistorys
     * @return
     */
    int insertList(List<PortSpeedHistory> portSpeedHistorys);

    /**
     * 获取历史数据
     *
     * @param ip
     * @param name
     * @return
     */
    List<PortFlowDto> selectFlow(String ip, String name);
}
