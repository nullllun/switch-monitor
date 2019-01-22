package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.SwitchesReachable;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;

import java.util.List;

public interface SwitchesReachableService{

    int insert(SwitchesReachable switchesReachable);

    int insertList(List<SwitchesReachable> switchesReachables);

    int update(SwitchesReachable switchesReachable);

    int updateList(List<SwitchesReachable> switchesReachables);

    List<SwitchesReachable> select();

    SwitchesReachable selectBySwitch(SwitchesReachable switchesReachable);

    BriefStatusDto fetchBrief();
}
