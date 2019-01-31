package cn.albumenj.switchmonitor.service.impl;

import cn.albumenj.switchmonitor.dto.WebLoginInfoDto;
import cn.albumenj.switchmonitor.service.WebLogin;
import cn.albumenj.switchmonitor.util.WebSocketUtils;
import org.springframework.stereotype.Service;

/**
 * 网页认证
 *
 * @author albumen
 */
@Service
public class WebLoginImpl implements WebLogin {
    /**
     * 请求登录
     *
     * @param uuid Session标记UUID
     * @return
     */
    @Override
    public void requestLogin(String uuid) {
        WebSocketUtils.sendMessage(uuid,"hello");
    }

    /**
     * 获取登录信息
     *
     * @param code
     * @return
     */
    @Override
    public WebLoginInfoDto fetchInformation(String code) {
        return null;
    }

    /**
     * 确认登录
     *
     * @param code
     */
    @Override
    public void confirmLogin(String code) {

    }
}
