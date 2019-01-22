package cn.albumenj.switchmonitor.dao;

import cn.albumenj.switchmonitor.bean.PortSpeedHistoryBlank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PortSpeedHistoryBlankMapper {
    int insert(@Param("portSpeedHistoryBlank") PortSpeedHistoryBlank portSpeedHistoryBlank);

    int update(@Param("portSpeedHistoryBlank") PortSpeedHistoryBlank portSpeedHistoryBlank);

    int insertList(@Param("portSpeedHistoryBlanks") List<PortSpeedHistoryBlank> portSpeedHistoryBlanks);

    int updateList(@Param("portSpeedHistoryBlanks") List<PortSpeedHistoryBlank> portSpeedHistoryBlanks);

    int updateHistory(@Param("portSpeedHistoryBlanks") List<PortSpeedHistoryBlank> portSpeedHistoryBlanks);

    int deleteFirst(@Param("portSpeedHistoryBlank") PortSpeedHistoryBlank portSpeedHistoryBlank);

    List<PortSpeedHistoryBlank> selectByPort(@Param("portSpeedHistoryBlank") PortSpeedHistoryBlank portSpeedHistoryBlank);

    List<PortSpeedHistoryBlank> select();

    List<PortSpeedHistoryBlank> selectOld(@Param("portSpeedHistoryBlank") PortSpeedHistoryBlank portSpeedHistoryBlank);
}
