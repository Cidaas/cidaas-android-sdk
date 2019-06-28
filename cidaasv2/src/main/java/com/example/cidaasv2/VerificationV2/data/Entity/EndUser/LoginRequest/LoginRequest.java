package com.example.cidaasv2.VerificationV2.data.Entity.EndUser.LoginRequest;

import com.example.cidaasv2.Helper.Entity.FingerPrintEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest implements Serializable {

    private String pass_code="";
    private String sub="";

    //For face and voice
    private File fileToSend;
    private int attempt =0;

    //For Fingerprint
    @JsonIgnore
    private FingerPrintEntity fingerPrintEntity;

    private String usageType="";


    //For Pattern only
    public LoginRequest(String pass_code, String sub, String usageType) {
        this.pass_code = pass_code;
        this.sub = sub;
        this.usageType = usageType;
    }

    //For Push only
    public LoginRequest(String sub, String usageType) {
        this.sub = sub;
        this.usageType = usageType;
    }

    //For Face and Voice
    public LoginRequest(String sub, File fileToSend, int attempt, String usageType) {
        this.sub = sub;
        this.fileToSend = fileToSend;
        this.attempt = attempt;
        this.usageType = usageType;
    }


    //For Fingerprint
    public LoginRequest(String sub, FingerPrintEntity fingerPrintEntity,String usageType) {
        this.sub = sub;
        this.fingerPrintEntity = fingerPrintEntity;
        this.usageType = usageType;
    }

    public String getPass_code() {
        return pass_code;
    }

    public void setPass_code(String pass_code) {
        this.pass_code = pass_code;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
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

    public int getAttempt() {
        return attempt;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public FingerPrintEntity getFingerPrintEntity() {
        return fingerPrintEntity;
    }

    public void setFingerPrintEntity(FingerPrintEntity fingerPrintEntity) {
        this.fingerPrintEntity = fingerPrintEntity;
    }



}
