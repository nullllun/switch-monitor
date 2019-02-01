package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.dto.WebLoginInfoDto;

import javax.servlet.http.HttpServletRequest;

/**
 * 网页认证
 *
 * @author albumen
 */
public interface WebLogin {
    /**
     * 请求登录
     *
     * @param uuid Session标记UUID
     * @return
     */
    void requestLogin(String uuid);

    /**
     * 获取登录信息
     *
     * @param code
     * @return
     */
    WebLoginInfoDto fetchInformation(String code, String openId);

    /**
     * 确认登录
     *
     * @param code
     * @param openId
     */
    void confirmLogin(String code, String openId);

    /**
     * 清除本地记录
     *
     * @param uuid
     */
    void cleanToken(String uuid);

    /**
     * 填充用户访问的Servlet信息
     * 用于记录IP
     *
     * @param request
     */
    void setRequest(HttpServletRequest request);
}
