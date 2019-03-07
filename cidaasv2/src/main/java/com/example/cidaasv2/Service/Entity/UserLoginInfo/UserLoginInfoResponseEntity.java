package com.example.cidaasv2.Service.Entity.UserLoginInfo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class UserLoginInfoResponseEntity implements Serializable {
    private Boolean success;
    private int status;
   // private UserLoginInfoResponseDataEntity[] data;

    @JsonProperty("data")
    private List<UserLoginInfoResponseDataEntity> data;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<UserLoginInfoResponseDataEntity> getData() {
        return data;
    }

    public void setData(List<UserLoginInfoResponseDataEntity> data) {
        this.data = data;
    }

    /* public UserLoginInfoResponseDataEntity[] getData() {
        return data;
    }

    public void setData(UserLoginInfoResponseDataEntity[] data) {
        this.data = data;
    }*/
}
