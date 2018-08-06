package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.FIDO;

import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFAResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SetupFIDOMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    SetupFIDOMFAResponseDataEntity data;

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

    public SetupFIDOMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(SetupFIDOMFAResponseDataEntity data) {
        this.data = data;
    }
}
