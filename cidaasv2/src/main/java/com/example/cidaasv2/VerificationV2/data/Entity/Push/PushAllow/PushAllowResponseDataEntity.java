package com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAllow;

import com.example.cidaasv2.VerificationV2.data.Entity.ExcangeId.ExchangeIDEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushAllowResponseDataEntity implements Serializable {

    private String sub="";
    private ExchangeIDEntity exchange_id;
    private String client_id="";
    private String device_id="";
    private String push_id="";
    private String status_id="";
    private String[] push_random_numbers;


    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public ExchangeIDEntity getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(ExchangeIDEntity exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
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

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String[] getPush_random_numbers() {
        return push_random_numbers;
    }

    public void setPush_random_numbers(String[] push_random_numbers) {
        this.push_random_numbers = push_random_numbers;
    }
}
