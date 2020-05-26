package de.cidaas.sdk.android.service.entity.mfa.SetupMFA.SmartPush;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetupSmartPushMFAResponseEntity implements Serializable {
    boolean success;
    int status;
    SetupSmartPushMFAResponseDataEntity data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public SetupSmartPushMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(SetupSmartPushMFAResponseDataEntity data) {
        this.data = data;
    }
}
