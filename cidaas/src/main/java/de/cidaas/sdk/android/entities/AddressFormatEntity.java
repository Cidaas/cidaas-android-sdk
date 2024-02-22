package de.cidaas.sdk.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

//Created entity based on https://docs.cidaas.com/docs/cidaas-iam/tnotekth3x1xg-progressive-update-user  (15-feb-2024)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressFormatEntity implements Serializable {
        String formatted;
        String street_address;
        String locality;
        String  region;
        String  country;
        String postal_code;

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }
}
