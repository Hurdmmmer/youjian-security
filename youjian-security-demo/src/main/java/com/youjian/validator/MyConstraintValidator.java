package com.youjian.validator;

import com.youjian.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 实现自定义校验规则, 该类实现 ConstraintValidator接口会自动被spring 管理, 不需要使用 Component 等注解
 * 传入的泛型是 一个需要使用的校验规则的注解, 一个该注解可以使用在那些类型上, object 表示任意类型
 * @author shen youjian
 * @date 12/8/2018 9:25 AM
 */
public class MyConstraintValidator implements ConstraintValidator<MyValidator, Object> {

    // 可以直接注入 spring 管理的 bean
    @Autowired
    private HelloService helloService;


    @Override
    public void initialize(MyValidator constraintAnnotation) {
        // 初始化
    }

    /**
     *  校验逻辑, 返回true则进入当前请求的访问的方法中, 返回 false 则不进入
     * @param value 传如的值
     * @param context 校验的上下文
     * @return true 校验成功, false 校验失败
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 校验的逻辑
        System.out.println("value = " + value);
        helloService.helloService("to'm");
        return true;
    }
}
