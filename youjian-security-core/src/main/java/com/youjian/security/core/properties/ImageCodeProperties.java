package com.youjian.security.core.properties;

import lombok.Data;

/**
 *  图形验证码默认配置
 * @author shen youjian
 * @date 12/11/2018 7:57 PM
 */
@Data
public class ImageCodeProperties {
    private int width = 80;// 图片宽
    private int height = 20;// 图片高
    private int lineSize = 40;// 干扰线数量
    private int length = 4;// 随机产生字符数量
    private int expiredIn = 60; // 过期时间

    private String urls; // 配置拦截验证码拦截的url
}
