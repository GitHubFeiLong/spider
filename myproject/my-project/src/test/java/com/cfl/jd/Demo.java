package com.cfl.jd;

import java.time.LocalDateTime;

/**
 * @ClassName Demo
 * @Author msi
 * @Date 2020/5/23 23:10
 * @Version 1.0
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now().toLocalDate() + " - " + LocalDateTime.now().toLocalTime());
        System.out.println(LocalDateTime.now().toString());
//        System.out.println(LocalDateTime.now().);
    }
}
