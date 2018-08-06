package com.example.cidaasv2.Service.Register.RegisterUser;

import com.example.cidaasv2.Helper.Entity.RegistrationEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterNewUserRequestEntity implements Serializable{
    String requestId;
    RegistrationEntity registrationEntity;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public RegistrationEntity getRegistrationEntity() {
        return registrationEntity;
    }

    public void setRegistrationEntity(RegistrationEntity registrationEntity) {
        this.registrationEntity = registrationEntity;
    }
}
