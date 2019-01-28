package cn.albumenj.switchmonitor.security;

import cn.albumenj.switchmonitor.constant.HttpConst;
import cn.albumenj.switchmonitor.constant.PageCodeEnum;
import cn.albumenj.switchmonitor.schedule.SwitchesUpdate;
import cn.albumenj.switchmonitor.service.impl.GrantedAuthorityImpl;
import cn.albumenj.switchmonitor.util.JwtUtil;
import cn.albumenj.switchmonitor.util.PageCodeUtil;
import cn.albumenj.switchmonitor.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 认证状态检测
 *
 * @author Albumen
 */
public class CustomAuthenticationFilter extends BasicAuthenticationFilter {
    private final static Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);
    private JwtUtil jwtUtil;

    private RedisUtil redisUtil;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, RedisUtil redisUtil, JwtUtil jwtUtil) {
        super(authenticationManager);
        this.redisUtil = redisUtil;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getHeader(HttpConst.AUTHORIZATION) != null && request.getHeader(HttpConst.AUTHORIZATION).startsWith(HttpConst.AUTHORIZATION_PREFIX)) {

            String token = request.getHeader(HttpConst.AUTHORIZATION);

            if (token != null && !token.isEmpty()) {
                String[] ret = jwtUtil.verify(token.substring(HttpConst.AUTHORIZATION_PREFIX.length()));

                if (ret != null && ret[0] != null) {
                    String tokenRedis = redisUtil.get(ret[0]);
                    if (tokenRedis != null && tokenRedis.compareTo(token.substring(HttpConst.AUTHORIZATION_PREFIX.length())) == 0) {
                        //验证成功
                        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                        for (String permission : ret) {
                            if (!permission.equals(ret[0])) {
                                authorities.add(new GrantedAuthorityImpl(permission));
                            }
                        }
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(ret[0], null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
            //验证失败
            logger.trace("Login Failed");
            PageCodeUtil.printCode(response, PageCodeEnum.NOT_LOGIN, 401);
        } else {
            //验证失败
            logger.trace("Login Failed");
            PageCodeUtil.printCode(response, PageCodeEnum.NOT_LOGIN, 401);
        }
    }
}
