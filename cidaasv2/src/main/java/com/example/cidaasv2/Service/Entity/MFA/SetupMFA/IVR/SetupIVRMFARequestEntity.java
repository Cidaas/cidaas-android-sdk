package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR;

import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;

public class SetupIVRMFARequestEntity {
    String phone;
    DeviceInfoEntity deviceInfo;
    String usage_pass;

    public String getUsage_pass() {
        return usage_pass;
    }

    public void setUsage_pass(String usage_pass) {
        this.usage_pass = usage_pass;
    }


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
