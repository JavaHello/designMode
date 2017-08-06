package com.openfree.api.controller.v1;

import com.openfree.api.controller.BaseController;
import com.openfree.cache.redis.RedisHelper;
import com.openfree.domain.model.user.OAuth2App;
import com.openfree.exception.ApiException;
import com.openfree.service.user.OAuth2AppService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@RestController("v1/app/oauth2")
public class AuthorizeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OAuth2AppService oAuth2AppService;

    @Autowired
    private RedisHelper redisHelper;

    @RequestMapping("/authorize")
    @ResponseBody
    public String authorize(HttpServletRequest request)  {
        String responseStr = "";
        try {
            OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
            if (!Pattern.compile("^[a-zA-Z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\s*)?$").matcher(oauthRequest.getRedirectURI()).matches()) {
                OAuthResponse oauthResponse = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.CodeResponse.INVALID_REQUEST)
                        .setErrorDescription(OAuthError.OAUTH_ERROR_URI)
                        .buildJSONMessage();
                logger.error("oauthRequest.getRedirectURI() : " + oauthRequest.getRedirectURI() + " oauthResponse.getBody() : " + oauthResponse.getBody());
                return oauthResponse.getBody();
            }
            OAuth2App oAuth2App = oAuth2AppService.findByAppId(oauthRequest.getClientId());
            if (oAuth2App == null){
                OAuthResponse oauthResponse = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                        .setErrorDescription(OAuthError.CodeResponse.UNAUTHORIZED_CLIENT)
                        .buildJSONMessage();
                logger.error("oauthRequest.getClientId() : "+oauthRequest.getClientId()+" oauthResponse.getBody() : "+oauthResponse.getBody());
                return oauthResponse.getBody();
            }
            String userId = request.getParameter("userId");
            String userToken = request.getParameter("userToken");

            if (userToken == null || userId == null || apiService.verificationTokenInfo(userId, userToken) == null){
                return "{message:\"请用户登录后授权\"}";
            }
            //判断此次请求是否是用户授权
            if(request.getParameter("action")==null||!request.getParameter("action").equalsIgnoreCase("authorize")){
                //到申请用户同意授权页
                return "{message:\"请用户同意授权\"}";
            }

            //生成授权码 UUIDValueGenerator OR MD5Generator
            String authorizationCode = new OAuthIssuerImpl(new MD5Generator()).authorizationCode();
            //把授权码存入缓存
            redisHelper.saveObj(authorizationCode, DigestUtils.sha1Hex(oauthRequest.getClientId()+oauthRequest.getRedirectURI()));
            //构建oauth2授权返回信息
            OAuthResponse oauthResponse = OAuthASResponse
                    .authorizationResponse(request,HttpServletResponse.SC_FOUND)
                    .setCode(authorizationCode)
                    .location(oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI))
                    .buildQueryMessage();
            return oauthResponse.getBody();

        } catch (OAuthProblemException e) {
            e.printStackTrace();
            responseStr = buildErrorMessage(e);
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return responseStr;
    }

    private String buildErrorMessage(OAuthProblemException oe) {
        try {
            return OAuthResponse
                            .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                            .error(oe)
                            .buildJSONMessage().getBody();
        } catch (OAuthSystemException e) {
            e.printStackTrace();
        }
        return "授权异常";
    }


    @RequestMapping(value = "/access_token",method = RequestMethod.POST)
    @ResponseBody
    public String access_token(HttpServletRequest request, HttpServletResponse response)
            throws IOException, OAuthSystemException {
        String responseStr = "";
        OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

        //构建oauth2请求
        try {
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
            //验证redirecturl格式是否合法 (8080端口测试)
            if (!Pattern.compile("^[a-zA-Z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\s*)?$").matcher(oauthRequest.getRedirectURI()).matches()) {
                OAuthResponse oauthResponse = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.CodeResponse.INVALID_REQUEST)
                        .setErrorDescription(OAuthError.OAUTH_ERROR_URI)
                        .buildJSONMessage();
                return oauthResponse.getBody();
            }

            OAuth2App oAuth2App = oAuth2AppService.findByAppId(oauthRequest.getClientId());
            if (oAuth2App == null){
                OAuthResponse oauthResponse = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.CodeResponse.ACCESS_DENIED)
                        .setErrorDescription(OAuthError.CodeResponse.UNAUTHORIZED_CLIENT)
                        .buildJSONMessage();
                return oauthResponse.getBody();
            }
            if (!oAuth2App.getAppSecret().equals(oauthRequest.getClientSecret())){
                OAuthResponse oauthResponse = OAuthASResponse
                        .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                        .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                        .setErrorDescription("INVALID_CLIENT_SECRET")
                        .buildJSONMessage();
                return oauthResponse.getBody();
            }
            String authzCode = oauthRequest.getCode();
            //验证AUTHORIZATION_CODE , 其他的还有PASSWORD 或 REFRESH_TOKEN (考虑到更新令牌的问题，在做修改)
            if (GrantType.AUTHORIZATION_CODE.name().equalsIgnoreCase(oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE))) {
                if (redisHelper.getCacheObject(authzCode) == null) {
                    OAuthResponse oauthResponse = OAuthASResponse
                            .errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                            .setError(OAuthError.TokenResponse.INVALID_GRANT)
                            .setErrorDescription("INVALID_CLIENT_GRANT")
                            .buildJSONMessage();
                    return oauthResponse.getBody();
                }
            }

            //生成token
            final String accessToken = oauthIssuerImpl.accessToken();
            String refreshToken = oauthIssuerImpl.refreshToken();
            //cache.put(accessToken,cache.get(authzCode).get());
            redisHelper.saveObj(accessToken,accessToken);
            redisHelper.saveObj(refreshToken,accessToken);
            logger.info("accessToken : "+accessToken +"  refreshToken: "+refreshToken);
            //清除授权码 确保一个code只能使用一次
            redisHelper.removeCacheObject(authzCode);
            //构建oauth2授权返回信息
            OAuthResponse oauthResponse = OAuthASResponse
                    .tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn("3600")
                    .setRefreshToken(refreshToken)
                    .buildJSONMessage();
            responseStr = oauthResponse.getBody();
        } catch (OAuthProblemException e) {
            e.printStackTrace();
        }

        return responseStr;
    }

}   