package com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory;

import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.PushDeviceinfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedHistoryDataEntity implements Serializable {

    private String _id="";
    private String sub="";
    private String status_id="";
    private String auth_time;
    private LocationDetailsTrackingEntity location_details;
    private PushDeviceinfoEntity device_info;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

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

    public String getAuth_time() {
        return auth_time;
    }

    public void setAuth_time(String auth_time) {
        this.auth_time = auth_time;
    }

    public LocationDetailsTrackingEntity getLocation_details() {
        return location_details;
    }

    public void setLocation_details(LocationDetailsTrackingEntity location_details) {
        this.location_details = location_details;
    }

    public PushDeviceinfoEntity getDevice_info() {
        return device_info;
    }

    public void setDevice_info(PushDeviceinfoEntity device_info) {
        this.device_info = device_info;
    }
}
