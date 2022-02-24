package com.exam.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: gaoyk
 * @Date: 2021/12/28 5:48
 * 全局解决跨域问题
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")   //允许的路由
                .allowedOriginPatterns("*")    //允许跨域请求的域名
                .allowedMethods("*")    //设置允许的方法
                .allowCredentials(true);
                //.maxAge(3600);   跨域允许时间
    }
}
