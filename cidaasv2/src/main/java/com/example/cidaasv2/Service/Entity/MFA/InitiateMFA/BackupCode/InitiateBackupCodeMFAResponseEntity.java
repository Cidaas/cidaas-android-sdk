package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateBackupCodeMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    InitiateBackupCodeMFAResponseDataEntity data;

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

    public InitiateBackupCodeMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateBackupCodeMFAResponseDataEntity data) {
        this.data = data;
    }
}
