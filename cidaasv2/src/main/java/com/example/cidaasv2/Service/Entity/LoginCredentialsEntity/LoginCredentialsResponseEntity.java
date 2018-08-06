package com.example.cidaasv2.Service.Entity.LoginCredentialsEntity;


import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordResultDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginCredentialsResponseEntity implements Serializable {
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AccessTokenEntity getData() {
        return data;
    }

    public void setData(AccessTokenEntity data) {
        this.data = data;
    }

    private boolean success;
    private int status;
    private AccessTokenEntity data;
}
