package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateEmailResponseEntity implements Serializable{
    boolean success;
    int status;
    AuthenticateEmailResponseDataEntity data;

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

    public AuthenticateEmailResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateEmailResponseDataEntity data) {
        this.data = data;
    }
}
