package com.youjian.security.core.validate.filter;

import com.youjian.security.core.properties.SecurityConstants;
import com.youjian.security.core.properties.SecurityProperties;
import com.youjian.security.core.validate.code.ValidateCode;
import com.youjian.security.core.validate.code.ValidateCodeProcessor;
import com.youjian.security.core.validate.code.image.ImageCode;
import com.youjian.security.core.validate.controller.ValidateCodeController;
import com.youjian.security.core.validate.exception.ValidateCodeException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 实现spring提供的工具类 {@link OncePerRequestFilter}
 * 来保证一次请求该过滤器只会被执行一次, 可以用于做校验验证码
 * 实现 {@link InitializingBean} 用来spring 初始化 bean 对象后再继续注入需要的配置
 *
 * @author shen youjian
 * @date 12/10/2018 9:49 PM
 */
@Slf4j
@Data
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    // 安全配置类
    private SecurityProperties securityProperties;
    private Set<String> urls = new HashSet<>();


    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String url = securityProperties.getCode().getImage().getUrls();
        String[] configUrls = StringUtils.split(url, ",");
        if (configUrls != null) {
            urls.addAll(Arrays.asList(configUrls));  // 配置文件中配置的url
        }
        urls.add("/authentication/form"); // 配置基本的表单路径
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        boolean flag = false;
        // 校验是否匹配配置文件中拦截的 url
        for (String url : urls) {
            if (pathMatcher.match(url, httpServletRequest.getRequestURI())) {
                flag = true;
                break;
            }
        }
        if (flag) {
            // 校验验证码 ServletWebRequest 是 spring 提供的 request 工具类
            try {
                validate(new ServletWebRequest(httpServletRequest));
            } catch (ValidateCodeException e) {
                // 使用我们自定义的错误处理器处理这个异常
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }

        // 否则执行下面的拦截器,不进行校验
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 验证验证码是否合法
     *
     * @param request
     * @throws ValidateCodeException
     */
    private void validate(ServletWebRequest request) throws ValidateCodeException {
        // session 取出验证码
        ValidateCode imageCode = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
        // 请求中获取验证码
        String codeInRequest = null;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
        } catch (ServletRequestBindingException e) {
            log.error("Failed to get verification code: {}", e.getMessage(), e);
        }

        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (imageCode == null) {
            throw new ValidateCodeException("验证码不存在");
        }
        if (imageCode.isExpried()) {
            // 过期移出该验证码
            sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equalsAnyIgnoreCase(codeInRequest, imageCode.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX + "IMAGE");
    }


}
