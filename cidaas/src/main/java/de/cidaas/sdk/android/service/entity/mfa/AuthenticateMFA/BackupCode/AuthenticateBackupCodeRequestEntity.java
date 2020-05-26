package de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.BackupCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateBackupCodeRequestEntity implements Serializable {

    String statusId;
    String verifierPassword;
    DeviceInfoEntity deviceInfo;


    public String getVerifierPassword() {
        return verifierPassword;
    }

    public void setVerifierPassword(String verifierPassword) {
        this.verifierPassword = verifierPassword;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
