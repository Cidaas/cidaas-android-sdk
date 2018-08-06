package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateFingerprintMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    InitiateFingerprintMFAResponseDataEntity data;

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

    public InitiateFingerprintMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateFingerprintMFAResponseDataEntity data) {
        this.data = data;
    }
}
