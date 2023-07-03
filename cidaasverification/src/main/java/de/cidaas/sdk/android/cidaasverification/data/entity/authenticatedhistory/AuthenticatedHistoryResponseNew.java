package de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedHistoryResponseNew implements Serializable {
    private boolean success;
    private int status;

    @JsonProperty("data")
    private AuthenticatedHistoryResponseDataList data;

    public AuthenticatedHistoryResponseDataList getData() {
        return data;
    }

    public void setData(AuthenticatedHistoryResponseDataList data) {
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
}
