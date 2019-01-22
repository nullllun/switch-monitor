package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.PortStatus;
import cn.albumenj.switchmonitor.dto.DevicePortDto;

import java.util.List;

/**
 * 端口状态
 *
 * @author Albumen
 */
public interface PortStatusService{

    /**
     * 插入单条数据
     *
     * @param portStatus
     * @return
     */
    int insert(PortStatus portStatus);

    /**
     * 修改单条数据
     * 修改失败转插入
     *
     * @param portStatus
     * @return
     */
    int update(PortStatus portStatus);

    /**
     * 修改多条数据
     * 修改失败转插入
     *
     * @param portStatuses
     * @return
     */
    int updateList(List<PortStatus> portStatuses);

    /**
     * 删除数据
     *
     * @param portStatus
     * @return
     */
    int delete(PortStatus portStatus);

    /**
     * 通过交换机获取数据
     *
     * @param portStatus
     * @return
     */
    List<PortStatus> selectBySwitch(PortStatus portStatus);

    /**
     * 通过端口获取数据
     *
     * @param portStatus
     * @return
     */
    PortStatus selectByPort(PortStatus portStatus);

    /**
     * 通过 IP 获取数据
     *
     * @param ip
     * @return
     */
    DevicePortDto selectByIP(String ip);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<PortStatus> select();

    /**
     * 批量修改 VLAN
     */
    void updateVlan();

    /**
     * 通过 IP和端口名获取端口
     *
     * @param ip
     * @param port
     * @return
     */
    String fetchPort(String ip, String port);
}
