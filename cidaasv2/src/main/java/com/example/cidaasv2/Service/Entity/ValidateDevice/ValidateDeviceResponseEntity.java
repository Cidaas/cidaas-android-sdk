package com.example.cidaasv2.Service.Entity.ValidateDevice;


import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFAResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateDeviceResponseEntity implements Serializable{
    boolean success;
    int status;
    ValidateDeviceResponseDataEntity data;

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

    public ValidateDeviceResponseDataEntity getData() {
        return data;
    }

    public void setData(ValidateDeviceResponseDataEntity data) {
        this.data = data;
    }
}
