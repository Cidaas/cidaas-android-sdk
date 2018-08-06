package com.example.cidaasv2.Service.Register.RegisterUserAccountVerification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterUserAccountInitiateResponseDataEntity implements Serializable{
    String accvid="";

    public String getAccvid() {
        return accvid;
    }

    public void setAccvid(String accvid) {
        this.accvid = accvid;
    }
}
