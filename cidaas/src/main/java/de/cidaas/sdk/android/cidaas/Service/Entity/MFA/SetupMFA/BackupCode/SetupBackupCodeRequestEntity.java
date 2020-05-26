package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.BackupCode;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetupBackupCodeRequestEntity implements Serializable {
    DeviceInfoEntity deviceInfo;
    String usage_pass;

    public String getUsage_pass() {
        return usage_pass;
    }

    public void setUsage_pass(String usage_pass) {
        this.usage_pass = usage_pass;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
