package com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushDeviceinfoEntity implements Serializable {
    private String ipAddress="";
    private OsEntity os;
    private EngineEntity engine;
    private String deviceMake;
    private String deviceModel;
    private String userAgent;
    private BrowserEntity browser;

    private String lat="";
    private String lon="";
    private String purpose="";


    private String sub="";
    private String deviceId="";


    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public BrowserEntity getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserEntity browser) {
        this.browser = browser;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}