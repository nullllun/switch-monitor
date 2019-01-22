package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.SwitchesReachableHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SwitchesReachableHistoryMapper {
    int insert(@Param("switchesReachableHistory") SwitchesReachableHistory switchesReachableHistory);

    int delete(@Param("switchesReachableHistory") SwitchesReachableHistory switchesReachableHistory);

    int insertList(@Param("switchesReachableHistorys") List<SwitchesReachableHistory> switchesReachableHistorys);

    int update(@Param("switchesReachableHistory") SwitchesReachableHistory switchesReachableHistory);

    List<SwitchesReachableHistory> selectBySwitch(@Param("switchesReachableHistory") SwitchesReachableHistory switchesReachableHistory);

    List<SwitchesReachableHistory> select();
}
