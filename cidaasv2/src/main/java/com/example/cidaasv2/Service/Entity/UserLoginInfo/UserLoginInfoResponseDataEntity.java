package com.example.cidaasv2.Service.Entity.UserLoginInfo;

import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.LocationEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginInfoResponseDataEntity implements Serializable {
    private String _id;
    private String currentState;
    private String verificationType;
    private DeviceInfoEntityWithDetailsEntity deviceInfo;
    private LocationEntity address;
    private String time;
    private int totalCount=0;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public DeviceInfoEntityWithDetailsEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntityWithDetailsEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }


    public LocationEntity getAddress() {
        return address;
    }

    public void setAddress(LocationEntity address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
