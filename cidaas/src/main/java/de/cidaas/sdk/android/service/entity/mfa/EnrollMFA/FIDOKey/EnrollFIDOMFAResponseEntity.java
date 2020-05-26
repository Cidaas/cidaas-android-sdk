package de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.FIDOKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollFIDOMFAResponseEntity implements Serializable {

    boolean success;
    int status;
    private EnrollFIDOMFAResponseDataEntity data;

    public EnrollFIDOMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollFIDOMFAResponseDataEntity data) {
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
