package de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.IVR;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollIVRMFARequestEntity implements Serializable {
    String statusId;
    String code;
    String sub;
    DeviceInfoEntity deviceInfo;

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
