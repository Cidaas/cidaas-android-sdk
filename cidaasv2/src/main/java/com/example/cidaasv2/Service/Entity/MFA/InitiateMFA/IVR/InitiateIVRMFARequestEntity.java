package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateIVRMFARequestEntity implements Serializable{
    String sub;
    String physicalVerificationId;
    String userDeviceId;
    String verificationType;
    String usageType;
    DeviceInfoEntity deviceInfo;
    String email;
    String mobile;

    String source="MOBILE";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getPhysicalVerificationId() {
        return physicalVerificationId;
    }

    public void setPhysicalVerificationId(String physicalVerificationId) {
        this.physicalVerificationId = physicalVerificationId;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
