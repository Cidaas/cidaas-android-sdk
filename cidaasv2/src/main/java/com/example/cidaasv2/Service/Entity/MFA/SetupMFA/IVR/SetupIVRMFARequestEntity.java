package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

public class SetupIVRMFARequestEntity {
    String phone;
    DeviceInfoEntity deviceInfo;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
