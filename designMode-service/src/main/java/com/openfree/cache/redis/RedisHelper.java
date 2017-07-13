package com.openfree.cache.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

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
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, t);
        return valueOperations;
    }

    public <T> T getCacheObject(String key){
        ValueOperations<String,T> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }
}
