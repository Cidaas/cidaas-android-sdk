package com.example.cidaasv2.Service.Entity.ConsentManagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ConsentManagementAcceptResponseEntity implements Serializable {

    int status;
    boolean success;
    ConsentManagementResponseDataEntity data;

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

    public ConsentManagementResponseDataEntity getData() {
        return data;
    }

    public void setData(ConsentManagementResponseDataEntity data) {
        this.data = data;
    }
}
