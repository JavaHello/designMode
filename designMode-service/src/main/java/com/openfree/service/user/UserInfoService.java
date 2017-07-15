package com.openfree.service.user;

import com.openfree.domain.model.user.UserInfo;
import com.openfree.service.BaseService;

/**
 * Created by luokai on 17-7-15.
 */
public interface UserInfoService extends BaseService<UserInfo> {

    /**
     * 注册用户
     * @param userInfo 用户信息
     */
    void registerUser(UserInfo userInfo);

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return userInfo
     */
    UserInfo findByUserName(String username);
}
