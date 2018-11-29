package com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PushNotificationEntity implements Serializable {

    private long requestTime;
    private String sub;
    private String verificationType;
    private String randomNumbers;
    private LocationEntity address;
    private PushDeviceinfoEntity deviceInfo;
    @JsonProperty("metadata")
    NFCSignObject nfcSignObject;
    private String physicalVerificationId;
    private String statusId;

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }

    public String getRandomNumbers() {
        return randomNumbers;
    }

    public void setRandomNumbers(String randomNumbers) {
        this.randomNumbers = randomNumbers;
    }

    public LocationEntity getAddress() {
        return address;
    }

    public void setAddress(LocationEntity address) {
        this.address = address;
    }

    public PushDeviceinfoEntity getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(PushDeviceinfoEntity deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public NFCSignObject getNfcSignObject() {
        return nfcSignObject;
    }

    public void setNfcSignObject(NFCSignObject nfcSignObject) {
        this.nfcSignObject = nfcSignObject;
    }

    public String getPhysicalVerificationId() {
        return physicalVerificationId;
    }

    public void setPhysicalVerificationId(String physicalVerificationId) {
        this.physicalVerificationId = physicalVerificationId;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}
