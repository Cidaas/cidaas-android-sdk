package com.example.cidaasv2.Service.Entity.ConsentManagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ConsentResponseEntity implements Serializable{
 boolean success;
 int status;
   ConsentResponseDataEntity data;

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

    public ConsentResponseDataEntity getData() {
        return data;
    }

    public void setData(ConsentResponseDataEntity data) {
        this.data = data;
    }
}
