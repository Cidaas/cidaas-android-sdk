package com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory;

import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponseDataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticatedHistoryResponse implements Serializable {
    boolean success;
    int status;
    AuthenticateResponseDataEntity data;
}
