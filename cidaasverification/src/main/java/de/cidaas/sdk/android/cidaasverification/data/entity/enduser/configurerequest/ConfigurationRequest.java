package de.cidaas.sdk.android.cidaasverification.data.entity.enduser.configurerequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.io.Serializable;

import de.cidaas.sdk.android.entities.FingerPrintEntity;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationRequest implements Serializable {


    private String pass_code = "";
    private String sub = "";

    //For face and voice
    private File fileToSend;
    private int attempt = 0;

    //For Fingerprint
    @JsonIgnore
    private FingerPrintEntity fingerPrintEntity;


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

    //For Pattern
    public ConfigurationRequest(String sub, String pass_code) {
        this.pass_code = pass_code;
        this.sub = sub;
    }

    //For Smart push and TOTP
    public ConfigurationRequest(String sub) {
        this.sub = sub;
    }

    //For Face and Voice
    public ConfigurationRequest(String sub, File fileToSend, int attempt) {
        this.sub = sub;
        this.fileToSend = fileToSend;
        this.attempt = attempt;
    }

    //For Fingerprint
    public ConfigurationRequest(String sub, FingerPrintEntity fingerPrintEntity) {
        this.sub = sub;
        this.fingerPrintEntity = fingerPrintEntity;
    }
}
