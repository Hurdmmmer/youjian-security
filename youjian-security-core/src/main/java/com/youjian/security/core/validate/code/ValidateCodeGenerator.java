package com.youjian.security.core.validate.code;

import com.youjian.security.core.properties.SecurityProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码接口, 用户其他用户配置更高级的生成验证码的逻辑
 *
 * @author shen youjian
 * @date 12/11/2018 10:39 PM
 */
@Data
public abstract class ValidateCodeGenerator {

    @Autowired
    protected SecurityProperties securityProperties;

    /***
     * 构建验证码实现方法
     * @param request
     * @return
     */
    public abstract ValidateCode generate(ServletWebRequest request);
}

