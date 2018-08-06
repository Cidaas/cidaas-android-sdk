package com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS;

import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown=true)
public class SetupSMSMFAResponseEntity implements Serializable{
    private boolean success;
    private int status;
    private SetupSMSMFAResponseDataEntity data;

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

    public SetupSMSMFAResponseDataEntity getData() {
        return data;
    }

    public void setData(SetupSMSMFAResponseDataEntity data) {
        this.data = data;
    }
}
