package com.example.cidaasv2.Service.Register.RegistrationSetup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationSetupErrorEntity implements Serializable{
    boolean success;
    int status;
    RegistrationSetupErrorData error;

    public RegistrationSetupErrorData getError() {
        return error;
    }

    public void setError(RegistrationSetupErrorData error) {
        this.error = error;
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
