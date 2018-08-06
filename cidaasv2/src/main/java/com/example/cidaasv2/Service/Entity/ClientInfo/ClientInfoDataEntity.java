package com.example.cidaasv2.Service.Entity.ClientInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 23/5/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientInfoDataEntity implements Serializable {

    public boolean isPasswordless_enabled() {
        return passwordless_enabled;
    }

    public void setPasswordless_enabled(boolean passwordless_enabled) {
        this.passwordless_enabled = passwordless_enabled;
    }

    public String getLogo_uri() {
        return logo_uri;
    }

    public void setLogo_uri(String logo_uri) {
        this.logo_uri = logo_uri;
    }

    public String[] getLogin_providers() {
        return login_providers;
    }

    public void setLogin_providers(String[] login_providers) {
        this.login_providers = login_providers;
    }

    public String getPolicy_uri() {
        return policy_uri;
    }

    public void setPolicy_uri(String policy_uri) {
        this.policy_uri = policy_uri;
    }

    public String getTos_uri() {
        return tos_uri;
    }

    public void setTos_uri(String tos_uri) {
        this.tos_uri = tos_uri;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    private boolean passwordless_enabled;
     private String logo_uri;
     private String[] login_providers;
     private String policy_uri;
     private String tos_uri;
     private String client_name;
}
