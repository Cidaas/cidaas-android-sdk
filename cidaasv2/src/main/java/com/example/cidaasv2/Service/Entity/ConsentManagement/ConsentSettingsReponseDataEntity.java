package com.example.cidaasv2.Service.Entity.ConsentManagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown=true)
public class ConsentSettingsReponseDataEntity implements Serializable{

    boolean sensitive;
    String policyUrl;
    String userAgreeText;
    String description;
    String name;
    String language;
    String consentReceiptID;
    String collectionMethod;
    String jurisdiction;
    boolean enabled;
    String status;
    String consent_type;
    String[] spiCat;
    ConsentSettingsResponseServiceEntity[] services;
    ConsentSettingsPIIControllersEntity[] piiControllers;
    String version;


    public boolean isSensitive() {
        return sensitive;
    }

    public void setSensitive(boolean sensitive) {
        this.sensitive = sensitive;
    }

    public String getPolicyUrl() {
        return policyUrl;
    }

    public void setPolicyUrl(String policyUrl) {
        this.policyUrl = policyUrl;
    }

    public String getUserAgreeText() {
        return userAgreeText;
    }

    public void setUserAgreeText(String userAgreeText) {
        this.userAgreeText = userAgreeText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getConsentReceiptID() {
        return consentReceiptID;
    }

    public void setConsentReceiptID(String consentReceiptID) {
        this.consentReceiptID = consentReceiptID;
    }

    public String getCollectionMethod() {
        return collectionMethod;
    }

    public void setCollectionMethod(String collectionMethod) {
        this.collectionMethod = collectionMethod;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConsent_type() {
        return consent_type;
    }

    public void setConsent_type(String consent_type) {
        this.consent_type = consent_type;
    }

    public String[] getSpiCat() {
        return spiCat;
    }

    public void setSpiCat(String[] spiCat) {
        this.spiCat = spiCat;
    }

    public ConsentSettingsResponseServiceEntity[] getServices() {
        return services;
    }

    public void setServices(ConsentSettingsResponseServiceEntity[] services) {
        this.services = services;
    }

    public ConsentSettingsPIIControllersEntity[] getPiiControllers() {
        return piiControllers;
    }

    public void setPiiControllers(ConsentSettingsPIIControllersEntity[] piiControllers) {
        this.piiControllers = piiControllers;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
