package de.cidaas.sdk.android.consent.data.entity.resumeconsent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumeConsentEntity implements Serializable {
    private String sub = "";

    private String track_id = "";

    private String consent_id = "";
    private String consent_version_id = "";
    private String client_id = "";

    private String name = "";
    private String version = "";

/*
    public ResumeConsentEntity(String sub, String name, String version, String track_id, String client_id) {
        this.sub = sub;
        this.name = name;
        this.version = version;
        this.track_id = track_id;
        this.client_id = client_id;
    }*/

    public ResumeConsentEntity(String sub, String track_id, String consent_id, String consent_version_id, String client_id, String name, String version) {
        this.sub = sub;
        this.track_id = track_id;
        this.consent_id = consent_id;
        this.consent_version_id = consent_version_id;
        this.client_id = client_id;
        this.name = name;
        this.version = version;
    }

    public ResumeConsentEntity(String sub, String track_id, String consent_id, String consent_version_id, String client_id) {
        this.sub = sub;
        this.track_id = track_id;
        this.consent_id = consent_id;
        this.consent_version_id = consent_version_id;
        this.client_id = client_id;
    }

    public String getConsent_id() {
        return consent_id;
    }

    public void setConsent_id(String consent_id) {
        this.consent_id = consent_id;
    }

    public String getConsent_version_id() {
        return consent_version_id;
    }

    public void setConsent_version_id(String consent_version_id) {
        this.consent_version_id = consent_version_id;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
