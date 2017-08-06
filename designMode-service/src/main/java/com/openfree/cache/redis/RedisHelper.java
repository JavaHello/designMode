package com.openfree.cache.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * redis 工具
 * Created by luokai on 17-7-13.
 */
public class RedisHelper {

    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T>ValueOperations<String, T> saveObj(String key, T t){
        return saveObj(key, t, -1);
    }

    public <T>ValueOperations<String, T> saveObj(String key, T t, long expire){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, t);
        if ( expire > 0) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return valueOperations;
    }

    public <T> T getCacheObject(String key){
        ValueOperations<String,T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void removeCacheObject(Object key){
        redisTemplate.delete(key);
    }
}
