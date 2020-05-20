package com.example.cidaasv2.Controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.DocumentScanner.DocumentScannnerController;
import com.example.cidaasv2.Controller.Repository.LocalAuthentication.LocalAuthenticationController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Controller.Repository.UserLoginInfo.UserLoginInfoController;
import com.example.cidaasv2.Controller.Repository.UserProfile.UserProfileController;
import com.example.cidaasv2.Helper.Entity.LocalAuthenticationEntity;
import com.example.cidaasv2.Helper.Entity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Helper.Entity.SocialAccessTokenEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.CidaasHelper;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Loaders.ICustomLoader;
/*import com.example.cidaasv2.Interface.IOAuthWebLogin;*/
import com.example.cidaasv2.Library.BiometricAuthentication.BiometricCallback;
import com.example.cidaasv2.Library.BiometricAuthentication.BiometricEntity;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.DocumentScanner.DocumentScannerServiceResultEntity;
import com.example.cidaasv2.Service.Entity.UserLoginInfo.UserLoginInfoEntity;
import com.example.cidaasv2.Service.Entity.UserLoginInfo.UserLoginInfoResponseEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;

import java.io.File;
import java.util.HashMap;

/**
 * Created by widasrnarayanan on 16/1/18.
 */
/*
implements IOAuthWebLogin*/

public class Cidaas  {

    private static final int MY_SCAN_REQUEST_CODE = 3;
    private static final int LOCAL_AUTH_REQUEST_CODE = 303;
    private static final int LOCAL_REQUEST_CODE = 302;
    private static final int RESULT_OK = -1;



    Result<LocalAuthenticationEntity> localAuthenticationEntityCallback;


    public Context context;
    public Activity activityFromCidaas;

    public static String usagePass = "";
    public static ICustomLoader loader;
    
    public static final String FIDO_VERSION = "U2F_V2";

    public WebAuthError webAuthError = null;
    public static String logoURLlocal="https://cdn.shortpixel.ai/client/q_glossy,ret_img/https://www.cidaas.com/wp-content/uploads/2018/02/logo.png";

    //saved Properties for Global Access
  //  Dictionary<String, String> savedProperties;

    //To check the Loader
    private boolean displayLoader = false;

    //Todo Confirm it must be a static one
    //Extra parameter that is passed in URL
    public static HashMap<String, String> extraParams = new HashMap<>();

//Create a Shared Instance

    private static Cidaas cidaasInstance;

   // public  static CidaasVerification verification;
   // =SetupController.getShared()



   /* public static CidaasVerification getVerification(Context YourActivitycontext) {
        if (verification == null) {
            verification = new CidaasVerification(YourActivitycontext);
        }

        return verification;
    }
*/

    public static Cidaas getInstance(Context YourActivitycontext) {
        if (cidaasInstance == null) {
            cidaasInstance = new Cidaas(YourActivitycontext);
        }

        return cidaasInstance;
    }

    //Constructor
    public Cidaas(Context yourActivityContext) {
        this.context = yourActivityContext;
        CidaasHelper.getShared(yourActivityContext).initialiseObject();
    }


    //-----------------------------------------_Common For Cidaas Instances-------------------------------------------------------------------------


    public boolean isENABLE_PKCE() {
       return CidaasHelper.getShared(context).isENABLE_PKCE();
    }

    public void setENABLE_PKCE(boolean ENABLE_PKCE)
    {
        CidaasHelper.getShared(context).setENABLE_PKCE(ENABLE_PKCE);
    }

    //enableLog

    public boolean isLogEnable()
    {
        return CidaasHelper.getShared(context).isLogEnable();
    }

    public String enableLog()
    {
        return CidaasHelper.getShared(context).enableLog();
    }

    public String disableLog()
    {
        return CidaasHelper.getShared(context).disableLog();
    }




    // ****** LOGIN WITH Document *****-------------------------------------------------------------------------------------------------------
    public void VerifyDocument(final File photo, final String sub, final Result<DocumentScannerServiceResultEntity> resultEntityResult) {
        DocumentScannnerController.getShared(context).sendtoServicecall( photo, sub,resultEntityResult);
    }


    //----------------------------------------------------------------------------------------------------------------------------------------

    //@Override
    public void getAccessToken(String sub, Result<AccessTokenEntity> result) {
        AccessTokenController.getShared(context).getAccessToken(sub, result);
    }

    public void getAccessTokenFromRefreshToken(String refershtoken, Result<AccessTokenEntity> result) {
        AccessTokenController.getShared(context).getAccessToken(refershtoken, result);
    }

    public void getAccessTokenBySocial(SocialAccessTokenEntity socialAccessTokenEntity, Result<AccessTokenEntity> result) {
        AccessTokenController.getShared(context).getAccessTokenBySocial(socialAccessTokenEntity, result);
    }

    public void setAccessToken(final AccessTokenEntity accessTokenEntity, final Result<LoginCredentialsResponseEntity> result){
        AccessTokenController.getShared(context).setAccessToken(accessTokenEntity,result);
    }


    //Get userinfo Based on Access Token
   // @Override
    public void getUserInfo(String sub, final Result<UserinfoEntity> callback) {
        UserProfileController.getShared(context).getUserProfile(sub,callback);
    }

  /*  @Override
    public void renewToken(String refershtoken, Result<AccessTokenEntity> result) {
        //TODO
    }*/

    //Resume After open App From Broswer
    public void handleToken(String code){   /*,Result<AccessTokenEntity> callbacktoMain*/

        LoginController.getShared(context).handleToken(code);
    }

     // Custom Tab
    public void loginWithBrowser(@NonNull final Context activityContext, @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
       LoginController.getShared(context).loginWithBrowser(activityContext,color,callbacktoMain);
    }

    //Get Login URL
    public void getLoginURL( final Result<String> callback) {
        LoginController.getShared(context).getLoginURL(callback);
    }


    //Get Registration URL
    public void getRegistrationURL( final Result<String> callback) {
        LoginController.getShared(context).getRegistrationURL(callback);
    }

    // Custom Tab
    public void RegisterWithBrowser(@NonNull final Context activityContext, @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
        LoginController.getShared(context).registerWithBrowser(activityContext,color,callbacktoMain);
    }
    //------------------------------------------------------------------------------------------Local Authentication----------------------------------------

    //Cidaas Set OnActivity Result For Handling Device Authentication
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LocalAuthenticationController.getShared(context).onActivityResult(requestCode,resultCode,data);
    }

    //Show the Alert Dilog Which is go to settings
    private void showDialogToSetupLock(final Activity activity,Result<LocalAuthenticationEntity> result) {
        LocalAuthenticationController.getShared(context).showDialogToSetupLock(activity,result);
    }



    //Method for Local Authentocation
    //TOdo Ask Ganesh For Authenticator
    public void localAuthentication(final Activity activity, Result<LocalAuthenticationEntity> result) {
        LocalAuthenticationController.getShared(context).localAuthentication(activity,result);
    }


    //Method for Local Biometric Authentocation
    @TargetApi(Build.VERSION_CODES.P)
    public void localBiometricAuthentication(final BiometricEntity biometricBuilder, BiometricCallback callback) {
        LocalAuthenticationController.getShared(context).localBiometricAuthenticate(biometricBuilder,callback);
    }

    //------------------------------------------------------------------------------------------XXXXXXX----------------------------------------

    public static String getSDKVersion(){
        String version="";
        try {

            version ="("+ BuildConfig.VERSION_NAME+")";
        } catch (Exception e) {
            return "";
        }

        return  version;
    }

    public String getUserAgent()
    {
        return DBHelper.getShared().getUserAgent();
    }


    //----------------------------------LocationHistory------------------------------------------------------------------------------------------------------
    //Todo Add Logs
    public void getUserLoginInfo(final UserLoginInfoEntity userLoginInfoEntity, final Result<UserLoginInfoResponseEntity> result)
    {
        UserLoginInfoController.getShared(context).getUserLoginInfo(userLoginInfoEntity,result);
    }

    // Ask Ganehs
    public void loginWithSocial(@NonNull final Context activityContext,@NonNull final String requestId, @NonNull final String provider,
                                @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
        LoginController.getShared(context).loginWithSocial(activityContext,requestId,provider,color,callbacktoMain);
    }

    //Get Social Login URL
    public void getSocialLoginURL(final String requestId, final String provider, final Result<String> callback) {
        LoginController.getShared(context).getSocialLoginURL(provider,requestId, callback);
    }



    // ****** DONE Verification *****-------------------------------------------------------------------------------------------------------

/*

    public void scanned(@NonNull final String statusId, @NonNull final String sub,final String secret, @NonNull final String verificationType, final Result<ScannedResponseEntity> scannedResult)
    {
        if(verificationType.equalsIgnoreCase("PATTERN"))
        {
            scannedPattern(statusId,scannedResult);
        }
        else if(verificationType.equalsIgnoreCase(AuthenticationType.FINGERPRINT))
        {
            scannedFingerprint(statusId,scannedResult);
        }
        else if(verificationType.equalsIgnoreCase("VOICE"))
        {
            scannedVoice(statusId,scannedResult);
        }
        else if(verificationType.equalsIgnoreCase("FACE"))
        {
            scannedFace(statusId,scannedResult);
        }
        else if(verificationType.equalsIgnoreCase("TOTP"))
        {
            scannedTOTP(statusId,sub,secret,scannedResult);
        }
        else if(verificationType.equalsIgnoreCase(AuthenticationType.SMARTPUSH))
        {
            scannedSmartPush(statusId,scannedResult);
        }
        else if(verificationType.equalsIgnoreCase("FIDOU2F"))
        {
            scannedFIDO(statusId,scannedResult);
        }

    }
*/

   /* public void setupv2(@NonNull final SetupEntity setupEntity, final Result<SetupResponse> setupResponseResult)
    {
        SetupController.getShared(context).setupVerification(setupEntity,setupResponseResult);
    }

    public void scannedv2(@NonNull final ScannedEntity scannedEntity, final Result<ScannedResponse> scannedResult)
    {
        ScannedController.getShared(context).scannedVerification(scannedEntity,scannedResult);
    }

    public void enrollv2(@NonNull final EnrollEntity enrollEntity, final Result<EnrollResponse> enrollResponseResult)
    {
        EnrollController.getShared(context).enrollVerification(enrollEntity,enrollResponseResult);
    }


    public void pushAcknowledgev2(PushAcknowledgeEntity pushAcknowledgeEntity, Result<PushAcknowledgeResponse> pushAcknowledgeResult)
    {
        PushAcknowledgeController.getShared(context).pushAcknowledgeVerification(pushAcknowledgeEntity,pushAcknowledgeResult);
    }

    public void pushAllowv2(PushAllowEntity pushAllowEntity, Result<PushAllowResponse> pushAllowResponseResult)
    {
        PushAllowController.getShared(context).pushAllowVerification(pushAllowEntity,pushAllowResponseResult);
    }

    public void pushRejectv2(PushRejectEntity pushRejectEntity, Result<PushRejectResponse> pushRejectResponseResult)
    {
        PushRejectController.getShared(context).pushRejectVerification(pushRejectEntity,pushRejectResponseResult);
    }

    public void authenticatev2(AuthenticateEntity authenticateEntity, Result<AuthenticateResponse> authenticateResponseResult)
    {
        AuthenticateController.getShared(context).authenticateVerification(authenticateEntity,authenticateResponseResult);
    }
*/




/*

    public void getAccessTokenBySocial(final SocialAccessTokenEntity socialAccessTokenEntity, final Result<AccessTokenEntity> accessTokenCallback,
                                       final HashMap<String,String>... extraParams)
    {
        try
        {
           // CidaasHelper.baseurl=socialAccessTokenEntity.getDomainURL();

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(final Dictionary<String, String> loginProperties)
                {

                    if(socialAccessTokenEntity.getRequestId().equalsIgnoreCase("")|| socialAccessTokenEntity.getRequestId()==null)
                    {

                        getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                            @Override
                            public void success(AuthRequestResponseEntity result) {
                                socialAccessTokenEntity.setRequestId(result.getData().getRequestId());
                                AccessTokenController.getShared(context).getAccessTokenBySocial(socialAccessTokenEntity, accessTokenCallback);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                accessTokenCallback.failure(error);
                            }
                        }, extraParams);

                    }
                    else
                        {
                            AccessTokenController.getShared(context).getAccessTokenBySocial(socialAccessTokenEntity, accessTokenCallback);
                        }
                }

                @Override
                public void failure(WebAuthError error) {
                    accessTokenCallback.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            accessTokenCallback.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getAccessTokenBySocial()",WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,"Access Token exception"+ e.getMessage()));
        }
    }

    public void loginWithSocial(@NonNull final Context activityContext,@NonNull final String provider, @Nullable final String color,
                                final Result<AccessTokenEntity> callbacktoMain, final HashMap<String, String>... extraParams)
    {

        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> loginProperties) {
                getRequestId(loginProperties,new Result<AuthRequestResponseEntity>() {
                    @Override
                    public void success(AuthRequestResponseEntity result) {
                        loginWithSocial(activityContext,result.getData().getRequestId(),provider,color,callbacktoMain);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        callbacktoMain.failure(error);
                    }
                },extraParams);
            }

            @Override
            public void failure(WebAuthError error) {
                callbacktoMain.failure(error);
            }
        });

    }

    public void getSocialLoginURL(final String provider, final Result<String> callback, final HashMap<String,String>... extraParams) {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties,new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            getSocialLoginURL(result.getData().getRequestId(),provider,callback);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            callback.failure(error);
                        }
                    },extraParams);
                }

                @Override
                public void failure(WebAuthError error) {

                }
            });

        }
        catch (Exception e)
        {

        }
    }*/

}




  /* // -----------------------------------------------------***** CONSENT MANAGEMENT *****---------------------------------------------------------------
    @Override
    public void getConsentDetails(@NonNull final String consentName, final Result<ConsentDetailsResultEntity> consentResult) {
        ConsentController.getShared(context).getConsentDetails(consentName, consentResult);
    }

    @Override
    public void loginAfterConsent(@NonNull final ConsentEntity consentEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        ConsentController.getShared(context).acceptConsent(consentEntity,loginresult);
    }



    public void getConsentDetailsV2(@NonNull final ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity, final Result<ConsentDetailsV2ResponseEntity> consentResult) {
      if(consentDetailsV2RequestEntity.getRequestId()!=null && !consentDetailsV2RequestEntity.getRequestId().equals("")) {

         getRequestId(new Result<AuthRequestResponseEntity>() {
             @Override
             public void success(AuthRequestResponseEntity result) {
                 consentDetailsV2RequestEntity.setRequestId(result.getData().getRequestId());
                 ConsentController.getShared(context).getConsentDetailsV2(consentDetailsV2RequestEntity, consentResult);
             }

             @Override
             public void failure(WebAuthError error) {
                    consentResult.failure(error);
             }
         });
      }
      else
      {
          ConsentController.getShared(context).getConsentDetailsV2(consentDetailsV2RequestEntity, consentResult);
      }
    }
*/



//-----------------------------------------END_OF_Common For Cidaas Instances-------------------------------------------------------------------------


    /*//Get the remote messages from the Push notification
    public static void validateDevice(Map<String, String> instanceIdFromPush) {
        try {
            if (instanceIdFromPush.get("usage_pass") != null && !instanceIdFromPush.get("usage_pass").equals("")) {
                usagePass = instanceIdFromPush.get("usage_pass");
            } else {
                usagePass = "";
            }
        } catch (Exception e) {
            String loggerMessage = "Set remote Message : " + " Error Message - " + e.getMessage();
            Timber.e(""+loggerMessage);
        }

    }*/
/*

    // Get the instance ID
    public String getInstanceId() {
        if (usagePass != null && !usagePass.equals("")) {
            return usagePass;
        } else {
            return null;
        }
    }

*/

// -----------------------------------------------------***** GET MFA LIST *****---------------------------------------------------------------
/*
    @Override
    public void getMFAList(final String sub, final Result<MFAListResponseEntity> mfaresult) {
        VerificationSettingsController.getShared(context).getmfaList(sub, mfaresult);
    }


    public void getMFAListByEmail(final String email, final Result<MFAListResponseEntity> mfaresult) {
        VerificationSettingsController.getShared(context).getmfaListByEmail(email, mfaresult);
    }
 */   // -----------------------------------------------------***** PASSWORD LESS LOGIN AND MFA SERVICE CALL *****---------------------------------------------------------------


// ****** DONE LOGIN WITH EMAIL AND VERIFY EMAIL *****----------------------------------------------------------------------

/*

    @Override
    public void configureEmail(final String sub, final Result<SetupEmailMFAResponseEntity> result) {
        EmailConfigurationController.getShared(context).configureEmail(sub, result);
    }

    @Override
    public void enrollEmail(final String code, final String statusId, final Result<EnrollEmailMFAResponseEntity> enrollresult) {
        EmailConfigurationController.getShared(context).enrollEmailMFA(code, statusId, enrollresult);
    }


    @Override
    public void loginWithEmail(final PasswordlessEntity passwordlessEntity, final Result<InitiateEmailMFAResponseEntity> initiateresult) {
        EmailConfigurationController.getShared(context).loginWithEmail(passwordlessEntity, initiateresult);
    }

    @Override
    public void verifyEmail(@NonNull final String code, @NonNull final String statusId, final Result<LoginCredentialsResponseEntity> loginresult) {
        EmailConfigurationController.getShared(context).verifyEmail(code, statusId, loginresult);
    }

    // ****** DONE LOGIN WITH SMS AND VERIFY SMS *****----------------------------------------------------------------------


    @Override
    public void configureSMS(final String sub, final Result<SetupSMSMFAResponseEntity> result) {
        SMSConfigurationController.getShared(context).configureSMS(sub, result);
    }


    public void enrollSMS(final String code, final String statusId, final Result<EnrollSMSMFAResponseEntity> result) {
        SMSConfigurationController.getShared(context).enrollSMSMFA(code, statusId, result);
    }

    public void loginWithSMS(final PasswordlessEntity passwordlessEntity, final Result<InitiateSMSMFAResponseEntity> initiateresult) {
        SMSConfigurationController.getShared(context).loginWithSMS(passwordlessEntity, initiateresult);
    }

    @Override
    public void verifySMS(final String code, final String statusId, final Result<LoginCredentialsResponseEntity> loginresult) {
        SMSConfigurationController.getShared(context).verifySMS(code,statusId, loginresult);
    }

    // ****** done LOGIN WITH IVR AND VERIFY IVR *****----------------------------------------------------------------------


    @Override
    public void configureIVR(final String sub, final Result<SetupIVRMFAResponseEntity> result) {
        IVRConfigurationController.getShared(context).configureIVR(sub, result);
    }

    public void enrollIVR(final String code, final String statusId, final Result<EnrollIVRMFAResponseEntity> result) {
        IVRConfigurationController.getShared(context).enrollIVRMFA(code, statusId,  result);
    }


    @Override
    public void loginWithIVR(final PasswordlessEntity passwordlessEntity, final Result<InitiateIVRMFAResponseEntity> initiateresult) {
        IVRConfigurationController.getShared(context).loginWithIVR(passwordlessEntity, initiateresult);

    }

    public void verifyIVR(final String code, final String statusId, final Result<LoginCredentialsResponseEntity> loginresult) {
        IVRConfigurationController.getShared(context).verifyIVR(code,statusId, loginresult);
    }

    // ****** DONE ENROLL AND LOGIN WITH Backupcode  *****----------------------------------------------------------------------

    @Override
    public void configureBackupcode(final String sub, final Result<SetupBackupCodeMFAResponseEntity> result) {
        BackupCodeConfigurationController.getShared(context).configureBackupCode(sub,result);
    }

    @Override
    public void loginWithBackupcode(final String code, final PasswordlessEntity passwordlessEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        BackupCodeConfigurationController.getShared(context).loginWithBackupCode(code, passwordlessEntity, loginresult);
    }


    // ****** DONE PATTERN *****-------------------------------------------------------------------------------------------------------

    //Configure Pattern
    @Override
    public void configurePatternRecognition(@NonNull final String pattern, @NonNull final String sub,@NonNull final String logoURL,
                                            final Result<EnrollPatternMFAResponseEntity> enrollresult)
    {
        PatternConfigurationController.getShared(context).configurePattern(pattern, sub,logoURL, enrollresult);
    }

    //Enroll pattern
    public void enrollPattern(@NonNull final String patternString, @NonNull final String sub,@NonNull final String statusId,  final Result<EnrollPatternMFAResponseEntity> enrollResult)
    {
        PatternConfigurationController.getShared(context).enrollPattern(patternString,sub,statusId,enrollResult);
    }

    //Scanned pattern
    public void scannedPattern(@NonNull final String statusId,final Result<ScannedResponseEntity> scannedResult)
    {
        PatternConfigurationController.getShared(context).scannedWithPattern(statusId,scannedResult);
    }

    //Login With Pattern
    @Override
    public void loginWithPatternRecognition(@NonNull final String pattern, @NonNull final PasswordlessEntity passwordlessEntity,
                                            final Result<LoginCredentialsResponseEntity> loginresult) {
        PatternConfigurationController.getShared(context).LoginWithPattern(pattern,passwordlessEntity,loginresult);
    }

    public void verifyPattern(final String patternString, final String statusId, final Result<AuthenticatePatternResponseEntity> result)
    {
        PatternConfigurationController.getShared(context).authenticatePattern(patternString,statusId,result);
    }

    // ****** TODO LOGIN WITH FACE *****-------------------------------------------------------------------------------------------------------


    @Override
    public void configureFaceRecognition(final File photo, final String sub,@NonNull final String logoURL,@NonNull final int attempt, final Result<EnrollFaceMFAResponseEntity> enrollresult)
    {
        FaceConfigurationController.getShared(context).configureFace(photo, sub,logoURL, attempt, enrollresult);
    }


    @Override
    public void loginWithFaceRecognition(@NonNull final File photo, @NonNull final PasswordlessEntity passwordlessEntity,
                                         final Result<LoginCredentialsResponseEntity> loginresult) {
        FaceConfigurationController.getShared(context).LoginWithFace(photo, passwordlessEntity, loginresult);
    }

    public void scannedFace(@NonNull final String statusId, final Result<ScannedResponseEntity> scannedResult)
    {
        FaceConfigurationController.getShared(context).scannedWithFace(statusId,scannedResult);
    }

    public void enrollFace(@NonNull final File Face, @NonNull final String sub,@NonNull final String statusId,@NonNull final int attempt,  final Result<EnrollFaceMFAResponseEntity> enrollResult)
    {

        FaceConfigurationController.getShared(context).enrollFace(Face,sub,statusId,attempt,enrollResult);
    }


    public void verifyFace(@NonNull final File photo,@NonNull final String statusId, final Result<AuthenticateFaceResponseEntity> result) {
        FaceConfigurationController.getShared(context).authenticateFace(photo,statusId,result);
    }

    // ****** TODO LOGIN WITH Finger *****-------------------------------------------------------------------------------------------------------


    @Override
    public void configureFingerprint(final Context context, final String sub, @NonNull final String logoURL, FingerPrintEntity fingerPrintEntity, final Result<EnrollFingerprintMFAResponseEntity> enrollresult) {
        FingerprintConfigurationController.getShared(context).configureFingerprint(context,sub, logoURL, fingerPrintEntity,enrollresult);
    }

    public void scannedFingerprint(@NonNull final String statusId, final Result<ScannedResponseEntity> scannedResult)
    {
        FingerprintConfigurationController.getShared(context).scannedWithFingerprint( statusId,  scannedResult);
    }

    public void enrollFingerprint(final Context context, @NonNull final String sub, @NonNull final String statusId, FingerPrintEntity fingerPrintEntity, final Result<EnrollFingerprintMFAResponseEntity> enrollResult)
    {
        FingerprintConfigurationController.getShared(context).enrollFingerprint(context,sub,statusId,fingerPrintEntity,enrollResult);
    }

    @Override
    public void loginWithFingerprint(final Context context, final PasswordlessEntity passwordlessEntity, FingerPrintEntity fingerPrintEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        FingerprintConfigurationController.getShared(context).LoginWithFingerprint(context,passwordlessEntity,fingerPrintEntity,loginresult);
    }

    public void verifyFingerprint(final Context context, final String statusId, FingerPrintEntity fingerPrintEntity, final Result<AuthenticateFingerprintResponseEntity> callBackresult) {
        FingerprintConfigurationController.getShared(context).authenticateFingerprint(context,statusId,fingerPrintEntity,callBackresult);
    }

    // ****** DONE FIDO *****-------------------------------------------------------------------------------------------------------


    public void enrollFIDO(@NonNull final FIDOTouchResponse fidoResponse,  @NonNull final String sub, @NonNull final String statusId, final Result<EnrollFIDOMFAResponseEntity> enrollResult)
    {
        FIDOConfigurationController.getShared(context).enrollFIDO(fidoResponse,sub,statusId,enrollResult);
    }
    public void configureFIDO(@NonNull final IsoDep isoTag, @NonNull final String sub, @NonNull final String logoURL,
                                         final Result<EnrollFIDOMFAResponseEntity> enrollresult)
    {
        FIDOConfigurationController.getShared(context).configureFIDO(isoTag,sub, logoURL,enrollresult);
    }

    public void scannedFIDO(@NonNull final String statusId, final Result<ScannedResponseEntity> scannedResult)
    {
        FIDOConfigurationController.getShared(context).scannedWithFIDO(statusId,scannedResult);
    }

    public void loginWithFIDO(@NonNull final IsoDep isoTag, @NonNull final PasswordlessEntity passwordlessEntity,
                                            final Result<LoginCredentialsResponseEntity> loginresult) {
        FIDOConfigurationController.getShared(context).LoginWithFIDO(isoTag, passwordlessEntity, loginresult);
    }

    public void verifyFIDO(final FidoSignTouchResponse fidoSignTouchResponse, final String statusId, final Result<AuthenticateFIDOResponseEntity> result)
    {
        FIDOConfigurationController.getShared(context).authenticateFIDO(fidoSignTouchResponse,statusId,result);
    }

    // ****** TODO LOGIN WITH Smart push *****-------------------------------------------------------------------------------------------------------

    @Override
    public void configureSmartPush(final String sub,@NonNull final String logoURL, final Result<EnrollSmartPushMFAResponseEntity> enrollresult)
    {
        SmartPushConfigurationController.getShared(context).configureSmartPush(sub, logoURL,enrollresult);
    }

    public void scannedSmartPush(@NonNull final String statusId,final Result<ScannedResponseEntity> scannedResult)
    {
        SmartPushConfigurationController.getShared(context).scannedWithSmartPush(statusId,scannedResult);
    }

    public void enrollSmartPush(@NonNull final String randomNumber, @NonNull final String sub,@NonNull final String statusId,  final Result<EnrollSmartPushMFAResponseEntity> enrollResult)
    {
        SmartPushConfigurationController.getShared(context).enrollSmartPush(randomNumber,sub,statusId,enrollResult);
    }

    @Override
    public void loginWithSmartPush(final PasswordlessEntity passwordlessEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        SmartPushConfigurationController.getShared(context).LoginWithSmartPush(passwordlessEntity, loginresult);
    }

    public void verifySmartPush(final String randomNumber, final String statusId, final Result<AuthenticateSmartPushResponseEntity> result) {
        SmartPushConfigurationController.getShared(context).authenticateSmartPush(randomNumber,statusId,result);
    }

    // ****** TODO LOGIN WITH TOTP *****-------------------------------------------------------------------------------------------------------


    @Override
    public void configureTOTP(final String sub,@NonNull final String logoURL, final Result<EnrollTOTPMFAResponseEntity> enrollresult) {
        TOTPConfigurationController.getShared(context).configureTOTP(sub, logoURL,  enrollresult);
    }

    @Override
    public void loginWithTOTP(final PasswordlessEntity passwordlessEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        TOTPConfigurationController.getShared(context).LoginWithTOTP(passwordlessEntity, loginresult);
    }

    public void scannedTOTP(@NonNull final String statusId, @NonNull final String sub, @NonNull final String secret, final Result<ScannedResponseEntity> scannedResult)
    {
        TOTPConfigurationController.getShared(context).scannedWithTOTP(statusId,sub,secret,scannedResult);
    }

    public void listenTOTP(String sub) {
        TOTPConfigurationController.getShared(context).ListenTOTP(sub);
    }

    public void cancelListenTOTP() {
        TOTPConfigurationController.getShared(context).cancelTOTP();
    }


    // ******  LOGIN WITH voice *****-------------------------------------------------------------------------------------------------------


    @Override
    public void configureVoiceRecognition(final File voice, @NonNull final String logoURL,final String sub, final Result<EnrollVoiceMFAResponseEntity> enrollresult) {
        VoiceConfigurationController.getShared(context).configureVoice(voice,logoURL, sub, enrollresult);
    }

    public void scannedVoice(@NonNull final String statusId, final Result<ScannedResponseEntity> scannedResult)
    {
        VoiceConfigurationController.getShared(context).scannedWithVoice(statusId,scannedResult);
    }

    public void enrollVoice(@NonNull final File voice, @NonNull final String sub,@NonNull final String statusId,  final Result<EnrollVoiceMFAResponseEntity> enrollResult)
    {
        VoiceConfigurationController.getShared(context).enrollVoice(voice,sub,statusId,enrollResult);
    }


    @Override
    public void loginWithVoiceRecognition(final File voice, final PasswordlessEntity passwordlessEntity,
                                          final Result<LoginCredentialsResponseEntity> loginresult) {
        VoiceConfigurationController.getShared(context).LoginWithVoice(voice, passwordlessEntity, loginresult);
    }

    public void verifyVoice(final File voice, final String statusId, final Result<AuthenticateVoiceResponseEntity> result) {
        VoiceConfigurationController.getShared(context).authenticateVoice(voice,statusId,result);
    }
*/

  /*  //---------------------------------------DELETE CALL-------------------------------------------------------------------------------------------------
    //Delete call
    public void deleteVerificationByType(@NonNull final String verificationType, @NonNull final String sub, final Result<DeleteMFAResponseEntity> deleteResult)
    {
        VerificationSettingsController.getShared(context).deleteMFA(verificationType,sub,deleteResult);
    }

    //Delete call
    public void deleteVerificationByDevice( @NonNull final String sub,final Result<DeleteMFAResponseEntity> result)
    {
        VerificationSettingsController.getShared(context).deleteAllMFA(sub,result);
    }

    //Deny Call
    public void denyNotification(@NonNull final String sub, @NonNull final String reason, @NonNull final String statusId, final Result<DenyNotificationResponseEntity> result)
    {
        VerificationSettingsController.getShared(context).denyNotification(sub,reason,statusId, result);
    }

    //Get Pending Notification
    public void getPendingNotificationList(@NonNull final String sub,  final Result<NotificationEntity> result)
    {
        VerificationSettingsController.getShared(context).getPendingNotification(sub, result);
    }

    //Get user List Notification
    public void getConfigurationList(@NonNull final String sub,  final Result<ConfiguredMFAListEntity> result,final String... baseURL)
    {
        VerificationSettingsController.getShared(context).getConfiguredMFAList(sub,result,baseURL);
    }*/