package de.cidaas.sdk.android.cidaasnative.data.Entity.AccountVerification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountVerificationListResponseDataEntity implements Serializable {
    private Boolean EMAIL = false;
    private Boolean MOBILE = false;
    private Boolean USER_NAME = false;

    public Boolean getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(Boolean EMAIL) {
        this.EMAIL = EMAIL;
    }

    public Boolean getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(Boolean MOBILE) {
        this.MOBILE = MOBILE;
    }

    public Boolean getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(Boolean USER_NAME) {
        this.USER_NAME = USER_NAME;
    }
}
