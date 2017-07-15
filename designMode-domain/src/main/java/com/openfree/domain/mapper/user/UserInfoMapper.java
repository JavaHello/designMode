package com.openfree.domain.mapper.user;

import com.openfree.domain.model.user.UserInfo;

import java.util.List;
import java.util.Map;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    List<UserInfo> findAll(Map<String, Object> queryMap);

    UserInfo findByUserName(String username);
}