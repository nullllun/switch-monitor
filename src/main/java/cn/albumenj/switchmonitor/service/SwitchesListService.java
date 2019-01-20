package cn.albumenj.switchmonitor.service;

import java.util.List;
import cn.albumenj.switchmonitor.bean.SwitchesList;
import org.apache.ibatis.annotations.Param;

public interface SwitchesListService{

    int insert(SwitchesList switchesList);

    int insertList(List<SwitchesList> switchesLists);

    int update(SwitchesList switchesList);

    List<SwitchesList> select(SwitchesList switchesList);

    int delete(SwitchesList switchesList);

    List<String> selectBuilding();
}
