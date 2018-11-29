package com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisteredKeys   implements Serializable {
    private String keyHandle;

    private String version;

    public String getKeyHandle() {
        return keyHandle;
    }

    public void setKeyHandle(String keyHandle) {
        this.keyHandle = keyHandle;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}