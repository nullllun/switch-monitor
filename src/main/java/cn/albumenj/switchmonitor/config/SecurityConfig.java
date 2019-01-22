package cn.albumenj.switchmonitor.config;

import cn.albumenj.switchmonitor.security.*;
import cn.albumenj.switchmonitor.util.JwtUtil;
import cn.albumenj.switchmonitor.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 配置Spring Security
 *
 * @author Albumen
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomLoginHandler customLoginHandler;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    private CustomHttp401AuthenticationEntryPoint customHttp401AuthenticationEntryPoint;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/druid/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .authorizeRequests()
                .antMatchers("/login.html").permitAll()
                .antMatchers("/mpapi/login**").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .addFilter(new CustomAuthenticationFilter(authenticationManager(), redisUtil, jwtUtil))
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)
                .authenticationEntryPoint(customHttp401AuthenticationEntryPoint);

        http.addFilterAt(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);*/
        /*http
                .authorizeRequests()
                .antMatchers("/druid/**").permitAll()
                .antMatchers("/settings**").hasRole("ADMIN")
                .antMatchers("/api**").permitAll()
                .antMatchers("/static**").permitAll()
                //.anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html").permitAll()
                .loginProcessingUrl("/api/login").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login.html")
                .permitAll()
                .and()
                .csrf().disable();*/
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
        /*auth
                .inMemoryAuthentication()
                .withUser("view")
                .password("gdutview")
                .roles("USER")
                .and()
                .withUser("admin").password("admin")
                .roles("ADMIN", "USER");*/
    }

    @Bean
    CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter filter = new CustomUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(customLoginHandler);
        filter.setAuthenticationFailureHandler(customLoginHandler);
        filter.setFilterProcessesUrl("/api/login");

        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    /*@Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }*/


}
