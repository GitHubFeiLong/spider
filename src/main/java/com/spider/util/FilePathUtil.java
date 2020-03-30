package com.spider.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1.获取 数据库文件名的绝对路径
 * 2.获取 存储文件路径
 */
public class FilePathUtil {

    /**
     * 根据文件格式，获取文件绝对路径
     * fileFullName 文件名
     * @return
     */
    public static String getReadUrl(String fileFullName){
        // 检查是否是图片
        boolean flag = FilePathUtil.checkImg(fileFullName);
        String fullName = "";
        String prefix;
        if(flag){   // 是图片
            prefix = PropertiesUtil.getPath("config.properties", "read_img");
        } else{ // 不是图片
            prefix = PropertiesUtil.getPath("config.properties", "read_other");
        }
        fullName = prefix + fileFullName;
        return fullName;
    }

    /**
     * @param fileFullName
     * @return 返回文件存储的磁盘完整路径：路径+文件名+uuid+文件格式
     */
    public static String getSaveUrl(String fileFullName){
        // 检查是否是图片
        boolean flag = FilePathUtil.checkImg(fileFullName);
        StringBuffer fullName = new StringBuffer();
        String prefix;
        if(flag){   // 是图片
            prefix = PropertiesUtil.getPath("config.properties", "wirte_img");
        } else{ // 不是图片
            prefix = PropertiesUtil.getPath("config.properties", "wirte_other");
        }

        fullName.append(prefix).append(fileFullName);

        return fullName.toString();
    }

    /**
     * 判断文件是否是图片
     * @return true 是图片；false不是图片
     */
    public static boolean checkImg(String fileFullName){
        boolean flag = false;
        try{
            String reg = "\\.(jpg|gif|jpeg|png)+$";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(fileFullName);
            flag = matcher.find();
        } catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("判断文件是否是图片格式失败");
        }
        return flag;
    }

   
    public static void main(String[] args) {
//        System.out.println(FilePathUtil.checkImg("a.doc"));
//        System.out.println(FilePathUtil.checkImg("a.txt"));
//        System.out.println(FilePathUtil.checkImg("a.jpeg"));
//        System.out.println(FilePathUtil.checkImg("a.png"));

        System.out.println(FilePathUtil.getSaveUrl("a.png"));
    }
}
