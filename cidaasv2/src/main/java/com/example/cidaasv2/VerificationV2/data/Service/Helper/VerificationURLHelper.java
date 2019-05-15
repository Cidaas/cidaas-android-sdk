package com.example.cidaasv2.VerificationV2.data.Service.Helper;

public class VerificationURLHelper {
    public static String contentType = "application/x-www-form-urlencoded";
    public static String contentTypeJson = "application/json";

    //Shared Instances
    public static VerificationURLHelper shared;


    public static VerificationURLHelper getShared()
    {
        if(shared==null)
        {
            shared=new VerificationURLHelper();
        }
        return shared;
    }

    //V2-Verification
    String setupURL="/verification-srv/v2/setup/initiate/";
    String scannedURL="/verification-srv/v2/setup/scan/";
    String enrollURL="/verification-srv/v2/setup/enroll/";

    String initiateURL="/verification-srv/v2/setup/enroll/";

    String pushAcknowledgeURL="/verification-srv/v2/authenticate/push_acknowledge/";
    String pushAllowURL="/verification-srv/v2/authenticate/allow/";
    String pushRejectURL ="/verification-srv/v2/authenticate/reject/";

    String authenticateURL="/verification-srv/v2/authenticate/authenticate/";



    public String getSetupURL(String baseurl,String verificationType) {
        return baseurl+setupURL+(verificationType.toLowerCase());
    }

    public String getScannedURL(String baseurl,String verificationType) {
        return baseurl+scannedURL+(verificationType.toLowerCase());
    }

    public String getEnrollURL(String baseurl,String verificationType) {
        return baseurl+enrollURL+(verificationType.toLowerCase());
    }


    public String getInitiateURL(String baseurl,String verificationType) {
        return baseurl+initiateURL+(verificationType.toLowerCase());
    }

    public String getPushAcknowledgeURL(String baseurl,String verificationType) {
        return baseurl+pushAcknowledgeURL+(verificationType.toLowerCase());
    }

    public String getPushAllowURL(String baseurl,String verificationType) {
        return baseurl+pushAllowURL+(verificationType.toLowerCase());
    }

    public String getPushDenyURL(String baseurl,String verificationType) {
        return baseurl+ pushRejectURL +(verificationType.toLowerCase());
    }

    public String getAuthenticateURL(String baseurl,String verificationType) {
        return baseurl+authenticateURL+(verificationType.toLowerCase());
    }
}
