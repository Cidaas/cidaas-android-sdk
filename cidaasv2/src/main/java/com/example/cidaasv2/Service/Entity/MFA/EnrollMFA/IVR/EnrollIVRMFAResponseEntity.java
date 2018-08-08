package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.BackupCode.EnrollBackupCodeResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollIVRMFAResponseEntity implements Serializable{

    boolean success;
    int status;
    EnrollIVRResponseDataEntity data;

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

    public EnrollIVRResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollIVRResponseDataEntity data) {
        this.data = data;
    }
}
