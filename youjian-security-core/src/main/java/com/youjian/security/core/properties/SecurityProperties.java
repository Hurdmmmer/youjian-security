package com.youjian.security.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *  安全配置类, 一些安全信息的配置, 如 登陆页面, json 等
 * @author shen youjian
 * @date 12/9/2018 11:12 AM
 */
@Data
@ConfigurationProperties(prefix = "youjian.security")
public class SecurityProperties {
    private BrowserProperties browser = new BrowserProperties();
}
