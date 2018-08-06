package com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordResponseEntity implements Serializable{
    boolean success;
    int status;
    ChangePasswordResponseDataEntity data;

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

    public ChangePasswordResponseDataEntity getData() {
        return data;
    }

    public void setData(ChangePasswordResponseDataEntity data) {
        this.data = data;
    }
}
