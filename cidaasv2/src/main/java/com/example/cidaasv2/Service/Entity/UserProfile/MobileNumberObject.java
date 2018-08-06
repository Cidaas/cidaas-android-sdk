package com.example.cidaasv2.Service.Entity.UserProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileNumberObject implements Serializable{
    String _id;
    String updatedTime;
    String createdTime;
    String className;
    String carrier_name;
    String carrier_type;
    String country;
    String dail_code;
    String E164_format;
    String given_phone;
    String international_format;
    String national_format;
    String phone;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCarrier_name() {
        return carrier_name;
    }

    public void setCarrier_name(String carrier_name) {
        this.carrier_name = carrier_name;
    }

    public String getCarrier_type() {
        return carrier_type;
    }

    public void setCarrier_type(String carrier_type) {
        this.carrier_type = carrier_type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDail_code() {
        return dail_code;
    }

    public void setDail_code(String dail_code) {
        this.dail_code = dail_code;
    }

    public String getE164_format() {
        return E164_format;
    }

    public void setE164_format(String e164_format) {
        E164_format = e164_format;
    }

    public String getGiven_phone() {
        return given_phone;
    }

    public void setGiven_phone(String given_phone) {
        this.given_phone = given_phone;
    }

    public String getInternational_format() {
        return international_format;
    }

    public void setInternational_format(String international_format) {
        this.international_format = international_format;
    }

    public String getNational_format() {
        return national_format;
    }

    public void setNational_format(String national_format) {
        this.national_format = national_format;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
