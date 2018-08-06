package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateFaceMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    InitiateFaceMFAResponseDataEntity data;

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

    public InitiateFaceMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateFaceMFAResponseDataEntity data) {
        this.data = data;
    }
}
