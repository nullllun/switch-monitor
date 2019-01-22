package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.PortStatusHistory;

import java.util.List;

public interface PortStatusHistoryService{

    int insert(PortStatusHistory portStatusHistory);

    int delete();

    List<PortStatusHistory> selectBySwitch(PortStatusHistory portStatusHistory);

    List<PortStatusHistory> selectByPort(PortStatusHistory portStatusHistory);

}
