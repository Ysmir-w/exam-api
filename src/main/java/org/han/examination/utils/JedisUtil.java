package org.han.examination.utils;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class JedisUtil {

    private final JedisPool jedisPool;

    private JedisUtil() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(2);
        jedisPoolConfig.setMaxTotal(10);
        String host = "192.168.165.67";
        int port = 6379;
        String password = "5214zxc***";
        jedisPool = new JedisPool(jedisPoolConfig, host, port, null, password);
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }
}
