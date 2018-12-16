package com.youjian.security.core.authentication;

import com.youjian.security.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author shen youjian
 * @date 12/16/2018 8:54 PM
 */
public abstract class AbstractSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    protected AuthenticationSuccessHandler youjianAuthenticationSuccessHandler;

    @Autowired
    protected AuthenticationFailureHandler youjianAuthenticationFailureHandler;

    protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
        /**
         * 当使用 formLogin 方法,表示当前验证为 form 表单的账号密码验证
         * 当使用 httpBasic 方法,表示当前使用 Basic 64 加密验证,
         * 基于表单的这
         */
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL) // 配置登陆页面
                // 配置让 UsernamePasswordAuthenticationFilter 基于账号密码的方式校验
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)    // 配置表单验证拦截的url, 该配置默认拦截路径 /login , 用于生效 UsernamePasswordAuthenticationFilter
                .successHandler(youjianAuthenticationSuccessHandler) // 配置登陆成功处理器
                .failureHandler(youjianAuthenticationFailureHandler); // 配置验证失败处理器
    }
}
