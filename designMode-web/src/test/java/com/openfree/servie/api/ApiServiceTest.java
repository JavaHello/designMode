package com.openfree.servie.api;

import com.alibaba.fastjson.JSONObject;
import com.openfree.BaseServiceTest;
import com.openfree.enums.ErrorCodeEnum;
import com.openfree.exception.ApiException;
import com.openfree.service.token.ApiService;
import com.openfree.service.token.TokenInfo;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by luokai on 17-7-15.
 */
public class ApiServiceTest extends BaseServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApiService apiService;

    @Test
    public void testSign() throws ApiException {
        logger.info("Test: ApiServiceTest.testSign()");
        TokenInfo tokenInfo = apiService.genTokenInfo("test", 213L, 5L);
        apiService.verificationTokenInfo(tokenInfo.getUserId(), tokenInfo.getAccessToken());
        JSONObject json = new JSONObject();
        json.put("name", "zhang");
        json.put("passworld", "li");
        json.put("code", ErrorCodeEnum.SUCCESS.getCode());
        apiService.sign(json, tokenInfo);
        Assert.assertTrue("验证签名失败", apiService.verificationSign(json,tokenInfo));
        logger.info("sing:{}", json);
        logger.info("SUCCESS");
    }

    @Test(expected = ApiException.class)
    public void testVerificationTokenInfo() throws ApiException, InterruptedException {
        logger.info("Test: ApiServiceTest.testVerificationTokenInfo()");
        TokenInfo tokenInfo = apiService.genTokenInfo("test", 213L, 1L);
        Thread.sleep(2000);
        apiService.verificationTokenInfo(tokenInfo.getUserId(), tokenInfo.getAccessToken());
        logger.info("SUCCESS");
    }
}
