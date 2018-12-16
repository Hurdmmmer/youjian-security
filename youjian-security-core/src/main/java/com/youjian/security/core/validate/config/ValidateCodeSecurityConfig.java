package com.youjian.security.core.validate.config;

import com.youjian.security.core.validate.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

/**
 *  自定义安全配置, 用于 httpSecurity.apply 方法使用,
 *  需要继承 {@link SecurityConfigurerAdapter}
 * @author shen youjian
 * @date 12/16/2018 9:02 PM
 */
@Component
public class ValidateCodeSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private ValidateCodeFilter validateCodeFilter;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);

    }
}
