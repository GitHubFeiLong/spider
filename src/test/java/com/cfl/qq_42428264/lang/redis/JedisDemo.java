package com.cfl.qq_42428264.lang.redis;

import redis.clients.jedis.Jedis;

public class JedisDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1");
        jedis.auth("123456");
        System.out.println("连接成功");
        // 查看服务器是否运行
        System.out.println("服务正在运行：" + jedis.ping());

        // 设置字符串
        jedis.set("name", "陈飞龙");
        // 获取设置的键
        System.out.print("redis 存储的字符串为：" + jedis.get("name"));
    }
}
