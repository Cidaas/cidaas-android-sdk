package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollVoiceMFARequestEntity implements Serializable{
    String statusId="";
    File audioFile;
    String userDeviceId="";
    String usage_pass="";
    DeviceInfoEntity deviceInfo;
    String client_id="";

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public File getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(File audioFile) {
        this.audioFile = audioFile;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public String getUsage_pass() {
        return usage_pass;
    }

    public void setUsage_pass(String usage_pass) {
        this.usage_pass = usage_pass;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
