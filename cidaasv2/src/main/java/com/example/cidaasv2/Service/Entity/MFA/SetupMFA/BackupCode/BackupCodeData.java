package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown=true)
public class BackupCodeData implements Serializable {


    private String code="";
    private String statusId="";
    private String usedTime="";
    private String sub="";
    private boolean used;
    UserDeviceInfo usedDeviceInfo;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public UserDeviceInfo getUsedDeviceInfo() {
        return usedDeviceInfo;
    }

    public void setUsedDeviceInfo(UserDeviceInfo usedDeviceInfo) {
        this.usedDeviceInfo = usedDeviceInfo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
