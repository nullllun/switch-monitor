package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.dto.WebLoginInfoDto;

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
     * @param code
     * @return
     */
    WebLoginInfoDto fetchInformation(String code);

    /**
     * 确认登录
     * @param code
     */
    void confirmLogin(String code);
}
