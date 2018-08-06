package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateFIDOResponseEntity implements Serializable{
    boolean success;
    int status;
    AuthenticateFIDOResponseDataEntity data;

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

    public AuthenticateFIDOResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateFIDOResponseDataEntity data) {
        this.data = data;
    }
}
