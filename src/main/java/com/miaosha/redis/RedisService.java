package com.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisService {

    @Autowired
    RedisPoolFactory redisPoolFactory;

    /**
     * 从redis获取值
     *
     * @param prefix
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        JedisPool jedisPool;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolFactory.JedisPoolFactory();
            jedis = jedisPool.getResource();
            //生成真正的key
            String redisKey = prefix.getPrefix() + key;
            String value = jedis.get(redisKey);
            T t = stringToBean(value, clazz);
            return t;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 向redis赋值
     *
     * @param prefix
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        JedisPool jedisPool;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolFactory.JedisPoolFactory();
            jedis = jedisPool.getResource();
            String redisValue = beanToString(value);
            //生成真正的key
            String redisKey = prefix.getPrefix() + key;
            int seconds = prefix.expirePrefix();
            if (seconds <= 0) {
                jedis.set(redisKey, redisValue);
            } else {
                jedis.setex(redisKey, seconds, redisValue);
            }
            return true;
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断redis的key是否存在
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exist(KeyPrefix prefix, String key) {
        JedisPool jedisPool;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolFactory.JedisPoolFactory();
            jedis = jedisPool.getResource();
            //生成真正的key
            String redisKey = prefix.getPrefix() + key;
            return jedis.exists(redisKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * redis的key的value增加1
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix, String key) {
        JedisPool jedisPool;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolFactory.JedisPoolFactory();
            jedis = jedisPool.getResource();
            //生成真正的key
            String redisKey = prefix.getPrefix() + key;
            return jedis.incr(redisKey);
        } finally {
            returnToPool(jedis);
        }
    }

    /**
     * redis的key的value减少1
     *
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        JedisPool jedisPool;
        Jedis jedis = null;
        try {
            jedisPool = redisPoolFactory.JedisPoolFactory();
            jedis = jedisPool.getResource();
            //生成真正的key
            String redisKey = prefix.getPrefix() + key;
            return jedis.decr(redisKey);
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

    //list如何处理？
    private <T> T stringToBean(String value, Class<T> tClass) {
        if (StringUtils.isBlank(value) || tClass == null) {
            return null;
        }
        if (tClass == int.class || tClass == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (tClass == String.class) {
            return (T) value;
        } else if (tClass == long.class || tClass == Long.class) {
            return (T) Long.valueOf(value);
        } else {
            return JSON.toJavaObject(JSON.parseObject(value), tClass);
        }
    }
}
