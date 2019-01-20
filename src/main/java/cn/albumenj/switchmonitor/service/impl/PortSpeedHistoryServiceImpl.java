package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.PortStatusHistory;
import cn.albumenj.switchmonitor.dto.PortFlowDto;
import cn.albumenj.switchmonitor.dto.PortFlowOriginDto;
import cn.albumenj.switchmonitor.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import cn.albumenj.switchmonitor.bean.PortSpeedHistory;
import cn.albumenj.switchmonitor.dao.PortSpeedHistoryMapper;
import cn.albumenj.switchmonitor.service.PortSpeedHistoryService;

@Service
public class PortSpeedHistoryServiceImpl implements PortSpeedHistoryService{
    private static List<PortSpeedHistory> portSpeedHistoryRest = new LinkedList<>();

    @Value("${history.port-speed-saveTime}")
    Integer portSpeedSaveTime;

    @Resource
    private PortSpeedHistoryMapper portSpeedHistoryMapper;

    @Override
    public int insert(PortSpeedHistory portSpeedHistory){
        return portSpeedHistoryMapper.insert(portSpeedHistory);
    }

    @Override
    public int delete(){
        PortSpeedHistory portSpeedHistory = new PortSpeedHistory();
        portSpeedHistory.setTimeStamp(DateUtil.beforeNow(portSpeedSaveTime));
        return portSpeedHistoryMapper.delete(portSpeedHistory);
    }

    @Override
    public int insertList(List<PortSpeedHistory> portSpeedHistorys){
        return portSpeedHistoryMapper.insertList(portSpeedHistorys);
    }

    @Override
    public List<PortFlowDto> selectFlow(String ip, String name) {
        List<PortFlowOriginDto> portFlowOriginDtos = portSpeedHistoryMapper.selectFlow(ip,name);
        List<PortFlowDto> portFlowDtos = new LinkedList<>();
        for(PortFlowOriginDto portFlowOriginDto:portFlowOriginDtos){
            PortFlowDto portFlowDto = new PortFlowDto();
            portFlowDto.setIn(portFlowOriginDto.getIn());
            portFlowDto.setOut(portFlowOriginDto.getOut());
            portFlowDto.setTimestamp(portFlowOriginDto.getTimestamp().getTime());
            if(portFlowDto.getIn()<0||portFlowDto.getOut()<0){
                portFlowDto = null;
            }
            else {
                portFlowDtos.add(portFlowDto);
            }
        }
        return portFlowDtos;
    }
}
