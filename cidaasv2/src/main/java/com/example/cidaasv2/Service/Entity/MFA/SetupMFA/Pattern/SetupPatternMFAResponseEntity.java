package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SetupPatternMFAResponseEntity implements Serializable{

    boolean success;
    int status;
    SetupPatternMFAResponseDataEntity data;

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

    public SetupPatternMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(SetupPatternMFAResponseDataEntity data) {
        this.data = data;
    }
}
