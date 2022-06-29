package com.youngyedu.rjzentaoauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.youngyedu.rjzentaoauth.mapper")
public class RjZentaoAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(RjZentaoAuthApplication.class, args);
    }

}
