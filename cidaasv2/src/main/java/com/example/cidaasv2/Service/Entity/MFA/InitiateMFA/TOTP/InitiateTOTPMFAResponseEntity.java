package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateTOTPMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    InitiateTOTPMFAResponseDataEntity data;

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

    public InitiateTOTPMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateTOTPMFAResponseDataEntity data) {
        this.data = data;
    }
}
