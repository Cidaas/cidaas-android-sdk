package de.cidaas.sdk.android.cidaasVerification.data.entity.authenticatedhistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedHistoryResponse implements Serializable {
    private boolean success;
    private int status;

    @JsonProperty("data")
    private List<AuthenticatedHistoryDataEntity> data;

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

    public List<AuthenticatedHistoryDataEntity> getData() {
        return data;
    }

    public void setData(List<AuthenticatedHistoryDataEntity> data) {
        this.data = data;
    }
}
