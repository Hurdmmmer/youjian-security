package com.youjian.security.browser;

import com.youjian.security.browser.support.SimpleResponse;
import com.youjian.security.core.properties.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  该控制器用于跳转返回 html 或 json
 * @author shen youjian
 * @date 12/9/2018 10:34 AM
 */
@RestController
@Slf4j
public class BrowserSecurityController {
    /** 可以获取当前请求的原来需要访问的 url 地址 */
    private RequestCache requestCache = new HttpSessionRequestCache();
    /** 重定向工具类 */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @RequestMapping("/authentication/require")
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取缓存在session中的原来访问的url
        SavedRequest req = requestCache.getRequest(request, response);
        if (null != req) {
            String redirectUrl = req.getRedirectUrl();
            log.info("引发跳转的请求是: {}", redirectUrl);
            if (redirectUrl.endsWith(".html")){
                redirectStrategy.sendRedirect(request,response, securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问服务需要身份验证, 清引导用户进入登陆页面");
    }

}
