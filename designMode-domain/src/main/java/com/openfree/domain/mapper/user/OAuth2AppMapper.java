package com.openfree.domain.mapper.user;

import com.openfree.domain.model.user.OAuth2App;

public interface OAuth2AppMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OAuth2App record);

    int insertSelective(OAuth2App record);

    OAuth2App selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OAuth2App record);

    int updateByPrimaryKey(OAuth2App record);

    OAuth2App findByAppId(String appId);
}