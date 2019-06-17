package com.example.cidaasv2.VerificationV2.domain.Controller.ConfigureRequest;

import com.example.cidaasv2.Helper.Entity.FingerPrintEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigureRequest implements Serializable {


    private String pass_code="";
    private String sub="";

    //For face and voice
    private File fileToSend;
    private int face_attempt =0;

    //For Fingerprint
    @JsonIgnore
    private FingerPrintEntity fingerPrintEntity;


    //todo generate Constructor

    public String getPass_code() {
        return pass_code;
    }

    public void setPass_code(String pass_code) {
        this.pass_code = pass_code;
    }


    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public File getFileToSend() {
        return fileToSend;
    }

    public void setFileToSend(File fileToSend) {
        this.fileToSend = fileToSend;
    }

    public int getFace_attempt() {
        return face_attempt;
    }

    public void setFace_attempt(int face_attempt) {
        this.face_attempt = face_attempt;
    }

    public FingerPrintEntity getFingerPrintEntity() {
        return fingerPrintEntity;
    }

    public void setFingerPrintEntity(FingerPrintEntity fingerPrintEntity) {
        this.fingerPrintEntity = fingerPrintEntity;
    }

    //For Pattern and Smart push
    public ConfigureRequest( String sub ,String pass_code) {
        this.pass_code = pass_code;
        this.sub = sub;
    }

    //For Face and Voice
    public ConfigureRequest(String sub, File fileToSend, int face_attempt) {
        this.sub = sub;
        this.fileToSend = fileToSend;
        this.face_attempt = face_attempt;
    }

    //For Fingerprint
    public ConfigureRequest(String sub, FingerPrintEntity fingerPrintEntity) {
        this.sub = sub;
        this.fingerPrintEntity = fingerPrintEntity;
    }
}
