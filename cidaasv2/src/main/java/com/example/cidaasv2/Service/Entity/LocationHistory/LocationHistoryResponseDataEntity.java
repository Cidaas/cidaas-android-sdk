package com.example.cidaasv2.Service.Entity.LocationHistory;

import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.LocationEntity;

import java.io.Serializable;


public class LocationHistoryResponseDataEntity implements Serializable {
    private String currentState;
    private String verificationType;
    private DeviceInfoEntityWithDetailsEntity deviceInfo;
    private LocationEntity address;
    private String time;


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
