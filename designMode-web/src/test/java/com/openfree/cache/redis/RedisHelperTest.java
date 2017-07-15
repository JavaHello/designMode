package com.openfree.cache.redis;

import com.openfree.BaseServiceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

/**
 * Created by luokai on 17-7-14.
 */
public class RedisHelperTest extends BaseServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisHelper redisHelper;

    @Test
    public void testSaveObjAndGetObj(){
        logger.info("Test: RedisHelperTest.testSaveObjAndGetObj()");
        String obj = "world";
        String key = "hello";
        redisHelper.saveObj(key, obj, 5);
        Object hello = redisHelper.getCacheObject(key);
        logger.info("saveObj[key:{},value:{}]", key, hello);
        assertTrue(obj.equals(hello));
        logger.info("SUCCESS");
    }
}
