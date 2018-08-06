package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateEmailMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    InitiateEmailMFAResponseDataEntity data;

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

    public InitiateEmailMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateEmailMFAResponseDataEntity data) {
        this.data = data;
    }
}
