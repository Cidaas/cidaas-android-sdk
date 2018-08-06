package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollFingerprintMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    EnrollFingerprintResponseDataEntity data;

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

    public EnrollFingerprintResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollFingerprintResponseDataEntity data) {
        this.data = data;
    }
}
