package com.openfree.api.controller.v1;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.openfree.api.controller.BaseController;
import com.openfree.domain.model.user.UserInfo;
import com.openfree.enums.ErrorCodeEnum;
import com.openfree.exception.ApiException;
import com.openfree.service.token.TokenInfo;
import com.openfree.service.user.UserInfoService;
import com.openfree.service.util.PasswordHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by luokai on 17-7-15.
 */
@Controller
@RequestMapping("/v1/app/user")
public class UserInfoController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordHelper passwordHelper;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String registerUser(HttpServletRequest request){
        JSONObject respObj = new JSONObject();
        String interfaceName = "用户注册";
        try {
            String param = readRequestParam(request);
            logger.info(interfaceName + "请求参数:{}", param);
            JSONObject reqJson = JSON.parseObject(param);
            verifyParamEmpty(reqJson, "username", "upassword", "email", "province", "city","district");
            UserInfo userInfo = reqJson.toJavaObject(UserInfo.class);
            userInfoService.verifyExist(userInfo.getUsername(), userInfo.getEmail())
            userInfoService.registerUser(userInfo);
        } catch (ApiException e) {
            logger.error(interfaceName + "异常", e);
            putMessage(respObj, e.getCode(), e.getMessage());
        }catch (Exception e){
            logger.error(interfaceName + "异常", e);
            putErrorMessage(respObj);
        }

        logger.info(interfaceName + "返回结果:{}", respObj);
        return respObj.toJSONString();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request){
        JSONObject respObj = new JSONObject();
        String interfaceName = "用户登录";
        try {
            String param = readRequestParam(request);
            logger.info(interfaceName + "请求参数:{}", param);
            JSONObject reqJson = JSON.parseObject(param);
            verifyParamEmpty(reqJson, "username", "upassword");
            UserInfo userInfo = passwordHelper.verifyUserInfoPassword(reqJson.getString("upassword"), userInfoService.findByUserName(reqJson.getString("username")));
            TokenInfo tokenInfo = apiService.genTokenInfo(userInfo.getUsername(), userInfo.getId());

            respObj.put("userId", tokenInfo.getUserId());
            respObj.put("accessToken", tokenInfo.getAccessToken());
            respObj.put("secret", tokenInfo.getSecret());
            putSuccessMessage(respObj,interfaceName + "成功");
        } catch (ApiException e) {
            logger.error(interfaceName + "异常", e);
            putMessage(respObj, e.getCode(), e.getMessage());
        }catch (Exception e){
            logger.error(interfaceName + "异常", e);
            putErrorMessage(respObj);
        }

        logger.info(interfaceName + "返回结果:{}", respObj);
        return respObj.toJSONString();
    }
}
