package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollFaceMFARequestEntity implements Serializable{
    String statusId;
    File imagetoSend;
    private String userDeviceId;
    String sub;
    DeviceInfoEntity deviceInfo;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public File getImagetoSend() {
        return imagetoSend;
    }

    public void setImagetoSend(File imagetoSend) {
        this.imagetoSend = imagetoSend;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
