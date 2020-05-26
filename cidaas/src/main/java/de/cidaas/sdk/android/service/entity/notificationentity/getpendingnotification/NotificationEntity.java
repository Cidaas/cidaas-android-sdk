package de.cidaas.sdk.android.service.entity.notificationentity.getpendingnotification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationEntity implements Serializable {

    private int status;

    private List<PushNotificationEntity> data;

    private boolean success;


    public List<PushNotificationEntity> getData() {
        return data;
    }

    public void setData(List<PushNotificationEntity> data) {
        this.data = data;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
