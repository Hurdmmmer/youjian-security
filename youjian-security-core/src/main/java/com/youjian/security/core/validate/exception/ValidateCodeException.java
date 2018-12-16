package com.youjian.security.core.validate.exception;


import org.springframework.security.core.AuthenticationException;

/**
 *  自定义验证码异常, 继承 Spring Security定义的抽象异常
 * @author shen youjian
 * @date 12/10/2018 10:26 PM
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
