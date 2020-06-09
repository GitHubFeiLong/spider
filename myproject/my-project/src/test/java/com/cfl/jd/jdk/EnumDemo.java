package com.cfl.jd.jdk;

import java.util.Arrays;

/**
 * @ClassName EnumDemo
 * @Author msi
 * @Date 2020/6/9 11:59
 * @Version 1.0
 */
public class EnumDemo {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(EnumTest.values()));
        EnumTest enumTest = EnumTest.SPRING;
        System.out.println(enumTest.getSeq());
    }
}
