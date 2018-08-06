package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)

public class SetupEmailMFAResponseEntity implements Serializable{

    private boolean success;
    private int status;
    private SetupEmailResponseDataEntity data;

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

    public SetupEmailResponseDataEntity getData() {
        return data;
    }

    public void setData(SetupEmailResponseDataEntity data) {
        this.data = data;
    }
}
