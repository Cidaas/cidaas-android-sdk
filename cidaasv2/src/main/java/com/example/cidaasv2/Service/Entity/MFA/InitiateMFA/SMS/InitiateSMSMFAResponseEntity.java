package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateSMSMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    InitiateSMSMFAResponseDataEntity data;

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

    public InitiateSMSMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateSMSMFAResponseDataEntity data) {
        this.data = data;
    }
}
