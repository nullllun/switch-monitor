package cn.albumenj.switchmonitor.controller;

import cn.albumenj.switchmonitor.constant.StatusConst;
import cn.albumenj.switchmonitor.dto.BriefStatusDto;
import cn.albumenj.switchmonitor.service.SwitchesReachableService;
import cn.albumenj.switchmonitor.service.SwitchesStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页状态
 *
 * @author Albumen
 */
@RestController
public class HomeStatusController {
    @Autowired
    SwitchesStatusService switchesStatusService;
    @Autowired
    SwitchesReachableService switchesReachableService;

    @RequestMapping("/api/warnings")
    public BriefStatusDto warnings() {
        return switchesStatusService.fetchBriefDetail(StatusConst.WARNING);
    }

    @RequestMapping("/api/down_time")
    public BriefStatusDto downTime() {
        return switchesReachableService.fetchBrief();
    }

    @RequestMapping("/api/cpu_load")
    public BriefStatusDto cpuLoad() {
        return switchesStatusService.fetchBriefDetail(StatusConst.CPU_LOAD);
    }

    @RequestMapping("/api/mem_used")
    public BriefStatusDto memUsed() {
        return switchesStatusService.fetchBriefDetail(StatusConst.MEM_USED);
    }

    @RequestMapping("/api/temp")
    public BriefStatusDto temp() {
        return switchesStatusService.fetchBriefDetail(StatusConst.TEMP);
    }
}
