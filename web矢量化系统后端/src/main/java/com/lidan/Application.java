package com.lidan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

@SpringBootApplication(scanBasePackages="com.lidan")
@EnableConfigurationProperties
@MapperScan(basePackages = "com.lidan.Dao")
public class Application {
    public static void main(String[] args)
    {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class) ;
        builder.bannerMode(Banner.Mode.OFF).run(args) ;


    }
}

