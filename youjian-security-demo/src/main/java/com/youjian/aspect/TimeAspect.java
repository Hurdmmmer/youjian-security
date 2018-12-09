package com.youjian.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author shen youjian
 * @date 12/8/2018 4:28 PM
 */
@Aspect
@Component
public class TimeAspect {


    @Around(value = "execution(* com.youjian.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("aspect 开始");
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg = " + arg);
        }
        Object result = pjp.proceed();
        System.out.println("aspect 结束");
        return result;
    }
}
