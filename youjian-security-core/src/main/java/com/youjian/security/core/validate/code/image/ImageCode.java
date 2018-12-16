package com.youjian.security.core.validate.code.image;

import com.youjian.security.core.validate.code.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 *  图形验证码类
 * @author shen youjian
 * @date 12/10/2018 8:44 PM
 */
@Data
public class ImageCode extends ValidateCode {
    /** 图片信息 */
    private BufferedImage bufferedImage;


    /**
     * @param bufferedImage 验证码图片
     * @param code 验证码
     * @param expireIn 过期时间, 单位秒
     */
    public ImageCode(BufferedImage bufferedImage, String code, int expireIn) {
        super(code, LocalDateTime.now().plusSeconds(expireIn));
        this.bufferedImage = bufferedImage;
    }

    public ImageCode(BufferedImage bufferedImage, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.bufferedImage = bufferedImage;
    }

//    public boolean isExpired() {
//        // 判断当前事件是否在生成验证码的事件之后, 如果是 则返回 true
//        return LocalDateTime.now().isAfter(super.expireTime);
//    }
}
