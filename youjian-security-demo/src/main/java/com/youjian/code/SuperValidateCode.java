package com.youjian.code;

import com.youjian.security.core.validate.code.ImageCode;
import com.youjian.security.core.validate.code.ValidateCode;
import com.youjian.security.core.validate.code.ValidateCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 测试新的配置图形验证码生成器
 * @author shen youjian
 * @date 12/11/2018 10:50 PM
 */
@Component
@Slf4j
public class SuperValidateCode extends ValidateCodeGenerator {


    @Override
    public ImageCode generateImageCode(HttpServletRequest request) {
        log.info("自定义输出图形验证码");
        ValidateCode validateCode = new ValidateCode();
        int width = ServletRequestUtils.getIntParameter(request, "width", securityProperties.getCode().getImage().getWidth());
        int length = securityProperties.getCode().getImage().getLength();
        int height = ServletRequestUtils.getIntParameter(request, "height", securityProperties.getCode().getImage().getHeight());
        int expireIn = securityProperties.getCode().getImage().getExpiredIn();
        validateCode.setHeight(height);
        validateCode.setLength(length);
        validateCode.setWidth(width);

        return new ImageCode(validateCode.getRandcode(), validateCode.getRandCodeStr(), expireIn);
    }
}
