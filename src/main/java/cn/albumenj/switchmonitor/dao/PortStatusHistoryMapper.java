package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.PortStatusHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortStatusHistoryMapper {
    int insert(@Param("portStatusHistory") PortStatusHistory portStatusHistory);

    int insertList(@Param("portStatusHistorys") List<PortStatusHistory> portStatusHistorys);

    int delete(@Param("portStatusHistory") PortStatusHistory portStatusHistory);

    List<PortStatusHistory> selectBySwitch(@Param("portStatusHistory") PortStatusHistory portStatusHistory);

    List<PortStatusHistory> selectByPort(@Param("portStatusHistory") PortStatusHistory portStatusHistory);

}
