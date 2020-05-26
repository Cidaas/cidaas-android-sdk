package de.cidaas.sdk.android.service.entity.mfa.SetupMFA.IVR;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;

public class SetupIVRMFARequestEntity {
    String phone;
    String usage_pass;
    DeviceInfoEntity deviceInfo;


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
