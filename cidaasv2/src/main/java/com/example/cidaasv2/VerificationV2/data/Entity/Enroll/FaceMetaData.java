package com.example.cidaasv2.VerificationV2.data.Entity.Enroll;

import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.FaceMetadataEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FaceMetaData implements Serializable {

    private String sub="";
    private String trackingCode="";
    private String verificationType="";
    private String usageType="";
    private Boolean enrolled=null;

    FaceMetadataEntity meta;

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

    public FaceMetadataEntity getMeta() {
        return meta;
    }

    public void setMeta(FaceMetadataEntity meta) {
        this.meta = meta;
    }
}
