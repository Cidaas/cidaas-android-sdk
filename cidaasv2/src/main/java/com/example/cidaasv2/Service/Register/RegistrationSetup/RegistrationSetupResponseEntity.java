package com.example.cidaasv2.Service.Register.RegistrationSetup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationSetupResponseEntity implements Serializable {
    private boolean success;
    private int status;
    private RegistrationSetupResultDataEntity[] data;

    public RegistrationSetupResultDataEntity[] getData() {
        return data;
    }

    public void setData(RegistrationSetupResultDataEntity[] data) {
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
