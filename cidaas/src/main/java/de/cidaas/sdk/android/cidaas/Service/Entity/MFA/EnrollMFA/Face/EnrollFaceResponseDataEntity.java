package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.Face;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollFaceResponseDataEntity implements Serializable {
    String sub = "";
    String trackingCode = "";
    String verificationType = "";
    String usageType = "";
    String current_status = "";
    String usage_pass = "";
    Boolean enrolled = null;

    FaceMetadataEntity meta;


    public FaceMetadataEntity getMeta() {
        return meta;
    }

    public void setMeta(FaceMetadataEntity meta) {
        this.meta = meta;
    }

    public Boolean getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Boolean enrolled) {
        this.enrolled = enrolled;
    }

    public String getUsage_pass() {
        return usage_pass;
    }

    public void setUsage_pass(String usage_pass) {
        this.usage_pass = usage_pass;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

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

}
