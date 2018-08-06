package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollTOTPMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    EnrollTOTPResponseDataEntity data;

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

    public EnrollTOTPResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollTOTPResponseDataEntity data) {
        this.data = data;
    }
}
