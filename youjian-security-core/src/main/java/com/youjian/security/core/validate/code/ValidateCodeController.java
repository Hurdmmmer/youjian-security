package com.youjian.security.core.validate.code;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 *  验证码接口
 * @author shen youjian
 * @date 12/10/2018 9:08 PM
 */
@RestController
public class ValidateCodeController {
    public final static String SESSION_KEY = "SESSION_KEY_VALIDATE_CODE";
    /** Session 工具支持 */
    private SessionStrategy sessionStrategy =  new HttpSessionSessionStrategy();

    @GetMapping("/code/image")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode = createImageCode(request);
        // 将验证码存放到session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        // 将验证码输出到页面中
        ImageIO.write(Objects.requireNonNull(imageCode).getBufferedImage(), "JPEG", response.getOutputStream());
    }

    /**
     *  生成图片验证码
     */
    private ImageCode createImageCode(HttpServletRequest request) {
        ValidateCode validateCode = new ValidateCode();
        return new ImageCode(validateCode.getRandcode(), validateCode.getRandCodeStr(), 60);
    }
}
