package com.example.cidaasv2.VerificationV2.data.Entity.Push.PushReject;

import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAllow.PushAllowResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushRejectResponse implements Serializable {
    boolean success;
    int status;
    PushRejectResponseDataEntity data;


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

    public PushRejectResponseDataEntity getData() {
        return data;
    }

    public void setData(PushRejectResponseDataEntity data) {
        this.data = data;
    }
}
