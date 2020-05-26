package de.cidaas.sdk.android.cidaasVerification.data.Entity.Settings.ConfiguredMFAList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfiguredMFAList implements Serializable {
    boolean success;
    int status;
    MFAListResponseData data;

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

    public MFAListResponseData getData() {
        return data;
    }

    public void setData(MFAListResponseData data) {
        this.data = data;
    }
}
