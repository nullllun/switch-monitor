package cn.albumenj.switchmonitor.security;

import cn.albumenj.switchmonitor.bean.User;
import cn.albumenj.switchmonitor.constant.PermissionConst;
import cn.albumenj.switchmonitor.schedule.SwitchesUpdate;
import cn.albumenj.switchmonitor.service.UserService;
import cn.albumenj.switchmonitor.service.impl.GrantedAuthorityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * 自定义身份认证验证组件
 *
 * @author zhaoxinguo on 2017/9/12.
 * @author Albumen
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final static Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
    @Autowired
    private UserService userService;

    public CustomAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        // 认证逻辑
        user = userService.check(user);
        if (null != user) {
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            for (Integer i = Integer.parseInt(user.getPermission()); i <= PermissionConst.PERMISSION.size(); i++) {
                authorities.add(new GrantedAuthorityImpl(PermissionConst.PERMISSION.get(i)));
            }

            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
            return auth;
        } else {
            logger.debug("Username or Password Error");
            throw new BadCredentialsException("Username or Password Error");
        }
    }

    /**
     * 是否可以提供输入类型的认证服务
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
