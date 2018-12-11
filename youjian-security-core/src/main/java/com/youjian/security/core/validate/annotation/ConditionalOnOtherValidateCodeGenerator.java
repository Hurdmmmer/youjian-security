package com.youjian.security.core.validate.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 条件装配自定义注解, 如果没有实现了验证码生成逻辑 {@link com.youjian.security.core.validate.code.ValidateCodeGenerator}
 * 则装配默认的验证码生成器
 * @author shen youjian
 * @date 12/11/2018 11:02 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(OnOtherValidateCodeGeneratorBean.class)
public @interface ConditionalOnOtherValidateCodeGenerator {
}
