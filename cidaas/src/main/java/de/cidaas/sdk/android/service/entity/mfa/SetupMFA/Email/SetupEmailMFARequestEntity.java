package de.cidaas.sdk.android.service.entity.mfa.SetupMFA.Email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;


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
