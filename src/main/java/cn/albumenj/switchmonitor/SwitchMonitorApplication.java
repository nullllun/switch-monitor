package cn.albumenj.switchmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Albumen
 */
@SpringBootApplication
@EnableAsync
public class SwitchMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwitchMonitorApplication.class, args);
    }

}

