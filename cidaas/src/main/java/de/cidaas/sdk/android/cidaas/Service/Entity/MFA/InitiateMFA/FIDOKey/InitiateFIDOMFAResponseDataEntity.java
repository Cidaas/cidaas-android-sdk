package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.InitiateMFA.FIDOKey;

import de.cidaas.sdk.android.cidaas.Service.Entity.NotificationEntity.GetPendingNotification.NFCSignObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateFIDOMFAResponseDataEntity implements Serializable {
    String statusId;
    NFCSignObject fido_init_request_data;
    String current_status;

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public NFCSignObject getFido_init_request_data() {
        return fido_init_request_data;
    }

    public void setFido_init_request_data(NFCSignObject fido_init_request_data) {
        this.fido_init_request_data = fido_init_request_data;
    }
}
