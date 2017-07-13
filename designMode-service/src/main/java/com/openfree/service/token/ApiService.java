package com.openfree.service.token;

import com.openfree.exception.ApiException;

/**
 * Created by luokai on 17-7-13.
 */
public interface ApiService {

    /**
     * 生成token
     * @param userId 用户唯一标识
     * @param id 用户id
     * @return TokenInfo
     * @throws ApiException 生成失败抛出
     */
    TokenInfo genTokenInfo(String userId,Long id) throws ApiException;

    /**
     * 生成随机字符串(全字母)
     * @return 随机字符
     */
    String genRandomStr();
}
