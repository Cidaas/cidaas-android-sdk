package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiatePatternMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    InitiatePatternMFAResponseDataEntity data;

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

    public InitiatePatternMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiatePatternMFAResponseDataEntity data) {
        this.data = data;
    }
}
