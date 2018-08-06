package com.example.cidaasv2.Service.Register.RegisterUserAccountVerification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserAccountVerifyRequestEntity implements Serializable{
    String accvid;
    String code;

    public String getAccvid() {
        return accvid;
    }

    public void setAccvid(String accvid) {
        this.accvid = accvid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
