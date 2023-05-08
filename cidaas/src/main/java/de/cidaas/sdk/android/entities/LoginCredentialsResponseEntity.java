package de.cidaas.sdk.android.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginCredentialsResponseEntity implements Serializable {

    private boolean success;
    private int status;
    private AccessTokenEntity data;
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

    public AccessTokenEntity getData() {
        return data;
    }

    public void setData(AccessTokenEntity data) {
        this.data = data;
    }

}
