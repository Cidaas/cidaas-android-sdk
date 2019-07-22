package com.example.cidaasv2.VerificationV2.data.Entity.ResumeLogin;

import com.example.cidaasv2.Helper.Enums.UsageType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumeLoginEntity implements Serializable {
    private String requestId;
    private String sub;
    private String status_id;
    private String verificationType;
    private String trackId;
    private String usageType;

    private String device_id="";
    private String push_id="";


    public ResumeLoginEntity() {
    }


    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getPush_id() {
        return push_id;
    }

    public void setPush_id(String push_id) {
        this.push_id = push_id;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
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

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }


    public static ResumeLoginEntity getResumeMFAEntity(String requestId, String sub, String trackId,String status_id, String verificationType) {
        ResumeLoginEntity resumeLoginEntity=new ResumeLoginEntity();
        resumeLoginEntity.setRequestId(requestId);
        resumeLoginEntity.setSub(sub);
        resumeLoginEntity.setStatus_id(status_id);
        resumeLoginEntity.setTrackId(trackId);
        resumeLoginEntity.setVerificationType(verificationType);
        resumeLoginEntity.setUsageType(UsageType.MFA);
        return resumeLoginEntity;

    }

    public static ResumeLoginEntity getResumePasswordlessEntity(String requestId, String sub, String status_id, String verificationType)
    {
            ResumeLoginEntity resumeLoginEntity=new ResumeLoginEntity();
            resumeLoginEntity.setRequestId(requestId);
            resumeLoginEntity.setSub(sub);
            resumeLoginEntity.setStatus_id(status_id);
            resumeLoginEntity.setVerificationType(verificationType);
            resumeLoginEntity.setUsageType(UsageType.PASSWORDLESS);
            return resumeLoginEntity;

    }

    public static ResumeLoginEntity getResumeConsentEntity(String trackId,String requestId, String sub, String status_id)
    {
        ResumeLoginEntity resumeLoginEntity=new ResumeLoginEntity();
        resumeLoginEntity.setRequestId(requestId);
        resumeLoginEntity.setSub(sub);
        resumeLoginEntity.setStatus_id(status_id);
        resumeLoginEntity.setTrackId(trackId);
        resumeLoginEntity.setUsageType(UsageType.MFA);
        return resumeLoginEntity;

    }
}
