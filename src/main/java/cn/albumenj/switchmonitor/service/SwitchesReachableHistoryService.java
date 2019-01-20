package cn.albumenj.switchmonitor.service;

import java.util.List;
import cn.albumenj.switchmonitor.bean.SwitchesReachableHistory;
import cn.albumenj.switchmonitor.bean.SwitchesStatusHistory;
import org.apache.ibatis.annotations.Param;

public interface SwitchesReachableHistoryService{

    int insert(SwitchesReachableHistory switchesReachableHistory);

    int delete();

    int insertList(List<SwitchesReachableHistory> switchesReachableHistorys);

    List<SwitchesReachableHistory> select();

    List<SwitchesReachableHistory> selectBySwitch(SwitchesReachableHistory switchesReachableHistory);
}
