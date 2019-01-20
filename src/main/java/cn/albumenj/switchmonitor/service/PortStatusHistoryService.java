package cn.albumenj.switchmonitor.service;

import java.util.List;
import cn.albumenj.switchmonitor.bean.PortStatusHistory;

public interface PortStatusHistoryService{

    int insert(PortStatusHistory portStatusHistory);

    int delete();

    List<PortStatusHistory> selectBySwitch(PortStatusHistory portStatusHistory);

    List<PortStatusHistory> selectByPort(PortStatusHistory portStatusHistory);

}
