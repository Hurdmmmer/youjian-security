package com.youjian.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  spring 提供的拦截器, 可以获取当前请求访问的哪一个方法
 * @author shen youjian
 * @date 12/8/2018 3:21 PM
 */
@Component
public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        System.out.println("preHandle...");
        // 获取当前访问的方法
        System.out.println(((HandlerMethod)o).getBean().getClass().getName());
        System.out.println(((HandlerMethod)o).getMethod().getName());

        httpServletRequest.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle...");

        Long start = (Long) httpServletRequest.getAttribute("startTime");
        System.out.println("interceptor 耗时: " + (System.currentTimeMillis() - start));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion...");
        Long start = (Long) httpServletRequest.getAttribute("startTime");
        System.out.println("interceptor 耗时: " + (System.currentTimeMillis() - start));
        /**
         *
         * 获取当前访问的方法返回的异常, 这里注意如果当前项目中使用了全局处理异常 {@link com.youjian.exception.ControllerExceptionHandler}
         * 并处理当前方法抛出的异常 {@link com.youjian.exception.UserExistException }
         * 那么该方法将获取不到异常
          */
        System.out.println("exception is : " + e);
    }
}
