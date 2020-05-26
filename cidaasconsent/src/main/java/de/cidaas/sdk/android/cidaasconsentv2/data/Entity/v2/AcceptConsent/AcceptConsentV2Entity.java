package de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.AcceptConsent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcceptConsentV2Entity implements Serializable {

    private String sub = "";
    private String consent_id = "";
    private String consent_version_id = "";
    private String client_id = "";
    private String trackId = "";

    public AcceptConsentV2Entity(String sub, String consent_id, String consent_version_id, String trackId) {
        this.sub = sub;
        this.consent_id = consent_id;
        this.consent_version_id = consent_version_id;
        this.trackId = trackId;
    }

    public AcceptConsentV2Entity(String sub, String consent_id, String consent_version_id, String client_id, String trackId) {
        this.sub = sub;
        this.consent_id = consent_id;
        this.consent_version_id = consent_version_id;
        this.client_id = client_id;
        this.trackId = trackId;

    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
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

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
}
