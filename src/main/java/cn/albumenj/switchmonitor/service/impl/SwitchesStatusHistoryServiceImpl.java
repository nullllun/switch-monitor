package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.SwitchesStatusHistory;
import cn.albumenj.switchmonitor.dao.SwitchesStatusHistoryMapper;
import cn.albumenj.switchmonitor.dto.DeviceHistoryDto;
import cn.albumenj.switchmonitor.service.SwitchesStatusHistoryService;
import cn.albumenj.switchmonitor.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class SwitchesStatusHistoryServiceImpl implements SwitchesStatusHistoryService {
    @Value("${history.switch-saveTime}")
    Integer switchSaveTime;

    @Resource
    private SwitchesStatusHistoryMapper switchesStatusHistoryMapper;

    @Override
    public int insert(SwitchesStatusHistory switchesStatusHistory) {
        return switchesStatusHistoryMapper.insert(switchesStatusHistory);
    }

    @Override
    public int insertList(List<SwitchesStatusHistory> switchesStatusHistory) {
        return switchesStatusHistoryMapper.insertList(switchesStatusHistory);
    }

    @Override
    public List<SwitchesStatusHistory> select() {
        return switchesStatusHistoryMapper.select();
    }

    @Override
    public int delete() {
        SwitchesStatusHistory switchesStatusHistory = new SwitchesStatusHistory();
        switchesStatusHistory.setTimeStamp(DateUtil.beforeNow(switchSaveTime));
        return switchesStatusHistoryMapper.delete(switchesStatusHistory);
    }

    @Override
    public List<DeviceHistoryDto> selectDevice(String ip) {
        List<SwitchesStatusHistory> switchesStatusHistories = switchesStatusHistoryMapper.selectBySwitch(ip);
        List<DeviceHistoryDto> deviceHistoryDtos = new LinkedList<>();
        for (SwitchesStatusHistory switchesStatusHistory : switchesStatusHistories) {
            if (!(switchesStatusHistory.getCpuLoad() == -1
                    || switchesStatusHistory.getMemoryUsed() == -1
                    || switchesStatusHistory.getTemp() == -1
                    || switchesStatusHistory.getTimeStamp().getTime() == -1)) {
                DeviceHistoryDto deviceHistoryDto = new DeviceHistoryDto();
                deviceHistoryDto.setCpu(switchesStatusHistory.getCpuLoad());
                deviceHistoryDto.setMem(switchesStatusHistory.getMemoryUsed());
                deviceHistoryDto.setTemp(switchesStatusHistory.getTemp());
                deviceHistoryDto.setTimestamp(switchesStatusHistory.getTimeStamp().getTime());
                deviceHistoryDtos.add(deviceHistoryDto);
            }
        }
        return deviceHistoryDtos;
    }
}
