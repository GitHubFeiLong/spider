package com.cfl.jd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.cfl.jd.dao"}) // 可以不在mapper层添加注解
public class JDApplication {

    public static void main(String[] args) {
        SpringApplication.run(JDApplication.class, args);
    }

}
