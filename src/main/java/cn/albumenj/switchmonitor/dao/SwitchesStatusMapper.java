package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import cn.albumenj.switchmonitor.bean.SwitchesStatus;
import cn.albumenj.switchmonitor.dto.SwitchesDetailDto;
import cn.albumenj.switchmonitor.dto.SwitchesStatusDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SwitchesStatusMapper {
    int insert(@Param("switchesStatus") SwitchesStatus switchesStatus);

    int insertList(@Param("switchesStatuss") List<SwitchesStatus> switchesStatuss);

    int update(@Param("switchesStatus") SwitchesStatus switchesStatus);

    int updateList(@Param("switchesStatuses") List<SwitchesStatus> switchesStatuses);

    int delete(@Param("switchesStatus") SwitchesStatus switchesStatus);

    List<SwitchesStatus> select(@Param("switchesStatus") SwitchesStatus switchesStatus);

    List<SwitchesStatus> selectBySwitch(@Param("switchesStatus") SwitchesStatus switchesStatus);

    List<SwitchesStatus> selectSome(@Param("switchesStatus") SwitchesStatus switchesStatus);

    List<SwitchesDetailDto> selectDetail();

    List<SwitchesStatusDto> selectByBuilding(@Param("switchesList") SwitchesList switchesList);
}
