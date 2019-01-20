package cn.albumenj.switchmonitor.service;

import java.util.List;
import cn.albumenj.switchmonitor.bean.SwitchesStatusHistory;
import cn.albumenj.switchmonitor.dto.DeviceHistoryDto;
import org.apache.ibatis.annotations.Param;

public interface SwitchesStatusHistoryService{

    int insert(SwitchesStatusHistory switchesStatusHistory);

    int insertList(List<SwitchesStatusHistory> switchesStatusHistory);

    List<SwitchesStatusHistory> select();

    int delete();

    List<DeviceHistoryDto> selectDevice(String ip);
}
