package de.cidaas.sdk.android.cidaasVerification.data.Entity.Push.PushReject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.ExcangeId.ExchangeIDEntity;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PushRejectResponseDataEntity implements Serializable {


    private String sub = "";
    private ExchangeIDEntity exchange_id;
    private String client_id = "";
    private String device_id = "";
    private String push_id = "";
    private String status_id = "";

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public ExchangeIDEntity getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(ExchangeIDEntity exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }
}
