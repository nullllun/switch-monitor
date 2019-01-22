package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.PortSpeedHistoryBlank;

import java.util.List;

public interface PortSpeedHistoryBlankService {

    int insert(PortSpeedHistoryBlank portSpeedHistoryBlank);

    int update(PortSpeedHistoryBlank portSpeedHistoryBlank);

    int insertList(List<PortSpeedHistoryBlank> portSpeedHistoryBlanks);

    int updateList(List<PortSpeedHistoryBlank> portSpeedHistoryBlanks);

    List<PortSpeedHistoryBlank> selectByPort(String switchPort);

    List<PortSpeedHistoryBlank> selectOld();

    int delete();
}
