package de.cidaas.sdk.android.cidaasVerification.data.entity.authenticate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.cidaasVerification.data.entity.excangeid.ExchangeIDEntity;


@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticateResponseDataEntity implements Serializable {

    private ExchangeIDEntity exchange_id;
    private String sub = "";
    private String status_id = "";

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
}
