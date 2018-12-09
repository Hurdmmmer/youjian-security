package com.youjian.security.browser.authentication;

import com.youjian.security.core.properties.LoginType;
import com.youjian.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功处理器, 默认实现接口 {@link AuthenticationSuccessHandler}
 * 当前使用继承 {@link SavedRequestAwareAuthenticationSuccessHandler} 这个处理器,
 * 该处理器, 提供了页面跳转的功能
 *
 * @author shen youjian
 * @date 12/9/2018 5:44 PM
 */
@Component("youjianAuthenticationSuccessHandler")
@Slf4j
public class YoujianAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private SecurityProperties properties;

    /**
     * @param request 请求
     * @param response 响应
     * @param authentication 登陆成功后, 该参数包含了一些用户的信息, 权限等
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("login success");

        if (properties.getBrowser().getLoginType().equals(LoginType.REDIRECT)) {
            // 父类中的方法就跳转页面
            super.onAuthenticationSuccess(request, response, authentication);
        } else {

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }

    }
}
