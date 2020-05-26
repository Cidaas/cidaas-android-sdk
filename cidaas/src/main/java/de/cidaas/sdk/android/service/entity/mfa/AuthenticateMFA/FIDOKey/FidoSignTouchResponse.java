package de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.FIDOKey;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FidoSignTouchResponse implements Serializable {
    private String keyHandle;
    private String signatureData;
    private String clientData;
    private String fidoRequestId;

    public String getFidoRequestId() {
        return fidoRequestId;
    }

    public void setFidoRequestId(String fidoRequestId) {
        this.fidoRequestId = fidoRequestId;
    }

    public String getKeyHandle() {
        return keyHandle;
    }

    public void setKeyHandle(String keyHandle) {
        this.keyHandle = keyHandle;
    }

    public String getSignatureData() {
        return signatureData;
    }

    public void setSignatureData(String signatureData) {
        this.signatureData = signatureData;
    }

    public String getClientData() {
        return clientData;
    }

    public void setClientData(String clientData) {
        this.clientData = clientData;
    }
}
