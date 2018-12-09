package com.youjian.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *  自定义异常处理 @ControllerAdvice 注解用于Controller抛出异常时会执行该注解修饰的异常处理类
 * @author shen youjian
 * @date 12/8/2018 10:33 AM
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * @ExceptionHandler 注解指定该方法处理的异常对象
     * @param e 异常对象
     * @return 处理完成后的结果集
     */
    @ExceptionHandler(UserExistException.class)
    @ResponseBody     // 指定返回的 json 格式
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 返回的状态码
    public Map<String, Object> handUserExistException(UserExistException e) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", e.getId());
        result.put("message", e.getMessage());
        return result;
    }

}
