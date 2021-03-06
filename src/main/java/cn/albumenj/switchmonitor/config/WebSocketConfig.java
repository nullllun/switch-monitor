package cn.albumenj.switchmonitor.config;

import cn.albumenj.switchmonitor.controller.WebLoginController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置文件
 *
 * @author Albumen
 */
@Configuration
public class WebSocketConfig {
    /**
     * 注册 ServerEndpointExporter Bean对象（因为Springboot没有自动注册，所以得手动注册）
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public CustomEndpointConfigure customEndpointConfigure() {
        return new CustomEndpointConfigure();
    }

    /**
     * 注册 端点对象
     */
    @Bean
    public WebLoginController webLoginController() {
        return new WebLoginController();
    }
}
