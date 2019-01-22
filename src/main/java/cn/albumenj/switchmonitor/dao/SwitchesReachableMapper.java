package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.SwitchesReachable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SwitchesReachableMapper {
    int insert(@Param("switchesReachable") SwitchesReachable switchesReachable);

    int insertList(@Param("switchesReachables") List<SwitchesReachable> switchesReachables);

    int update(@Param("switchesReachable") SwitchesReachable switchesReachable);

    int updateList(@Param("switchesReachables") List<SwitchesReachable> switchesReachables);

    List<SwitchesReachable> select();

    SwitchesReachable selectBySwitch(@Param("switchesReachable") SwitchesReachable switchesReachable);
}
