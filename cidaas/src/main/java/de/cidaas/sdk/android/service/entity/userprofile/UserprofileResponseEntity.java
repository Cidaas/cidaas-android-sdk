package de.cidaas.sdk.android.service.entity.userprofile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserprofileResponseEntity implements Serializable {
    boolean success;
    int status;
    de.cidaas.sdk.android.Service.Entity.UserProfile.UseprofileResponseDataEntity data;

    public void setData(de.cidaas.sdk.android.Service.Entity.UserProfile.UseprofileResponseDataEntity data) {
        this.data = data;
    }

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

    public de.cidaas.sdk.android.Service.Entity.UserProfile.UseprofileResponseDataEntity getData() {
        return data;
    }


}
