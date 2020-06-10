package de.cidaas.sdk.android.cidaasverification.data.entity.scanned;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScannedEntity implements Serializable {

    private String sub = "";
    private String exchange_id = "";
    private String client_id = "";
    private String device_id = "";
    private String push_id = "";
    private String verificationType = "";

    //Constructors
    public ScannedEntity() {
    }

    //Mandatory Fields
    public ScannedEntity(String sub, String exchange_id, String verificationType) {
        this.sub = sub;
        this.exchange_id = exchange_id;
        this.verificationType = verificationType;
    }


    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(String exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
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
}
