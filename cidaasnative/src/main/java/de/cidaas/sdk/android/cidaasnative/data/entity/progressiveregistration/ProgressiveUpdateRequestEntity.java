package de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Dictionary;

//Created entity based on https://docs.cidaas.com/docs/cidaas-iam/tnotekth3x1xg-progressive-update-user  (15-feb-2024)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressiveUpdateRequestEntity implements Serializable {
    UserDataEntity userData;

    UserStatus userStatus;

    Dictionary<String,Object> customFields;
    boolean user_status_reason;

    boolean mfa_enabled;


    public UserDataEntity getUserData() {
        return userData;
    }

    public void setUserData(UserDataEntity userData) {
        this.userData = userData;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Dictionary<String, Object> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Dictionary<String, Object> customFields) {
        this.customFields = customFields;
    }

    public boolean isUser_status_reason() {
        return user_status_reason;
    }

    public void setUser_status_reason(boolean user_status_reason) {
        this.user_status_reason = user_status_reason;
    }

    public boolean isMfa_enabled() {
        return mfa_enabled;
    }

    public void setMfa_enabled(boolean mfa_enabled) {
        this.mfa_enabled = mfa_enabled;
    }
}



enum UserStatus {
    VERIFIED,
    PENDING,
    DELETED,
    DECLINED ,
    LOCKED ,
    COMBINED
}