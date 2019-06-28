package com.example.cidaasv2.Service.Entity.ConsentManagement.v2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ConsentDetailsV2ResponseEntity implements Serializable {
    private boolean success;
    private int status;
    ConsentDetailsV2ResponseDataEntity data;

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

    public ConsentDetailsV2ResponseDataEntity getData() {
        return data;
    }

    public void setData(ConsentDetailsV2ResponseDataEntity data) {
        this.data = data;
    }
}
