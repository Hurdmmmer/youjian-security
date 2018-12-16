package com.youjian.security.core.validate.code.sms;

/**
 * @author shen youjian
 * @date 12/13/2018 9:24 PM
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String phone, String code) {
        System.out.println("往手机号: " + phone + "发送验证码: " + code);
    }
}
