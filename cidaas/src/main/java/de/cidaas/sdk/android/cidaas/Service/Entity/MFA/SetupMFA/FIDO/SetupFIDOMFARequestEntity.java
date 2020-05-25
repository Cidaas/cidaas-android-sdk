package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.SetupMFA.FIDO;

import de.cidaas.sdk.android.cidaas.Helper.Entity.DeviceInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Scanned.FIDOInitRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetupFIDOMFARequestEntity implements Serializable {
    String logoUrl;
    DeviceInfoEntity deviceInfo;
    String client_id;
    String usage_pass;
    FIDOInitRequest fidoInitRequest;

    public FIDOInitRequest getFidoInitRequest() {
        return fidoInitRequest;
    }

    public void setFidoInitRequest(FIDOInitRequest fidoInitRequest) {
        this.fidoInitRequest = fidoInitRequest;
    }

    public String getUsage_pass() {
        return usage_pass;
    }

    public void setUsage_pass(String usage_pass) {
        this.usage_pass = usage_pass;
    }


    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
