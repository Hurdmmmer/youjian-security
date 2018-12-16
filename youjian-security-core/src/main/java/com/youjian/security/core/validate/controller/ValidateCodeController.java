package com.youjian.security.core.validate.controller;

import com.youjian.security.core.properties.SecurityProperties;
import com.youjian.security.core.validate.ValidateCodeProcessorHolder;
import com.youjian.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码接口
 *
 * @author shen youjian
 * @date 12/10/2018 9:08 PM
 */
@RestController
public class ValidateCodeController {
    @Autowired
    private ValidateCodeGenerator imageValidateCodeGenerator;

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;
    @Autowired
    private SecurityProperties securityProperties;

    @PostConstruct
    public void init() {
        /** 初始化安全配置类, 子类注入父类属性 */
        if (null == imageValidateCodeGenerator.getSecurityProperties()) {
            imageValidateCodeGenerator.setSecurityProperties(securityProperties);
        }
    }

    @GetMapping("/code/{type}")
    public void validateCode(@PathVariable("type")String type, HttpServletRequest request, HttpServletResponse response) throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }


}