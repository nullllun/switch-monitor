package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.bean.SwitchesStatus;
import cn.albumenj.switchmonitor.dto.SwitchesDetailDto;
import cn.albumenj.switchmonitor.dto.SwitchesStatusDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Albumen
 */
@Mapper
public interface SwitchesStatusMapper {
    /**
     * 插入单条数据
     *
     * @param switchesStatus
     * @return
     */
    int insert(@Param("switchesStatus") SwitchesStatus switchesStatus);

    /**
     * 插入多条数据
     *
     * @param switchesStatuss
     * @return
     */
    int insertList(@Param("switchesStatuss") List<SwitchesStatus> switchesStatuss);

    /**
     * 修改单条数据
     *
     * @param switchesStatus
     * @return
     */
    int update(@Param("switchesStatus") SwitchesStatus switchesStatus);

    /**
     * 修改多条数据
     *
     * @param switchesStatuses
     * @return
     */
    int updateList(@Param("switchesStatuses") List<SwitchesStatus> switchesStatuses);

    /**
     * 删除数据
     *
     * @param switchesStatus
     * @return
     */
    int delete(@Param("switchesStatus") SwitchesStatus switchesStatus);

    /**
     * 获取当前所有数据
     *
     * @return
     */
    List<SwitchesStatus> select();

    /**
     * 通过交换机获取数据
     *
     * @param switchesStatus
     * @return
     */
    List<SwitchesStatus> selectBySwitch(@Param("switchesStatus") SwitchesStatus switchesStatus);

    /**
     * 获取当前数据
     * 对select内容进行筛选
     *
     * @param switchesStatus 非空值为所获取列
     * @return
     */
    List<SwitchesStatus> selectSome(@Param("switchesStatus") SwitchesStatus switchesStatus);

    /**
     * 获取交换机所有状态数据
     * 多表联查
     *
     * @return
     */
    List<SwitchesDetailDto> selectDetail();

    /**
     * 获取楼栋交换机数据
     *
     * @param switchesList
     * @return
     */
    List<SwitchesStatusDto> selectByBuilding(@Param("switchesList") SwitchesList switchesList);
}
