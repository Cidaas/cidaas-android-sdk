package de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressiveUpdateResponseEntity implements Serializable {
    String trackId;
    String sub;
    boolean email_verified;
    boolean mobile_number_verified;

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public boolean isEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = email_verified;
    }

    public boolean isMobile_number_verified() {
        return mobile_number_verified;
    }

    public void setMobile_number_verified(boolean mobile_number_verified) {
        this.mobile_number_verified = mobile_number_verified;
    }
}
