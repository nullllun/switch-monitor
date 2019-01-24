package cn.albumenj.switchmonitor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * 与微信服务器交互的工具类
 *
 * @author Albumen
 */
@Component
public class WechatServer {
    @Value("${wechat.appid}")
    String appid;
    @Value("${wechat.appSecret}")
    String secret;
    @Autowired
    RestTemplate restTemplate;

    public String code2session(String code) {
        String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appid", appid);
        params.add("secret", secret);
        params.add("js_code", code);
        params.add("grant_type", "authorization_code");
        URI code2Session = HttpUtil.getURIwithParams(code2SessionUrl, params);
        return restTemplate.exchange(code2Session, HttpMethod.GET, new HttpEntity<String>(new HttpHeaders()), String.class).getBody();
    }

}
