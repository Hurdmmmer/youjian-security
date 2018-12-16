package com.youjian.security.core.validate.annotation;

import com.youjian.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.beans.BeansException;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 *   自定义条件装配逻辑
 * @author shen youjian
 * @date 12/11/2018 11:03 PM
 */
public class OnOtherValidateCodeGeneratorBean implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            context.getBeanFactory().getBean(ValidateCodeGenerator.class);
        } catch (BeansException e) {
            return true;
        }
        return false;
    }

}
