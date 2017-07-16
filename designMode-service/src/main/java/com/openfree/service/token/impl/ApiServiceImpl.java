package com.openfree.service.token.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.openfree.cache.redis.CacheKey;
import com.openfree.cache.redis.RedisHelper;
import com.openfree.enums.ErrorCodeEnum;
import com.openfree.exception.ApiException;
import com.openfree.service.token.ApiService;
import com.openfree.service.token.TokenInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by luokai on 17-7-13.
 */
@Service("apiService")
public class ApiServiceImpl implements ApiService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final static String SIGN = "sign";

    @Autowired
    private RedisHelper redisHelper;

    @Override
    public TokenInfo genTokenInfo(String userId, Long id) throws ApiException {
        return genTokenInfo(userId, id, -1L);
    }

    @Override
    public TokenInfo verificationTokenInfo(String userId, String accessToken) throws ApiException {
        Object cacheObject = redisHelper.getCacheObject(buildTokenKey(userId, accessToken));
        if (cacheObject instanceof TokenInfo){
            return (TokenInfo) cacheObject;
        }else {
            throw new ApiException(ErrorCodeEnum.TOKEN_INVALID);
        }
    }

    @Override
    public TokenInfo genTokenInfo(String userId, Long id, Long expire) throws ApiException {
        if (StringUtil.isEmpty(userId) || null == id) {
            throw new ApiException(ErrorCodeEnum.PARAMETER_IS_NULL);
        }
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setId(id);
        tokenInfo.setUserId(userId);
        String secret = genRandomStr();
        String accessToken = genRandomStr();
        tokenInfo.setSecret(secret);
        tokenInfo.setAccessToken(accessToken);
        redisHelper.saveObj(buildTokenKey(userId, accessToken), tokenInfo, expire);
        return tokenInfo;
    }

    private String buildTokenKey(String key, String accessToken) {
        return CacheKey.ACCESS_TOKEN_CACHE_KEY + key + accessToken;
    }


    @Override
    public String genRandomStr() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    @Override
    public void sign(JSONObject param, TokenInfo tokenInfo) throws ApiException {
        if (param == null){
            logger.debug("参数为空,不予签名");
            return;
        }
        if (tokenInfo == null){
            logger.debug("token为空,不予签名");
            return;
        }
        if ( ErrorCodeEnum.SUCCESS.getCode() != param.getInteger("code") ){
            logger.debug("请求不成功,不予签名");
            return;
        }
        String signParam = genSignParam(param, SIGN);
        String singValue = new Md5Hash(signParam, tokenInfo.getSecret()).toHex();
        param.put(SIGN, singValue);

    }

    @Override
    public boolean verificationSign(JSONObject param, TokenInfo tokenInfo) throws ApiException {
        if (param == null)
            throw new ApiException(ErrorCodeEnum.PARAMETER_IS_NULL);
        if (tokenInfo == null)//invalid
            throw new ApiException(ErrorCodeEnum.TOKEN_INVALID);
        String signParam = genSignParam(param, SIGN);
        String signValue = new Md5Hash(signParam, tokenInfo.getSecret()).toHex();
        if(signValue.equals(param.getString(SIGN))){
            return true;
        }else {//Failure
            throw new ApiException(ErrorCodeEnum.VERIFICATION_SIGN_FAILURE);
        }
    }

    private String genSignParam(JSONObject param, String sign) {
        StringBuilder sb = new StringBuilder();
        Set<String> key = param.keySet();
        String[] keys = key.toArray(new String[key.size()]);
        for (String s : keys) {
            String value = param.getString(s);
            if (null == value)
                continue;
            if (sign.equals(s)) {
                continue;
            }
            sb.append(s).append("=").append(value).append("&");
        }
        return sb.toString();
    }
}
