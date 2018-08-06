package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateSMSResponseEntity implements Serializable{
    boolean success;
    int status;
    AuthenticateSMSResponseDataEntity data;

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

    public AuthenticateSMSResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateSMSResponseDataEntity data) {
        this.data = data;
    }
}
