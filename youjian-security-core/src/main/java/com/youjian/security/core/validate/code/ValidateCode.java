package com.youjian.security.core.validate.code;

import lombok.Data;

import java.time.LocalDateTime;

/**
 *  短信验证码
 * @author shen youjian
 * @date 12/13/2018 8:39 PM
 */
@Data
public class ValidateCode {
    /** 随机数 */
    private String code;
    /** 过期事件 */
    protected LocalDateTime expireTime;

    public ValidateCode(String code, int expire) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expire);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }


    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expireTime);
    }
}
