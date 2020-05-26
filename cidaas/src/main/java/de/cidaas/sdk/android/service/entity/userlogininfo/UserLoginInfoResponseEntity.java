package de.cidaas.sdk.android.service.entity.userlogininfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserLoginInfoResponseEntity implements Serializable {
    private Boolean success;
    private int status;
    // private UserLoginInfoResponseDataEntity[] data;

    UserLoginInfoDataEntity data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserLoginInfoDataEntity getData() {
        return data;
    }

    public void setData(UserLoginInfoDataEntity data) {
        this.data = data;
    }

}
