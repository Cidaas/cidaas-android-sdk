package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateVoiceMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    InitiateVoiceMFAResponseDataEntity data;

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

    public InitiateVoiceMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateVoiceMFAResponseDataEntity data) {
        this.data = data;
    }
}
