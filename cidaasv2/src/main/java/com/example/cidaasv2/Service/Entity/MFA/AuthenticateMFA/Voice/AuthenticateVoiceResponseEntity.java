package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateVoiceResponseEntity implements Serializable{
    boolean success;
    int status;
    AuthenticateVoiceResponseDataEntity data;

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

    public AuthenticateVoiceResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateVoiceResponseDataEntity data) {
        this.data = data;
    }
}
