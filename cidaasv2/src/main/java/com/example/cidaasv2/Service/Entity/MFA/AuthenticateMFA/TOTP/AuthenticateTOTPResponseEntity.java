package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateTOTPResponseEntity implements Serializable{
    boolean success;
    int status;
    AuthenticateTOTPResponseDataEntity data;

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

    public AuthenticateTOTPResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateTOTPResponseDataEntity data) {
        this.data = data;
    }
}
