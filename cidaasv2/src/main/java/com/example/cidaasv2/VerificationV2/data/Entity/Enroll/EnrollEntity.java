package com.example.cidaasv2.VerificationV2.data.Entity.Enroll;

import com.example.cidaasv2.Helper.Entity.FingerPrintEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollEntity implements Serializable {

    private String exchange_id="";
    private String device_id="";
    private String client_id="";
    private String push_id="";
    private String pass_code="";
    private String verificationType="";


    //For face and voice
    private File fileToSend;
    private int attempt =0;

    //For Fingerprint
    @JsonIgnore
    private FingerPrintEntity fingerPrintEntity;

    //EmptyConstructors
    public EnrollEntity() {
    }


    //For Pattern and push
    public EnrollEntity(String exchange_id,  String pass_code, String verificationType) {
        this.exchange_id = exchange_id;
        this.pass_code = pass_code;
        this.verificationType = verificationType;
    }

    //For FingerPrint
    public EnrollEntity(String exchange_id,  String verificationType, FingerPrintEntity fingerPrintEntity) {
        this.exchange_id = exchange_id;
        this.verificationType = verificationType;
        this.fingerPrintEntity = fingerPrintEntity;
    }

    //For Face and Voice
    public EnrollEntity(String exchange_id, String verificationType, File fileToSend, int face_attempt) {
        this.exchange_id = exchange_id;
        this.verificationType = verificationType;
        this.fileToSend = fileToSend;
        this.attempt = face_attempt;
    }

    public File getFileToSend() {
        return fileToSend;
    }

    public void setFileToSend(File fileToSend) {
        this.fileToSend = fileToSend;
    }

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    @JsonIgnore
    public FingerPrintEntity getFingerPrintEntity() {
        return fingerPrintEntity;
    }

    @JsonIgnore
    public void setFingerPrintEntity(FingerPrintEntity fingerPrintEntity) {
        this.fingerPrintEntity = fingerPrintEntity;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
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

    public String getPass_code() {
        return pass_code;
    }

    public void setPass_code(String pass_code) {
        this.pass_code = pass_code;
    }
}
