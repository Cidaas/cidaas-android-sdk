package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollVoiceMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    EnrollVoiceResponseDataEntity data;

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

    public EnrollVoiceResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollVoiceResponseDataEntity data) {
        this.data = data;
    }
}
