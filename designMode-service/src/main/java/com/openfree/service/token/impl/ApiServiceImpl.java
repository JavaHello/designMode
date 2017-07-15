package com.openfree.service.token.impl;

import com.github.pagehelper.util.StringUtil;
import com.openfree.cache.redis.CacheKey;
import com.openfree.cache.redis.RedisHelper;
import com.openfree.enums.ErrorCodeEnum;
import com.openfree.exception.ApiException;
import com.openfree.service.token.ApiService;
import com.openfree.service.token.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by luokai on 17-7-13.
 */
@Service("apiService")
public class ApiServiceImpl implements ApiService {

    @Autowired
    private RedisHelper redisHelper;

    @Override
    public TokenInfo genTokenInfo(String userId, Long id) throws ApiException {
        if (StringUtil.isEmpty(userId) || null == id){
            throw new ApiException(ErrorCodeEnum.PARAMETER_IS_NULL);
        }
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setId(id);
        tokenInfo.setUserId(userId);
        String secret = genRandomStr();
        String accessToken = genRandomStr();
        tokenInfo.setSecret(secret);
        tokenInfo.setAccessToken(accessToken);
        redisHelper.saveObj(buildTokenKey(userId,accessToken), tokenInfo);
        return tokenInfo;
    }

    private String buildTokenKey(String key, String accessToken){
        return CacheKey.ACCESS_TOKEN_CACHE_KEY + key + accessToken;
    }


    @Override
    public String genRandomStr() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
