package com.openfree.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.openfree.enums.ErrorCodeEnum;
import com.openfree.exception.ApiException;
import com.openfree.service.token.ApiService;
import com.openfree.service.token.TokenInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by luokai on 17-7-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:spring/applicationContext.xml","classpath:spring/springmvc.xml"})
@WebAppConfiguration
@TransactionConfiguration(transactionManager = "transactionManager",defaultRollback = true)
@Transactional
public abstract class BaseControllerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ApiService apiService;

    protected MockMvc mockMvc;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public JSONObject post(String url, JSONObject param, TokenInfo tokenInfo, boolean sign, boolean verify) throws Exception {
        JSONObject respObj = null;
        if (sign){
            apiService.sign(param, tokenInfo);
        }
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post(url).content(param.toJSONString()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        respObj = JSON.parseObject(contentAsString);
        if (verify){
            apiService.verificationSign(param, tokenInfo);
        }
        logger.info("请求参数:{}\n返回参数:{}",param, respObj);
        return respObj;
    }

    protected void verifyRequest(JSONObject json){
        Assert.assertTrue("FAILURE", json.getInteger("code") == ErrorCodeEnum.SUCCESS.getCode());
    }
}
