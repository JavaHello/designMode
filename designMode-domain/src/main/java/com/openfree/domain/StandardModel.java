package com.openfree.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by luokai on 17-7-15.
 */
public class StandardModel implements Examined,Serializable {

    private static final long serialVersionUID = 1L;
    protected Long id;

    protected Integer version;

    protected Date createTime;

    protected Long createUserId;

    protected Date lastUpdateTime;

    protected Long lastUpdateUserId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public Long getCreateUserId() {
        return createUserId;
    }

    @Override
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public Long getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    @Override
    public void setLastUpdateUserId(Long lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }
}
