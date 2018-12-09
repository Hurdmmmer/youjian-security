package com.youjian.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author shen youjian
 * @date 12/8/2018 11:09 AM
 */
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("执行耗时: " + (System.currentTimeMillis() - start));
        System.out.println("拦截执行成功");
    }

    @Override
    public void destroy() {
        System.out.println("filter destroy");
    }
}
