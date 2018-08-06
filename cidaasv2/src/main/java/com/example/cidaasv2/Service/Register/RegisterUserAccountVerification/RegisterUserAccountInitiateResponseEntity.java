package com.example.cidaasv2.Service.Register.RegisterUserAccountVerification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserAccountInitiateResponseEntity implements Serializable {
        boolean  success=false;
        int status=0;
        RegisterUserAccountInitiateResponseDataEntity data;

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

    public RegisterUserAccountInitiateResponseDataEntity getData() {
        return data;
    }

    public void setData(RegisterUserAccountInitiateResponseDataEntity data) {
        this.data = data;
    }
}
