package de.cidaas.sdk.android.cidaasverification.data.entity.scanned;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DevicesListData implements Serializable {

    private String device_id = "";
    private String client_id = "";
    private String device_make = "";
    private String device_model = "";
    private String sub = "";
    private String last_used_time = "";

    private String last_used_location = "";

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

    public String getDevice_make() {
        return device_make;
    }

    public void setDevice_make(String device_make) {
        this.device_make = device_make;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getLast_used_time() {
        return last_used_time;
    }

    public void setLast_used_time(String last_used_time) {
        this.last_used_time = last_used_time;
    }

    public String getLast_used_location() {
        return last_used_location;
    }

    public void setLast_used_location(String last_used_location) {
        this.last_used_location = last_used_location;
    }




}
