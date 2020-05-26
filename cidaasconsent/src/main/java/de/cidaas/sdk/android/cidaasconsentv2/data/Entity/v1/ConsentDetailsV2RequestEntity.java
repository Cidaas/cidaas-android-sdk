package de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsentDetailsV2RequestEntity implements Serializable {

    private String sub;
    private String requestId;
    private String track_id;

    private String consent_id;
    private String consent_version_id;
    private String client_id;

    public ConsentDetailsV2RequestEntity(String sub, String requestId, String track_id, String consent_id, String consent_version_id) {
        this.sub = sub;
        this.requestId = requestId;
        this.track_id = track_id;
        this.consent_id = consent_id;
        this.consent_version_id = consent_version_id;
    }

    public ConsentDetailsV2RequestEntity(String sub, String requestId, String track_id, String consent_id, String consent_version_id, String client_id) {
        this(sub, requestId, track_id, consent_id, consent_version_id);
        this.client_id = client_id;
    }

    public String getConsent_id() {
        return consent_id;
    }

    public void setConsent_id(String consent_id) {
        this.consent_id = consent_id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getConsent_version_id() {
        return consent_version_id;
    }

    public void setConsent_version_id(String consent_version_id) {
        this.consent_version_id = consent_version_id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
