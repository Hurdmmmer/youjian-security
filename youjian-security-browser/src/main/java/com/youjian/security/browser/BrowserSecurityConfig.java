package com.youjian.security.browser;

import com.youjian.security.core.validate.code.sms.SmsCodeAuthenticationSecurityConfig;
import com.youjian.security.core.properties.SecurityProperties;
import com.youjian.security.core.validate.filter.SmsValidateCodeFilter;
import com.youjian.security.core.validate.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    /** 注入自定义的登陆成功处理器 */
    @Autowired
    private AuthenticationSuccessHandler youjianAuthenticationSuccessHandler;
    /** 注入自定义验证失败处理器 */
    @Autowired
    private AuthenticationFailureHandler youjianAuthenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;
    /** 短信验证码的配置 */
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    /** 记住我数据源 */
    @Autowired
    private DataSource dataSource;
    /** 记住我数据库存储 */
    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 设置启动的时候创建表
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
    ////////////////////////////////////////////////////////////////////////////////

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

        ///////////////////////////// 配置图片验证码验证 ///////////////////////////////

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        // 设置失败处理器
        validateCodeFilter.setAuthenticationFailureHandler(youjianAuthenticationFailureHandler);
        // 注入我们验证码拦截的url, 并执行后置属性配置
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        ///////////////////////////// 配置短信验证码 ///////////////////////////
        SmsValidateCodeFilter smsValidateCodeFilter = new SmsValidateCodeFilter();
        // 设置失败处理器
        smsValidateCodeFilter.setAuthenticationFailureHandler(youjianAuthenticationFailureHandler);
        // 注入我们验证码拦截的url, 并执行后置属性配置
        smsValidateCodeFilter.setSecurityProperties(securityProperties);
        smsValidateCodeFilter.afterPropertiesSet();

        /**
         * 当使用 formLogin 方法,表示当前验证为 form 表单的账号密码验证
         * 当使用 httpBasic 方法,表示当前使用 Basic 64 加密验证,
         * 基于表单的这
         */
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class) // 添加前置我们图形的验证码过滤器, 会在 UsernamePasswordAuthenticationFilter 前面执行
            .addFilterBefore(smsValidateCodeFilter, UsernamePasswordAuthenticationFilter.class)//添加短信验证码过滤器, 在 UsernamePasswordAuthenticationFilter 前面
                .formLogin()
                    .loginPage("/authentication/require") // 配置登陆页面
                    // 配置让 UsernamePasswordAuthenticationFilter 基于账号密码的方式校验
                    .loginProcessingUrl("/authentication/form")    // 配置表单验证拦截的url, 该配置默认拦截路径 /login , 用于生效 UsernamePasswordAuthenticationFilter
                    .successHandler(youjianAuthenticationSuccessHandler) // 配置登陆成功处理器
                    .failureHandler(youjianAuthenticationFailureHandler) // 配置验证失败处理器
                    .and()
                .rememberMe() // 配置记住我的功能
                    .tokenRepository(tokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                    .and()
                .authorizeRequests()  //授权配置
                    .antMatchers("/authentication/require",
                            securityProperties.getBrowser().getLoginPage(),
                            securityProperties.getBrowser().getSmsPage(),
                            "/code/*")
                    .permitAll()
                    .anyRequest()  // 其他请求
                    .authenticated()
                    .and()
                .csrf().disable()
                // 加入我们短信验证码, 验证提供处理器, 相当于给SpringSecurity 中注入 UsernamePasswordAuthenticationFilter
                .apply(smsCodeAuthenticationSecurityConfig);
    }
}
