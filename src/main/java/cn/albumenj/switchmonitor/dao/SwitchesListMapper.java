package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.SwitchesList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SwitchesListMapper {
    int insert(@Param("switchesList") SwitchesList switchesList);

    int insertList(@Param("switchesLists") List<SwitchesList> switchesLists);

    int update(@Param("switchesList") SwitchesList switchesList);

    List<SwitchesList> select(@Param("switchesList") SwitchesList switchesList);

    int delete(@Param("switchesList") SwitchesList switchesList);

    List<String> selectBuilding();
}
