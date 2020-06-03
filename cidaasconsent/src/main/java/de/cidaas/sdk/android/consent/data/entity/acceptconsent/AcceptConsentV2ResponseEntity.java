package de.cidaas.sdk.android.consent.data.entity.acceptconsent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AcceptConsentV2ResponseEntity implements Serializable {
    private boolean success = false;
    private int status;
    private AcceptConsentV2ResponseData data;

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

    public AcceptConsentV2ResponseData getData() {
        return data;
    }

    public void setData(AcceptConsentV2ResponseData data) {
        this.data = data;
    }
}

