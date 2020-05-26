package de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.FIDOKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FIDOTouchResponse implements Serializable {

    private String challenge;
    private String registrationData;
    private String clientData;

    private String version;
    private String fidoRequestId;

    public String getFidoRequestId() {
        return fidoRequestId;
    }

    public void setFidoRequestId(String fidoRequestId) {
        this.fidoRequestId = fidoRequestId;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getRegistrationData() {
        return registrationData;
    }

    public void setRegistrationData(String registrationData) {
        this.registrationData = registrationData;
    }

    public String getClientData() {
        return clientData;
    }

    public void setClientData(String clientData) {
        this.clientData = clientData;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}

