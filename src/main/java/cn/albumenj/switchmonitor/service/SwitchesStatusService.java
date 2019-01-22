package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.SwitchesStatus;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;
import cn.albumenj.switchmonitor.dto.SwitchesStatusDto;

import java.util.List;

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
