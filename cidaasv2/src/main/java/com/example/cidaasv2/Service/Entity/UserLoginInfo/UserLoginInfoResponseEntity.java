package com.example.cidaasv2.Service.Entity.UserLoginInfo;

import java.io.Serializable;

public class UserLoginInfoResponseEntity implements Serializable {
    private Boolean success;
    private int status;
    private UserLoginInfoResponseDataEntity data;


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

    public UserLoginInfoResponseDataEntity getData() {
        return data;
    }

    public void setData(UserLoginInfoResponseDataEntity data) {
        this.data = data;
    }
}
