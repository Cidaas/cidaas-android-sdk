package widas.raja.cidaasconsentv2.data.Entity.v2.ConsentDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ConsentDetailsV2ResponseDataEntity implements Serializable {
    
    private String consent_id = "";
    private String consent_name = "";
    private String consent_version_id = "";
    private String content = "";
    private ConsentResponseScopes[] scopes;
    public String sub = "";

    public String getConsent_id() {
        return consent_id;
    }

    public void setConsent_id(String consent_id) {
        this.consent_id = consent_id;
    }

    public String getConsent_name() {
        return consent_name;
    }

    public void setConsent_name(String consent_name) {
        this.consent_name = consent_name;
    }

    public String getConsent_version_id() {
        return consent_version_id;
    }

    public void setConsent_version_id(String consent_version_id) {
        this.consent_version_id = consent_version_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ConsentResponseScopes[] getScopes() {
        return scopes;
    }

    public void setScopes(ConsentResponseScopes[] scopes) {
        this.scopes = scopes;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
