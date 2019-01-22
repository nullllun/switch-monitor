package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.SwitchesList;

import java.util.List;

public interface SwitchesListService{

    int insert(SwitchesList switchesList);

    int insertList(List<SwitchesList> switchesLists);

    int update(SwitchesList switchesList);

    List<SwitchesList> select(SwitchesList switchesList);

    int delete(SwitchesList switchesList);

    List<String> selectBuilding();
}
