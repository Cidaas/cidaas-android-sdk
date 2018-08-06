package com.example.cidaasv2.Helper.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.Dictionary;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationEntity implements Serializable{
    private String username="";
    private String email="";
    private String given_name="";
    private String family_name="";
    private String password="";
    private String password_echo="";
    private String mobile_number="";
    private Date birthdate;
    private String gender="";
    private String website="";
    private String provider="";
    private Dictionary<String,RegistrationCustomFieldEntity> customFields;




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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_echo() {
        return password_echo;
    }

    public void setPassword_echo(String password_echo) {
        this.password_echo = password_echo;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Dictionary<String, RegistrationCustomFieldEntity> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Dictionary<String, RegistrationCustomFieldEntity> customFields) {
        this.customFields = customFields;
    }
}
