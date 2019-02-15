package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollFIDOMFARequestEntity implements Serializable{
    String statusId;

    String usage_pass;
    String userDeviceId;
    FIDOTouchResponse fidoTouchResponse;
    DeviceInfoEntity deviceInfo;

    String client_id;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public String getUsage_pass() {
        return usage_pass;
    }

    public void setUsage_pass(String usage_pass) {
        this.usage_pass = usage_pass;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public FIDOTouchResponse getFidoTouchResponse() {
        return fidoTouchResponse;
    }

    public void setFidoTouchResponse(FIDOTouchResponse fidoTouchResponse) {
        this.fidoTouchResponse = fidoTouchResponse;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
