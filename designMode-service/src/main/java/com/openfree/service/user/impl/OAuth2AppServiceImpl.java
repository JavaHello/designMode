package com.openfree.service.user.impl;

import com.openfree.domain.mapper.user.OAuth2AppMapper;
import com.openfree.domain.model.user.OAuth2App;
import com.openfree.service.user.OAuth2AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("oAuth2AppService")
public class OAuth2AppServiceImpl implements OAuth2AppService {

    @Autowired
    private OAuth2AppMapper oAuth2AppMapper;

    @Override
    public int addObj(OAuth2App oAuth2App) {
        return oAuth2AppMapper.insert(oAuth2App);
    }

    @Override
    public int delObj(Long id) {
        return oAuth2AppMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateObj(OAuth2App oAuth2App) {
        return oAuth2AppMapper.updateByPrimaryKey(oAuth2App);
    }

    @Override
    public OAuth2App findById(Long id) {
        return null;
    }

    @Override
    public List<OAuth2App> findAll(Map<String, Object> queryMap) {
        return null;
    }

    @Override
    public boolean validateOAuth2AppKey(String appId) {
        OAuth2App oAuth2App = oAuth2AppMapper.findByAppId(appId);
        return oAuth2App != null;
    }

    @Override
    public OAuth2App findByAppId(String appId) {
        return oAuth2AppMapper.findByAppId(appId);
    }
}
