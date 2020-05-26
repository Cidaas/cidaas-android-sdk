package de.cidaas.sdk.android.cidaasVerification.data.Entity.VerificationContinue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationContinueResponseEntity implements Serializable {
    boolean success;
    int status;
    VerificationContinueResponseDataEntity data;

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

    public VerificationContinueResponseDataEntity getData() {
        return data;
    }

    public void setData(VerificationContinueResponseDataEntity data) {
        this.data = data;
    }
}
