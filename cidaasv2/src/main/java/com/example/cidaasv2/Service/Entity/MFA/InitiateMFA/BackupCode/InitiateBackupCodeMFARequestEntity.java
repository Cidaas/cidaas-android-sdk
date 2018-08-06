package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateBackupCodeMFARequestEntity implements Serializable{
    String sub;
    String physicalVerificationId;
    String userDeviceId;
    String verificationType;
    String usageType;
    DeviceInfoEntity deviceInfo;

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
