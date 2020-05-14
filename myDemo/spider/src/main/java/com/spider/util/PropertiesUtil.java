package com.spider.util;

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Properties;

/**
 * 取配置文件参数公共工具
 */
public class PropertiesUtil {
    /**
     *
     * @param properName 配置文件名
     * @param proVal 配置文件中参数名
     * @return 参数对应值
     */
    public static String getPath(String properName,String proVal) {
        String value = "";
        try {
            Properties prop = new Properties();
            InputStream in = new ClassPathResource("/"+properName).getInputStream();
            prop.load(in);
            value = (String)prop.get(proVal);
        }catch (Exception e){
            e.printStackTrace();
        }

        return value;
    }
}
