package de.cidaas.sdk.android.Service.Entity.ValidateDevice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateDeviceRequestEntity implements Serializable {
    String intermediate_verifiation_id;
    DeviceInfoEntity deviceInfo;
    String access_verifier;
    String statusId;

    public String getIntermediate_verifiation_id() {
        return intermediate_verifiation_id;
    }

    public void setIntermediate_verifiation_id(String intermediate_verifiation_id) {
        this.intermediate_verifiation_id = intermediate_verifiation_id;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getAccess_verifier() {
        return access_verifier;
    }

    public void setAccess_verifier(String access_verifier) {
        this.access_verifier = access_verifier;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}
