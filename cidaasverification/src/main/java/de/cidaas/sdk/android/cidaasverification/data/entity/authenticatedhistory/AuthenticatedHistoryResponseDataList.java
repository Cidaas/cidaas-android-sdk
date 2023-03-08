package de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedHistoryResponseDataList implements Serializable {

    private int success;
    private String initiated = "";

    private String authenticated = "";
    private String failed = "";

    public int isSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
    @JsonProperty("data")
    private List<AuthenticatedHistoryDataEntity> data;

    public List<AuthenticatedHistoryDataEntity> getData() {
        return data;
    }

    public void setData(List<AuthenticatedHistoryDataEntity> data) {
        this.data = data;
    }

    public String getInitiated() {
        return initiated;
    }

    public void setInitiated(String initiated) {
        this.initiated = initiated;
    }

    public String getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(String authenticated) {
        this.authenticated = authenticated;
    }

    public String getFailed() {
        return failed;
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }
}
