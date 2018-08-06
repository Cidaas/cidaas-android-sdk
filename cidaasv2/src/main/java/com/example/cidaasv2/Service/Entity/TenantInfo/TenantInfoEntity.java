package com.example.cidaasv2.Service.Entity.TenantInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 23/5/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantInfoEntity implements Serializable{
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

    public TenantInfoDataEntity getData() {
        return data;
    }

    public void setData(TenantInfoDataEntity data) {
        this.data = data;
    }

    private boolean success;
    private int status;
private TenantInfoDataEntity data;
}
