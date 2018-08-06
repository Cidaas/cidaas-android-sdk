package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateFaceResponseEntity implements Serializable{
    boolean success;
    int status;
    AuthenticateFaceResponseDataEntity data;

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

    public AuthenticateFaceResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateFaceResponseDataEntity data) {
        this.data = data;
    }
}
