package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.SwitchesStatusHistory;
import cn.albumenj.switchmonitor.dto.DeviceHistoryDto;

import java.util.List;

public interface SwitchesStatusHistoryService{

    int insert(SwitchesStatusHistory switchesStatusHistory);

    int insertList(List<SwitchesStatusHistory> switchesStatusHistory);

    List<SwitchesStatusHistory> select();

    int delete();

    List<DeviceHistoryDto> selectDevice(String ip);
}
