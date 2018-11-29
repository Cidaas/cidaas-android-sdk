package com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationEntity implements Serializable {

    private String status;

    private List<PushNotificationEntity> data;

    private String success;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PushNotificationEntity> getData() {
        return data;
    }

    public void setData(List<PushNotificationEntity> data) {
        this.data = data;
    }


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
