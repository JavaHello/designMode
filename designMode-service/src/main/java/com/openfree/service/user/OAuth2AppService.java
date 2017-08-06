package com.openfree.service.user;

import com.openfree.domain.model.user.OAuth2App;
import com.openfree.service.BaseService;

public interface OAuth2AppService extends BaseService<OAuth2App>{
    /**
     * 验证appId 是否存在
     * @param appId
     * @return
     */
    boolean validateOAuth2AppKey(String appId);

    /**
     * 根据appId查询
     * @param appId　appId
     * @return app 信息
     */
    OAuth2App findByAppId(String appId);
}
