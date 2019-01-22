package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.SwitchesStatus;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;
import cn.albumenj.switchmonitor.dto.SwitchesStatusDto;

import java.util.List;

/**
 * 交换机状态
 *
 * @author Albumen
 */
public interface SwitchesStatusService{

    /**
     * 插入单条数据
     *
     * @param switchesStatus
     * @return
     */
    int insert(SwitchesStatus switchesStatus);

    /**
     * 修改单条数据
     * 修改失败转插入
     *
     * @param switchesStatus
     * @return
     */
    int update(SwitchesStatus switchesStatus);

    /**
     * 修改多条数据
     * 修改失败转单条修改
     *
     * @param switchesStatuses
     * @return
     */
    int updateList(List<SwitchesStatus> switchesStatuses);

    /**
     * 删除数据
     *
     * @param switchesStatus
     * @return
     */
    int delete(SwitchesStatus switchesStatus);

    /**
     * 获取数据
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
    List<SwitchesStatus> selectBySwitch(SwitchesStatus switchesStatus);

    /**
     * 获取交换机概况
     *
     * @param mode
     * @return
     */
    BriefStatusDto fetchBriefDetail(Integer mode);

    /**
     * 通过楼栋获取数据
     *
     * @param building
     * @return
     */
    List<SwitchesStatusDto> selectByBuilding(String building);

}
