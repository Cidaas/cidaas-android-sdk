package com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationEntity implements Serializable {

    public String id="" ;
    public String lat="" ;
    public String lon="" ;
    public AddressEntity address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
