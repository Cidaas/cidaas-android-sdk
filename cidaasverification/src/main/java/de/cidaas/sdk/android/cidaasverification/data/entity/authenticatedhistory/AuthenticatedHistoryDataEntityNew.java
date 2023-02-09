package de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedHistoryDataEntityNew implements Serializable {


    @JsonProperty("data")
    private List<AuthenticatedHistoryDataEntity> data;

    public List<AuthenticatedHistoryDataEntity> getData() {
        return data;
    }

    public void setData(List<AuthenticatedHistoryDataEntity> data) {
        this.data = data;
    }
}
