package com.example.cidaasv2.Helper.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalVerificationEntity implements Serializable{
    private String sub;
    private String physicalVerificationId;
    private String userDeviceId;
    private String usageType;



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

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

}
