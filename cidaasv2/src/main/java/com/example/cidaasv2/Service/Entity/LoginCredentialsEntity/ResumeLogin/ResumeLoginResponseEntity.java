package com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown=true)
public class ResumeLoginResponseEntity implements Serializable{
  boolean success;
  int status;
  ResumeLoginResponseDataEntity data;

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

    public ResumeLoginResponseDataEntity getData() {
        return data;
    }

    public void setData(ResumeLoginResponseDataEntity data) {
        this.data = data;
    }
}
