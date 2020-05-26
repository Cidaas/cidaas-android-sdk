package de.cidaas.sdk.android.cidaasVerification.data.Entity.Initiate;

import java.io.Serializable;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.ExcangeId.ExchangeIDEntity;


public class InitiateResponseDataEntity implements Serializable {

    ExchangeIDEntity exchange_id;
    String sub;
    String status_id;

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
