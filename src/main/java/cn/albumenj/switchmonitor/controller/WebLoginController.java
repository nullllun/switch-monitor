package cn.albumenj.switchmonitor.controller;

import cn.albumenj.switchmonitor.config.CustomEndpointConfigure;
import cn.albumenj.switchmonitor.dto.SessionDto;
import cn.albumenj.switchmonitor.service.WebLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.UUID;

import static cn.albumenj.switchmonitor.util.WebSocketUtils.livingSessionsCache;
import static cn.albumenj.switchmonitor.util.WebSocketUtils.livingSessionsCacheN;

/**
 * 聊天室
 *
 * @author Levin
 * @since 2018/6/26 0026
 */
@RestController
@ServerEndpoint(value = "/auth/weblogin/", configurator = CustomEndpointConfigure.class)
public class WebLoginController {
    private static final Logger log = LoggerFactory.getLogger(WebLoginController.class);
    @Autowired
    WebLogin webLogin;

    @OnOpen
    public void openSession(Session session) {
        String uuid = UUID.randomUUID().toString();
        SessionDto sessionDto = new SessionDto(session);
        livingSessionsCache.put(uuid, sessionDto);
        livingSessionsCacheN.put(session, uuid);
        webLogin.requestLogin(uuid);
    }

    @OnMessage
    public void onMessage(String message) {
        log.info(message);
    }

    @OnClose
    /**
     * 当前的Session 移除
     */
    public void onClose(Session session) {
        String uuid = livingSessionsCacheN.get(session);
        if (uuid != null) {
            livingSessionsCache.remove(livingSessionsCacheN.get(session));
            livingSessionsCacheN.remove(session);
            webLogin.cleanToken(uuid);
        }
        log.info("Close Session");
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throwable.printStackTrace();
    }
}