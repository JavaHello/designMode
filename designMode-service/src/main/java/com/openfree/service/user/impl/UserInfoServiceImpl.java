package com.openfree.service.user.impl;

import com.openfree.domain.mapper.user.UserInfoMapper;
import com.openfree.domain.model.user.UserInfo;
import com.openfree.enums.ErrorCodeEnum;
import com.openfree.exception.ApiException;
import com.openfree.service.user.UserInfoService;
import com.openfree.service.util.PasswordHelper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luokai on 17-7-15.
 */

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public int addObj(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    @Override
    public int delObj(Long id) {
        return userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateObj(UserInfo userInfo) {
        return userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public UserInfo findById(Long id) {
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserInfo> findAll(Map<String, Object> queryMap) {
        return userInfoMapper.findAll(queryMap);
    }

    @Override
    public void registerUser(UserInfo userInfo) {
        passwordHelper.genUserInfoPassword(userInfo);
        userInfoMapper.insert(userInfo);
    }

    @Override
    public UserInfo findByUserName(String username) {
        return userInfoMapper.findByUserName(username);
    }

    @Override
    public boolean verifyExist(String username, String email) throws ApiException {
        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("username", username);
        List<UserInfo> userInfoList = userInfoMapper.selectByUsernameOrEmail(queryMap);
        if (CollectionUtils.isNotEmpty(userInfoList)){
            throw new ApiException(ErrorCodeEnum.USER_EXIST);
        }
        queryMap.remove("username");
        queryMap.put("email", email);
        userInfoList = userInfoMapper.selectByUsernameOrEmail(queryMap);
        if (CollectionUtils.isNotEmpty(userInfoList)){
            throw new ApiException(ErrorCodeEnum.EMAIL_EXIST);
        }
        return false;

    }
}
