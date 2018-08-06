package com.example.cidaasv2.Service.Entity.ClientInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 23/5/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientInfoEntity implements Serializable {
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

    public ClientInfoDataEntity getData() {
        return data;
    }

    public void setData(ClientInfoDataEntity data) {
        this.data = data;
    }

    private boolean success;
  private int status;
  private ClientInfoDataEntity data;
}
