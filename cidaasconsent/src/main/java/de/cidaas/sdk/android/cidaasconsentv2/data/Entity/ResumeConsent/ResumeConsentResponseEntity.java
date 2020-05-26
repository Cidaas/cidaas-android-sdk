package de.cidaas.sdk.android.cidaasconsentv2.data.Entity.ResumeConsent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumeConsentResponseEntity implements Serializable {
    boolean success;
    int status;
    ResumeConsentResponseDataEntity data;

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

    public ResumeConsentResponseDataEntity getData() {
        return data;
    }

    public void setData(ResumeConsentResponseDataEntity data) {
        this.data = data;
    }
}
