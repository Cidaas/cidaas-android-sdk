package com.example.cidaasv2.VerificationV2.data.Entity.Setup;

import com.example.cidaasv2.VerificationV2.data.Entity.ExcangeIdEntity.ExchangeIDEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SetupResponseData implements Serializable {
    private ExchangeIDEntity exchange_id;
    private String sub="";
    private String status_id="";
    private String authenticator_client_id;

    public ExchangeIDEntity getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(ExchangeIDEntity exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getAuthenticator_client_id() {
        return authenticator_client_id;
    }

    public void setAuthenticator_client_id(String authenticator_client_id) {
        this.authenticator_client_id = authenticator_client_id;
    }
}
