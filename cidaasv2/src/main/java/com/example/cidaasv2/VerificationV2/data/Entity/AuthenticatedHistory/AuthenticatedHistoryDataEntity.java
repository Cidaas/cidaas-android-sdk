package com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory;

import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.PushDeviceinfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedHistoryDataEntity implements Serializable {

    private String sub="";
    private String status_id="";
    private Date auth_time;
    private PushAddressEntity address;
    private PushDeviceinfoEntity device_info;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public Date getAuth_time() {
        return auth_time;
    }

    public void setAuth_time(Date auth_time) {
        this.auth_time = auth_time;
    }

    public PushAddressEntity getAddress() {
        return address;
    }

    public void setAddress(PushAddressEntity address) {
        this.address = address;
    }

    public PushDeviceinfoEntity getDevice_info() {
        return device_info;
    }

    public void setDevice_info(PushDeviceinfoEntity device_info) {
        this.device_info = device_info;
    }
}
