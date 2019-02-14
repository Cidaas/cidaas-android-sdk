package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateSmartPushMFAResponseDataEntity implements Serializable{
    String statusId;
    String randomNumber;
String current_status;


    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public String getRandomNumber() {
        return randomNumber;
    }



    public void setRandomNumber(String randomNumber) {
        this.randomNumber = randomNumber;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}
