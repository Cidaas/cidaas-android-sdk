package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollFaceMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    EnrollFaceResponseDataEntity data;

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

    public EnrollFaceResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollFaceResponseDataEntity data) {
        this.data = data;
    }
}
