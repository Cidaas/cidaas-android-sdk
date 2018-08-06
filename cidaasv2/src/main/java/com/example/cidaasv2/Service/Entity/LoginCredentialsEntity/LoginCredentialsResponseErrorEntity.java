package com.example.cidaasv2.Service.Entity.LoginCredentialsEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LoginCredentialsResponseErrorEntity implements Serializable{
    private boolean success;
    private int status;

    public String getConsentUrl() {
        return consentUrl;
    }

    public void setConsentUrl(String consentUrl) {
        this.consentUrl = consentUrl;
    }

    private String consentUrl;

    public LoginCredentialsErrorDataEntity getError() {
        return error;
    }

    public void setError(LoginCredentialsErrorDataEntity error) {
        this.error = error;
    }

    private LoginCredentialsErrorDataEntity error;

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


}
