package cn.albumenj.switchmonitor.controller.miniprogram;

import cn.albumenj.switchmonitor.schedule.SwitchesUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MpSettingController {
    @Autowired
    SwitchesUpdate switchesUpdate;

    @RequestMapping("/mpapi/reboot_scan_process")
    public String rebootScan(){
        switchesUpdate.execute();
        return "重启成功";
    }
}
