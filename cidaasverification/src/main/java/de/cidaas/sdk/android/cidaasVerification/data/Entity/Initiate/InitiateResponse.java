package de.cidaas.sdk.android.cidaasVerification.data.Entity.Initiate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateResponse implements Serializable {

    boolean success;
    int status;
    InitiateResponseDataEntity data;

    public InitiateResponseDataEntity getData() {
        return data;
    }

    public void setData(InitiateResponseDataEntity data) {
        this.data = data;
    }

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
}
