package com.example.cidaasv2.Service.Entity.ResetPassword;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by widasrnarayanan on 23/5/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPasswordResultDataEntity implements Serializable{
    boolean reset_initiated;
    String rprq;

    public boolean isReset_initiated() {
        return reset_initiated;
    }

    public void setReset_initiated(boolean reset_initiated) {
        this.reset_initiated = reset_initiated;
    }

    public String getRprq() {
        return rprq;
    }

    public void setRprq(String rprq) {
        this.rprq = rprq;
    }
}
