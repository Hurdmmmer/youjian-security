package com.youjian.security.browser;

import com.youjian.security.core.authentication.AbstractSecurityConfig;
import com.youjian.security.core.properties.SecurityConstants;
import com.youjian.security.core.properties.SecurityProperties;
import com.youjian.security.core.validate.code.sms.SmsCodeAuthenticationSecurityConfig;
import com.youjian.security.core.validate.config.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * spring security 配置
 *
 * @author shen youjian
 * @date 12/8/2018 8:00 PM
 */
@Configuration
public class BrowserSecurityConfig extends AbstractSecurityConfig {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 短信验证码的配置
     */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    /**
     * 记住我数据源
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 记住我数据库存储
     */
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 设置启动的时候创建表
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 注入加密算法
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 条用父类中添加账号密码认证 form 表单
        applyPasswordAuthenticationConfig(http);

        http        // 加入我们短信验证码, 验证提供处理器, 模仿账号密码验证
                .apply(smsCodeAuthenticationSecurityConfig)
            .and()
                .apply(validateCodeSecurityConfig)
            .and()
            .rememberMe() // 配置记住我的功能
                .tokenRepository(tokenRepository())
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
            .and()
            .authorizeRequests()  //授权配置
                .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                        securityProperties.getBrowser().getLoginPage(),
                        securityProperties.getBrowser().getSmsPage(),
                        SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*")
                .permitAll()
            .anyRequest()  // 其他请求
                .authenticated() // 都需要权限
            .and()
                .csrf().disable();
    }
}
