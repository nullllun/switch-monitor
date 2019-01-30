package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.WechatUser;
import cn.albumenj.switchmonitor.dao.WechatUserMapper;
import cn.albumenj.switchmonitor.service.WechatUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 微信用户
 *
 * @author Albumen
 */
@Service
public class WechatUserServiceImpl implements WechatUserService {

    @Resource
    private WechatUserMapper wechatUserMapper;

    /**
     * 插入用户
     *
     * @param wechatUser
     * @return
     */
    @Override
    public int insert(WechatUser wechatUser) {
        return wechatUserMapper.insert(wechatUser);
    }

    /**
     * 验证用户
     *
     * @param openId
     * @param sessionKey 更新Key
     * @return 成功：对应用户信息 失败：null
     */
    @Override
    public WechatUser auth(String openId, String sessionKey) {
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenId(openId);
        wechatUser = wechatUserMapper.selectByOpenId(wechatUser);
        if (wechatUser != null) {
            WechatUser wechatUserNew = new WechatUser();
            wechatUserNew.setNo(wechatUser.getNo());
            wechatUserNew.setSessionKey(sessionKey);
            wechatUserMapper.update(wechatUserNew);
        }
        return wechatUser;
    }

    /**
     * 验证用户
     *
     * @param openId
     * @return 成功：对应用户信息 失败：null
     */
    @Override
    public WechatUser auth(String openId) {
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenId(openId);
        wechatUser = wechatUserMapper.selectByOpenId(wechatUser);
        WechatUser wechatUserNew = new WechatUser();
        return wechatUser;
    }

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
    @Override
    public WechatUser authToken(String token, String openId, String sessionKey, String unionKey) {
        WechatUser wechatUser = checkToken(token);

        if (wechatUser != null) {
            wechatUser.setToken("已验证");
            wechatUser.setOpenId(openId);
            wechatUser.setSessionKey(sessionKey);
            wechatUser.setUnionId(unionKey);
            wechatUserMapper.update(wechatUser);
            wechatUser = wechatUserMapper.selectByOpenId(wechatUser);
        }

        return wechatUser;
    }

    /**
     * 获取Token信息
     *
     * @param token
     * @return 成功：对应用户信息 失败：null
     */
    @Override
    public WechatUser checkToken(String token) {
        String valid = "已验证";
        if (token == null || token.compareTo(valid) == 0) {
            return null;
        }

        WechatUser wechatUser = new WechatUser();
        wechatUser.setToken(token);
        wechatUser = wechatUserMapper.selectByToken(wechatUser);
        return wechatUser;
    }

    /**
     * 查找姓名
     *
     * @param openId
     * @return
     */
    @Override
    public String selectName(String openId) {
        WechatUser wechatUser = new WechatUser();
        wechatUser.setOpenId(openId);
        return wechatUserMapper.selectName(wechatUser).getUsername();
    }
}
