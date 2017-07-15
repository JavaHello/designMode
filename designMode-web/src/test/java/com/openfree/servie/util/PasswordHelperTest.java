package com.openfree.servie.util;

import com.openfree.BaseServiceTest;
import com.openfree.domain.model.user.UserInfo;
import com.openfree.exception.ApiException;
import com.openfree.service.util.PasswordHelper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by luokai on 17-7-15.
 */
public class PasswordHelperTest extends BaseServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordHelper passwordHelper;

    @Test
    public void testVerifyUserInfoPassword_1() throws ApiException {
        logger.info("Test: PasswordHelperTest.testVerifyUserInfoPassword_1()");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("zhang");
        userInfo.setUpassword("123123");
        passwordHelper.genUserInfoPassword(userInfo);
        passwordHelper.verifyUserInfoPassword("123123",userInfo);
        logger.info("SUCCESS");
    }
    @Test(expected = ApiException.class)
    public void testVerifyUserInfoPassword_2() throws ApiException {
        logger.info("Test: PasswordHelperTest.testVerifyUserInfoPassword_2()");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("zhang");
        userInfo.setUpassword("123123");
        passwordHelper.genUserInfoPassword(userInfo);
        passwordHelper.verifyUserInfoPassword("1231231",userInfo);
        logger.info("SUCCESS");
    }
}
