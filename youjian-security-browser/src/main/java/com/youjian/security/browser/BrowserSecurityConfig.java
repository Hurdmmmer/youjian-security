package com.youjian.security.browser;

import com.youjian.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * spring security 配置
 *
 * @author shen youjian
 * @date 12/8/2018 8:00 PM
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /** 注入自定义的登陆成功处理器 */
    @Autowired
    private AuthenticationSuccessHandler youjianAuthenticationSuccessHandler;
    /** 注入自定义验证失败处理器 */
    @Autowired
    private AuthenticationFailureHandler youjianAuthenticationFailureHandler;

    /**
     * 注入加密算法
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 当使用 formLogin 方法,表示当前验证为 form 表单的账号密码验证
         * 当使用 httpBasic 方法,表示当前使用 Basic 64 加密验证,
         * 基于表单的这
         */
        http.formLogin()
                .loginPage("/authentication/require") // 配置登陆页面
                // 配置让 UsernamePasswordAuthenticationFilter 基于账号密码的方式校验
                .loginProcessingUrl("/authentication/form")    // 配置验证拦url, 表单拦截表单提交的路径
                .successHandler(youjianAuthenticationSuccessHandler) // 配置登陆成功处理器
                .failureHandler(youjianAuthenticationFailureHandler) // 配置验证失败处理器
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",
                        securityProperties.getBrowser().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();
    }
}
