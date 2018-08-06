package com.example.cidaasv2.Service.Entity.ConsentManagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ConsentDetailsResultDataEntity implements Serializable{
    String _id;
    String description;
    String title;

    String userAgreeText;
    String consentURL;


    public String getConsentURL() {
        return consentURL;
    }

    public void setConsentURL(String consentURL) {
        this.consentURL = consentURL;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserAgreeText() {
        return userAgreeText;
    }

    public void setUserAgreeText(String userAgreeText) {
        this.userAgreeText = userAgreeText;
    }
}
