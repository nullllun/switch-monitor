package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.PortSpeed;

import java.util.List;

/**
 * 端口流量速率
 *
 * @author Albumen
 */
public interface PortSpeedService{

    /**
     * 插入单条数据
     *
     * @param portSpeed
     * @return
     */
    int insert(PortSpeed portSpeed);

    /**
     * 插入多条数据
     * 插入失败转单条插入
     *
     * @param portSpeeds
     * @return
     */
    int insertList(List<PortSpeed> portSpeeds);

    /**
     * 修改单条数据
     * 修改失败转插入
     *
     * @param portSpeed
     * @return
     */
    int update(PortSpeed portSpeed);

    /**
     * 修改多条数据
     * 修改失败转插入
     *
     * @param portSpeeds
     * @return
     */
    int updateList(List<PortSpeed> portSpeeds);

    /**
     * 通过交换机获取数据
     *
     * @param portSpeed
     * @return
     */
    List<PortSpeed> selectBySwitch(PortSpeed portSpeed);

    /**
     * 获取当前端口所有数据
     *
     * @return
     */
    List<PortSpeed> select();
}
