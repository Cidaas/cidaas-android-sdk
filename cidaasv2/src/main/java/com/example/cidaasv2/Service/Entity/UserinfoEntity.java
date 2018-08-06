package com.example.cidaasv2.Service.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserinfoEntity implements Serializable{


    //Properties
    private String name;
    private String family_name;
    private String sub;
    private String given_name;
    private String email;
    private String mobile;
    private String nickname;
    private String preferred_username;
    private String website;
    private String locale;
    private String last_used_identity_id;
    private String provider;
    private String username;
    private boolean email_verified;
    private String user_status;

    private int updated_at;

    private int last_accessed_at;



//Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPreferred_username() {
        return preferred_username;
    }

    public void setPreferred_username(String preferred_username) {
        this.preferred_username = preferred_username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLast_used_identity_id() {
        return last_used_identity_id;
    }

    public void setLast_used_identity_id(String last_used_identity_id) {
        this.last_used_identity_id = last_used_identity_id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public String isUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public int getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }

    public int getLast_accessed_at() {
        return last_accessed_at;
    }

    public void setLast_accessed_at(int last_accessed_at) {
        this.last_accessed_at = last_accessed_at;
    }
}
