package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.SwitchesStatusHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SwitchesStatusHistoryMapper {
    int insert(@Param("switchesStatusHistory") SwitchesStatusHistory switchesStatusHistory);

    int insertList(@Param("switchesStatusHistorys") List<SwitchesStatusHistory> switchesStatusHistorys);

    List<SwitchesStatusHistory> select();

    List<SwitchesStatusHistory> selectBySwitch(@Param("ip") String ip);

    int delete(@Param("switchesStatusHistory") SwitchesStatusHistory switchesStatusHistory);
}
