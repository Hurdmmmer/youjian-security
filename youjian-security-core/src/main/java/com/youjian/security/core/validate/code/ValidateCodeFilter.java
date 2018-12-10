package com.youjian.security.core.validate.code;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 实现spring提供的工具类 {@link OncePerRequestFilter}
 * 来保证一次请求该过滤器只会被执行一次, 可以用于做校验验证码
 * @author shen youjian
 * @date 12/10/2018 9:49 PM
 */
@Slf4j
@Data
public class ValidateCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        只有在登陆请求并且是post的方式才做验证码校验
        if (StringUtils.equals("/authentication/form", httpServletRequest.getRequestURI()) &&
            StringUtils.equalsAnyIgnoreCase("POST", httpServletRequest.getMethod())) {
            // 校验验证码 ServletWebRequest 是 spring 提供的 request 工具类
            try{
                validate(new ServletWebRequest(httpServletRequest));
            }catch (ValidateCodeException e) {
                // 使用我们自定义的错误处理器处理这个异常
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return ;
            }
        }
        // 否则执行下面的拦截器,不进行校验
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 验证验证码是否合法
     * @param request
     * @throws ValidateCodeException
     */
    private void validate(ServletWebRequest request) throws ValidateCodeException {
        // session 取出验证码
        ImageCode imageCode = (ImageCode) sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY);
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
        if (imageCode.isExpired()) {
            // 过期移出该验证码
            sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
            throw new ValidateCodeException("验证码已过期");
        }
        if (!StringUtils.equalsAnyIgnoreCase(codeInRequest, imageCode.getCode())) {
            throw new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY);
    }


}
