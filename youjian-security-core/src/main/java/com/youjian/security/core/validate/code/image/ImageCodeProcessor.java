package com.youjian.security.core.validate.code.image;

import com.youjian.security.core.validate.code.AbstractValidateCodeProcessor;
import com.youjian.security.core.validate.code.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 *  图形验证码处理器
 * @author shen youjian
 * @date 12/14/2018 10:25 PM
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor {
    /**
     * 发送图形验证码，将其写到响应中
     */
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        ImageIO.write(((ImageCode)validateCode).getBufferedImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
