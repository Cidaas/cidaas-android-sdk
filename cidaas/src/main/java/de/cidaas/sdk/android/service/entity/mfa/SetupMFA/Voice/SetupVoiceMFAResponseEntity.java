package de.cidaas.sdk.android.service.entity.mfa.SetupMFA.Voice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetupVoiceMFAResponseEntity implements Serializable {
    boolean success;
    int status;
    SetupVoiceMFAResponseDataEntity data;

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

    public SetupVoiceMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(SetupVoiceMFAResponseDataEntity data) {
        this.data = data;
    }
}
