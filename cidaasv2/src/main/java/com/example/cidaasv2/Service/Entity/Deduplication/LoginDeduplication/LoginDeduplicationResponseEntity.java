package com.example.cidaasv2.Service.Entity.Deduplication.LoginDeduplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LoginDeduplicationResponseEntity implements Serializable{
    int status;
    boolean success;
    LoginDeduplicationDataEntity data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LoginDeduplicationDataEntity getData() {
        return data;
    }

    public void setData(LoginDeduplicationDataEntity data) {
        this.data = data;
    }
}
