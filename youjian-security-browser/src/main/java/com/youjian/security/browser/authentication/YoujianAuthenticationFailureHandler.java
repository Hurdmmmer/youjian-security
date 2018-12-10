package com.youjian.security.browser.authentication;

import com.youjian.security.browser.support.SimpleResponse;
import com.youjian.security.core.properties.LoginType;
import com.youjian.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  验证错误处理器, 原来实现 {@link org.springframework.security.web.authentication.AuthenticationFailureHandler}
 *  但是该接口不支持页面跳转, 我们则使用继承 spring 默认提供的 {@link SimpleUrlAuthenticationFailureHandler}
 *  该类提供了页面跳转
 * @author shen youjian
 * @date 12/9/2018 6:11 PM
 */
@Component("youjianAuthenticationFailureHandler")
@Slf4j
public class YoujianAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private SecurityProperties securityProperties;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("login failure");
        if (securityProperties.getBrowser().getLoginType().equals(LoginType.JSON)) {
            // 我们提供了错误的 json 格式返回
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
        } else {
            super.onAuthenticationFailure(request,response, exception);
        }
    }
}
