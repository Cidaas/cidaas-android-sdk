package de.cidaas.sdk.android.cidaasverification.data.service.helper;

public class VerificationURLHelper {
    public static String contentType = "application/x-www-form-urlencoded";
    public static String contentTypeJson = "application/json";

    //Shared Instances
    public static VerificationURLHelper shared;


    public static VerificationURLHelper getShared() {
        if (shared == null) {
            shared = new VerificationURLHelper();
        }
        return shared;
    }

    //V2-Verification-Setup
    String setupCommonURL = "/verification-actions-srv/setup/";
    String scannedURL = "/verification-srv/v2/setup/scan/";
    String enrollURL = "/verification-srv/v2/setup/enroll/";

    //V2-Verification-initiate
    String authenticateCommonURL = "/verification-srv/authentication/";

    //V2-Verification-Push
    private String pushAcknowledgeURL = "/verification-srv/v2/authenticate/push_acknowledge/";
    private String pushAllowURL = "/verification-srv/v2/authenticate/allow/";
    private String pushRejectURL = "/verification-srv/v2/authenticate/reject/";

    //V2-Verification-Authenticate
    private String authenticateURL = "/verification-srv/v2/authenticate/authenticate/";

    //V2-Verification-Delete
    private String deleteURL = "/verification-srv/v2/setup/device/configured/remove/";
    private String deleteCommonURL = "/verification-actions-srv/setup/device/";

    //V2-Verification-Get Details
    private String getConfiguredListURL = "/verification-actions-srv/setup/device/list";

    private String getPendingNotificationURL = "/verification-actions-srv/setup/device/notification/list";

    private String getAuthentictedHistoryURL = "/verification-srv/v2/setup/device/authenticated/list";
    private String userHistoryURL = "/verification-actions-srv/mfa/history";
    private String userHistoryDetailURL = "/verification-actions-srv/mfa/timeline";
    //Update FCM Token
    private String updateFCMTokenURL = "/verification-actions-srv/setup/device/pushid";

    //Login Continue call Passwordless
    private String passwordlessContinueUrl = "/login-srv/verification/sdk/login/";

    //Login Continue call MFA
    private String mfaContinueCallUrl = "/login-srv/precheck/continue/sdk/";

    private String userDevices = "/verification-actions-srv/device/list";
    private String userDevicesUnlink = "/verification-actions-srv/device/unlink";


    public String getSetupURL(String baseurl, String verificationType) {
        return baseurl + setupCommonURL + (verificationType.toLowerCase()) +"/initiate";
    }

    public String getScannedURL(String baseurl, String verificationType) {
        return baseurl + setupCommonURL + (verificationType.toLowerCase())+"/scan";
    }

    public String getEnrollURL(String baseurl, String verificationType) {
        return baseurl + setupCommonURL + (verificationType.toLowerCase())+ "/verification";
    }

    public String getSetUpCancelURL(String baseurl, String verificationType) {
        return baseurl + authenticateCommonURL + (verificationType.toLowerCase())+ "/cancel";
    }

    public String getInitiateURL(String baseurl, String verificationType) {
        return baseurl + authenticateCommonURL + (verificationType.toLowerCase()+ "/initiation");
    }

    public String getPushAcknowledgeURL(String baseurl, String verificationType) {
        return baseurl + authenticateCommonURL + (verificationType.toLowerCase()+ "/push/acknowledge");
    }

    public String getPushAllowURL(String baseurl, String verificationType) {
        return baseurl + authenticateCommonURL + (verificationType.toLowerCase()+ "/allow");
    }

    public String getPushDenyURL(String baseurl, String verificationType) {
        return baseurl + authenticateCommonURL + (verificationType.toLowerCase()+ "/reject");
    }

    public String getAuthenticateURL(String baseurl, String verificationType) {
        return baseurl + authenticateCommonURL + (verificationType.toLowerCase()+ "/verification");
    }

    public String getDeleteURL(String baseurl, String verificationType, String sub) {
        return baseurl + deleteCommonURL + (verificationType.toLowerCase()) + "/" + sub;
    }

    public String getDeleteAllURL(String baseurl, String deviceId) {
        return baseurl + deleteCommonURL + deviceId;
    }

    public String getConfiguredListURL(String baseurl) {

        return baseurl + getConfiguredListURL;
    }

    public String getPendingNotificationURL(String baseurl) {
        return baseurl + getPendingNotificationURL;
    }

    public String getAuthentictedHistoryURL(String baseurl) {
        return baseurl + getAuthentictedHistoryURL;
    }
    public String getAuthentictedHistoryURLNew(String baseurl) {
         return baseurl + userHistoryURL;
    }
    public String getAuthentictedHistoryDetailURL(String baseurl) {
        return baseurl + userHistoryDetailURL;
    }

    public String getUpdateFCMTokenURL(String baseurl) {
        return baseurl + updateFCMTokenURL;
    }

    public String getPasswordlessContinueUrl(String baseurl) {
        return baseurl + passwordlessContinueUrl;
    }

    public String getMfaContinueCallUrl(String baseurl, String trackId) {
        return baseurl + mfaContinueCallUrl + trackId;
    }
    public String getDevicesList(String baseurl) {
        //return baseurl + getAuthentictedHistoryURL;
        return baseurl + userDevices;
    }
    public String getDevicesRemove(String baseurl) {
        //return baseurl + getAuthentictedHistoryURL;
        return baseurl + userDevicesUnlink;
    }

}
