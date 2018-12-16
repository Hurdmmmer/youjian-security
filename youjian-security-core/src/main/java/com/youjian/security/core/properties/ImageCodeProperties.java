package com.youjian.security.core.properties;

import lombok.Data;

/**
 *  图形验证码默认配置
 * @author shen youjian
 * @date 12/11/2018 7:57 PM
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{
    private int width = 80;// 图片宽
    private int height = 20;// 图片高
    private int lineSize = 40;// 干扰线数量

    public ImageCodeProperties() {
        // 设置默认的长度为4
        super(4);
    }
}
