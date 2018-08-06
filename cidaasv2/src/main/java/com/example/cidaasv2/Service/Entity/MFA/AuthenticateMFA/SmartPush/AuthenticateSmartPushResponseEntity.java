package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateSmartPushResponseEntity implements Serializable{
    boolean success;
    int status;
    AuthenticateSmartPushResponseDataEntity data;

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

    public AuthenticateSmartPushResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateSmartPushResponseDataEntity data) {
        this.data = data;
    }
}
