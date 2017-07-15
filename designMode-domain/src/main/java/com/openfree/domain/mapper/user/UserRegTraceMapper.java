package com.openfree.domain.mapper.user;

import com.openfree.domain.model.user.UserRegTrace;

public interface UserRegTraceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRegTrace record);

    int insertSelective(UserRegTrace record);

    UserRegTrace selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRegTrace record);

    int updateByPrimaryKey(UserRegTrace record);

    UserRegTrace selectByUserId(Long id);
}