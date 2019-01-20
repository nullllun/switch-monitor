package cn.albumenj.switchmonitor.controller.miniprogram;

import cn.albumenj.switchmonitor.dto.PortFlowDto;
import cn.albumenj.switchmonitor.service.PortSpeedHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MpPortController {
    @Autowired
    PortSpeedHistoryService portSpeedHistoryService;

    @RequestMapping("/mpapi/flow_history/{ip}/{port}")
    public List<PortFlowDto> flowHistory(@PathVariable("ip") String ip, @PathVariable("port") String port){
        port = port.replaceAll("_","/");
        return portSpeedHistoryService.selectFlow(ip,port);
    }
}
