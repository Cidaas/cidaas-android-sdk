package com.example.cidaasv2.VerificationV2.data.Entity.Delete;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeleteEntity implements Serializable {
    private String sub;
    private String client_id;
    private String push_id;
    private String verificationType;


    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
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

    //For Delete User by Sub and verification Type
    public DeleteEntity(String sub, String verificationType) {
        this.sub = sub;
        this.verificationType = verificationType;
    }

    //Empty Constructor
    public DeleteEntity() {
    }
}
