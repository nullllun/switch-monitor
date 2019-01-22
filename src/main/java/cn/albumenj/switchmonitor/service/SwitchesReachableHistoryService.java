package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.SwitchesReachableHistory;

import java.util.List;

public interface SwitchesReachableHistoryService{

    int insert(SwitchesReachableHistory switchesReachableHistory);

    int delete();

    int insertList(List<SwitchesReachableHistory> switchesReachableHistorys);

    List<SwitchesReachableHistory> select();

    List<SwitchesReachableHistory> selectBySwitch(SwitchesReachableHistory switchesReachableHistory);
}
