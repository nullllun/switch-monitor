package cn.albumenj.switchmonitor.service;

import cn.albumenj.switchmonitor.bean.WechatUser;

/**
 * 微信用户
 *
 * @author Albumen
 */
public interface WechatUserService {

    /**
     * 插入用户
     *
     * @param wechatUser
     * @return
     */
    int insert(WechatUser wechatUser);

    /**
     * 验证用户
     *
     * @param openId
     * @return 成功：对应用户信息 失败：null
     */
    WechatUser auth(String openId);

    /**
     * 验证用户
     *
     * @param openId
     * @param sessionKey 更新Key
     * @return 成功：对应用户信息 失败：null
     */
    WechatUser auth(String openId, String sessionKey);

    /**
     * 验证Token
     * 成功写入相关信息
     *
     * @param token
     * @param openId
     * @param sessionKey
     * @param unionKey
     * @return 成功：对应用户信息 失败：null
     */
    WechatUser authToken(String token, String openId, String sessionKey, String unionKey);

    /**
     * 获取Token信息
     *
     * @param token
     * @return 成功：对应用户信息 失败：null
     */
    WechatUser checkToken(String token);

    /**
     * 查找姓名
     *
     * @param openId
     * @return
     */
    String selectName(String openId);
}
