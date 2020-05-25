package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.IVR;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateIVRMFAResponseDataEntity implements Serializable {
    String statusId;

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}
