package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateBackupCodeResponseEntity implements Serializable{
    boolean success;
    int status;
    AuthenticateBackupCodeResponseDataEntity data;

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

    public AuthenticateBackupCodeResponseDataEntity getData() {
        return data;
    }

    public void setData(AuthenticateBackupCodeResponseDataEntity data) {
        this.data = data;
    }
}
