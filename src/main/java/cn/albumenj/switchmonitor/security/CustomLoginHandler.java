package cn.albumenj.switchmonitor.security;

import cn.albumenj.switchmonitor.constant.HttpConst;
import cn.albumenj.switchmonitor.constant.PageCodeEnum;
import cn.albumenj.switchmonitor.dto.LoginStatusDto;
import cn.albumenj.switchmonitor.util.JwtUtil;
import cn.albumenj.switchmonitor.util.PageCodeUtil;
import cn.albumenj.switchmonitor.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 自定义登陆认证
 * 兼容JWT
 *
 * @author Albumen
 */
@Component
public class CustomLoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private final static Logger logger = LoggerFactory.getLogger(CustomLoginHandler.class);
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtUtil jwtUtil;
    @Value("${security.jwtLoginExp}")
    Integer expTime;

    /**
     * Login Success
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // builder the token
        String token = null;
        try {
            List<String> authorities =
                    authentication.getAuthorities()
                            .stream()
                            .map(e -> ((GrantedAuthority) e).getAuthority())
                            .collect(Collectors.toList());
            String[] permission = authorities.toArray(new String[authorities.size()]);
            String uuid = UUID.randomUUID().toString();
            List<String> claim = new LinkedList<>();
            claim.add(uuid);
            claim.addAll(Arrays.asList(permission));

            token = jwtUtil.create(authentication.getName(), claim.stream().toArray(String[]::new), expTime);
            redisUtil.set(uuid, token, expTime);
            // 登录成功后，返回token到Body里面

            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            LoginStatusDto loginStatusDto = new LoginStatusDto();
            loginStatusDto.setToken(HttpConst.AUTHORIZATION_PREFIX + token);
            loginStatusDto.setSuccess(true);
            String json = objectMapper.writeValueAsString(loginStatusDto);
            writer.print(json);
            writer.close();
            response.flushBuffer();

        } catch (Exception e) {
            try {
                PageCodeUtil.printCode(response, PageCodeEnum.SYSTEM_ERROR, 400);
            } catch (Exception e1) {
                logger.debug("Print Page Code Error " + e1.toString());
            }
        }
    }

    /**
     * Login Failure
     *
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        PageCodeUtil.printCode(response, PageCodeEnum.LOGIN_FAILED, 403);
    }
}