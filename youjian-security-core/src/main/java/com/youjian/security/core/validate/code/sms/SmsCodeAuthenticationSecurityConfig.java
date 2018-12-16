package com.youjian.security.core.validate.code.sms;

import com.youjian.security.core.mobile.SmsAuthenticationFilter;
import com.youjian.security.core.mobile.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 *  配置短信验证码拦截器, 验证器, 必须要继承 {@link SecurityConfigurerAdapter}
 *  才能将我们写的拦截器加入到 Spring Security 管理中, 指定泛型
 * @author shen youjian
 * @date 12/16/2018 12:42 PM
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        // 实例短信验证拦截器
        SmsAuthenticationFilter smsAuthenticationFilter = new SmsAuthenticationFilter();
        //http.getSharedObject(AuthenticationManager.class) 获取验证管理器
        smsAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        // 配置登陆成功, 失败处理器
        smsAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        smsAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);


        // 配置短信验证处理器
        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider();
        smsAuthenticationProvider.setUserDetailsService(userDetailsService);

        // 加入我们自定义的短信验证逻辑到 Spring Security 管理中, 在 UsernamePasswordAuthenticationFilter 后面执行
        http.authenticationProvider(smsAuthenticationProvider)
                .addFilterAfter(smsAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
