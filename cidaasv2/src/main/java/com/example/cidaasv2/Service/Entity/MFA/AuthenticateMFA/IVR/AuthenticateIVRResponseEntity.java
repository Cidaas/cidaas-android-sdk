package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateIVRResponseEntity implements Serializable{
    boolean success;
    int status;
    AuthenticateIVRResponseDataEntity data;

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

    public AuthenticateIVRResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateIVRResponseDataEntity data) {
        this.data = data;
    }
}
