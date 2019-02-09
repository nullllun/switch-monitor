package cn.albumenj.switchmonitor.controller;

import cn.albumenj.switchmonitor.bean.Log;
import cn.albumenj.switchmonitor.bean.WechatUser;
import cn.albumenj.switchmonitor.constant.LogConst;
import cn.albumenj.switchmonitor.constant.PageCodeEnum;
import cn.albumenj.switchmonitor.dto.PageCodeDto;
import cn.albumenj.switchmonitor.schedule.SwitchesCheckReach;
import cn.albumenj.switchmonitor.schedule.SwitchesUpdate;
import cn.albumenj.switchmonitor.service.LogService;
import cn.albumenj.switchmonitor.service.PortStatusService;
import cn.albumenj.switchmonitor.service.WechatUserService;
import cn.albumenj.switchmonitor.util.IpUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    @Autowired
    WechatUserService wechatUserService;

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

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping("/api/setting/log_all")
    public List<Log> fetchAllLog(UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.WARN, LogConst.USER, authentication.getName(),
                "Fetch All Log", IpUtil.getIpAddr(request));
        logService.insert(log);

        return logService.selectAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping("/api/setting/log_level")
    public List<Log> fetchLogLevel(@Param("level") Integer level,
                                   UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.WARN, LogConst.USER, authentication.getName(),
                "Fetch Log By Level", IpUtil.getIpAddr(request));
        logService.insert(log);

        return logService.selectByLevel(level);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping("/api/setting/log_type")
    public List<Log> fetchLogType(@Param("type") Integer type,
                                  UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.WARN, LogConst.USER, authentication.getName(),
                "Fetch Log By Type", IpUtil.getIpAddr(request));
        logService.insert(log);

        return logService.selectByType(type);
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping("/api/setting/user_all")
    public List<WechatUser> fetchUser(UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.WARN, LogConst.USER, authentication.getName(),
                "Fetch All Wechat User", IpUtil.getIpAddr(request));
        logService.insert(log);

        return wechatUserService.selectAll();
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping("/api/setting/user_delete")
    public PageCodeDto deleteUser(@Param("no") String no,
                                  UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.WARN, LogConst.USER, authentication.getName(),
                "Delete Wechat User No: " + no, IpUtil.getIpAddr(request));
        logService.insert(log);

        if (wechatUserService.delete(no) == 1) {
            return new PageCodeDto(PageCodeEnum.DELETE_SUCCESS);
        } else {
            return new PageCodeDto(PageCodeEnum.DELETE_FAILED);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "/api/setting/user_modify", method = RequestMethod.POST)
    public PageCodeDto modifyUser(WechatUser wechatUser,
                                  UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.WARN, LogConst.USER, authentication.getName(),
                "Modify Wechat User No: " + wechatUser.getNo()
                        + " username: " + wechatUser.getUsername()
                        + " groupName: " + wechatUser.getGroupName()
                        + " openId: " + wechatUser.getOpenId()
                        + " sessionKey: " + wechatUser.getSessionKey()
                        + " unionId: " + wechatUser.getUnionId()
                        + " token: " + wechatUser.getToken()
                        + " permission: " + wechatUser.getPermission(), IpUtil.getIpAddr(request));
        logService.insert(log);

        if (wechatUserService.update(wechatUser) == 1) {
            return new PageCodeDto(PageCodeEnum.MODIFY_SUCCESS);
        } else {
            return new PageCodeDto(PageCodeEnum.MODIFY_FAILED);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @RequestMapping(value = "/api/setting/user_insert", method = RequestMethod.POST)
    public PageCodeDto insertUser(WechatUser wechatUser,
                                  UsernamePasswordAuthenticationToken authentication, HttpServletRequest request) {
        Log log = new Log(LogConst.WARN, LogConst.USER, authentication.getName(),
                "Insert Wechat User No: " + wechatUser.getNo()
                        + " username: " + wechatUser.getUsername()
                        + " groupName: " + wechatUser.getGroupName()
                        + " openId: " + wechatUser.getOpenId()
                        + " sessionKey: " + wechatUser.getSessionKey()
                        + " unionId: " + wechatUser.getUnionId()
                        + " token: " + wechatUser.getToken()
                        + " permission: " + wechatUser.getPermission(), IpUtil.getIpAddr(request));
        logService.insert(log);

        if (wechatUserService.insert(wechatUser) == 1) {
            return new PageCodeDto(PageCodeEnum.ADD_SUCCESS);
        } else {
            return new PageCodeDto(PageCodeEnum.ADD_FAILED);
        }
    }
}
