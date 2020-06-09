package com.cfl.jd.util;

import java.time.LocalDateTime;

/**
 * @ClassName GetNow
 * @Author msi
 * @Date 2020/5/23 23:21
 * @Version 1.0
 */
public class GetNowUtil {

    /**
     * 获取当前时间返回格式
     * 2020-05-23 23:40:44.120
     * @return
     */
    public static String getDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();

        return localDateTime.toLocalDate() + " " + localDateTime.toLocalTime();
    }
}
