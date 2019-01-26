package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface SwitchesListMapper {
    /**
     * 插入单条数据
     *
     * @param switchesList
     * @return
     */
    int insert(@Param("switchesList") SwitchesList switchesList);

    /**
     * 插入多条数据
     *
     * @param switchesLists
     * @return
     */
    int insertList(@Param("switchesLists") List<SwitchesList> switchesLists);

    /**
     * 修改单条数据
     *
     * @param switchesList
     * @return
     */
    int update(@Param("switchesList") SwitchesList switchesList);

    /**
     * 获取数据
     * 可自定义查询条件
     * 通过传入非空判断
     *
     * @param switchesList
     * @return
     */
    List<SwitchesList> select(@Param("switchesList") SwitchesList switchesList);

    /**
     * 获取数据
     * 可自定义查询条件
     * 通过传入非空判断
     * 设备在线
     *
     * @param switchesList
     * @return
     */
    List<SwitchesList> selectOnline(@Param("switchesList") SwitchesList switchesList);

    /**
     * 删除数据
     *
     * @param switchesList
     * @return
     */
    int delete(@Param("switchesList") SwitchesList switchesList);

    /**
     * 通过楼栋查询
     *
     * @return
     */
    List<String> selectBuilding();
}
