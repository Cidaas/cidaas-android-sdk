package de.cidaas.sdk.android.cidaas.Helper.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SocialAccessTokenEntity implements Serializable {

    private String token;
    private String provider;
    private String DomainURL;
    private String requestId;
    private String viewType;

    public SocialAccessTokenEntity() {
    }

    public SocialAccessTokenEntity(String token, String provider, String domainURL, String requestId, String viewType) {
        this.token = token;
        this.provider = provider;
        this.DomainURL = domainURL;
        this.requestId = requestId;
        this.viewType = viewType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDomainURL() {
        return DomainURL;
    }

    public void setDomainURL(String domainURL) {
        DomainURL = domainURL;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }
}
