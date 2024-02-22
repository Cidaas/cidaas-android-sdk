package de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgressiveRegistrationResponseDataEntity implements Serializable {
    public boolean isLogged_in() {
        return logged_in;
    }

    public void setLogged_in(boolean logged_in) {
        this.logged_in = logged_in;
    }

    boolean logged_in;
    String validation_type;

    ProgressiveRegistrationMetaDataEntity meta_data;
    boolean used;



    public String getValidation_type() {
        return validation_type;
    }

    public void setValidation_type(String validation_type) {
        this.validation_type = validation_type;
    }

    public ProgressiveRegistrationMetaDataEntity getMeta_data() {
        return meta_data;
    }

    public void setMeta_data(ProgressiveRegistrationMetaDataEntity meta_data) {
        this.meta_data = meta_data;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
