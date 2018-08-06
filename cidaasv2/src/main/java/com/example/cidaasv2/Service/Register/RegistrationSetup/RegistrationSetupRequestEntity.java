package com.example.cidaasv2.Service.Register.RegistrationSetup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationSetupRequestEntity implements Serializable {

    String requestId;
    String acceptedLanguage;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getAcceptedLanguage() {
        return acceptedLanguage;
    }

    public void setAcceptedLanguage(String acceptedLanguage) {
        this.acceptedLanguage = acceptedLanguage;
    }


}
