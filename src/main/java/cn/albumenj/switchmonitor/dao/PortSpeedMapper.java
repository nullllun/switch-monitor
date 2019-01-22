package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.PortSpeed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface PortSpeedMapper {
    /**
     * 插入单条数据
     *
     * @param portSpeed
     * @return
     */
    int insert(@Param("portSpeed") PortSpeed portSpeed);

    /**
     * 插入多条数据
     *
     * @param portSpeeds
     * @return
     */
    int insertList(@Param("portSpeeds") List<PortSpeed> portSpeeds);

    /**
     * 修改单条数据
     *
     * @param portSpeed
     * @return
     */
    int update(@Param("portSpeed") PortSpeed portSpeed);

    /**
     * 修改多条数据
     *
     * @param portSpeed
     * @return
     */
    int updateList(@Param("portSpeeds") List<PortSpeed> portSpeed);

    /**
     * 通过交换机获取数据
     *
     * @param portSpeed
     * @return
     */
    List<PortSpeed> selectBySwitch(@Param("portSpeed") PortSpeed portSpeed);

    /**
     * 获取当前所有数据
     *
     * @return
     */
    List<PortSpeed> select();
}
