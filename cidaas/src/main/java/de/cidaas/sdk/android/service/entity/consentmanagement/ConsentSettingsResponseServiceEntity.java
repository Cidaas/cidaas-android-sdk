package de.cidaas.sdk.android.service.entity.consentmanagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsentSettingsResponseServiceEntity {

    String service;
    ConsentSettingsServicePurposeEntity[] purposes;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public ConsentSettingsServicePurposeEntity[] getPurposes() {
        return purposes;
    }

    public void setPurposes(ConsentSettingsServicePurposeEntity[] purposes) {
        this.purposes = purposes;
    }
}
