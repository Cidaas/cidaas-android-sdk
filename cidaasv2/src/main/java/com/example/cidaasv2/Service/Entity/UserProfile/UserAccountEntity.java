package com.example.cidaasv2.Service.Entity.UserProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccountEntity implements Serializable {
    String _id;
    String updatedTime;
    String createdTime;
    String className;
    String userStatus;
    String sub;
    String lastLoggedInTime;
    String lastUsedIdentity;
    int __v;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getLastLoggedInTime() {
        return lastLoggedInTime;
    }

    public void setLastLoggedInTime(String lastLoggedInTime) {
        this.lastLoggedInTime = lastLoggedInTime;
    }

    public String getLastUsedIdentity() {
        return lastUsedIdentity;
    }

    public void setLastUsedIdentity(String lastUsedIdentity) {
        this.lastUsedIdentity = lastUsedIdentity;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
