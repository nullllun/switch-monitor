package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.SwitchesStatusHistory;
import cn.albumenj.switchmonitor.dto.DeviceHistoryDto;

import java.util.List;

/**
 * 交换机状态（历史）
 *
 * @author Albumen
 */
public interface SwitchesStatusHistoryService {

    /**
     * 插入单条数据
     *
     * @param switchesStatusHistory
     * @return
     */
    int insert(SwitchesStatusHistory switchesStatusHistory);

    /**
     * 插入多条数据
     * 插入失败转单条插入
     *
     * @param switchesStatusHistory
     * @return
     */
    int insertList(List<SwitchesStatusHistory> switchesStatusHistory);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<SwitchesStatusHistory> select();

    /**
     * 删除过期数据
     *
     * @return
     */
    int delete();

    /**
     * 通过 IP 获取数据
     *
     * @param ip
     * @return
     */
    List<DeviceHistoryDto> selectDevice(String ip);
}
