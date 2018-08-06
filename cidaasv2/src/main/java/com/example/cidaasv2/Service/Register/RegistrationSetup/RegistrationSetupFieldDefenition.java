package com.example.cidaasv2.Service.Register.RegistrationSetup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationSetupFieldDefenition implements Serializable {

    int maxLength;
    boolean applyPasswordPoly;
    String matchWith;
    boolean verificationRequired;
    int minLength;
    String name;
    String locale;
    String language;
    String[] attributesKeys;

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isApplyPasswordPoly() {
        return applyPasswordPoly;
    }

    public void setApplyPasswordPoly(boolean applyPasswordPoly) {
        this.applyPasswordPoly = applyPasswordPoly;
    }

    public String getMatchWith() {
        return matchWith;
    }

    public void setMatchWith(String matchWith) {
        this.matchWith = matchWith;
    }

    public boolean isVerificationRequired() {
        return verificationRequired;
    }

    public void setVerificationRequired(boolean verificationRequired) {
        this.verificationRequired = verificationRequired;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String[] getAttributesKeys() {
        return attributesKeys;
    }

    public void setAttributesKeys(String[] attributesKeys) {
        this.attributesKeys = attributesKeys;
    }
}
