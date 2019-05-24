package com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAcknowledge;

import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.PushDeviceinfoEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushDeviceInformation implements Serializable {
    private String user_device_id="";
    private PushDeviceinfoEntity device_info;

    public String getUser_device_id() {
        return user_device_id;
    }

    public void setUser_device_id(String user_device_id) {
        this.user_device_id = user_device_id;
    }

    public PushDeviceinfoEntity getDevice_info() {
        return device_info;
    }

    public void setDevice_info(PushDeviceinfoEntity device_info) {
        this.device_info = device_info;
    }
}
