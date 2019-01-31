package cn.albumenj.switchmonitor;

import cn.albumenj.switchmonitor.config.CustomEndpointConfigure;
import cn.albumenj.switchmonitor.controller.WebLoginController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Albumen
 */
@SpringBootApplication
@EnableAsync
@EnableWebSocket
public class SwitchMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwitchMonitorApplication.class, args);
    }

    /**
     * 注册 ServerEndpointExporter Bean对象（因为Springboot没有自动注册，所以得手动注册）
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Bean
    public CustomEndpointConfigure customEndpointConfigure(){
        return new CustomEndpointConfigure();
    }

    /**
     * 注册 端点对象
     */
    @Bean
    public WebLoginController webLoginController(){
        return new WebLoginController();
    }
}

