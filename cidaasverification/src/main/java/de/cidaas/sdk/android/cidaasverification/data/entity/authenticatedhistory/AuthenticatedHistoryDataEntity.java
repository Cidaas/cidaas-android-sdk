package de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedHistoryDataEntity implements Serializable {

    public String getFinalstatus() {
        return finalstatus;
    }

    public void setFinalstatus(String finalstatus) {
        this.finalstatus = finalstatus;
    }

    String finalstatus ="";

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    boolean authenticated;

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    String createdTime ="";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String status ="";
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String address ="";
    private String _id = "";
    private String sub = "";
    private String status_id = "";
    private String auth_time;

    public String getVerification_type() {
        return verification_type;
    }

    public void setVerification_type(String verification_type) {
        this.verification_type = verification_type;
    }

    private String verification_type;
    private LocationDetailsTrackingEntity location_details;
    private PushDeviceInformation device_info;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getAuth_time() {
        return auth_time;
    }

    public void setAuth_time(String auth_time) {
        this.auth_time = auth_time;
    }

    public LocationDetailsTrackingEntity getLocation_details() {
        return location_details;
    }

    public void setLocation_details(LocationDetailsTrackingEntity location_details) {
        this.location_details = location_details;
    }

    public PushDeviceInformation getDevice_info() {
        return device_info;
    }

    public void setDevice_info(PushDeviceInformation device_info) {
        this.device_info = device_info;
    }
}
