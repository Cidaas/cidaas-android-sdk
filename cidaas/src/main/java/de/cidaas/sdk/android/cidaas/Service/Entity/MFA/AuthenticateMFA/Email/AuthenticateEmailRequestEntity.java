package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Email;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateEmailRequestEntity implements Serializable {

    String status_id;
    String code;
    DeviceInfoEntity deviceInfo;

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
