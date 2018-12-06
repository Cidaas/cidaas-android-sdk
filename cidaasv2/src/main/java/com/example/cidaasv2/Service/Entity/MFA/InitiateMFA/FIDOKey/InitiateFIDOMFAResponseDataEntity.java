package com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey;

import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey.FidoSignTouchResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InitiateFIDOMFAResponseDataEntity implements Serializable{
    String statusId;
    NFC_sign_object fido_init_request_data;


    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public NFC_sign_object getFido_init_request_data() {
        return fido_init_request_data;
    }

    public void setFido_init_request_data(NFC_sign_object fido_init_request_data) {
        this.fido_init_request_data = fido_init_request_data;
    }
}
