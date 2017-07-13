package com.openfree.service.token;

import java.io.Serializable;

/**
 * Created by luokai on 17-7-13.
 */
public class TokenInfo implements Serializable {

    private String userId;

    private Long id;

    private String accessToken;

    private String secret;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "userId='" + userId + '\'' +
                ", id=" + id +
                ", accessToken='" + accessToken + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
