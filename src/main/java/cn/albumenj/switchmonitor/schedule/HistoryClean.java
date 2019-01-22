package cn.albumenj.switchmonitor.schedule;

import cn.albumenj.switchmonitor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoryClean {
    @Autowired
    SwitchesStatusHistoryService switchesStatusHistoryService;
    @Autowired
    PortSpeedHistoryBlankService portSpeedHistoryBlankService;
    @Autowired
    PortStatusHistoryService portStatusHistoryService;
    @Autowired
    PortSpeedHistoryService portSpeedHistoryService;
    @Autowired
    SwitchesReachableHistoryService switchesReachableHistoryService;

    public void clean() {
        switchesStatusHistoryService.delete();
        portStatusHistoryService.delete();
        portSpeedHistoryService.delete();
        switchesReachableHistoryService.delete();
        portSpeedHistoryBlankService.delete();
    }
}
