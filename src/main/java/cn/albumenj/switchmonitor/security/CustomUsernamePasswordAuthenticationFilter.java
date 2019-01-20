package cn.albumenj.switchmonitor.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * AuthenticationFilter that supports rest login(json login) and form login.
 *
 * @author chenhuanming
 */
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String json = "application/json";
        //attempt Authentication when Content-Type is json
        if (request.getContentType() != null && request.getContentType().startsWith(json)) {
            //use jackson to deserialize json
            UsernamePasswordAuthenticationToken authRequest = null;
            authRequest = new UsernamePasswordAuthenticationToken(
                    request.getParameter("username"),
                    request.getParameter("password"),
                    new ArrayList<>());

            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
        //transmit it to UsernamePasswordAuthenticationFilter
        else {
            return super.attemptAuthentication(request, response);
        }
    }

}
