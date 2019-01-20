package cn.albumenj.switchmonitor.service;

import java.util.List;
import cn.albumenj.switchmonitor.bean.PortSpeedHistory;
import cn.albumenj.switchmonitor.dto.PortFlowDto;
import cn.albumenj.switchmonitor.dto.PortFlowOriginDto;

public interface PortSpeedHistoryService{

    int insert(PortSpeedHistory portSpeedHistory);

    int delete();

    int insertList(List<PortSpeedHistory> portSpeedHistorys);

    List<PortFlowDto> selectFlow(String ip, String name);
}
