package widas.raja.cidaasconsentv2.data.Entity.v2.AcceptConsent;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AcceptConsentV2ResponseData implements Serializable {
    private boolean accepted;
    private String consent_id="";
    private String consent_version_id="";

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getConsent_id() {
        return consent_id;
    }

    public void setConsent_id(String consent_id) {
        this.consent_id = consent_id;
    }

    public String getConsent_version_id() {
        return consent_version_id;
    }

    public void setConsent_version_id(String consent_version_id) {
        this.consent_version_id = consent_version_id;
    }
}
