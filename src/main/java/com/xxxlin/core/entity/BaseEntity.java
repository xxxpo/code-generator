package com.xxxlin.core.entity;

import java.io.Serializable;

/**
 * Created by qyf on 2016/6/20.
 * 元数据，pojo
 */
public abstract class BaseEntity<ID> implements Serializable {

    protected static long serialVersionUID = 1L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public abstract ID getId();
    public abstract void setId(ID id);
    public abstract Long getCreateTime();
    public abstract void setCreateTime(Long time);
    public abstract Long getUpdateTime();
    public abstract void setUpdateTime(Long time);

}
