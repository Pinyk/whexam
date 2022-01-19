package com.exam.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: gaoyk
 * @Date: 2022/1/19 13:36
 */
@Configuration
@MapperScan("com.exam.demo.mapper")
public class MybatisPlusConfig {
}
