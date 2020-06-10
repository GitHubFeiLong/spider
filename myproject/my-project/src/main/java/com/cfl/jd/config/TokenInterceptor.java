package com.cfl.jd.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类描述：
 * 拦截器
 * @ClassName TokenInterceptor
 * @Description TODO 拦截器，之前想的是用来解决表单重复提交，暂时未用
 * @Author msi
 * @Date 2020/6/10 19:34
 * @Version 1.0
 */
@Configuration
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("执行preHandle方法-->01");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("执行postHandle方法-->02");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("执行afterCompletion方法-->03");
    }
}

