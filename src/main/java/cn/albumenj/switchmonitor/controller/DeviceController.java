package cn.albumenj.switchmonitor.controller;

import cn.albumenj.switchmonitor.dto.DeviceHistoryDto;
import cn.albumenj.switchmonitor.dto.DevicePortDto;
import cn.albumenj.switchmonitor.service.PortStatusService;
import cn.albumenj.switchmonitor.service.SwitchesStatusHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 设备
 *
 * @author Albumen
 */
@RestController
public class DeviceController {
    @Autowired
    PortStatusService portStatusService;
    @Autowired
    SwitchesStatusHistoryService statusHistoryService;

    @RequestMapping("/api/devices/{ip}")
    public DevicePortDto device(@PathVariable("ip") String ip){
        return portStatusService.selectByIP(ip);
    }

    @RequestMapping("/api/history/{ip}")
    public List<DeviceHistoryDto> history(@PathVariable("ip") String ip){
        return statusHistoryService.selectDevice(ip);
    }
}
