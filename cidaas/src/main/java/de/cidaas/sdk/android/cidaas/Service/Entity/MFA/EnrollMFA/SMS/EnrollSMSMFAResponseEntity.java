package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.SMS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollSMSMFAResponseEntity implements Serializable {

    boolean success;
    int status;
    EnrollSMSResponseDataEntity data;

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

    public EnrollSMSResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollSMSResponseDataEntity data) {
        this.data = data;
    }
}
