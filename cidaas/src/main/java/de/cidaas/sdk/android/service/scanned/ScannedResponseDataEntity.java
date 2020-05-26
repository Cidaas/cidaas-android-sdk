package de.cidaas.sdk.android.service.scanned;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScannedResponseDataEntity implements Serializable {
    String userDeviceId;
    String current_status;
    FIDOInitRequest fidoInitRequest;


    public FIDOInitRequest getFidoInitRequest() {
        return fidoInitRequest;
    }

    public void setFidoInitRequest(FIDOInitRequest fidoInitRequest) {
        this.fidoInitRequest = fidoInitRequest;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }
}
