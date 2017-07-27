package com.openfree.api.controller.v1;

import com.alibaba.fastjson.JSONObject;
import com.openfree.api.controller.BaseControllerTest;
import com.openfree.domain.model.user.UserInfo;
import com.openfree.enums.ErrorCodeEnum;
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
        reqObj.put("email", "123123@sdf.com");
        JSONObject respObj = redisterUser(reqObj);
        verifyResponseParam(respObj);
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

    private JSONObject redisterUser(JSONObject reqObj) throws Exception {

        reqObj.put("upassword", "123123");
        reqObj.put("gender", "1");
        reqObj.put("province", "北京");
        reqObj.put("city", "北京市");
        reqObj.put("district", "朝阳区");
        return post("/v1/app/user/register", reqObj, null, false, false);
    }

    @Test
    public void testRepetitionRegisterUser() throws Exception {
        logger.info("Test: UserInfoControllerTest.testRepetitionRegisterUser()");
        JSONObject reqObj = new JSONObject();
        reqObj.put("username", "zhang");
        reqObj.put("email", "123123@sdf.com");
        JSONObject respObj = redisterUser(reqObj);
        verifyResponseParam(respObj);

        reqObj.put("username", "zhang");
        reqObj.put("email", "12312asas3@sdf.com");
        JSONObject respObj2 = redisterUser(reqObj);
        Assert.assertTrue("重复注册：" + respObj2.getString(CODE_MSG), ErrorCodeEnum.USER_EXIST.getCode() == respObj2.getInteger(CODE));

        reqObj.put("username", "zhangsss");
        reqObj.put("email", "123123@sdf.com");
        JSONObject respObj3 = redisterUser(reqObj);
        Assert.assertTrue("重复注册：" + respObj3.getString(CODE_MSG), ErrorCodeEnum.EMAIL_EXIST.getCode() == respObj3.getInteger(CODE));
    }

    @Test
    public void testLogin() throws Exception {
        logger.info("Test: UserInfoControllerTest.testLogin()");

        logger.info("SUCCESS");
    }

}
