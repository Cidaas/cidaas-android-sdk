package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateSmartPushMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    @JsonProperty("data")
    InitiateSmartPushMFAResponseDataEntity data;

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

    public InitiateSmartPushMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateSmartPushMFAResponseDataEntity data) {
        this.data = data;
    }
}
