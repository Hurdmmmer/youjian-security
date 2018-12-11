package com.youjian.security.core.validate.code;

import com.youjian.security.core.properties.SecurityProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 *  默认的图形验证码生成逻辑
 * @author shen youjian
 * @date 12/11/2018 10:41 PM
 */
public class DefaultValidateCodeGenerator extends ValidateCodeGenerator {

    @Override
    public ImageCode generateImageCode(HttpServletRequest request) {
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
