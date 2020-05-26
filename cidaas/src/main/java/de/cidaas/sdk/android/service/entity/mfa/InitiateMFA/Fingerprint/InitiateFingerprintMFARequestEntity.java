package de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.Fingerprint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateFingerprintMFARequestEntity implements Serializable {
    String email;
    String mobile;
    String sub;
    String userDeviceId;
    String client_id;
    String usageType;
    String usage_pass;
    DeviceInfoEntity deviceInfo;

    String source = "MOBILE";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
