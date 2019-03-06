package com.example.cidaasv2.Service.Entity.LocationHistory;

import java.io.Serializable;

public class LocationHistoryResponseEntity implements Serializable {
    private Boolean success;
    private int status;
    private LocationHistoryResponseDataEntity data;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocationHistoryResponseDataEntity getData() {
        return data;
    }

    public void setData(LocationHistoryResponseDataEntity data) {
        this.data = data;
    }
}
