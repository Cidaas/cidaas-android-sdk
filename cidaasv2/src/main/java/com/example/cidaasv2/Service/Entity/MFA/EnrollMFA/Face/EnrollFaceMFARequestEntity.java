package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollFaceMFARequestEntity implements Serializable{
    String statusId="";
    File imagetoSend;
    String client_id;
    List<File> imagesToSend;
    String usage_pass="";
    private String userDeviceId="";
    DeviceInfoEntity deviceInfo;
    int attempt;

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public List<File> getImagesToSend() {
        return imagesToSend;
    }

    public void setImagesToSend(List<File> imagesToSend) {
        this.imagesToSend = imagesToSend;
    }

    public String getUsage_pass() {
        return usage_pass;
    }

    public void setUsage_pass(String usage_pass) {
        this.usage_pass = usage_pass;
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
