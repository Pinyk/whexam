package com.exam.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: gaoyk
 * @Date: 2022/2/28 21:32
 * 热部署
 */
@Configuration

public class FileUploadConfig implements WebMvcConfigurer {

        public void addResourceHandlers(ResourceHandlerRegistry registry) {

            String path = System.getProperty("user.dir")+"\\src\\main\\resources\\resources\\study\\";

            registry.addResourceHandler("/study/**").addResourceLocations("file:"+path);

        }

}
