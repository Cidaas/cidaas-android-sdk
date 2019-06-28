package com.example.cidaasv2.VerificationV2.data.Entity.ResumeLogin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumeLoginEntity implements Serializable {
    private String requestId;
    private String sub;
    private String status_id;
    private String verificationType;

    public ResumeLoginEntity(String requestId, String sub, String status_id, String verificationType) {
        this.requestId = requestId;
        this.sub = sub;
        this.status_id = status_id;
        this.verificationType = verificationType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public String getVerificationType() {
        return verificationType;
    }

    public void setVerificationType(String verificationType) {
        this.verificationType = verificationType;
    }
}
