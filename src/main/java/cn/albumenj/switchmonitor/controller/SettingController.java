package cn.albumenj.switchmonitor.controller;

import cn.albumenj.switchmonitor.bean.Log;
import cn.albumenj.switchmonitor.constant.LogConst;
import cn.albumenj.switchmonitor.constant.PageCodeEnum;
import cn.albumenj.switchmonitor.dto.PageCodeDto;
import cn.albumenj.switchmonitor.schedule.SwitchesCheckReach;
import cn.albumenj.switchmonitor.schedule.SwitchesUpdate;
import cn.albumenj.switchmonitor.service.LogService;
import cn.albumenj.switchmonitor.service.PortStatusService;
import cn.albumenj.switchmonitor.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 设置
 *
 * @author Albumen
 */
@RestController
public class SettingController {
    @Autowired
    SwitchesUpdate switchesUpdate;
    @Autowired
    PortStatusService portStatusService;
    @Autowired
    SwitchesCheckReach switchesCheckReach;
    @Autowired
    LogService logService;

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping("/api/reboot_scan_process")
    public String rebootScan(UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.WARN,
                LogConst.USER, authentication.getName(),
                "Restart Scan Process", IpUtil.getIpAddr(request));
        logService.insert(log);

        switchesUpdate.execute();
        return "重启成功";
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping("/api/setting/update_vlan")
    public String updateVlan(UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.WARN, LogConst.USER,
                authentication.getName(), "Update Device VLAN Information", IpUtil.getIpAddr(request));
        logService.insert(log);

        portStatusService.updateVlan();
        return "刷新成功";
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @RequestMapping("/api/setting/permission")
    public PageCodeDto test(UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.INFO, LogConst.USER,
                authentication.getName(), "Enter Setting", IpUtil.getIpAddr(request));
        logService.insert(log);

        return new PageCodeDto(PageCodeEnum.PERMISSION_ACCEPT);
    }


    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping("/api/setting/update_reach")
    public String updateReach(UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.WARN, LogConst.USER, authentication.getName(),
                "Update Device Reachable Information", IpUtil.getIpAddr(request));
        logService.insert(log);

        switchesCheckReach.submit();
        return "刷新成功";
    }

}
