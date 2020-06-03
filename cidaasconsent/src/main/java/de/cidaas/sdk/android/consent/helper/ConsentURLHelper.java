package de.cidaas.sdk.android.consent.helper;

public class ConsentURLHelper {
    public static String contentType = "application/x-www-form-urlencoded";
    public static String contentTypeJson = "application/json";

    //Shared Instances
    public static ConsentURLHelper shared;


    public static ConsentURLHelper getShared() {
        if (shared == null) {
            shared = new ConsentURLHelper();
        }
        return shared;
    }


    String consent_url = "/consent-management-srv/tenant/version/pageurl?consent_name=";

    String consent_details = "/consent-management-srv/settings/public?name=";
    String acceptConsent = "/consent-management-srv/user/status";


    //----------------------------------------------------v2--------------------------------------------------
    String consentDetailsV2 = "/consent-management-srv/v2/consent/usage/public/info";
    String acceptConsentV2 = "/consent-management-srv/v2/consent/usage/accept";


    String resumeConsentURL = "/login-srv/precheck/continue/sdk/";


    public String getConsent_url(String consentName, String consentVersion) {
        return consent_url + consentName + "&version=" + consentVersion;
    }

    public String getConsent_details(String baseurl, String consentName) {
        return baseurl + consent_details + consentName;
    }

    public String getAcceptConsent(String baseurl) {
        return acceptConsent;
    }

    public String getConsentDetailsV2(String baseurl) {
        return baseurl + consentDetailsV2;
    }

    public String getAcceptConsentV2(String baseurl) {
        return baseurl + acceptConsentV2;
    }

    public String getResumeConsentURL(String baseurl) {
        return baseurl + resumeConsentURL;
    }
}
