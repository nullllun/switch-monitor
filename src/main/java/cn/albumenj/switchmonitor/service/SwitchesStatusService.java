package cn.albumenj.switchmonitor.service;

import java.util.List;
import cn.albumenj.switchmonitor.bean.SwitchesStatus;
import cn.albumenj.switchmonitor.constant.StatusConst;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;
import cn.albumenj.switchmonitor.dto.SwitchesStatusDto;
import org.apache.ibatis.annotations.Param;

public interface SwitchesStatusService{

    int insert(SwitchesStatus switchesStatus);

    int update(SwitchesStatus switchesStatus);

    int updateList(List<SwitchesStatus> switchesStatuses);

    int delete(SwitchesStatus switchesStatus);

    List<SwitchesStatus> select(SwitchesStatus switchesStatus);

    List<SwitchesStatus> selectBySwitch(SwitchesStatus switchesStatus);

    BriefStatusDto fetchBriefDetail(Integer mode);

    List<SwitchesStatusDto> selectByBuilding(String building);

}
