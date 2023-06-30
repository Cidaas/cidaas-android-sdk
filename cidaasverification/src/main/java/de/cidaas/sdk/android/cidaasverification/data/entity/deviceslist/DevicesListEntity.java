package de.cidaas.sdk.android.cidaasverification.data.entity.deviceslist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DevicesListEntity implements Serializable {

    private String client_id = "";
    private String push_id = "";

    public String[] getSub() {
        return sub;
    }

    public void setSub(String[] sub) {
        this.sub = sub;
    }

    String[] sub;


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



}
