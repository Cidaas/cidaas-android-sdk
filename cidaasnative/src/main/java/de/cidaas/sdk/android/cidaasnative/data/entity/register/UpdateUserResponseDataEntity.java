package de.cidaas.sdk.android.cidaasnative.data.entity.register;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserResponseDataEntity implements Serializable {
    public boolean updated = false;

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
