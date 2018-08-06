package com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent;

import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ResumeConsentResponseEntity implements Serializable{
    boolean success;
    int status;
    ResumeConsentResponseDataEntity data;

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

    public ResumeConsentResponseDataEntity getData() {
        return data;
    }

    public void setData(ResumeConsentResponseDataEntity data) {
        this.data = data;
    }
}
