package com.example.cidaasv2.VerificationV2.data.Entity.Authenticate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthentitcateRequestEntity implements Serializable {

    private String exchange_id="";
    private String device_id="";
    private String push_id="";
    private String face_attempt="";
    private String pass_code="";
    private int client_id=0;

    public AuthentitcateRequestEntity() {
    }

    public AuthentitcateRequestEntity(String exchange_id, String device_id, String push_id, String face_attempt, String pass_code, int client_id) {
        this.exchange_id = exchange_id;
        this.device_id = device_id;
        this.push_id = push_id;
        this.face_attempt = face_attempt;
        this.pass_code = pass_code;
        this.client_id = client_id;
    }

    public AuthentitcateRequestEntity(String exchange_id, String pass_code, int client_id) {
        this.exchange_id = exchange_id;
        this.pass_code = pass_code;
        this.client_id = client_id;
    }

    public AuthentitcateRequestEntity(String exchange_id, String face_attempt, String pass_code, int client_id) {
        this.exchange_id = exchange_id;
        this.face_attempt = face_attempt;
        this.pass_code = pass_code;
        this.client_id = client_id;
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

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    public String getFace_attempt() {
        return face_attempt;
    }

    public void setFace_attempt(String face_attempt) {
        this.face_attempt = face_attempt;
    }

    public String getPass_code() {
        return pass_code;
    }

    public void setPass_code(String pass_code) {
        this.pass_code = pass_code;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
}
