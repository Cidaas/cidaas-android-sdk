package com.example.cidaasv2.VerificationV2.data.Entity.Enroll;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.FaceMetadataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceMetaData {
    private String sub="";
    private String trackingCode="";
    private String verificationType="";
    private String usageType="";
    private Boolean enrolled=null;

    VoiceMetaDataEntity meta;


    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
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

    public Boolean getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Boolean enrolled) {
        this.enrolled = enrolled;
    }

    public VoiceMetaDataEntity getMeta() {
        return meta;
    }

    public void setMeta(VoiceMetaDataEntity meta) {
        this.meta = meta;
    }
}
