package de.cidaas.sdk.android.cidaasVerification.data.entity.scanned;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScannedResponse implements Serializable {
    boolean success;
    int status;
    ScannedResponseData data;

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

    public ScannedResponseData getData() {
        return data;
    }

    public void setData(ScannedResponseData data) {
        this.data = data;
    }
}
