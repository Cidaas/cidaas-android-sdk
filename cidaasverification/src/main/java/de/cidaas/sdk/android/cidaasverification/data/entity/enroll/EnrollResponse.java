package de.cidaas.sdk.android.cidaasverification.data.entity.enroll;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollResponse implements Serializable {
    boolean success;
    int status;
    EnrollResponseDataEntity data;

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

    public EnrollResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollResponseDataEntity data) {
        this.data = data;
    }


    EnrollResponseDataErrorEntity errordata;

    public EnrollResponseDataErrorEntity getErrordata() {
        return errordata;
    }

    public void setErrordata(EnrollResponseDataErrorEntity errordata) {
        this.errordata = errordata;
    }
}
