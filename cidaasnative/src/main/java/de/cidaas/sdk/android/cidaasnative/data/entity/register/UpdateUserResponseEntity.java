package de.cidaas.sdk.android.cidaasnative.data.entity.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.cidaasnative.data.entity.register.registeruser.RegisterNewUserDataEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserResponseEntity implements Serializable {

    boolean success = false;
    int status = 400;
    UpdateUserResponseDataEntity data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UpdateUserResponseDataEntity getData() {
        return data;
    }

    public void setData(UpdateUserResponseDataEntity data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
