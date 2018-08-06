package com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication;

import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegisterDeduplicationEntity implements Serializable{
    int status;
    boolean success;
    RegisterDeduplicationDataEntity data;

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

    public RegisterDeduplicationDataEntity getData() {
        return data;
    }

    public void setData(RegisterDeduplicationDataEntity data) {
        this.data = data;
    }
}
