package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face;

import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFAResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SetupFaceMFAResponseEntity implements Serializable{

    boolean success;
    int status;
    SetupFaceMFAResponseDataEntity data;

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

    public SetupFaceMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(SetupFaceMFAResponseDataEntity data) {
        this.data = data;
    }
}
