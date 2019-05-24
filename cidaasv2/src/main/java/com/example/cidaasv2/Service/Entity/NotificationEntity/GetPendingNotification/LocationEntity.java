package com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationEntity implements Serializable {
    private String  country_short ;
    private String   country_long ;
    private String   region ;
    private String  city ;
    private String  zipcode ;
    private String   street ;
    private String  houseNo ;
    private String  formattedAddress ;
    private double  latitude ;
    private double  longitude ;
    private String  ip ;
    private String  ip_no ;
    private String  resolver ;
    private String status="";
    private int elevation;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getCountry_short() {
        return country_short;
    }

    public void setCountry_short(String country_short) {
        this.country_short = country_short;
    }

    public String getCountry_long() {
        return country_long;
    }

    public void setCountry_long(String country_long) {
        this.country_long = country_long;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp_no() {
        return ip_no;
    }

    public void setIp_no(String ip_no) {
        this.ip_no = ip_no;
    }

    public String getResolver() {
        return resolver;
    }

    public void setResolver(String resolver) {
        this.resolver = resolver;
    }
}