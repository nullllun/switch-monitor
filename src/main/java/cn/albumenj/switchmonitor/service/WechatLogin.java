package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.dto.LoginStatusDto;

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
}
