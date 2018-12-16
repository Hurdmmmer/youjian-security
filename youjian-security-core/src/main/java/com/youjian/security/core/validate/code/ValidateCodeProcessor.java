package com.youjian.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 检验码处理器, 封装不同的验证码的处理逻辑
 * 短信验证码, 图片验证码
 * @author shen youjian
 * @date 12/14/2018 9:51 PM
 */
public interface ValidateCodeProcessor {
    /** 验证码存入session 中的前置名称 */
    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     *  创建验证码
     */
    void create(ServletWebRequest request) throws Exception;

    /** 校验验证码是否过期 */
    @SuppressWarnings("unchecked")
    void validate(ServletWebRequest request);
}
