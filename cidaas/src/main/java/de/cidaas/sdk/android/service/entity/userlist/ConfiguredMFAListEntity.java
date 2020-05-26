package de.cidaas.sdk.android.Service.Entity.UserList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfiguredMFAListEntity implements Serializable {
    boolean success;
    int status;
    @JsonProperty("data")
    private List<UserListDataEntity> data;
    @JsonProperty("metaData")
    private MetadataSub metaData;

    private String sendedURL;

    public String getSendedURL() {
        return sendedURL;
    }

    public void setSendedURL(String sendedURL) {
        this.sendedURL = sendedURL;
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

    public List<UserListDataEntity> getData() {
        return data;
    }

    public void setData(List<UserListDataEntity> data) {
        this.data = data;
    }

    public MetadataSub getMetaData() {
        return metaData;
    }

    public void setMetaData(MetadataSub metaData) {
        this.metaData = metaData;
    }
}
