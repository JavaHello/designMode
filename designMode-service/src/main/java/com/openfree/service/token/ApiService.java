package com.openfree.service.token;

import com.alibaba.fastjson.JSONObject;
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
     * 验证token
     * @param userId 唯一标识
     * @param accessToken 访问令牌
     * @return token
     */
    TokenInfo verificationTokenInfo(String userId, String accessToken) throws ApiException;

    /**
     * 生成token
     * @param userId 用户唯一标识
     * @param id 用户id
     * @param expire 过期时间
     * @return TokenInfo
     * @throws ApiException 生成失败抛出
     */
    TokenInfo genTokenInfo(String userId,Long id, Long expire) throws ApiException;

    /**
     * 生成随机字符串(全字母)
     * @return 随机字符
     */
    String genRandomStr();

    /**
     * 添加签名
     * @param param 需要签名的参数
     * @param tokenInfo 访问令牌
     */
    void sign(JSONObject param, TokenInfo tokenInfo) throws ApiException;

    /**
     * 验证签名
     * @param param 需要验证参数
     * @param tokenInfo 访问令牌
     * @return 返回验证结果
     */
    boolean verificationSign(JSONObject param, TokenInfo tokenInfo) throws ApiException;
}
