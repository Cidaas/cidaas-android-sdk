package de.cidaas.sdk.android.cidaas.Service.Entity.MFA.EnrollMFA.IVR;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnrollIVRResponseDataEntity implements Serializable {
    String sub;
    String trackingCode;
    String verificationType;
    String usageType;


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
