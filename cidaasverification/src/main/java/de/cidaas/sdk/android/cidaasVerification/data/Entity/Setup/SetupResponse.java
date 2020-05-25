package de.cidaas.sdk.android.cidaasVerification.data.Entity.Setup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetupResponse implements Serializable {
    boolean success;
    int status;
    SetupResponseData data;

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

    public SetupResponseData getData() {
        return data;
    }

    public void setData(SetupResponseData data) {
        this.data = data;
    }
}
