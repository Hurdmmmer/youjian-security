package com.youjian.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *  该注解必须包含下面的三个字段, 必须指定该校验使用的逻辑
 *  由注解 @Constraint 指定 {@link MyConstraintValidator}
 * @author shen youjian
 * @date 12/8/2018 9:27 AM
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyValidator {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
