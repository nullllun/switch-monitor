package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.SwitchesStatusHistory;
import cn.albumenj.switchmonitor.dao.SwitchesStatusHistoryMapper;
import cn.albumenj.switchmonitor.dto.DeviceHistoryDto;
import cn.albumenj.switchmonitor.service.SwitchesStatusHistoryService;
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
public class SwitchesStatusHistoryServiceImpl implements SwitchesStatusHistoryService {
    @Value("${history.switch-saveTime}")
    Integer switchSaveTime;

    @Autowired
    private SwitchesStatusHistoryMapper switchesStatusHistoryMapper;

    @Override
    public int insert(SwitchesStatusHistory switchesStatusHistory) {
        return switchesStatusHistoryMapper.insert(switchesStatusHistory);
    }

    @Override
    public int insertList(List<SwitchesStatusHistory> switchesStatusHistory) {
        int ret = switchesStatusHistoryMapper.insertList(switchesStatusHistory);
        if (ret != switchesStatusHistory.size()) {
            for (SwitchesStatusHistory statusHistory : switchesStatusHistory) {
                ret += switchesStatusHistoryMapper.insert(statusHistory);
            }
        }
        return ret;
    }

    @Override
    public List<SwitchesStatusHistory> select() {
        return switchesStatusHistoryMapper.select();
    }

    @Override
    public int delete() {
        SwitchesStatusHistory switchesStatusHistory = new SwitchesStatusHistory();
        switchesStatusHistory.setTimeStamp(DateUtil.beforeNowDate(switchSaveTime));
        return switchesStatusHistoryMapper.delete(switchesStatusHistory);
    }

    @Override
    public List<DeviceHistoryDto> selectDevice(String ip) {
        List<SwitchesStatusHistory> switchesStatusHistories = switchesStatusHistoryMapper.selectBySwitch(ip);
        List<DeviceHistoryDto> deviceHistoryDtos = new LinkedList<>();
        boolean isCpu = false;
        boolean isMem = false;
        boolean isTemp = false;
        for (SwitchesStatusHistory switchesStatusHistory : switchesStatusHistories) {
            if (switchesStatusHistory.getCpuLoad() != -1) {
                isCpu = true;
            }
            if (switchesStatusHistory.getMemoryUsed() != -1) {
                isMem = true;
            }
            if (switchesStatusHistory.getTemp() != -1) {
                isTemp = true;
            }
        }
        for (SwitchesStatusHistory switchesStatusHistory : switchesStatusHistories) {
            boolean ret = !((switchesStatusHistory.getCpuLoad() == -1 && isCpu)
                    || (switchesStatusHistory.getMemoryUsed() == -1 && isMem)
                    || (switchesStatusHistory.getTemp() == -1 && isTemp)
                    || switchesStatusHistory.getTimeStamp().getTime() == -1);
            if (ret) {
                DeviceHistoryDto deviceHistoryDto = new DeviceHistoryDto();
                deviceHistoryDto.setCpu(isCpu ? switchesStatusHistory.getCpuLoad() : 0);
                deviceHistoryDto.setMem(isMem ? switchesStatusHistory.getMemoryUsed() : 0);
                deviceHistoryDto.setTemp(isTemp ? switchesStatusHistory.getTemp() : 0);
                deviceHistoryDto.setTimestamp(switchesStatusHistory.getTimeStamp().getTime());
                deviceHistoryDtos.add(deviceHistoryDto);
            }
        }
        return deviceHistoryDtos;
    }
}
