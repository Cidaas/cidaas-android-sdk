package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollPatternMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    EnrollPatternResponseDataEntity data;

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

    public EnrollPatternResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollPatternResponseDataEntity data) {
        this.data = data;
    }
}
