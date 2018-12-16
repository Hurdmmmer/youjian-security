package com.youjian.security.core.properties;

import lombok.Data;

/**
 * 验证码属性, 包含图形验证码和短信验证码
 * @author shen youjian
 * @date 12/11/2018 8:27 PM
 */
@Data
public class ValidateCodeProperties {
    private ImageCodeProperties image = new ImageCodeProperties();
    private SmsCodeProperties sms = new SmsCodeProperties();
}
