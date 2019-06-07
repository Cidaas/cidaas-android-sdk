package com.example.cidaasv2.VerificationV2.data.Entity.Push.PushReject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushRejectEntity implements Serializable {
    private String exchange_id="";
    private String device_id="";
    private String client_id="";
    private String push_id="";
    private String verificationType="";
    private String reason="";

    public PushRejectEntity() {
    }

    //Mandatory Fields
    public PushRejectEntity(String exchange_id, String verificationType, String reason) {
        this.exchange_id = exchange_id;
        this.verificationType = verificationType;
        this.reason = reason;
    }

    public PushRejectEntity(String exchange_id, String device_id, String client_id, String push_id, String verificationType, String reason) {
        this.exchange_id = exchange_id;
        this.device_id = device_id;
        this.client_id = client_id;
        this.push_id = push_id;
        this.verificationType = verificationType;
        this.reason = reason;
    }

    public String getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(String exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
