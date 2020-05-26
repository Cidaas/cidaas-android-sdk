package de.cidaas.sdk.android.cidaasVerification.Helper;

public class VerificationV1URLHelper {


    public static String contentType = "application/x-www-form-urlencoded";
    public static String contentTypeJson = "application/json";

    //Shared Instances
    public static VerificationV1URLHelper shared;


    public static VerificationV1URLHelper getShared() {
        if (shared == null) {
            shared = new VerificationV1URLHelper();
        }
        return shared;
    }


}
