package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateFingerprintResponseEntity implements Serializable{
    boolean success;
    int status;
    AuthenticateFingerprintResponseDataEntity data;

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

    public AuthenticateFingerprintResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateFingerprintResponseDataEntity data) {
        this.data = data;
    }
}
