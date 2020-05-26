package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.SmartPush;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateSmartPushRequestEntity implements Serializable {

    String statusId;
    String verifierPassword;
    DeviceInfoEntity deviceInfo;
    String userDeviceId;
    String usage_pass;
    String client_id;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getUsage_pass() {
        return usage_pass;
    }

    public void setUsage_pass(String usage_pass) {
        this.usage_pass = usage_pass;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    //  {"success":false,"status":417,"error":{"code":3001,"moreInfo":"","type":"VerificationException","status":417,"referenceNumber":"1531819500718","error":"statusId or verifierPassword or deviceInfo in body cannot be empty"}}

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getVerifierPassword() {
        return verifierPassword;
    }

    public void setVerifierPassword(String verifierPassword) {
        this.verifierPassword = verifierPassword;
    }

   /* public AuthenticateSmartPushRequestDevice getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(AuthenticateSmartPushRequestDevice deviceInfo) {
        this.deviceInfo = deviceInfo;
    }*/

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
