package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown=true)

public class SetupSMSMFARequestEntity implements Serializable {
    DeviceInfoEntity deviceInfo;


    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
