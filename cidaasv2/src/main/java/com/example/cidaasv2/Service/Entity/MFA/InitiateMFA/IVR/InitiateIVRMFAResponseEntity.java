package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateIVRMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    InitiateIVRMFAResponseDataEntity data;

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

    public InitiateIVRMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateIVRMFAResponseDataEntity data) {
        this.data = data;
    }
}
