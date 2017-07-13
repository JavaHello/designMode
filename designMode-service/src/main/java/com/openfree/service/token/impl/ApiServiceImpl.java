package com.openfree.service.token.impl;

import com.github.pagehelper.util.StringUtil;
import com.openfree.enums.ErrorCodeEnum;
import com.openfree.exception.ApiException;
import com.openfree.service.token.ApiService;
import com.openfree.service.token.TokenInfo;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by luokai on 17-7-13.
 */
@Service("apiService")
public class ApiServiceImpl implements ApiService {

    @Override
    public TokenInfo genTokenInfo(String userId, Long id) throws ApiException {
        if (StringUtil.isEmpty(userId) || null == id){
            throw new ApiException(ErrorCodeEnum.PARAMETER_IS_NULL);
        }
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setId(id);
        tokenInfo.setUserId(userId);
        String secret = genRandomStr();
        tokenInfo.setSecret(secret);


        return null;
    }

    private String buildSignParam(String userId, Integer id){
        return userId + id;
    }

    private String buildSingAccessToken(String key, String secret){
        //TODO 此处MD5(key + secret)

        return null;
    }


    @Override
    public String genRandomStr() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }
}
