package cn.albumenj.switchmonitor.controller.miniprogram;

import cn.albumenj.switchmonitor.constant.StatusConst;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;
import cn.albumenj.switchmonitor.service.SwitchesReachableService;
import cn.albumenj.switchmonitor.service.SwitchesStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MpHomeStatusController {
    @Autowired
    SwitchesStatusService switchesStatusService;
    @Autowired
    SwitchesReachableService switchesReachableService;

    @RequestMapping("/mpapi/warnings")
    public BriefStatusDto warnings(){
        return switchesStatusService.fetchBriefDetail(StatusConst.WARNING);
    }

    @RequestMapping("/mpapi/down_time")
    public BriefStatusDto downTime(){
        return switchesReachableService.fetchBrief();
    }

    @RequestMapping("/mpapi/cpu_load")
    public BriefStatusDto cpuLoad(){
        return switchesStatusService.fetchBriefDetail(StatusConst.CPU_LOAD);
    }

    @RequestMapping("/mpapi/mem_used")
    public BriefStatusDto memUsed(){
        return switchesStatusService.fetchBriefDetail(StatusConst.MEM_USED);
    }

    @RequestMapping("/mpapi/temp")
    public BriefStatusDto temp(){
        return switchesStatusService.fetchBriefDetail(StatusConst.TEMP);
    }
}
