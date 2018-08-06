package com.example.cidaasv2.Service.Register.RegisterUserAccountVerification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserAccountInitiateRequestEntity implements Serializable{
    String 	requestId;
    String sub;
    String verificationMedium;
    String processingType;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    public String getVerificationMedium() {
        return verificationMedium;
    }

    public void setVerificationMedium(String verificationMedium) {
        this.verificationMedium = verificationMedium;
    }

    public String getProcessingType() {
        return processingType;
    }

    public void setProcessingType(String processingType) {
        this.processingType = processingType;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
