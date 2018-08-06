package com.example.cidaasv2.Service.Entity.AuthRequest;

import com.example.cidaasv2.Service.Entity.RequestId.RequestIDEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by widasrnarayanan on 9/5/18.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequestResponseEntity {
    private boolean success;
    private int status;
    private RequestIDEntity data;

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

    public RequestIDEntity getData() {

        return data;
    }

    public void setData(RequestIDEntity data) {
        this.data = data;
    }
}
