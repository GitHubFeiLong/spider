package com.cfl.jd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交的方法，需要使用该注解进行拦截。
 * 默认值：2s不能重复提交
 * 可以自定义
 */
@Target({ElementType.METHOD, ElementType.TYPE}) // 可以放在类和方法上
@Retention(RetentionPolicy.RUNTIME) //该注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在；
public @interface RepeatSubmit {
    // 重复提交的间隔设置为2s
    int value() default 2;
}
