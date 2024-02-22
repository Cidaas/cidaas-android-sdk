package de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressiveRegistrationResponseEntity implements Serializable {
    boolean success;
    int status;
    ProgressiveRegistrationResponseDataEntity data;

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

    public ProgressiveRegistrationResponseDataEntity getData() {
        return data;
    }

    public void setData(ProgressiveRegistrationResponseDataEntity data) {
        this.data = data;
    }
}
