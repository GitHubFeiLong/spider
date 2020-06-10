package com.cfl.jd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 类描述：
 * SpringMVC的拦截器
 * @ClassName WebMvcConfig
 * @Description TODO  SpringMVC的拦截器，未使用
 * @Author msi
 * @Date 2020/6/10 19:34
 * @Version 1.0
 */
//@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册自定义拦截器，添加拦截路径和排除拦截路径
//        registry.addInterceptor(new TokenInterceptor()) // 添加拦截器
//                .addPathPatterns("/**") // 添加拦截路径
//                .excludePathPatterns(// 添加排除拦截路径(因为springboot2.0路径不包含static，所以只能使用类路径下的static)
//                        "login.html").order(0);//执行顺序
//        super.addInterceptors(registry);
    }

}

