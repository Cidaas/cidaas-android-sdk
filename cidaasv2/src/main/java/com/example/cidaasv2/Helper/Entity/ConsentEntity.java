package com.example.cidaasv2.Helper.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsentEntity implements Serializable{
     private String consentName;
     private String consentVersion;
     private String sub;
     private String trackId;
     private boolean isAccepted;


    public ConsentEntity(String consentName, String consentVersion, String sub, String trackId, boolean isAccepted) {
        this.consentName = consentName;
        this.consentVersion = consentVersion;
        this.sub = sub;
        this.trackId = trackId;
        this.isAccepted = isAccepted;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getConsentName() {
        return consentName;
    }

    public void setConsentName(String consentName) {
        this.consentName = consentName;
    }

    public String getConsentVersion() {
        return consentVersion;
    }

    public void setConsentVersion(String consentVersion) {
        this.consentVersion = consentVersion;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
