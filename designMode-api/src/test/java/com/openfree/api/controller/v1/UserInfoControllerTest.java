package com.openfree.api.controller.v1;

import com.alibaba.fastjson.JSONObject;
import com.openfree.api.controller.BaseControllerTest;
import com.openfree.domain.model.user.UserInfo;
import com.openfree.service.user.UserInfoService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by luokai on 17-7-15.
 */
public class UserInfoControllerTest extends BaseControllerTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoService userInfoService;

    @Test
    public void testRegisterUser() throws Exception {
        logger.info("Test: UserInfoControllerTest.testRegisterUser()");
        JSONObject reqObj = new JSONObject();
        //"username", "upassword", "email", "province", "city","district",gender
        reqObj.put("username", "zhang");
        reqObj.put("upassword", "123123");
        reqObj.put("gender", "1");
        reqObj.put("email", "123123@open.com");
        reqObj.put("province", "北京");
        reqObj.put("city", "北京市");
        reqObj.put("district", "朝阳区");
        JSONObject respObj = post("/v1/app/user/register", reqObj, null, false, false);
        verifyRequest(respObj);
        UserInfo zhang = userInfoService.findByUserName("zhang");
        Assert.assertTrue("注册失败:参数不匹配", new EqualsBuilder()
                .append(zhang.getUsername(),reqObj.getString("username"))
                .append(zhang.getGender(),reqObj.getShort("gender"))
                .append(zhang.getEmail(),reqObj.getString("email"))
                .append(zhang.getProvince(),reqObj.getString("province"))
                .append(zhang.getCity(),reqObj.getString("city"))
                .append(zhang.getDistrict(),reqObj.getString("district"))
                .build());
    }

    @Test
    public void testLogin() throws Exception {
        logger.info("Test: UserInfoControllerTest.testLogin()");

        logger.info("SUCCESS");
    }

}
