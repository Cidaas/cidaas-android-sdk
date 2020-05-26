package de.cidaas.sdk.android.cidaas.Service.Entity.NotificationEntity.GetPendingNotification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrowserEntity implements Serializable {
    public String major;
    public String name;
    public String version;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerison() {
        return version;
    }

    public void setVerison(String verison) {
        this.version = verison;
    }
}