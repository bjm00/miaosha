package com.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    RedisPoolFactory redisPoolFactory;

    public <T> T get(String key, Class<T> clazz) {
        JedisPool jedisPool;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolFactory.JedisPoolFactory();
            jedis = jedisPool.getResource();
            String value = jedis.get(key);
            T t = stringToBean(value);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(String key, T value) {
        JedisPool jedisPool;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolFactory.JedisPoolFactory();
            jedis = jedisPool.getResource();
            String beanToString = beanToString(value);
            jedis.set(key, beanToString);
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    private <T> String beanToString(T value) {
        if (null == value) {
            return null;
        }

        Class<?> valueClass = value.getClass();

        if (valueClass == int.class || valueClass == Integer.class) {
            return "" + value;
        } else if (valueClass == String.class) {
            return (String) value;
        } else if (valueClass == long.class || valueClass == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (null != jedis) {
            jedis.close();
        }
    }

    private <T> T stringToBean(String value) {

        return null;
    }


}
