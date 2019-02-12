package cn.albumenj.switchmonitor;

import cn.albumenj.switchmonitor.service.WebLogin;
import cn.albumenj.switchmonitor.util.WechatServer;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwitchMonitorApplicationTests {
    @Autowired
    WechatServer wechatServer;
    @Autowired
    StringEncryptor stringEncryptor;
    @Autowired
    WebLogin webLogin;

    @Test
    public void contextLoads() {
        System.out.println(stringEncryptor.encrypt(""));
    }

}

