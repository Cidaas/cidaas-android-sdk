package de.cidaas.sdk.android.cidaasverification.data.entity.settings.pendingnotification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

import de.cidaas.sdk.android.cidaasverification.data.entity.excangeid.ExchangeIDEntity;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PushEntity implements Serializable {
    String sub = "";
    String exchange_id;
    String tenant_name = "";
    String tenant_key = "";
    String verification_type = "";
    String request_time;

    String expires_at;
    String[] requested_types;

    private String message;

    private String client_name;

    public String getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(String expires_at) {
        this.expires_at = expires_at;
    }
    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getExchange_id() {
        return exchange_id;
    }

    public void setExchange_id(String exchange_id) {
        this.exchange_id = exchange_id;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getTenant_key() {
        return tenant_key;
    }

    public void setTenant_key(String tenant_key) {
        this.tenant_key = tenant_key;
    }

    public String getVerification_type() {
        return verification_type;
    }

    public void setVerification_type(String verification_type) {
        this.verification_type = verification_type;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String[] getRequested_types() {
        return requested_types;
    }

    public void setRequested_types(String[] requested_types) {
        this.requested_types = requested_types;
    }

    public String getMessage() {return message;}

    public void setMessage(String message) {this.message = message;}

    public String getClient_name() {return client_name;}

    public void setClient_name(String client_name){this.client_name = client_name;}
}
