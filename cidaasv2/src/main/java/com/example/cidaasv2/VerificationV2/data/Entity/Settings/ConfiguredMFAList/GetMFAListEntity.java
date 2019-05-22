package com.example.cidaasv2.VerificationV2.data.Entity.Settings.ConfiguredMFAList;

public class GetMFAListEntity {
    private String device_id="";
    private String push_id="";
    private String client_id="";

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

    //No need For Developer
    public GetMFAListEntity(String device_id, String push_id, String client_id) {
        this.device_id = device_id;
        this.push_id = push_id;
        this.client_id = client_id;
    }
}
