package cn.albumenj.switchmonitor.controller;

import cn.albumenj.switchmonitor.constant.PageCodeEnum;
import cn.albumenj.switchmonitor.dto.PageCodeDto;
import cn.albumenj.switchmonitor.schedule.SwitchesUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设置
 *
 * @author Albumen
 */
@RestController
public class SettingController {
    @Autowired
    SwitchesUpdate switchesUpdate;

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping("/api/reboot_scan_process")
    public String rebootScan(){
        switchesUpdate.execute();
        return "重启成功";
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @RequestMapping("/api/setting/permission")
    public PageCodeDto test() {
        return new PageCodeDto(PageCodeEnum.PERMISSION_ACCEPT);
    }
}
