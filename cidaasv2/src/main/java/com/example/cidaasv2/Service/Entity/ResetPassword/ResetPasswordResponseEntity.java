package com.example.cidaasv2.Service.Entity.ResetPassword;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 23/5/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPasswordResponseEntity implements Serializable {
    private boolean success;
    private int status;
    private ResetPasswordResultDataEntity data;

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

    public ResetPasswordResultDataEntity getData() {
        return data;
    }

    public void setData(ResetPasswordResultDataEntity data) {
        this.data = data;
    }
}
