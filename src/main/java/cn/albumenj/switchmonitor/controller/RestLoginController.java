package cn.albumenj.switchmonitor.controller;

import cn.albumenj.switchmonitor.bean.User;
import cn.albumenj.switchmonitor.bean.WechatUser;
import cn.albumenj.switchmonitor.dto.LoginStatusDto;
import cn.albumenj.switchmonitor.dto.WebLoginInfoDto;
import cn.albumenj.switchmonitor.service.LogService;
import cn.albumenj.switchmonitor.service.WebLogin;
import cn.albumenj.switchmonitor.service.WechatLogin;
import cn.albumenj.switchmonitor.service.WechatUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信登陆
 *
 * @author Albumen
 */
@RestController
public class RestLoginController {
    @Autowired
    WechatLogin wechatLogin;
    @Autowired
    WechatUserService wechatUserService;
    @Autowired
    LogService logService;
    @Autowired
    WebLogin webLogin;

    @RequestMapping(path = "/auth/loginfresh", method = RequestMethod.GET)
    public LoginStatusDto loginFresh(@Param("code") String code, HttpServletRequest request) {
        wechatLogin.setRequest(request);
        return wechatLogin.loginFresh(code);
    }

    @RequestMapping(path = "/auth/commonlogin", method = RequestMethod.GET)
    public LoginStatusDto loginFresh(User user, HttpServletRequest request) {
        wechatLogin.setRequest(request);
        return wechatLogin.webLogin(user);
    }

    @RequestMapping(path = "/auth/loginold", method = RequestMethod.GET)
    public LoginStatusDto loginOld(@Param("code") String code, HttpServletRequest request) {
        wechatLogin.setRequest(request);
        return wechatLogin.loginOld(code);
    }

    @RequestMapping(path = "/auth/logout", method = RequestMethod.GET)
    public LoginStatusDto logout(@Param("code") String code, HttpServletRequest request) {
        wechatLogin.setRequest(request);
        return wechatLogin.logout(code);
    }

    @RequestMapping(path = "/auth/tokenverify", method = RequestMethod.GET)
    public WechatUser tokenVerify(@Param("token") String token) {
        WechatUser wechatUser = wechatUserService.checkToken(token);
        if (wechatUser != null) {
            // 屏蔽敏感信息
            wechatUser.setPermission(null);
            wechatUser.setToken(null);
        }
        return wechatUser;
    }

    @RequestMapping(path = "/auth/tokensubmit", method = RequestMethod.GET)
    public LoginStatusDto tokenSubmit(@Param("code") String code, @Param("token") String token,
                                      HttpServletRequest request) {
        wechatLogin.setRequest(request);
        return wechatLogin.tokenSubmit(token, code);
    }

    @RequestMapping(path = "/api/qrscan_login_check", method = RequestMethod.GET)
    public WebLoginInfoDto qrScanCheck(@Param("code") String code, HttpServletRequest request,
                                       UsernamePasswordAuthenticationToken authentication) {
        webLogin.setRequest(request);
        return webLogin.fetchInformation(code, authentication.getName());
    }

    @RequestMapping(path = "/api/qrscan_login", method = RequestMethod.GET)
    public String qrScanLogin(@Param("code") String code, HttpServletRequest request,
                              UsernamePasswordAuthenticationToken authentication) {
        webLogin.setRequest(request);
        webLogin.confirmLogin(code, authentication.getName());
        return "服务器已收到信息";
    }
}
