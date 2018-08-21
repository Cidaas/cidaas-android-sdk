package com.example.cidaasv2.Service.Entity.MFA.TOTPEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TOTPEntity implements Serializable {
    String name;
    String issuer;
    String timer_count;
    String totp_string;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getTimer_count() {
        return timer_count;
    }

    public void setTimer_count(String timer_count) {
        this.timer_count = timer_count;
    }

    public String getTotp_string() {
        return totp_string;
    }

    public void setTotp_string(String totp_string) {
        this.totp_string = totp_string;
    }
}
