package de.cidaas.sdk.android.cidaasnative.data.entity.resetpassword.changepassword;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordResponseDataEntity implements Serializable {
    boolean changed;

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {

        this.changed = changed;
    }
}
