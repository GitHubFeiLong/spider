package com.cfl.qq_42428264.lang.redis;

import com.sun.jna.WString;
import redis.clients.jedis.Jedis;

import java.util.List;

public class JedisListJava {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        jedis.auth("123456");

        // 存储数据库,最后一个元素在最左边
        jedis.lpush("site-list", "Runoob", "one", "two");
        // 左闭右闭
        List<String> list = jedis.lrange("site-list",0,2);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("列表项为：" + list.get(i));
        }
    }
}
