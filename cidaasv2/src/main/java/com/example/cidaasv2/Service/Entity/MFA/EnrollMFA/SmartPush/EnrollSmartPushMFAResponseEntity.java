package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EnrollSmartPushMFAResponseEntity implements Serializable{
    boolean success;
    int status;
    EnrollSmartPushResponseDataEntity data;

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

    public EnrollSmartPushResponseDataEntity getData() {
        return data;
    }

    public void setData(EnrollSmartPushResponseDataEntity data) {
        this.data = data;
    }
}
