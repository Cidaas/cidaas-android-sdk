package com.example.cidaasv2.Service.Register.RegistrationSetup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationSetupLocaleTextEntity implements Serializable {

    private String locale;
    private String language;
    private String name;
    private String verificationRequired;
    private String required;
    private String maxLength;
    private String matchWith;
    RegistrationSetupAttributesEntity[] attributes;

    public RegistrationSetupAttributesEntity[] getAttributes() {
        return attributes;
    }

    public void setAttributes(RegistrationSetupAttributesEntity[] attributes) {
        this.attributes = attributes;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerificationRequired() {
        return verificationRequired;
    }

    public void setVerificationRequired(String verificationRequired) {
        this.verificationRequired = verificationRequired;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getMatchWith() {
        return matchWith;
    }

    public void setMatchWith(String matchWith) {
        this.matchWith = matchWith;
    }


}
