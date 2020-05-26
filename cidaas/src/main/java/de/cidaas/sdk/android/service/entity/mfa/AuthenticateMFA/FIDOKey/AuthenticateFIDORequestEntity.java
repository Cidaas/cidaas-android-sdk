package de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.FIDOKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.entities.DeviceInfoEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateFIDORequestEntity implements Serializable {

    String statusId;
    String code;
    String userDeviceId;
    DeviceInfoEntity deviceInfo;
    String usage_pass;
    String client_id;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }


    private FidoSignTouchResponse fidoSignTouchResponse;

    public String getUsage_pass() {
        return usage_pass;
    }

    public void setUsage_pass(String usage_pass) {
        this.usage_pass = usage_pass;
    }


    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }

    public FidoSignTouchResponse getFidoSignTouchResponse() {
        return fidoSignTouchResponse;
    }

    public void setFidoSignTouchResponse(FidoSignTouchResponse fidoSignTouchResponse) {
        this.fidoSignTouchResponse = fidoSignTouchResponse;
    }

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

    public DeviceInfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
