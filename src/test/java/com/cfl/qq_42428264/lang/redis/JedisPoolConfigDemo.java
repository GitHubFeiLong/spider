package com.cfl.qq_42428264.lang.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolConfigDemo {
    public static void main(String[] args) {
        // 连接池
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxTotal(18);
        JedisPool pool = new JedisPool(config, "127.0.0.1", 6379, 2000, "123456");
        Jedis jedis = pool.getResource();
        String value = jedis.get("key");
        jedis.close();
        pool.close();
    }
}
