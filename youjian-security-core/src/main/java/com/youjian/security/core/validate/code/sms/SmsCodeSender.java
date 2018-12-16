package com.youjian.security.core.validate.code.sms;

/**
 * 发送短信验证码的接口
 * @author shen youjian
 * @date 12/13/2018 9:14 PM
 */
public interface SmsCodeSender {
    /**
     * 发送短信
     * @param phone 手机号
     * @param code 验证码
     */
    void send(String phone, String code);
}
