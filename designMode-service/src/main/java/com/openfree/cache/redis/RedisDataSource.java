package com.openfree.cache.redis;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 暂时不使用
 * 当作笔记
 * Created by luokai on 17-7-14.
 */

@Deprecated
public class RedisDataSource {

    private ShardedJedisPool shardedJedisPool;

    public ShardedJedis getRedisClient() {
        try {
            return shardedJedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean returnResoutce(ShardedJedis shardedJedis) {
        try {
            shardedJedisPool.returnResource(shardedJedis);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean returnResource(ShardedJedis shardedJedis, boolean broken) {
        try {
            if (broken) {
                shardedJedisPool.returnBrokenResource(shardedJedis);
            } else {
                shardedJedisPool.returnResource(shardedJedis);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
