package com.example.cidaasv2.Service.Entity.LoginCredentialsEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.exc.IgnoredPropertyException;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LoginCredentialsErrorDataEntity implements Serializable{
    private String error;
    private String error_description;
    private String view_type;
    private String sub;
    private String requestId;
    private String suggested_url;
    String track_id;
    String client_id;
    String consent_version;
    String consent_name;

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

    public String getConsent_version() {
        return consent_version;
    }

    public void setConsent_version(String consent_version) {
        this.consent_version = consent_version;
    }

    public String getConsent_name() {
        return consent_name;
    }

    public void setConsent_name(String consent_name) {
        this.consent_name = consent_name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getView_type() {
        return view_type;
    }

    public void setView_type(String view_type) {
        this.view_type = view_type;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSuggested_url() {
        return suggested_url;
    }

    public void setSuggested_url(String suggested_url) {
        this.suggested_url = suggested_url;
    }
}
