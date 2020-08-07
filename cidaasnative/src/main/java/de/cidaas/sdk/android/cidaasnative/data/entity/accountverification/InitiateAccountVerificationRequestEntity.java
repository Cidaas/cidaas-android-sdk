package de.cidaas.sdk.android.cidaasnative.data.entity.accountverification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateAccountVerificationRequestEntity implements Serializable {
    String requestId;
    String email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
