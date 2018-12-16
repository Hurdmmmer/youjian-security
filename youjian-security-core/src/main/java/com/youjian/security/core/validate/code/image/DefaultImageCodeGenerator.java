package com.youjian.security.core.validate.code.image;

import com.youjian.security.core.validate.code.ValidateCode;
import com.youjian.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 *  默认的图形验证码生成逻辑
 * @author shen youjian
 * @date 12/11/2018 10:41 PM
 */
public class DefaultImageCodeGenerator extends ValidateCodeGenerator {

    @Override
    public ValidateCode generate(ServletWebRequest request) {
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
