package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.User;
import cn.albumenj.switchmonitor.dto.LoginStatusDto;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信登陆接口
 *
 * @author Albumen
 */
public interface WechatLogin {
    /**
     * 默认登陆
     *
     * @param code
     * @return
     */
    LoginStatusDto loginFresh(String code);

    /**
     * 再次登陆
     *
     * @param code
     * @return
     */
    LoginStatusDto loginOld(String code);

    /**
     * 提交Token验证
     * 新用户
     *
     * @param token
     * @param uuid
     * @return
     */
    LoginStatusDto tokenSubmit(String token, String uuid);

    /**
     * 填充用户访问的Servlet信息
     * 用于记录IP
     *
     * @param request
     */
    void setRequest(HttpServletRequest request);

    /**
     * 网页登陆返回Token
     *
     * @param openId
     * @return Token
     */
    LoginStatusDto webLogin(String openId);

    /**
     * 登出
     *
     * @param code
     * @return
     */
    LoginStatusDto logout(String code);

    /**
     * 网页普通登陆返回Token
     *
     * @param user
     * @return Token
     */
    LoginStatusDto webLogin(User user);
}
