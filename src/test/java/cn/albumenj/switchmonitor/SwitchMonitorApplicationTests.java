package cn.albumenj.switchmonitor;

import cn.albumenj.switchmonitor.service.WebLogin;
import cn.albumenj.switchmonitor.util.WechatServer;
import cn.albumenj.switchmonitor.util.qrcode.QrCodeGenWrapper;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
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
        String msg = "https://my.oschina.net/u/566591/blog/1359432";
        try {
            String ans = QrCodeGenWrapper.of(msg)
                    .setW(300)
                    .setPreColor(0x00000000)
                    .setBgColor(0xffffffff)
                    .setPadding(0)
                    .asString();
            System.out.println(ans);
        } catch (Exception e) {
            System.out.println("create qrcode error! e: " + e);
            Assert.assertTrue(false);
        }
    }

}

