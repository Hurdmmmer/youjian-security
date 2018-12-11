package com.youjian.security.core.validate.code;

import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 *  图形验证码类
 * @author shen youjian
 * @date 12/10/2018 8:44 PM
 */
@Data
public class ImageCode {
    /** 图片信息 */
    private BufferedImage bufferedImage;
    /** 随机数 */
    private String code;
    /** 过期事件 */
    private LocalDateTime expireTime;

    /**
     * @param bufferedImage 验证码图片
     * @param code 验证码
     * @param expireIn 过期时间, 单位秒
     */
    public ImageCode(BufferedImage bufferedImage, String code, int expireIn) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ImageCode(BufferedImage bufferedImage, String code, LocalDateTime expireTime) {
        this.bufferedImage = bufferedImage;
        this.code = code;
        this.expireTime = expireTime;
    }

    boolean isExpired() {
        // 判断当前事件是否在生成验证码的事件之后, 如果是 则返回 true
        return LocalDateTime.now().isAfter(this.expireTime);
    }
}
