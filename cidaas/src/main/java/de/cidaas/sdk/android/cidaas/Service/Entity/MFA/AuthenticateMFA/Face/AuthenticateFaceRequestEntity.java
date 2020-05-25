package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Face;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateFaceRequestEntity implements Serializable {

    String statusId = "";
    File imagetoSend;
    List<File> imagesToSend;
    DeviceInfoEntity deviceInfo;
    String userDeviceId = "";
    String usage_pass = "";
    String client_id = "";

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

    public List<File> getImagesToSend() {
        return imagesToSend;
    }

    public void setImagesToSend(List<File> imagesToSend) {
        this.imagesToSend = imagesToSend;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
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

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
