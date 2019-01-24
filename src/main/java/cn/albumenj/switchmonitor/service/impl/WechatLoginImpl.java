package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.bean.WechatUser;
import cn.albumenj.switchmonitor.constant.HttpConst;
import cn.albumenj.switchmonitor.constant.PermissionConst;
import cn.albumenj.switchmonitor.dto.LoginCodeDto;
import cn.albumenj.switchmonitor.dto.LoginStatusDto;
import cn.albumenj.switchmonitor.service.WechatLogin;
import cn.albumenj.switchmonitor.service.WechatUserService;
import cn.albumenj.switchmonitor.util.JSONUtil;
import cn.albumenj.switchmonitor.util.JwtUtil;
import cn.albumenj.switchmonitor.util.RedisUtil;
import cn.albumenj.switchmonitor.util.WechatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * 微信登陆接口
 *
 * @author Albumen
 */
@Service
public class WechatLoginImpl implements WechatLogin {
    @Autowired
    WechatServer wechatServer;
    @Autowired
    WechatUserService wechatUserService;

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    RedisUtil redisUtil;

    @Value("${security.jwtLoginExp}")
    Integer expTime;

    /**
     * 默认登陆
     *
     * @param code
     * @return
     */
    @Override
    public LoginStatusDto loginFresh(String code) {
        String resultJson = wechatServer.code2session(code);

        LoginCodeDto response = JSONUtil.jsonString2Object(resultJson, LoginCodeDto.class);
        LoginStatusDto loginStatusDto;
        if (response.getErrcode().equals(0)) {
            WechatUser wechatUser = wechatUserService.auth(response.getOpenid(), response.getSessionKey());
            if (wechatUser != null) {
                /**
                 * 认证成功
                 */
                loginStatusDto = fillUser(wechatUser);
            } else {
                /**
                 * 认证失败，下一步需提交 Token 确认
                 */
                loginStatusDto = new LoginStatusDto();
                loginStatusDto.setSuccess(false);
                String uuid = UUID.randomUUID().toString();
                loginStatusDto.setToken(uuid);
                try {
                    redisUtil.set(uuid, resultJson);
                } catch (Exception e) {
                    loginStatusDto.setToken(null);
                }
            }
        } else {
            /**
             * 微信服务器返回错误
             */
            loginStatusDto = new LoginStatusDto();
            loginStatusDto.setSuccess(false);
        }
        return loginStatusDto;
    }

    /**
     * 提交Token验证
     * 新用户
     *
     * @param token
     * @param uuid
     * @return
     */
    @Override
    public LoginStatusDto tokenSubmit(String token, String uuid) {
        String resultJson = redisUtil.get(uuid);
        redisUtil.delete(uuid);
        LoginStatusDto loginStatusDto;
        if (resultJson != null) {
            LoginCodeDto response = JSONUtil.jsonString2Object(resultJson, LoginCodeDto.class);
            WechatUser wechatUser =
                    wechatUserService.authToken(token,
                            response.getOpenid(),
                            response.getSessionKey(),
                            response.getUnionid());
            if (wechatUser != null) {
                loginStatusDto = fillUser(wechatUser);
            } else {
                loginStatusDto = new LoginStatusDto();
                loginStatusDto.setSuccess(false);
            }
        } else {
            loginStatusDto = new LoginStatusDto();
            loginStatusDto.setSuccess(false);
        }
        return loginStatusDto;
    }

    /**
     * 填充用户信息
     * 将用户信息保存到Redis
     *
     * @param wechatUser
     * @return
     */
    private LoginStatusDto fillUser(WechatUser wechatUser) {
        LoginStatusDto loginStatusDto = new LoginStatusDto();
        loginStatusDto.setSuccess(true);
        List<String> claim = new LinkedList<>();
        String uuid = UUID.randomUUID().toString();
        claim.add(uuid);

        for (Integer i = wechatUser.getPermission(); i <= PermissionConst.PERMISSION.size(); i++) {
            claim.add(PermissionConst.PERMISSION.get(i));
        }

        String token = jwtUtil.create(wechatUser.getOpenId(), claim.stream().toArray(String[]::new), expTime);
        loginStatusDto.setToken(HttpConst.AUTHORIZATION_PREFIX + token);
        try {
            redisUtil.set(uuid, token, expTime);
        } catch (Exception e) {
            loginStatusDto.setSuccess(false);
            loginStatusDto.setToken(null);
        }
        return loginStatusDto;
    }

    /**
     * 再次登陆
     *
     * @param code
     * @return
     */
    @Override
    public LoginStatusDto loginOld(String code) {
        String openId = jwtUtil.getName(code.substring(HttpConst.AUTHORIZATION_PREFIX.length()));
        String[] claim = jwtUtil.verify(code.substring(HttpConst.AUTHORIZATION_PREFIX.length()));

        String token = redisUtil.get(claim[0]);

        LoginStatusDto loginStatusDto;
        if (token != null) {
            redisUtil.delete(claim[0]);
            WechatUser wechatUser = wechatUserService.auth(openId);

            if (wechatUser != null) {
                loginStatusDto = fillUser(wechatUser);
            } else {
                loginStatusDto = new LoginStatusDto();
                loginStatusDto.setSuccess(false);
            }
        } else {
            loginStatusDto = new LoginStatusDto();
            loginStatusDto.setSuccess(false);
        }
        return loginStatusDto;
    }
}