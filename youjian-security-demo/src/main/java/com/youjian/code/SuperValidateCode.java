package com.youjian.code;

import com.youjian.security.core.validate.code.ValidateCodeGenerator;
import com.youjian.security.core.validate.code.image.DefaultImageCodeUtils;
import com.youjian.security.core.validate.code.image.ImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 测试新的配置图形验证码生成器
 * @author shen youjian
 * @date 12/11/2018 10:50 PM
 */
@Component("imageValidateCodeGenerator")
@Slf4j
public class SuperValidateCode extends ValidateCodeGenerator {


    @Override
    public ImageCode generate(ServletWebRequest request) {
        log.info("自定义输出图形验证码");
        DefaultImageCodeUtils defaultImageCodeUtils = new DefaultImageCodeUtils();
        int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", securityProperties.getCode().getImage().getWidth());
        int length = securityProperties.getCode().getImage().getLength();
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", securityProperties.getCode().getImage().getHeight());
        int expireIn = securityProperties.getCode().getImage().getExpiredIn();
        defaultImageCodeUtils.setHeight(height);
        defaultImageCodeUtils.setLength(length);
        defaultImageCodeUtils.setWidth(width);

        return new ImageCode(defaultImageCodeUtils.getRandcode(), defaultImageCodeUtils.getRandCodeStr(), expireIn);
    }
}
