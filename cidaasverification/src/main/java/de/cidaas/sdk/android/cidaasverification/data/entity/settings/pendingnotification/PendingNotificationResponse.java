package de.cidaas.sdk.android.cidaasverification.data.entity.settings.pendingnotification;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PendingNotificationResponse {
    boolean success;
    int status;
    @JsonProperty("data")
    private List<PushEntity> data;

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

    public List<PushEntity> getData() {
        return data;
    }

    public void setData(List<PushEntity> data) {
        this.data = data;
    }
}
