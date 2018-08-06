package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateFIDOMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    InitiateFIDOMFAResponseDataEntity data;

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

    public InitiateFIDOMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateFIDOMFAResponseDataEntity data) {
        this.data = data;
    }
}
