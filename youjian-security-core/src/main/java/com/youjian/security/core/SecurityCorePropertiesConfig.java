package com.youjian.security.core;

import com.youjian.security.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *  注册读取安全配置属性
 * @author shen youjian
 * @date 12/9/2018 11:17 AM
 */
@Configuration
@EnableConfigurationProperties(value = SecurityProperties.class)
public class SecurityCorePropertiesConfig {
}
