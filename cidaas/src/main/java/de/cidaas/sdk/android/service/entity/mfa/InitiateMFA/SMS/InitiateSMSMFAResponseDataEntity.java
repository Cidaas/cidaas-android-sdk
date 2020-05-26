package de.cidaas.sdk.android.service.entity.mfa.InitiateMFA.SMS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateSMSMFAResponseDataEntity implements Serializable {
    String statusId;

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}
