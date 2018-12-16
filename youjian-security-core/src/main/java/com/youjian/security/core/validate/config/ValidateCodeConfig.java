package com.youjian.security.core.validate.config;

import com.youjian.security.core.validate.code.ValidateCodeGenerator;
import com.youjian.security.core.validate.code.image.DefaultImageCodeGenerator;
import com.youjian.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.youjian.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  条件装配验证码生成逻辑, 默认使用自带的验证码生成逻辑
 * @author shen youjian
 * @date 12/11/2018 10:43 PM
 */
@Configuration
public class ValidateCodeConfig {

    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator() {
        return new DefaultImageCodeGenerator();
    }

    /**
     *  如果有其他的 SmsCodeSender 接口的实现, 就不注册默认的实现
     */
    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender() {
        return new DefaultSmsCodeSender();
    }

}
