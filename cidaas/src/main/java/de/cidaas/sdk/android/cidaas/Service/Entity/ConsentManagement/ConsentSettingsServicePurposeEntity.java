package de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)

public class ConsentSettingsServicePurposeEntity implements Serializable {

    String purpose;
    String purposeCategory;
    String consentType;
    String piiCategory;
    boolean primaryPurpose;
    String termination;
    boolean thirdPartyDisclosure;
    String thirdPartyName;

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurposeCategory() {
        return purposeCategory;
    }

    public void setPurposeCategory(String purposeCategory) {
        this.purposeCategory = purposeCategory;
    }

    public String getConsentType() {
        return consentType;
    }

    public void setConsentType(String consentType) {
        this.consentType = consentType;
    }

    public String getPiiCategory() {
        return piiCategory;
    }

    public void setPiiCategory(String piiCategory) {
        this.piiCategory = piiCategory;
    }

    public boolean isPrimaryPurpose() {
        return primaryPurpose;
    }

    public void setPrimaryPurpose(boolean primaryPurpose) {
        this.primaryPurpose = primaryPurpose;
    }

    public String getTermination() {
        return termination;
    }

    public void setTermination(String termination) {
        this.termination = termination;
    }

    public boolean isThirdPartyDisclosure() {
        return thirdPartyDisclosure;
    }

    public void setThirdPartyDisclosure(boolean thirdPartyDisclosure) {
        this.thirdPartyDisclosure = thirdPartyDisclosure;
    }

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }
}
