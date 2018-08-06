package com.example.cidaasv2.Service.Entity.ConsentManagement;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown=true)
public class ConsentDetailsResultEntity implements Serializable {
    boolean success;
    int status;
   // Object data;
    ConsentDetailsResultDataEntity data;

    public ConsentDetailsResultDataEntity getData() {
        return data;
    }

    public void setData(ConsentDetailsResultDataEntity data) {
        this.data = data;
    }

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

    /*public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
*/}
