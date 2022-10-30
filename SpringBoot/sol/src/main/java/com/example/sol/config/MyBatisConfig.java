package com.example.sol.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.example.sol.mapper")
public class MyBatisConfig {
    
}
