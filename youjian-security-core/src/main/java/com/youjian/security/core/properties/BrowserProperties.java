package com.youjian.security.core.properties;

import lombok.Data;

/**
 * @author shen youjian
 * @date 12/9/2018 11:13 AM
 */
@Data
public class BrowserProperties {
    /** 默认的登陆页面*/
    private String loginPage = "/loginImageCode.html";

    private String smsPage = "/loginSmsCode.html";

    /** 默认登陆类型 */
    private LoginType loginType = LoginType.JSON;

    /** 登陆成功跳转的页面 */
    private String successPage;

    /** 配置记住我的默认时间一小时 */
    private int rememberMeSeconds = 3600;

}
