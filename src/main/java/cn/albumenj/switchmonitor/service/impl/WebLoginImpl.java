package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.dto.WebLoginInfoDto;
import cn.albumenj.switchmonitor.service.WebLogin;
import cn.albumenj.switchmonitor.service.WechatLogin;
import cn.albumenj.switchmonitor.util.JwtUtil;
import cn.albumenj.switchmonitor.util.QrCode.QrCodeGenWrapper;
import cn.albumenj.switchmonitor.util.RedisUtil;
import cn.albumenj.switchmonitor.util.WebSocketUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 网页认证
 *
 * @author albumen
 */
@Service
public class WebLoginImpl implements WebLogin {
    private final static Logger logger = LoggerFactory.getLogger(WebLogin.class);
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    WechatLogin wechatLogin;

    private HttpServletRequest request;

    @Value("${security.webLoginExp}")
    Integer webLoginExp;
    /**
     * 请求登录
     *
     * @param uuid Session标记UUID
     * @return
     */
    @Override
    public void requestLogin(String uuid) {
        try {
            redisUtil.set(uuid, uuid, webLoginExp);
            String qrCode = QrCodeGenWrapper.of(uuid)
                    .setW(300)
                    .setPreColor(0x00000000)
                    .setBgColor(0xffffffff)
                    .setPadding(0)
                    .asString();
            WebSocketUtils.sendMessage(uuid, qrCode);
        } catch (Exception e) {
            logger.warn(e.toString());
        }
    }

    /**
     * 获取登录信息
     *
     * @param code
     * @return
     */
    @Override
    public WebLoginInfoDto fetchInformation(String code) {
        String uuid = redisUtil.get(code);
        WebLoginInfoDto webLoginInfoDto = new WebLoginInfoDto();
        if (uuid != null) {
            webLoginInfoDto.setContain(true);
            webLoginInfoDto.setTimeStamp(WebSocketUtils.livingSessionsCache.get(uuid).getTime());
        } else {
            webLoginInfoDto.setContain(false);
        }
        return webLoginInfoDto;
    }

    /**
     * 确认登录
     *
     * @param code
     */
    @Override
    public void confirmLogin(String code, String openId) {
        String uuid = redisUtil.get(code);
        if (uuid != null) {
            wechatLogin.setRequest(request);
            String msg = JSON.toJSONString(wechatLogin.webLogin(openId));
            WebSocketUtils.sendMessage(uuid, msg);
            redisUtil.delete(uuid);
        }
    }

    /**
     * 清除本地记录
     *
     * @param uuid
     */
    @Override
    public void cleanToken(String uuid) {
        redisUtil.delete(uuid);
    }

    /**
     * 填充用户访问的Servlet信息
     * 用于记录IP
     *
     * @param request
     */
    @Override
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
