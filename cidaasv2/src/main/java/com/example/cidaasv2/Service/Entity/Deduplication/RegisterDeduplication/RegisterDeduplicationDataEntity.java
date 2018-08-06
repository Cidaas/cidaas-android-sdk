package com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RegisterDeduplicationDataEntity implements Serializable {

        String sub;
        String userStatus;
        boolean email_verified;
        String suggested_action;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public String getSuggested_action() {
        return suggested_action;
    }

    public void setSuggested_action(String suggested_action) {
        this.suggested_action = suggested_action;
    }
}
