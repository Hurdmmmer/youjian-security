package com.youjian.security.core.properties;

import lombok.Data;

/**
 *  短信验证码默认配置
 * @author shen youjian
 * @date 12/11/2018 7:57 PM
 */
@Data
public class SmsCodeProperties {
    private int length = 4;// 随机产生字符数量
    private int expiredIn = 60; // 过期时间
    private String urls;

    public SmsCodeProperties() {
    }

    public SmsCodeProperties(int length) {
        this.length = length;
    }
}
