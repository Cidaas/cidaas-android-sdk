package com.example.cidaasv2.VerificationV2.data.Entity.Settings.PendingNotification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PendingNotificationEntity implements Serializable {
    private String device_id="";
    private String push_id="";
    private String client_id="";
    private String sub="";

    public PendingNotificationEntity() {
    }

    public PendingNotificationEntity(String device_id, String push_id, String client_id, String sub) {
        this.device_id = device_id;
        this.push_id = push_id;
        this.client_id = client_id;
        this.sub = sub;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
