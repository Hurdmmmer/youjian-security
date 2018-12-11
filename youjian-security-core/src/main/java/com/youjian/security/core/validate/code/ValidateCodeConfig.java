package com.youjian.security.core.validate.code;

import com.youjian.security.core.validate.annotation.ConditionalOnOtherValidateCodeGenerator;
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
    @ConditionalOnOtherValidateCodeGenerator
    public ValidateCodeGenerator imageCodeGenerator() {
        return new DefaultValidateCodeGenerator();
    }
}
