package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.PortStatus;
import cn.albumenj.switchmonitor.dto.PortStatusDto;
import cn.albumenj.switchmonitor.dto.VlanDto;
import cn.albumenj.switchmonitor.dto.VlanSearchDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface PortStatusMapper {
    /**
     * 插入单条数据
     *
     * @param portStatus
     * @return
     */
    int insert(@Param("portStatus") PortStatus portStatus);

    /**
     * 插入多条数据
     *
     * @param portStatuss
     * @return
     */
    int insertList(@Param("portStatuss") List<PortStatus> portStatuss);

    /**
     * 修改单条数据
     *
     * @param portStatus
     * @return
     */
    int update(@Param("portStatus") PortStatus portStatus);

    /**
     * 修改 VLAN 记录
     *
     * @param portStatus
     * @return
     */
    int updateVlan(@Param("portStatus") PortStatus portStatus);

    /**
     * 修改多条数据
     *
     * @param portStatuses
     * @return
     */
    int updateList(@Param("portStatuses") List<PortStatus> portStatuses);

    /**
     * 删除过期数据
     *
     * @param portStatus
     * @return
     */
    int delete(@Param("portStatus") PortStatus portStatus);

    /**
     * 通过交换机获取数据
     *
     * @param portStatus
     * @return
     */
    List<PortStatus> selectBySwitch(@Param("portStatus") PortStatus portStatus);

    /**
     * 通过端口获取数据
     *
     * @param portStatus
     * @return
     */
    PortStatus selectByPort(@Param("portStatus") PortStatus portStatus);

    /**
     * 通过 IP 联查获取数据
     *
     * @param ip
     * @return
     */
    List<PortStatusDto> selectByIP(@Param("ip") String ip);

    /**
     * 获取所有数据
     *
     * @return
     */
    List<PortStatus> select();

    /**
     * 获取 VLAN 原始数据
     *
     * @return
     */
    List<VlanDto> selectVlanOrigin();

    /**
     * 通过 IP 和端口名字联查
     *
     * @param ip
     * @param port
     * @return
     */
    String fetchPort(@Param("ip") String ip, @Param("port") String port);

    /**
     * 通过 VLAN 查找
     *
     * @param portStatus
     * @return
     */
    VlanSearchDto selectVlan(@Param("portStatus") PortStatus portStatus);
}
