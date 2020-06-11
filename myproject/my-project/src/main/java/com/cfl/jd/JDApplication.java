package com.cfl.jd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类描述：
 * SpringBoot启动类
 * @ClassName JDApplication
 * @Description TODO
 * @Author msi
 * @Date 2020/6/11 11:43
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.cfl.jd.dao"}) // 可以不在mapper层添加注解
public class JDApplication {

    public static void main(String[] args) {
        SpringApplication.run(JDApplication.class, args);
    }

}
