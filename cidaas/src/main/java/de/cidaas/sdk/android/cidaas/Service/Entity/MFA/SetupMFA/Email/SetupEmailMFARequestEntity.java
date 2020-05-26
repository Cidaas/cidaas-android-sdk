package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.Email;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;


@JsonIgnoreProperties(ignoreUnknown = true)

public class SetupEmailMFARequestEntity implements Serializable {
    DeviceInfoEntity deviceInfo;


    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
