package com.example.cidaasv2.Service.Entity.Deduplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DeduplicationResponseEntity implements Serializable {
    int status;
    boolean success;
    DeduplicationResponseDataEntity data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DeduplicationResponseDataEntity getData() {
        return data;
    }

    public void setData(DeduplicationResponseDataEntity data) {
        this.data = data;
    }
}
