package de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressiveRegistrationMetaDataEntity implements Serializable {
    String[] amr_values;
    String[] missing_fields;

    ProgressiveRegistrationIdentityEntity identity;


    public String[] getAmr_values() {
        return amr_values;
    }

    public void setAmr_values(String[] amr_values) {
        this.amr_values = amr_values;
    }

    public String[] getMissing_fields() {
        return missing_fields;
    }

    public void setMissing_fields(String[] missing_fields) {
        this.missing_fields = missing_fields;
    }

    public ProgressiveRegistrationIdentityEntity getIdentity() {
        return identity;
    }

    public void setIdentity(ProgressiveRegistrationIdentityEntity identity) {
        this.identity = identity;
    }
}
