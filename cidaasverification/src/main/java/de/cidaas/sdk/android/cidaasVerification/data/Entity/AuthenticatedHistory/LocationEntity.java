package de.cidaas.sdk.android.cidaasVerification.data.Entity.AuthenticatedHistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationEntity implements Serializable {

    public String id = "";
    public String lat = "";
    public String lon = "";
    public PushAddressEntity address;


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

    public PushAddressEntity getAddress() {
        return address;
    }

    public void setAddress(PushAddressEntity address) {
        this.address = address;
    }
}
