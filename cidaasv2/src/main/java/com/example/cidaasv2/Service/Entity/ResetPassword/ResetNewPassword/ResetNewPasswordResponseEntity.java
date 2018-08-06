package com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetNewPasswordResponseEntity implements Serializable{
    boolean success;
    int status;
    ResetNewPasswordResponseDataEntity data;

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

    public ResetNewPasswordResponseDataEntity getData() {
        return data;
    }

    public void setData(ResetNewPasswordResponseDataEntity data) {
        this.data = data;
    }
}
