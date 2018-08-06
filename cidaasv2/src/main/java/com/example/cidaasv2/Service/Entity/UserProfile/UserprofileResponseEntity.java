package com.example.cidaasv2.Service.Entity.UserProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
    @JsonIgnoreProperties(ignoreUnknown = true)
public class UserprofileResponseEntity implements Serializable{
    boolean success;
    int status;
    UseprofileResponseDataEntity data;

        public void setData(UseprofileResponseDataEntity data) {
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

    public UseprofileResponseDataEntity getData() {
        return data;
    }


}
