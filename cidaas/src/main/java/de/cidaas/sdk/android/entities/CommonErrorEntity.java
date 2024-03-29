package de.cidaas.sdk.android.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonErrorEntity implements Serializable {

    private boolean success;
    private int status;
    private ErrorEntity Error;
    private String refnumber;
    private String error_description;


    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getRefnumber() {
        return refnumber;
    }

    public void setRefnumber(String refnumber) {
        this.refnumber = refnumber;
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


    public ErrorEntity getError() {
        return Error;
    }

    public void setError(ErrorEntity error) {
        Error = error;
    }
}
