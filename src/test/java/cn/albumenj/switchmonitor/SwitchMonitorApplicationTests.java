package cn.albumenj.switchmonitor;

import cn.albumenj.switchmonitor.service.PortStatusService;
import cn.albumenj.switchmonitor.util.WechatServer;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SwitchMonitorApplicationTests {
    @Autowired
    WechatServer wechatServer;
    @Autowired
    StringEncryptor stringEncryptor;


    @Test
    public void contextLoads() {
        System.out.println(stringEncryptor.encrypt(""));
        wechatServer.sendDebugMessage("SpringBoot Test Runner");
    }

}

