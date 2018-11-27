package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateFaceRequestEntity implements Serializable {

    String statusId;
    File imagetoSend;
    List<File> imagesToSend;
    DeviceInfoEntity deviceInfo;
    String userDeviceId;
    String usage_pass;

    public String getUsage_pass() {
        return usage_pass;
    }

    public void setUsage_pass(String usage_pass) {
        this.usage_pass = usage_pass;
    }


    public List<File> getImagesToSend() {
        return imagesToSend;
    }

    public void setImagesToSend(List<File> imagesToSend) {
        this.imagesToSend = imagesToSend;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
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

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
