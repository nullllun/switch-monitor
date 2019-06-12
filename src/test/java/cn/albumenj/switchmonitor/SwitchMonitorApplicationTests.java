package cn.albumenj.switchmonitor;

import cn.albumenj.switchmonitor.service.WebLogin;
import cn.albumenj.switchmonitor.util.SnmpUtil;
import cn.albumenj.switchmonitor.util.WechatServer;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

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
        SnmpUtil snmpUtil = new SnmpUtil();
        snmpUtil.walk("172.16.101.1", "gdgydx_pub", "1.3.6.1.2.1.2.2.1.1", new HashMap<>());
    }

}

