package com.example.cidaasv2.VerificationV2.data.Entity.Initiate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InititateResponse implements Serializable {

  InitiateResponseDataEntity dataEntity;

    public InitiateResponseDataEntity getDataEntity() {
        return dataEntity;
    }

    public void setDataEntity(InitiateResponseDataEntity dataEntity) {
        this.dataEntity = dataEntity;
    }
}
