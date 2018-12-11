package com.youjian.security.core.validate.code;

import com.youjian.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 图形验证码接口, 用户其他用户配置更高级的生成验证码的逻辑
 *
 * @author shen youjian
 * @date 12/11/2018 10:39 PM
 */

public abstract class ValidateCodeGenerator {

    @Autowired
    protected SecurityProperties securityProperties;

    public abstract ImageCode generateImageCode(HttpServletRequest request);
}

