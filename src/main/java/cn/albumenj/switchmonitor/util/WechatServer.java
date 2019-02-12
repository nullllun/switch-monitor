package cn.albumenj.switchmonitor.util;

import cn.albumenj.switchmonitor.dto.MessageReturnDto;
import cn.albumenj.switchmonitor.dto.MessageSubmitDto;
import cn.albumenj.switchmonitor.dto.TokenDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 与微信服务器交互的工具类
 *
 * @author Albumen
 */
@Component
public class WechatServer {
    private final static Logger logger = LoggerFactory.getLogger(WechatServer.class);

    @Value("${wechat.appid}")
    String appid;
    @Value("${wechat.appSecret}")
    String secret;
    @Autowired
    RestTemplate restTemplate;
    @Value("${wechat.corpId}")
    String corpId;
    @Value("${wechat.debugSecret}")
    String debugSecret;
    @Value("${wechat.warnSecret}")
    String warnSecret;
    @Autowired
    RedisUtil redisUtil;

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

    private String accessToken(String secret) {
        String temp = redisUtil.get(secret);
        if (temp != null) {
            return temp;
        }
        String code2SessionUrl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("corpid", corpId);
        params.add("corpsecret", secret);
        URI code2Session = HttpUtil.getURIwithParams(code2SessionUrl, params);
        String resultJson = restTemplate.exchange(code2Session, HttpMethod.GET, new HttpEntity<String>(new HttpHeaders()), String.class).getBody();
        TokenDto token = JSONUtil.jsonString2Object(resultJson, TokenDto.class);
        if (token.getErrcode().equals(0)) {
            try {
                redisUtil.set(secret, token.getAccessToken(), token.getExpiresIn());
            } catch (Exception e) {
                logger.warn("Set Wechat CorpToken To Redis Failed !");
            }
            return token.getAccessToken();
        } else {
            logger.warn("Wechat CorpToken Fetch Failed !");
            logger.warn(token.getErrmsg());
            return "";
        }
    }

    public boolean sendDebugMessage(String msg) {
        return sendMessage(debugSecret, "2", msg);
    }

    public boolean sendWarnMessage(String msg) {
        return sendMessage(warnSecret, "6", msg);
    }

    public boolean sendMessage(String agentSecret, String agentId, String msg) {
        String token = accessToken(agentSecret);
        String code2SessionUrl = "https://qyapi.weixin.qq.com/cgi-bin/message/send";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("access_token", token);

        Map<String, String> text = new HashMap<>(16);
        text.put("content", msg);

        MessageSubmitDto messageSubmitDto = new MessageSubmitDto();
        messageSubmitDto.setTouser("@all");
        messageSubmitDto.setMsgtype("text");
        messageSubmitDto.setAgentid(agentId);
        messageSubmitDto.setText(text);

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<Map<String, String>> requestEntity =
                new HttpEntity<Map<String, String>>(BeanMap.create(messageSubmitDto), headers);

        URI code2Session = HttpUtil.getURIwithParams(code2SessionUrl, params);
        String resultJson = restTemplate.exchange(code2Session, HttpMethod.POST, requestEntity, String.class).getBody();
        MessageReturnDto messageReturn = JSONUtil.jsonString2Object(resultJson, MessageReturnDto.class);

        switch (messageReturn.getErrcode()) {
            case 0:
                return true;
            case 40014:
                redisUtil.delete(agentSecret);
                logger.warn(messageReturn.getErrmsg());
                return sendDebugMessage(msg);
            default:
                logger.warn(messageReturn.getErrmsg());
                return false;
        }
    }
}
