package com.example.cidaasv2.Service.Entity.UserLoginInfo;

import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.BrowserEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.EngineEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.OsEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceInfoEntityWithDetailsEntity implements Serializable {
    String sub="";
    BrowserEntity browser;
    OsEntity os;
    EngineEntity engine;
    String deviceMake="";
    String deviceModel="";
    String deviceId="";
    String pushNotificationId="";
    String deviceVersion="";


    public String getPushNotificationId() {
        return pushNotificationId;
    }

    public void setPushNotificationId(String pushNotificationId) {
        this.pushNotificationId = pushNotificationId;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public BrowserEntity getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserEntity browser) {
        this.browser = browser;
    }

    public OsEntity getOs() {
        return os;
    }

    public void setOs(OsEntity os) {
        this.os = os;
    }

    public EngineEntity getEngine() {
        return engine;
    }

    public void setEngine(EngineEntity engine) {
        this.engine = engine;
    }

    public String getDeviceMake() {
        return deviceMake;
    }

    public void setDeviceMake(String deviceMake) {
        this.deviceMake = deviceMake;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
