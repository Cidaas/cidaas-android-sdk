package com.example.cidaasv2.Helper.URLHelper;

import android.net.Uri;

import com.example.cidaasv2.Controller.Cidaas;

import java.util.Map;
import java.util.UUID;

import static com.example.cidaasv2.Helper.Genral.GenralHelper.codeChallengeMethod;

/**
 * Created by widasrnarayanan on 5/2/18.
 */

public class URLHelper {
    public static String contentType = "application/x-www-form-urlencoded";
    public static String contentTypeJson = "application/json";

    //Shared Instances
    public static URLHelper shared;


    public static URLHelper getShared()
    {
        if(shared==null)
        {
            shared=new URLHelper();
        }
        return shared;
    }

    String request_id_url="/authz-srv/authrequest/authz/generate";

    String consent_url= "/consent-management-srv/tenant/version/pageurl?consent_name=";

    String consent_details="/consent-management-srv/settings/public?name=";
    String  acceptConsent="/consent-management-srv/user/status";

    String loginWithCredentials="/login-srv/login/sdk";
    String logoutURLForEmbeddedBrowser="/session/end_session";
    String registrationSetup="/registration-callSetup-srv/public/list?acceptlanguage=";

    String clientUrl="/public-srv/public/";
    String tenantUrl="/public-srv/tenantinfo/basic";
    String registerNewUserurl="/users-srv/register";

    String mfa_URL="/verification-srv/settings/list?sub=";
    String mfaList="/verification-srv/settings/listbydeviceid";
    String deleteMFA="/verification-srv/settings/delete/";
    String deleteAllMFA="/verification-srv/settings/deleteall/";
    String denyNotification="/verification-srv/notification/reject";
    String PendingNotificationURL="/verification-srv/notification/initiated/";
    String configuredMFAListURL="/verification-srv/settings/listbydeviceid";
    String updateFCMTokenURL="/device-srv/device/updatefcm";

    String setupEmailMFA="/verification-srv/email/callSetup";
    String enrollEmailMFA="/verification-srv/email/enroll";
    String initiateemailMFA="/verification-srv/email/initiate";
    String authenticateemailMFA="/verification-srv/email/authenticate";


    String setupSMSMFA="/verification-srv/sms/callSetup";
    String enrollSMSMFA="/verification-srv/sms/enroll";
    String initiateSMSMFA="/verification-srv/sms/initiate";
    String authenticateSMSMFA="/verification-srv/sms/authenticate";

    String setupIVRMFA="/verification-srv/ivr/callSetup";
    String enrollIVRMFA="/verification-srv/ivr/enroll";
    String initiateIVRMFA="/verification-srv/ivr/initiate";
    String authenticateIVRMFA="/verification-srv/ivr/authenticate";

    String setupBackupCodeMFA="/verification-srv/backupcode/callSetup";
    String enrollBackupCodeMFA="/verification-srv/backupcode/enroll";
    String initiateBackupCodeMFA="/verification-srv/backupcode/initiate";
    String authenticateBackupCodeMFA="/verification-srv/backupcode/authenticate";

    String setupFaceMFA="/verification-srv/face/callSetup";
    String enrollFaceMFA="/verification-srv/face/enroll";
    String initiateFaceMFA="/verification-srv/face/initiate";
    String authenticateFaceMFA="/verification-srv/face/authenticate";

    String setupFingerprintMFA="/verification-srv/touchid/callSetup";
    String enrollFingerprintMFA="/verification-srv/touchid/enroll";
    String initiateFingerprintMFA="/verification-srv/touchid/initiate";
    String authenticateFingerprintMFA="/verification-srv/touchid/authenticate";

    String setupFIDOMFA="/verification-srv//fidou2f/mobile/callSetup";
    String enrollFIDOMFA="/verification-srv/fidou2f/mobile/enroll";
    String initiateFIDOMFA="/verification-srv/fidou2f/mobile/initiate";
    String authenticateFIDOMFA="/verification-srv/fidou2f/mobile/authenticate";

    String setupPatternMFA="/verification-srv/pattern/callSetup";
    String enrollPatternMFA="/verification-srv/pattern/enroll";
    String initiatePatternMFA="/verification-srv/pattern/initiate";
    String authenticatePatternMFA="/verification-srv/pattern/authenticate";

    String setupSmartPushMFA="/verification-srv/push/callSetup";
    String enrollSmartPushMFA="/verification-srv/push/enroll";
    String initiateSmartPushMFA="/verification-srv/push/initiate";
    String authenticateSmartPushMFA="/verification-srv/push/authenticate";

    String setupTOTPMFA="/verification-srv/totp/callSetup";
    String enrollTOTPMFA="/verification-srv/totp/enroll";
    String initiateTOTPMFA="/verification-srv/totp/initiate";
    String authenticateTOTPMFA="/verification-srv/totp/authenticate";

    String setupVoiceMFA="/verification-srv/voice/callSetup";
    String enrollVoiceMFA="/verification-srv/voice/enroll";
    String initiateVoiceMFA="/verification-srv/voice/initiate";
    String authenticateVoiceMFA="/verification-srv/voice/authenticate";


    String initiateResetPassword="/users-srv/resetpassword/initiate";
    String ResetPasswordValidateCode="/users-srv/resetpassword/validatecode";
    String ResetNewPasswordURl="/users-srv/resetpassword/accept";
    String ChangePasswordURl="/users-srv/changepassword";

    String resumeLoginURL="/login-srv/precheck/continue/sdk/";
    String resumeConsentURL="/login-srv/precheck/continue/sdk/";

    String internaluserProfileURL="/users-srv/internal/userinfo/profile/";
    String userInfoURL="/users-srv/userinfo";

    String RegisterUserAccountInitiate="/verification-srv/account/initiate";
    String RegisterUserAccountVerify="/verification-srv/account/verify";

    String validateDeviceURL="/verification-srv/device/validate";



    String scannedPatternURL="/verification-srv/pattern/scanned";
    String scannedFaceURL="/verification-srv/face/scanned";
    String scannedFingerprintURL="/verification-srv/touchid/scanned";
    String scannedSmartPushURL="/verification-srv/push/scanned";
    String scannedTOTPURL="/verification-srv/totp/scanned";
    String scannedVoiceURL="/verification-srv/voice/scanned";

    String scannedFIDOURL="/verification-srv/fidou2f/mobile/scanned";

    String tokenUrl="/token-srv/token";
    String socialTokenURL="/login-srv/social/token";

    String preAuthCode="&preAuthCode=" ;

    String passwordlessContinueUrl="/login-srv/verification/sdk/login/";
    String deduplicationList="/users-srv/deduplication/info/";

    String socialLoginURL="/login-srv/social/login/";

    String registerdeduplication="/users-srv/deduplication/register/";

    String documentScanner="/access-control-srv/ocr/validate";

    String userLoginInfoURL ="/verification-srv/verificationstatus/status/search/sdk";



    String openIdURL="/.well-known/openid-configuration";

    //V2-Verification
    String setupURL="/verification-srv/v2/callSetup/initiate/";
    String scannedURL="/verification-srv/v2/callSetup/scan/";
    String enrollURL="/verification-srv/v2/callSetup/enroll/";


    public String getSetupURL(String baseurl,String verificationType) {
        return baseurl+setupURL+verificationType;
    }

    public String getScannedURL(String baseurl,String verificationType) {
        return baseurl+scannedURL+verificationType;
    }

    public String getEnrollURL(String baseurl,String verificationType) {
        return baseurl+enrollURL+verificationType;
    }



    public String getLogoutURLForEmbeddedBrowser() {
        return logoutURLForEmbeddedBrowser;
    }

    public String getSocialLoginURL() {
        return socialLoginURL;
    }

    public String getOpenIdURL() {
        return openIdURL;
    }

    public String getPendingNotificationURL() {
        return PendingNotificationURL;
    }

    public String getConfiguredMFAListURL() {
        return configuredMFAListURL;
    }

    public String getDeleteMFA(String userDeviceId, String verificationType) {
        return deleteMFA+userDeviceId+"/"+verificationType;
    }

    public String getUserLoginInfoURL() {
        return userLoginInfoURL;
    }

    public String getUpdateFCMTokenURL() {
        return updateFCMTokenURL;
    }

    public String getDeleteAllMFA() {
        return deleteAllMFA;
    }

    public String getDenyNotification() {
        return denyNotification;
    }

    public String getDeleteEmailMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/EMAIL";
    }

    public String getDeleteSMSMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/SMS";
    }

    public String getDeleteIVRMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/IVR";
    }

    public String getDeleteBackupcodeMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/BACKUPCODE";
    }

    public String getDeletePatternMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/PATTERN";
    }
    public String getDeleteFingerprintMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/TOUCHID";
    }
    public String getDeleteSmartPushMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/PUSH";
    }
    public String getDeleteFaceMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/FACE";
    }

    public String getDeleteVoiceMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/VOICE";
    }


    public String getDeleteFIDOMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/FIDOU2F";
    }

    public String getDeleteTOTPMFA(String userDeviceId) {
        return deleteMFA+userDeviceId+"/TOTP";
    }






    public String getPreAuthCode() {
        return preAuthCode;
    }

    public String getSocialTokenURL() {
        return socialTokenURL;
    }

    public String getUserInfoURL() {
        return userInfoURL;
    }

    public String getDocumentScanner() {
        return documentScanner;
    }

    public String getMfaList() {
        return mfaList;
    }

    public String getRegisterdeduplication() {
        return registerdeduplication;
    }

    public String getDeduplicationList() {
        return deduplicationList;
    }

    public String getPasswordlessContinueUrl() {
        return passwordlessContinueUrl;
    }

    public String getScannedFIDOURL() {
        return scannedFIDOURL;
    }

    public String getScannedFaceURL() {
        return scannedFaceURL;
    }

    public String getScannedFingerprintURL() {
        return scannedFingerprintURL;
    }

    public String getScannedSmartPushURL() {
        return scannedSmartPushURL;
    }

    public String getScannedTOTPURL() {
        return scannedTOTPURL;
    }

    public String getScannedVoiceURL() {
        return scannedVoiceURL;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }



    public String getScannedPatternURL() {
        return scannedPatternURL;
    }

    public String getValidateDeviceURL() {
        return validateDeviceURL;
    }

    public String getRegisterUserAccountInitiate() {
        return RegisterUserAccountInitiate;
    }

    public String getRegisterUserAccountVerify() {
        return RegisterUserAccountVerify;
    }

    public String getSetupEmailMFA() {
        return setupEmailMFA;
    }

    public String getEnrollEmailMFA() {
        return enrollEmailMFA;
    }

    public String getSetupSMSMFA() {
        return setupSMSMFA;
    }

    public String getEnrollSMSMFA() {
        return enrollSMSMFA;
    }

    public String getSetupIVRMFA() {
        return setupIVRMFA;
    }

    public String getEnrollIVRMFA() {
        return enrollIVRMFA;
    }

    public String getSetupBackupCodeMFA() {
        return setupBackupCodeMFA;
    }

    public String getEnrollBackupCodeMFA() {
        return enrollBackupCodeMFA;
    }

    public String getSetupFaceMFA() {
        return setupFaceMFA;
    }

    public String getEnrollFaceMFA() {
        return enrollFaceMFA;
    }

    public String getSetupFingerprintMFA() {
        return setupFingerprintMFA;
    }

    public String getEnrollFingerprintMFA() {
        return enrollFingerprintMFA;
    }

    public String getSetupFIDOMFA() {
        return setupFIDOMFA;
    }

    public String getEnrollFIDOMFA() {
        return enrollFIDOMFA;
    }

    public String getSetupPatternMFA() {
        return setupPatternMFA;
    }

    public String getEnrollPatternMFA() {
        return enrollPatternMFA;
    }

    public String getSetupSmartPushMFA() {
        return setupSmartPushMFA;
    }

    public String getEnrollSmartPushMFA() {
        return enrollSmartPushMFA;
    }

    public String getSetupTOTPMFA() {
        return setupTOTPMFA;
    }

    public String getEnrollTOTPMFA() {
        return enrollTOTPMFA;
    }

    public String getSetupVoiceMFA() {
        return setupVoiceMFA;
    }

    public String getEnrollVoiceMFA() {
        return enrollVoiceMFA;
    }

    public String getResumeConsentURL() {
        return resumeConsentURL;
    }

    public String getInternaluserProfileURL() {
        return internaluserProfileURL;
    }

    public String getResumeLoginURL() {
        return resumeLoginURL;
    }

    public String getInitiateFaceMFA() {
        return initiateFaceMFA;
    }

    public String getAuthenticateFaceMFA() {
        return authenticateFaceMFA;
    }

    public String getInitiateFingerprintMFA() {
        return initiateFingerprintMFA;
    }

    public String getAuthenticateFingerprintMFA() {
        return authenticateFingerprintMFA;
    }

    public String getInitiateFIDOMFA() {
        return initiateFIDOMFA;
    }

    public String getAuthenticateFIDOMFA() {
        return authenticateFIDOMFA;
    }

    public String getInitiatePatternMFA() {
        return initiatePatternMFA;
    }

    public String getAuthenticatePatternMFA() {
        return authenticatePatternMFA;
    }

    public String getInitiateSmartPushMFA() {
        return initiateSmartPushMFA;
    }

    public String getAuthenticateSmartPushMFA() {
        return authenticateSmartPushMFA;
    }

    public String getInitiateTOTPMFA() {
        return initiateTOTPMFA;
    }

    public String getAuthenticateTOTPMFA() {
        return authenticateTOTPMFA;
    }

    public String getInitiateVoiceMFA() {
        return initiateVoiceMFA;
    }

    public String getAuthenticateVoiceMFA() {
        return authenticateVoiceMFA;
    }

    public String getInitiateIVRMFA() {
        return initiateIVRMFA;
    }

    public String getAuthenticateIVRMFA() {
        return authenticateIVRMFA;
    }

    public String getInitiateBackupCodeMFA() {
        return initiateBackupCodeMFA;
    }

    public String getAuthenticateBackupCodeMFA() {
        return authenticateBackupCodeMFA;
    }


    public String getResetNewPasswordURl() {
        return ResetNewPasswordURl;
    }


    public String getInitiateSMSMFA() {
        return initiateSMSMFA;
    }

    public String getAuthenticateSMSMFA() {
        return authenticateSMSMFA;
    }


    public String getChangePasswordURl() {
        return ChangePasswordURl;
    }

    public String getResetPasswordValidateCode() {
        return ResetPasswordValidateCode;
    }

    public String getInitiateResetPassword() {
        return initiateResetPassword;
    }

    public String getAuthenticateemailMFA() {
        return authenticateemailMFA;
    }

    public String getInitiateemailMFA() {
        return initiateemailMFA;
    }

    public String getMfa_URL() {
        return mfa_URL;
    }

    public String getRegisterNewUserurl() {
        return registerNewUserurl;
    }

    public String getRequest_id_url() {
        return request_id_url;
    }

    public String getConsent_url(String consentName,String consentVersion) {
        return consent_url+consentName+"&version=" +consentVersion;
    }

    public String getConsent_details() {
        return consent_details;
    }



    public String getAcceptConsent() {
        return acceptConsent;
    }

    public String getLoginWithCredentials() {
        return loginWithCredentials;
    }

    public String getRegistrationSetup(String acceptedLanguage,String requestId) {
        return registrationSetup+acceptedLanguage + "&requestId=" +requestId;
    }

    public String getClientUrl(String requestId) {
        return clientUrl+requestId;
    }

    public String getTenantUrl() {
        return tenantUrl;
    }

    public String constructLoginURL(String authzURL,String clientId,String redirectURL,String codeChallenge,String viewType)
    {
        try {
            Uri.Builder builder = new Uri.Builder();


            builder
                    .appendQueryParameter("client_id", clientId)
                    .appendQueryParameter("response_type", "code")
                    .appendQueryParameter("view_type", viewType)
                    .appendQueryParameter("nonce", UUID.randomUUID().toString())
                    .appendQueryParameter("redirect_uri", redirectURL)
                    .appendQueryParameter("code_challenge", codeChallenge)
                    .appendQueryParameter("code_challenge_method", codeChallengeMethod);

            for(Map.Entry<String,String> entry : Cidaas.extraParams.entrySet()) {
                builder.appendQueryParameter(entry.getKey(),entry.getValue());
            }

            return authzURL+builder.build().toString();
        }
        catch (Exception e)
        {
            return "";
            //Todo handle Exception
        }
    }

    //TOdo Perform Construct by Challenge
    public String constructURLbyChallenge(String challenge)
    {
       try {
           Uri.Builder builder = new Uri.Builder();

           String authority = challenge;

           builder.scheme("https")
                   .encodedAuthority(authority);

           //Todo Add ClientId and otherinformation
           //.appendQueryParameter("client_id", CidaasSDKEntity.cidaasSDKEntityInstance.getClientId());


           for (Map.Entry<String, String> entry : Cidaas.extraParams.entrySet()) {
               builder.appendQueryParameter(entry.getKey(), entry.getValue());
           }

           return builder.build().toString();
       }
       catch (Exception e)
       {
           return "";
           //Todo handle Exception
       }
    }


    //Construct url for social Login
    public String constructSocialURL(String baseURL,String provider,String requestId)
    {
        try
        {
          String finalSocialURL = baseURL+socialLoginURL+provider+"/"+requestId;

           /* Uri.Builder builder = new Uri.Builder();
            builder
                    .appendQueryParameter("provider", provider)
                    .appendQueryParameter("requestId", requestId);*/

          return finalSocialURL;/*+builder.build().toString()*/
        }
        catch (Exception e)
        {
          return  "";
        }
    }
}
