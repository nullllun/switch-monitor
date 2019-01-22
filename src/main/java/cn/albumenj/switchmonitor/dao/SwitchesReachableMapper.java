package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.SwitchesReachable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface SwitchesReachableMapper {
    /**
     * 插入单条数据
     *
     * @param switchesReachable
     * @return
     */
    int insert(@Param("switchesReachable") SwitchesReachable switchesReachable);

    /**
     * 插入多条数据
     *
     * @param switchesReachables
     * @return
     */
    int insertList(@Param("switchesReachables") List<SwitchesReachable> switchesReachables);

    /**
     * 修改单条数据
     *
     * @param switchesReachable
     * @return
     */
    int update(@Param("switchesReachable") SwitchesReachable switchesReachable);

    /**
     * 修改多条数据
     *
     * @param switchesReachables
     * @return
     */
    int updateList(@Param("switchesReachables") List<SwitchesReachable> switchesReachables);

    /**
     * 获取当前所有数据
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
    SwitchesReachable selectBySwitch(@Param("switchesReachable") SwitchesReachable switchesReachable);
}
