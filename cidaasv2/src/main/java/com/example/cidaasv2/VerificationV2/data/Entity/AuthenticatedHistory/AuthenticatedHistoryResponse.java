package com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory;

import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedHistoryResponse implements Serializable {
    private boolean success;
    private int status;

    @JsonProperty("configured_list")
    private List<AuthenticateResponseDataEntity> data;

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

    public List<AuthenticateResponseDataEntity> getData() {
        return data;
    }

    public void setData(List<AuthenticateResponseDataEntity> data) {
        this.data = data;
    }
}
