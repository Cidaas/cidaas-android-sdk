package com.example.cidaasv2.VerificationV2.data.Entity.EnrollEntity;

import com.example.cidaasv2.Service.Scanned.ScannedResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollResponseEntity implements Serializable {
    boolean success;
    int status;
    EnrollResponseDataEntity data;

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

    public EnrollResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollResponseDataEntity data) {
        this.data = data;
    }
}
