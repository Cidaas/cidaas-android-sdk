package com.example.cidaasv2.Service.Scanned;

import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScannedResponseEntity implements Serializable {
    boolean success;
    int status;
    ScannedResponseDataEntity data;

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

    public ScannedResponseDataEntity getData() {
        return data;
    }

    public void setData(ScannedResponseDataEntity data) {
        this.data = data;
    }
}
