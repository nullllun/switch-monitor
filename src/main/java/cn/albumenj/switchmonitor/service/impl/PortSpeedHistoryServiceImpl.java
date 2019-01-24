package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.PortSpeedHistory;
import cn.albumenj.switchmonitor.bean.PortSpeedHistoryBlank;
import cn.albumenj.switchmonitor.dao.PortSpeedHistoryMapper;
import cn.albumenj.switchmonitor.dto.PortFlowDto;
import cn.albumenj.switchmonitor.dto.PortFlowOriginDto;
import cn.albumenj.switchmonitor.service.PortSpeedHistoryBlankService;
import cn.albumenj.switchmonitor.service.PortSpeedHistoryService;
import cn.albumenj.switchmonitor.service.PortStatusService;
import cn.albumenj.switchmonitor.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Albumen
 */
@Service
public class PortSpeedHistoryServiceImpl implements PortSpeedHistoryService {
    private static List<PortSpeedHistory> portSpeedHistoryRest = new LinkedList<>();

    @Autowired
    PortStatusService portStatusService;

    @Autowired
    PortSpeedHistoryBlankService portSpeedHistoryBlankService;

    @Value("${history.port-speed-saveTime}")
    Integer portSpeedSaveTime;

    @Value("${sync.port}")
    Integer portTime;

    @Autowired
    private PortSpeedHistoryMapper portSpeedHistoryMapper;

    @Override
    public int insert(PortSpeedHistory portSpeedHistory) {
        return portSpeedHistoryMapper.insert(portSpeedHistory);
    }

    @Override
    public int delete() {
        PortSpeedHistory portSpeedHistory = new PortSpeedHistory();
        portSpeedHistory.setTimeStamp(DateUtil.beforeNow(portSpeedSaveTime));
        return portSpeedHistoryMapper.delete(portSpeedHistory);
    }

    @Override
    public int insertList(List<PortSpeedHistory> portSpeedHistorys) {
        if (portSpeedHistorys.size() == 0) {
            return 1;
        }
        int ret = portSpeedHistoryMapper.insertList(portSpeedHistorys);
        if (ret != portSpeedHistorys.size()) {
            ret = 0;
            for (PortSpeedHistory portSpeedHistory : portSpeedHistorys) {
                ret += portSpeedHistoryMapper.insert(portSpeedHistory);
            }
        }
        return ret;
    }

    @Override
    public List<PortFlowDto> selectFlow(String ip, String name) {
        String switchPort = portStatusService.fetchPort(ip, name);
        List<PortFlowOriginDto> portFlowOriginDtos = portSpeedHistoryMapper.select(switchPort);
        List<PortSpeedHistoryBlank> portSpeedHistoryBlanks = portSpeedHistoryBlankService.selectByPort(switchPort);
        List<PortFlowDto> portFlowDtos = new LinkedList<>();

        for (PortSpeedHistoryBlank portSpeedHistoryBlank : portSpeedHistoryBlanks) {
            if (portSpeedHistoryBlank.getTimeStart().getTime() < DateUtil.beforeNow(portSpeedSaveTime).getTime()) {
                portSpeedHistoryBlank.setTimeStart(DateUtil.beforeNow(portSpeedSaveTime));
            }
            for (Long i = portSpeedHistoryBlank.getTimeStart().getTime(); i <= portSpeedHistoryBlank.getTimeEnd().getTime(); i += portTime) {
                PortFlowDto portFlowDto = new PortFlowDto();
                portFlowDto.setIn(0);
                portFlowDto.setOut(0);
                portFlowDto.setTimestamp(i);
                portFlowDtos.add(portFlowDto);
            }
        }

        for (PortFlowOriginDto portFlowOriginDto : portFlowOriginDtos) {
            PortFlowDto portFlowDto = new PortFlowDto();
            portFlowDto.setIn(portFlowOriginDto.getIn());
            portFlowDto.setOut(portFlowOriginDto.getOut());
            portFlowDto.setTimestamp(portFlowOriginDto.getTimestamp().getTime());
            if (portFlowDto.getIn() < 0 || portFlowDto.getOut() < 0) {
                portFlowDto = null;
            } else {
                portFlowDtos.add(portFlowDto);
            }
        }
        return portFlowDtos;
    }
}
