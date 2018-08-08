package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.BackupCode.EnrollBackupCodeResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollEmailMFAResponseEntity implements Serializable{

    boolean success;
    int status;
    EnrollEmailResponseDataEntity data;

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

    public EnrollEmailResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollEmailResponseDataEntity data) {
        this.data = data;
    }
}
