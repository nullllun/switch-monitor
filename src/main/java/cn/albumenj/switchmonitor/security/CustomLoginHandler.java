package cn.albumenj.switchmonitor.security;

import cn.albumenj.switchmonitor.constant.HttpConst;
import cn.albumenj.switchmonitor.constant.PageCodeEnum;
import cn.albumenj.switchmonitor.dto.MpLoginDto;
import cn.albumenj.switchmonitor.util.JwtUtil;
import cn.albumenj.switchmonitor.util.PageCodeUtil;
import cn.albumenj.switchmonitor.util.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author Albumen
 */
@Component
public class CustomLoginHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtUtil jwtUtil;

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

            token = jwtUtil.create(authentication.getName(), claim.stream().toArray(String[]::new));
            redisUtil.set(uuid, token);
            // 登录成功后，返回token到Body里面

            response.setContentType("application/json; charset=utf-8");
            PrintWriter writer = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            MpLoginDto mpLoginDto = new MpLoginDto();
            mpLoginDto.setToken(HttpConst.AUTHORIZATION_PREFIX + token);
            mpLoginDto.setSuccess(true);
            String json = objectMapper.writeValueAsString(mpLoginDto);
            writer.print(json);
            writer.close();
            response.flushBuffer();

        } catch (Exception e) {
            try {
                PageCodeUtil.printCode(response, PageCodeEnum.SYSTEM_ERROR);
            } catch (Exception e1) {
                return;
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
        PageCodeUtil.printCode(response, PageCodeEnum.LOGIN_FAILED);
    }
}