package com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush;

import android.bluetooth.BluetoothClass;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateSmartPushRequestEntity implements Serializable {

    String statusId;
    String verifierPassword;
    DeviceInfoEntity deviceInfo;
    String userDeviceId;

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
