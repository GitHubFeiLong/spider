package com.cfl.qq_42428264.lang.redis;

import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

public class RedisKeyJava {
    public static void main(String[] args) {
        // 连接本地数据库
        Jedis jedis = new Jedis("192.168.1.168");
        jedis.auth("123456");
        System.out.println("连接成功！");
        // 获取数据输出
        Set<String> keys = jedis.keys("*");
        Iterator<String> it = keys.iterator();
        while(it.hasNext()){
            String s = it.next();
            System.out.println(s);
        }


    }
}
