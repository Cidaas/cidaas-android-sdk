package de.cidaas.sdk.android.cidaasnative.data.entity.accountverification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateAccountVerificationRequestEntity implements Serializable {
    String requestId;
    String sub;
    String verificationMedium;
    String processingType;
    String email;
    String mobile;

    public InitiateAccountVerificationRequestEntity() {
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    //For Email
    public InitiateAccountVerificationRequestEntity(String requestId, String verificationMedium, String processingType, String email) {
        this.requestId = requestId;
        this.verificationMedium = verificationMedium;
        this.processingType = processingType;
        this.email = email;
    }


}
