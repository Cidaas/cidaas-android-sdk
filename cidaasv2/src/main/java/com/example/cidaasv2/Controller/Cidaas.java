package com.example.cidaasv2.Controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.ChangePassword.ChangePasswordController;
import com.example.cidaasv2.Controller.Repository.Client.ClientController;
import com.example.cidaasv2.Controller.Repository.Configuration.BackupCode.BackupCodeConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.Email.EmailConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.Face.FaceConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.Fingerprint.FingerprintConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.IVR.IVRConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.Pattern.PatternConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.SMS.SMSConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.SmartPush.SmartPushConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.Voice.VoiceConfigurationController;
import com.example.cidaasv2.Controller.Repository.Consent.ConsentController;
import com.example.cidaasv2.Controller.Repository.Deduplication.DeduplicationController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Controller.Repository.MFASettings.MFAListSettingsController;
import com.example.cidaasv2.Controller.Repository.Registration.RegistrationController;
import com.example.cidaasv2.Controller.Repository.RequestId.RequestIdController;
import com.example.cidaasv2.Controller.Repository.ResetPassword.ResetPasswordController;
import com.example.cidaasv2.Controller.Repository.Tenant.TenantController;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Entity.RegistrationEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.FileHelper;
import com.example.cidaasv2.Helper.Loaders.ICustomLoader;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Interface.IOAuthWebLogin;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptedRequestEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationList;
import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.LoginDeduplication.LoginDeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.EnrollFIDOMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupRequestEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import com.example.cidaasv2.Service.Repository.Deduplication.DeduplicationService;
import com.example.cidaasv2.Service.Repository.OauthService;
import com.example.cidaasv2.Service.Repository.Registration.RegistrationService;
import com.example.cidaasv2.Service.Repository.Verification.BackupCode.BackupCodeVerificationService;

import java.io.File;
import java.security.spec.ECField;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import timber.log.Timber;

import static android.os.Build.MODEL;
import static android.os.Build.VERSION;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class Cidaas implements IOAuthWebLogin{

    public Context context;

    public static AssetManager assetManager;
    public static String fileName1;
    public  static String instanceId="";
    public static ICustomLoader loader;

    public WebAuthError webAuthError=null;


    public String loginURL;
    public String redirectUrl;
    public Result<AccessTokenEntity> logincallback;


//saved Properties for Global Access
    Dictionary<String, String> savedProperties;

//To check the Loader
    private boolean displayLoader = false;

    //Todo Confirm it must be a static one
    //Extra parameter that is passed in URL
    public static HashMap<String,String> extraParams=new HashMap<>();

//Create a Shared Instance

   private static Cidaas cidaasInstance;


    public static Cidaas getInstance(Context context)
    {
        if(cidaasInstance==null)
        {
            cidaasInstance=new Cidaas(context);
        }

        return cidaasInstance;
    }


    public boolean isENABLE_PKCE() {
       ENABLE_PKCE=DBHelper.getShared().getEnablePKCE();
        return ENABLE_PKCE;
    }

    public void setENABLE_PKCE(boolean ENABLE_PKCE) {
        this.ENABLE_PKCE = ENABLE_PKCE;
        DBHelper.getShared().setEnablePKCE(ENABLE_PKCE);
    }

    public boolean ENABLE_PKCE;
    public boolean ENABLE_LOG;
    public DeviceInfoEntity deviceInfoEntity;


    public Cidaas(Context yourContext) {
        this.context=yourContext;
        //Initialise Shared Preferences
        DBHelper.setConfig(context);
        //Default Value;
        ENABLE_PKCE=true;

        //Default Log Value
        ENABLE_LOG=false;

        //Set Callback Null;
        logincallback=null;


        //generateChallenge();

        //String token = FirebaseInstanceId.getInstance().getToken();

        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {

            } else {
              //  ActivityCompat.requestPermissions(yourContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 209);

            }
        } else { //permission is automatically granted on sdk<23 upon installation

        }


        //Add Device info
        deviceInfoEntity=new DeviceInfoEntity();
        deviceInfoEntity.setDeviceId(Settings.Secure.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID));
        deviceInfoEntity.setDeviceModel(MODEL);
        deviceInfoEntity.setDeviceVersion(String.valueOf(VERSION.RELEASE));
        deviceInfoEntity.setDeviceMake(Build.MANUFACTURER);

        //Store Device info for Later Purposes
        DBHelper.getShared().addDeviceInfo(deviceInfoEntity);

    }



//Get Request Id By passing loginProperties as an Object
    // 1. Read properties from file
    // 2. Call request id from dictionary method
    // 3. Maintain logs based on flags


    public void setFCMToken(String FCMToken){
        //Store Device info for Later Purposes
        DBHelper.getShared().setFCMToken(FCMToken);

    }


    public static void setremoteMessage(Map<String, String> instanceIdFromPush)
    {
        if(instanceIdFromPush.get("intermediate_verifiation_id")!=null && instanceIdFromPush.get("intermediate_verifiation_id")!="") {
            instanceId=instanceIdFromPush.get("intermediate_verifiation_id");
        }

    }


    public String getInstanceId(){
        return instanceId;

    }

    // -----------------------------------------------------***** REQUEST ID *****-------------------------------------------------------------------------

    //Get Request Id By Passing loginProperties as Value in parameters
    @Override
    public void getRequestId(@NonNull String DomainUrl, @NonNull String ClientId,@NonNull String RedirectURL,
                             @Nullable String ClientSecret,final Result<AuthRequestResponseEntity> result)
    {
        //WebError Code instance Creation
        webAuthError=WebAuthError.getShared(context);
        try {
            if (ClientId != null && !ClientId.equals("") && DomainUrl != null && !DomainUrl.equals("")
                    && RedirectURL != null && !RedirectURL.equals("") && ClientSecret != null && !ClientSecret.equals("")) {

                FileHelper.getShared(context).paramsToDictionaryConverter(DomainUrl, ClientId, RedirectURL,
                        ClientSecret, new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginproperties) {

                        //on successfully completion of file reading add it to LocalDB(shared Preference) and call requestIdByloginProperties

                        checkPKCEFlow(loginproperties, new Result<Dictionary<String, String>>() {
                            @Override
                            public void success(Dictionary<String, String> savedLoginProperties) {
                                //Call requestIdBy LoginProperties parameter

                                RequestIdController.getShared(context).getRequestId(savedLoginProperties, result);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                              result.failure(error);
                                String loggerMessage = "Request-Id params to dictionary conversion failure : " + "Error Code - "
                                        + error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " + error.statusCode;
                                LogFile.addRecordToLog(loggerMessage);
                            }
                        });

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        String loggerMessage = "Request-Id params to dictionary conversion failure : " + "Error Code - "
                                + error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " + error.statusCode;
                        LogFile.addRecordToLog(loggerMessage);
                        result.failure(error);
                    }
                });

            }
            else
            {
                result.failure(webAuthError.propertyMissingException());
            }
         }
     catch (Exception e)
     {
         String loggerMessage = "Request-Id service Exception :  Error Message - " + e.getMessage();
         LogFile.addRecordToLog(loggerMessage);
         Timber.d(e.getMessage());
     }
    }

    //Get Request Id without passing any value
    @Override
    public void getRequestId(final Result<AuthRequestResponseEntity> resulttoReturn)
    {
        try {

            //Todo Check in saved file
             final Dictionary<String,String> loginProperties= DBHelper.getShared().getLoginProperties();
             if(loginProperties!=null && !loginProperties.isEmpty() && loginProperties.size()>0 ){
                 //check here for already saved properties
                 checkPKCEFlow(loginProperties, new Result<Dictionary<String, String>>() {
                     @Override
                     public void success(Dictionary<String, String> result) {
                         RequestIdController.getShared(context). getRequestId(loginProperties, resulttoReturn);
                     }

                     @Override
                     public void failure(WebAuthError error) {
                         resulttoReturn.failure(error);

                     }
                 });


             }
             else {
                 //Read File from asset to get URL
               readFromFile(new Result<Dictionary<String, String>>() {
                   @Override
                   public void success(Dictionary<String, String> savedLoginProperties) {
                       //Call requestIdBy LoginProperties parameter
                       RequestIdController.getShared(context).getRequestId(savedLoginProperties, resulttoReturn);
                   }

                   @Override
                   public void failure(WebAuthError error) {
                      resulttoReturn.failure(error);
                   }
               });
             }
        }
        catch (Exception e)
        {
            //Todo Handle Exception
            Timber.d(e.getMessage());
        }
    }

    // -----------------------------------------------------***** TENANT INFO *****-------------------------------------------------------------------------
    @Override
    public void getTenantInfo(final Result<TenantInfoEntity> tenantresult) {
        try{
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> stringresult) {
                    String baseurl = stringresult.get("DomainURL");
                    String clientId=stringresult.get("ClientId");
                    TenantController.getShared(context).getTenantInfo(baseurl,tenantresult);
                }

                @Override
                public void failure(WebAuthError error) {
                    tenantresult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            tenantresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    // -----------------------------------------------------***** CLIENT INFO *****-------------------------------------------------------------------------


    @Override
    public void getClientInfo(final String RequestId, final Result<ClientInfoEntity> clientInfoEntityResult)
    {
        try{
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    //Todo Check notnull Re   questId
                    String baseurl = result.get("DomainURL");

                    if(RequestId!=null && RequestId!="") {
                        ClientController.getShared(context).getClientInfo(baseurl, RequestId, clientInfoEntityResult);
                    }
                    else
                    {
                        String errorMessage="RequestId must not be empty";

                        clientInfoEntityResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));

                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    clientInfoEntityResult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            String errorMessage="ClientInfo Exception"+e.getMessage();

            clientInfoEntityResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
        }
    }

    // -----------------------------------------------------***** LOGIN WITH CREDENTIALS *****---------------------------------------------------------------


    @Override
    public void loginWithCredentials(final String requestId, final LoginEntity loginEntity,
                                     final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId = savedProperties.get("ClientId");

                    if(loginEntity.getUsername_type()==null && loginEntity.getUsername_type()=="")
                    {
                        loginEntity.setUsername_type("email");
                    }

                    if ( loginEntity.getPassword() != null && loginEntity.getPassword() != "" &&
                            loginEntity.getUsername() != null && loginEntity.getUsername() != "")
                    {
                        LoginCredentialsRequestEntity loginCredentialsRequestEntity=new LoginCredentialsRequestEntity();
                        loginCredentialsRequestEntity.setUsername(loginEntity.getUsername());
                        loginCredentialsRequestEntity.setUsername_type(loginEntity.getUsername_type());
                        loginCredentialsRequestEntity.setPassword(loginEntity.getPassword());
                        loginCredentialsRequestEntity.setRequestId(requestId);
                        LoginController.getShared(context).loginwithCredentials(baseurl,loginCredentialsRequestEntity,loginresult);
                    }
                    else {

                        String errorMessage="Username or password must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));

                    }

                }

                @Override
                public void failure(WebAuthError error) {

                    loginresult.failure(error);
                }
            });
        }
        catch (Exception e){

            String errorMessage="Login with Credentials Exception"+e.getMessage();

            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
        }
    }

    // -----------------------------------------------------***** CONSENT MANAGEMENT *****---------------------------------------------------------------

    public void getConsentDetails(@NonNull final String Name, @NonNull final String Version, @NonNull final String trackId,
                                  final Result<ConsentDetailsResultEntity> consentResult){

        try {
            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId = savedProperties.get("ClientId");

                    if (Name != null && !Name.equals("") && Version != null && !Version.equals("") &&
                            trackId != null && !trackId.equals("")  && baseurl != null && !baseurl.equals("") && clientId != null && !clientId.equals("")) {


                        ConsentController.getShared(context).getConsentDetails(baseurl,Name,Version,trackId,consentResult);
                    }
                    else
                    {

                        String errorMessage="ConsentName or consentVersion or trackid must not be empty";

                        consentResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }

                    //Todo put not null
                }

                @Override
                public void failure(WebAuthError error) {
                    consentResult.failure(error);
                }
            });
        }
        catch (Exception e){
            String errorMessage="Get Consent Details Exception"+e.getMessage();

            consentResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
        }

    }


    public void loginAfterConsent(@NonNull final String sub,@NonNull final boolean accepted,
                                  final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result)
                {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId = savedProperties.get("ClientId");


                    if (  sub != null && !sub.equals("")  && accepted != false ) {

                        ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity=new ConsentManagementAcceptedRequestEntity();
                        consentManagementAcceptedRequestEntity.setAccepted(accepted);
                        consentManagementAcceptedRequestEntity.setSub(sub);
                        consentManagementAcceptedRequestEntity.setClient_id(clientId);


                        ConsentController.getShared(context).acceptConsent(baseurl,consentManagementAcceptedRequestEntity,loginresult);
                    }
                    else{
                        String errorMessage="Sub must not be null or Accepted must not be false";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(error);
                }
            });
        }
        catch (Exception e){
            String errorMessage="Login after Consent Exception"+e.getMessage();

            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
        }
    }

    // -----------------------------------------------------***** GET MFA LIST *****---------------------------------------------------------------


    public void getmfaList(final String sub, final Result<MFAListResponseEntity> mfaresult)
    {
        try{
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    if(sub!=null && sub!="") {
                        MFAListSettingsController.getShared(context).getmfaList(baseurl, sub,mfaresult);
                    }
                    else
                    {
                        String errorMessage="Sub must not be empty";

                        mfaresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    mfaresult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            String errorMessage="Getting MFA List Exception"+e.getMessage();

            mfaresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
        }
    }

    // -----------------------------------------------------***** PASSWORD LESS LOGIN AND MFA SERVICE CALL *****---------------------------------------------------------------


    // ****** DONE LOGIN WITH EMAIL AND VERIFY EMAIL *****----------------------------------------------------------------------


    @Override
    public void configureEmail(final String sub, final Result<SetupEmailMFAResponseEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    ///Todo Call configure email

                    EmailConfigurationController.getShared(context).configureEmail(sub,baseurl,result);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            String errorMessage="Configure Email Exception"+e.getMessage();

            result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
        }

    }


    public void enrollEmail(final String code, final Result<EnrollEmailMFAResponseEntity> enrollresult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    //todo call enroll Email
                   if(code!=null && code !="")
                   {
                       EmailConfigurationController.getShared(context).enrollEmailMFA(code,baseurl,enrollresult);
                   }
                   else
                   {

                   }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }



    public void loginWithEmail(final String email, final String mobile, final String sub,@NonNull final String usageType,@NonNull final String trackId,
                               @NonNull final String requestId,final Result<InitiateEmailMFAResponseEntity> initiateresult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    if(sub!=null && !sub.equals("") && (usageType!=null && !usageType.equals(""))) {

                        if(usageType.equals(UsageType.MFA))
                        {
                            if(trackId==null || trackId=="" )
                            {
                                String errorMessage="trackId must not be empty";

                                initiateresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                            }
                        }

                        InitiateEmailMFARequestEntity initiateEmailMFARequestEntity=new InitiateEmailMFARequestEntity();
                        initiateEmailMFARequestEntity.setSub(sub);
                        initiateEmailMFARequestEntity.setUsageType(usageType);
                        initiateEmailMFARequestEntity.setVerificationType("email");


                        EmailConfigurationController.getShared(context).loginWithEmail(baseurl,trackId,requestId,initiateEmailMFARequestEntity,initiateresult);
                    }
                    else
                    {

                        initiateresult.failure(WebAuthError.getShared(context).propertyMissingException());
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    initiateresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            initiateresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }

    @Override
    public void verifyEmail(@NonNull final String code, final Result<LoginCredentialsResponseEntity> loginresult) {
        try
        {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    //Todo call initiate

                    if(code!=null && code!="") {

                        AuthenticateEmailRequestEntity authenticateEmailRequestEntity = new AuthenticateEmailRequestEntity();
                        authenticateEmailRequestEntity.setCode(code);

                        EmailConfigurationController.getShared(context).verifyEmail(baseurl,code,clientId,authenticateEmailRequestEntity,loginresult);
/*
                          String userDeviceId = DBHelper.getShared().getUserDeviceId(baseurl);

                          if (userDeviceId != null && !userDeviceId.equals("")) {
                              authenticatePatternRequestEntity.setUserDeviceId(userDeviceId);
                          } else {
                              loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                          }*/
                    }
                    else {
                        loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                    }


                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(error);
                }
            });

        }
        catch (Exception e){
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    // ****** DONE LOGIN WITH SMS AND VERIFY SMS *****----------------------------------------------------------------------


    @Override
    public void configureSMS(final String sub,final Result<SetupSMSMFAResponseEntity> result) {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                   SMSConfigurationController.getShared(context).configureSMS(sub,baseurl,result);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }


    public void enrollSMS(final String code, final Result<EnrollSMSMFAResponseEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    if(code!=null && code !="")
                    {
                        SMSConfigurationController.getShared(context).enrollSMSMFA(code,baseurl,result);
                    }
                    else
                    {

                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }

    public void loginWithSMS( final String email, final String mobile, final String sub,@NonNull final String usageType,final String trackId,
                             @NonNull final String requestId,final Result<InitiateSMSMFAResponseEntity> initiateresult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    if(sub!=null && !sub.equals("") && (usageType!=null && !usageType.equals(""))) {


                        if(usageType.equals(UsageType.MFA))
                        {
                            if(trackId==null || trackId=="" )
                            {
                                String errorMessage="trackId must not be empty";

                                initiateresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                            }
                        }

                        InitiateSMSMFARequestEntity initiateSMSMFARequestEntity=new InitiateSMSMFARequestEntity();
                        initiateSMSMFARequestEntity.setSub(sub);
                        initiateSMSMFARequestEntity.setUsageType(usageType);
                        initiateSMSMFARequestEntity.setVerificationType("SMS");

                     /*   String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);
                        if(userDeviceId!=null && !userDeviceId.equals("") ) {
                            initiateSMSMFARequestEntity.setUserDeviceId(userDeviceId);
                        }
                        else
                        {
                            String errorMessage="UserDeviceId must not be empty";

                            initiateresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }
                        */
                        SMSConfigurationController.getShared(context).loginWithSMS(baseurl,trackId,clientId,requestId,initiateSMSMFARequestEntity,initiateresult);
                    }
                    else
                    {

                        initiateresult.failure(WebAuthError.getShared(context).propertyMissingException());
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    initiateresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            initiateresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }

    @Override
    public void verifySMS(final String code, final Result<LoginCredentialsResponseEntity> loginresult) {
        try {

            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    //Todo call initiate

                    if(code!=null && code!="") {

                        AuthenticateSMSRequestEntity authenticateSMSRequestEntity = new AuthenticateSMSRequestEntity();
                        authenticateSMSRequestEntity.setCode(code);

                        SMSConfigurationController.getShared(context).verifySMS(baseurl,clientId,authenticateSMSRequestEntity,loginresult);
/*
                          String userDeviceId = DBHelper.getShared().getUserDeviceId(baseurl);

                          if (userDeviceId != null && !userDeviceId.equals("")) {
                              authenticatePatternRequestEntity.setUserDeviceId(userDeviceId);
                          } else {
                              loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                          }*/
                    }
                    else {
                        String errorMessage="code must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));

                    }


                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(error);
                }
            });
        }
        catch (Exception e){
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    // ****** done LOGIN WITH IVR AND VERIFY IVR *****----------------------------------------------------------------------

    //done CHANGE TO LOGIN AND VERIFY

    @Override
    public void configureIVR(final String sub,final Result<SetupIVRMFAResponseEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    //todo call enroll Email

                    IVRConfigurationController.getShared(context).configureIVR(sub,baseurl,result);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }

    public void enrollIVR(final String code, final Result<EnrollIVRMFAResponseEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    if(code!=null && code !="")
                    {
                      IVRConfigurationController.getShared(context).enrollIVRMFA(code,baseurl,result);
                    }
                    else
                    {

                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }


    @Override
    public void loginWithIVR(final String email, final String mobile, final String sub,@NonNull final String usageType,@NonNull final String trackId,
                             @NonNull final String requestId,final Result<InitiateIVRMFAResponseEntity> initiateresult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    if(sub!=null && !sub.equals("") && (usageType!=null && !usageType.equals(""))) {


                        if(usageType.equals(UsageType.MFA))
                        {
                            if(trackId==null || trackId=="" )
                            {
                                String errorMessage="trackId must not be empty";

                                initiateresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                            }
                        }

                        InitiateIVRMFARequestEntity initiateIVRMFARequestEntity=new InitiateIVRMFARequestEntity();
                        initiateIVRMFARequestEntity.setSub(sub);
                        initiateIVRMFARequestEntity.setUsageType(usageType);
                        initiateIVRMFARequestEntity.setVerificationType("IVR");

                     /*   String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);
                        if(userDeviceId!=null && !userDeviceId.equals("") ) {
                            initiateIVRMFARequestEntity.setUserDeviceId(userDeviceId);
                        }
                        else
                        {
                            String errorMessage="UserDeviceId must not be empty";

                            initiateresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }
                        */
                        IVRConfigurationController.getShared(context).loginWithIVR(baseurl,trackId,clientId,requestId,initiateIVRMFARequestEntity,initiateresult);
                    }
                    else
                    {

                        initiateresult.failure(WebAuthError.getShared(context).propertyMissingException());
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    initiateresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            initiateresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }




    public void verifyIVR(final String code, final Result<LoginCredentialsResponseEntity> loginresult) {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    if(code!=null && code!="") {

                        AuthenticateIVRRequestEntity authenticateIVRRequestEntity = new AuthenticateIVRRequestEntity();
                        authenticateIVRRequestEntity.setCode(code);

                        IVRConfigurationController.getShared(context).verifyIVR(baseurl,clientId,authenticateIVRRequestEntity,loginresult);
/*
                          String userDeviceId = DBHelper.getShared().getUserDeviceId(baseurl);

                          if (userDeviceId != null && !userDeviceId.equals("")) {
                              authenticatePatternRequestEntity.setUserDeviceId(userDeviceId);
                          } else {
                              loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                          }*/
                    }
                    else {
                        String errorMessage="code must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));

                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    // ****** todO LOGIN WITH BACKUPCODE AND VERIFY BACKUPCODE *****----------------------------------------------------------------------

    //tODO CHANGE TO LOGIN AND VERIFY

    @Override
    public void configureBackupCode(final String sub,final Result<SetupBackupCodeMFAResponseEntity> result) {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    //todo call enroll Email
                    BackupCodeConfigurationController.getShared(context).configureBackupCode(sub,baseurl,result);

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }

    @Override
    public void loginWithBackupCode(final String code,final String email, final String mobile, final String sub,@NonNull final String usageType,
                                    @NonNull final String trackId, @NonNull final String requestId,
                                    final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    if(sub!=null && !sub.equals("") && (usageType!=null && !usageType.equals(""))) {


                        if(usageType.equals(UsageType.MFA))
                        {
                            if(trackId==null || trackId=="" )
                            {
                                String errorMessage="trackId must not be empty";

                                loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                            }
                        }

                        InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity=new InitiateBackupCodeMFARequestEntity();
                        initiateBackupCodeMFARequestEntity.setSub(sub);
                        initiateBackupCodeMFARequestEntity.setUsageType(usageType);
                        initiateBackupCodeMFARequestEntity.setVerificationType("BACKUPCODE");

                     /*   String userDeviceId=DBHelper.getShared().getUserDeviceId(baseurl);
                        if(userDeviceId!=null && !userDeviceId.equals("") ) {
                            initiateBackupCodeMFARequestEntity.setUserDeviceId(userDeviceId);
                        }
                        else
                        {
                            String errorMessage="UserDeviceId must not be empty";

                            initiateresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }
                        */
                        BackupCodeConfigurationController.getShared(context).loginWithBackupCode(code,baseurl,trackId,clientId,requestId,initiateBackupCodeMFARequestEntity,loginresult);
                    }
                    else
                    {

                        loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }

    @Override
    public void verifyBackupCode(final String code, final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    if(code!=null && code!="") {

                        AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity = new AuthenticateBackupCodeRequestEntity();
                        authenticateBackupCodeRequestEntity.setVerifierPassword(code);

                        BackupCodeConfigurationController.getShared(context).verifyBackupCode(baseurl,clientId,authenticateBackupCodeRequestEntity,loginresult);
/*
                          String userDeviceId = DBHelper.getShared().getUserDeviceId(baseurl);

                          if (userDeviceId != null && !userDeviceId.equals("")) {
                              authenticatePatternRequestEntity.setUserDeviceId(userDeviceId);
                          } else {
                              loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                          }*/
                    }
                    else {
                        String errorMessage="code must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));

                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    // ****** DONE PATTERN *****-------------------------------------------------------------------------------------------------------


    @Override
    public void configurePatternRecognition(@NonNull final String patternString, @NonNull final String sub, final Result<EnrollPatternMFAResponseEntity> enrollresult)
    {
        try {


            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl = result.get("DomainURL");

                    if (sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("") && patternString != null && !patternString.equals("")) {

                        final String finalBaseurl = baseurl;

                        String logoUrl = "https://docs.cidaas.de/assets/logoss.png";
                        SetupPatternMFARequestEntity setupPatternMFARequestEntity = new SetupPatternMFARequestEntity();
                        setupPatternMFARequestEntity.setClient_id(result.get("ClientId"));
                        setupPatternMFARequestEntity.setLogoUrl(logoUrl);
                        PatternConfigurationController.getShared(context).configurePattern(sub,finalBaseurl,patternString, setupPatternMFARequestEntity,
                                enrollresult);

                    }
                    else
                    {
                        String errorMessage="Sub or Pattern cannot be null";
                        enrollresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,errorMessage,
                                HttpStatusCode.EXPECTATION_FAILED));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }
            });

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("Configure Pattern exception"+e.getMessage());

            Timber.e("Configure Pattern exception"+e.getMessage());
        }


    }


    //Todo login with pattern by Passing the pattern String Directly
    // 1. Todo Check For Local Variable or Read properties from file
    // 2. Todo Check For NotNull Values
    // 3. Todo getAccessToken From Sub
    // 3. Todo Call configure Pattern From Pattern Controller and return the result
    // 4. Todo Maintain logs based on flags
    @Override
    public void loginWithPatternRecognition(@NonNull final String patternCode, final String email, final String mobile, final String sub,
                                 final String requestId, final String trackId, @NonNull final String usageType, final Result<LoginCredentialsResponseEntity> loginresult)
    {

        try {


            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    if ( usageType != null && usageType != "" &&  patternCode != null && !patternCode.equals("")  && requestId != null && requestId!="") {

                        if(baseurl == null || baseurl.equals("") &&  clientId == null || clientId!=""){
                            String errorMessage="baseurl or clientId or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if((sub == null || sub.equals("") && email==null || email.equals("") && mobile==null || mobile.equals("")))
                        {
                            String errorMessage="sub or email or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if(usageType.equals(UsageType.MFA))
                        {
                            if(trackId==null || trackId=="" )
                            {
                                String errorMessage="trackId must not be empty";

                                loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                            }
                        }

                        InitiatePatternMFARequestEntity initiatePatternMFARequestEntity=new InitiatePatternMFARequestEntity();
                        initiatePatternMFARequestEntity.setSub(sub);
                        initiatePatternMFARequestEntity.setUsageType(usageType);
                        initiatePatternMFARequestEntity.setEmail(email);
                        initiatePatternMFARequestEntity.setMobile(mobile);

                        //Todo check for email or sub or mobile


                        PatternConfigurationController.getShared(context).LoginWithPattern(patternCode,baseurl,clientId,trackId,requestId,
                                initiatePatternMFARequestEntity,loginresult);
                    }
                    else
                    {
                        String errorMessage="UsageType or PatternCode or requestId must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
loginresult.failure(error);
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("Login with Pattern exception"+e.getMessage());
            String errorMessage=e.getMessage();
            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
            Timber.e("Login with Pattern exception"+e.getMessage());
        }
    }


    public void verifyPattern(String patternString, String statusId, final Result<AuthenticatePatternResponseEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    //todo call enroll Email

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }

    // ****** TODO LOGIN WITH FACE *****-------------------------------------------------------------------------------------------------------




    @Override
    public void configureFaceRecognition(final File faceImageFile,final String sub,  final Result<EnrollFaceMFAResponseEntity> enrollresult) {
        try {


            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl = savedProperties.get("DomainURL");


                    if ( sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                        String logoUrl= "https://docs.cidaas.de/assets/logoss.png";

                        SetupFaceMFARequestEntity setupFaceMFARequestEntity=new SetupFaceMFARequestEntity();
                        setupFaceMFARequestEntity.setClient_id( savedProperties.get("ClientId"));
                        setupFaceMFARequestEntity.setLogoUrl(logoUrl);



                        FaceConfigurationController.getShared(context).ConfigureFace(faceImageFile,sub,baseurl,setupFaceMFARequestEntity,enrollresult);


                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }
            });





        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("Configure Face exception"+e.getMessage());

            Timber.e("Configure Face exception"+e.getMessage());
        }


    }


    @Override
    public void loginWithFaceRecognition(@NonNull final File faceImageFile, final String email, final String sub, final String mobile,
                                         @NonNull final String requestId,
                              final String trackId, @NonNull final String usageType, final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {

                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    if ( usageType != null && !usageType.equals("") && requestId != null && !requestId.equals("") && faceImageFile!=null) {

                        if(baseurl == null || baseurl.equals("") &&  clientId == null || clientId!=""){
                            String errorMessage="baseurl or clientId must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if((sub == null || sub.equals("") && email==null || email.equals("") && mobile==null || mobile.equals("")))
                        {
                            String errorMessage="sub or email or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if (usageType.equals(UsageType.MFA)) {
                            if (trackId == null || trackId == "") {
                                String errorMessage = "trackId must not be empty";

                                loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                            }
                        }

                        InitiateFaceMFARequestEntity initiateFaceMFARequestEntity = new InitiateFaceMFARequestEntity();
                        initiateFaceMFARequestEntity.setSub(sub);
                        initiateFaceMFARequestEntity.setUsageType(usageType);
                        initiateFaceMFARequestEntity.setEmail(email);
                        initiateFaceMFARequestEntity.setMobile(mobile);

                        //Todo check for email or sub or mobile


                        FaceConfigurationController.getShared(context).LoginWithFace(faceImageFile, baseurl, clientId, trackId, requestId,
                                initiateFaceMFARequestEntity, loginresult);
                    }
                    else
                    {
                        String errorMessage="Image File or RequestId or UsageType must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            String errorMessage=e.getMessage();
            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
        }
    }



    public void verifyFace(String statusId, final Result<AuthenticateFaceRequestEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    //todo call enroll Email

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            String errorMessage=e.getMessage();
            result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
        }

    }


    // ****** TODO LOGIN WITH Finger *****-------------------------------------------------------------------------------------------------------



    @Override
    public void configureFingerprint(final String sub, final Result<EnrollFingerprintMFAResponseEntity> enrollresult) {
        try {
            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl = result.get("DomainURL");

                    //Todo Call the finger print method


                    if (sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                        final String finalBaseurl = baseurl;



                        String logoUrl = "https://docs.cidaas.de/assets/logoss.png";
                        SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity = new SetupFingerprintMFARequestEntity();
                        setupFingerprintMFARequestEntity.setClient_id(result.get("ClientId"));
                        setupFingerprintMFARequestEntity.setLogoUrl(logoUrl);
                        FingerprintConfigurationController.getShared(context).configureFingerprint(sub,finalBaseurl, setupFingerprintMFARequestEntity,
                                enrollresult);

                    }
                    else
                    {
                        String errorMessage="Sub or Pattern cannot be null";
                        enrollresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,errorMessage,
                                HttpStatusCode.EXPECTATION_FAILED));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }
            });

        }
        catch (Exception e){
            enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }


    @Override
    public void loginWithFingerprint(final String email, final String sub, final String mobile, final String requestId,
                                     final String trackId, final String usageType, final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    if ( usageType != null && usageType != "" && requestId != null && requestId!="") {

                        if(baseurl == null || baseurl.equals("") &&  clientId == null || clientId!=""){
                            String errorMessage="baseurl or clientId  must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if((sub == null || sub.equals("") && email==null || email.equals("") && mobile==null || mobile.equals("")))
                        {
                            String errorMessage="sub or email or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if(usageType.equals(UsageType.MFA))
                        {
                            if(trackId==null || trackId=="" )
                            {
                                String errorMessage="trackId must not be empty";

                                loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                            }
                        }

                        InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity=new InitiateFingerprintMFARequestEntity();
                        initiateFingerprintMFARequestEntity.setSub(sub);
                        initiateFingerprintMFARequestEntity.setUsageType(usageType);
                        initiateFingerprintMFARequestEntity.setEmail(email);
                        initiateFingerprintMFARequestEntity.setMobile(mobile);

                        //Todo check for email or sub or mobile


                        FingerprintConfigurationController.getShared(context).LoginWithFingerprint(baseurl,clientId,trackId,requestId,
                                initiateFingerprintMFARequestEntity,loginresult);
                    }
                    else
                    {
                        String errorMessage="UsageType or FingerprintCode or requestId must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(error);
                }
            });
        }
        catch (Exception e){
            String errorMessage=e.getMessage();
            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
        }
    }

    public void verifyFingerprint(String statusId, final Result<AuthenticateFaceRequestEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    //todo call enroll Email

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }



    // ****** TODO LOGIN WITH Smart push *****-------------------------------------------------------------------------------------------------------



    @Override
    public void configureSmartPush(final String sub, final Result<EnrollSmartPushMFAResponseEntity> enrollresult) {
        try {
            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl = result.get("DomainURL");

                    if (sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                        final String finalBaseurl = baseurl;

                        String logoUrl = "https://docs.cidaas.de/assets/logoss.png";

                        SetupSmartPushMFARequestEntity setupSmartPushMFARequestEntity = new SetupSmartPushMFARequestEntity();
                        setupSmartPushMFARequestEntity.setClient_id(result.get("ClientId"));
                        setupSmartPushMFARequestEntity.setLogoUrl(logoUrl);

                        SmartPushConfigurationController.getShared(context).configureSmartPush(sub,finalBaseurl, setupSmartPushMFARequestEntity,
                                enrollresult);

                    }
                    else
                    {
                        String errorMessage="Sub or SmartPush cannot be null";
                        enrollresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,errorMessage,
                                HttpStatusCode.EXPECTATION_FAILED));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }
            });

        }
        catch (Exception e){
            enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }


    @Override
    public void loginWithSmartPush(final String email, final String sub, final String mobile, final String requestId,
                                   final String trackId, final String usageType,
                                   final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    if ( usageType != null && usageType != "" && requestId != null && requestId!="") {

                        if(baseurl == null || baseurl.equals("") &&  clientId == null || clientId!=""){
                            String errorMessage="baseurl or clientId  must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if((sub == null || sub.equals("") && email==null || email.equals("") && mobile==null || mobile.equals("")))
                        {
                            String errorMessage="sub or email or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if(usageType.equals(UsageType.MFA))
                        {
                            if(trackId==null || trackId=="" )
                            {
                                String errorMessage="trackId must not be empty";

                                loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                            }
                        }

                        InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntity=new InitiateSmartPushMFARequestEntity();
                        initiateSmartPushMFARequestEntity.setSub(sub);
                        initiateSmartPushMFARequestEntity.setUsageType(usageType);
                        initiateSmartPushMFARequestEntity.setEmail(email);
                        initiateSmartPushMFARequestEntity.setMobile(mobile);

                        //Todo check for email or sub or mobile


                        SmartPushConfigurationController.getShared(context).LoginWithSmartPush(baseurl,clientId,trackId,requestId,
                                initiateSmartPushMFARequestEntity,loginresult);
                    }
                    else
                    {
                        String errorMessage="UsageType or SmartPushCode or requestId must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    public void verifySmartPush(String randomNumber, String statusId, final Result<AuthenticateSmartPushRequestEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    //todo call enroll Email

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }

    // ****** TODO LOGIN WITH TOTP *****-------------------------------------------------------------------------------------------------------



    @Override
    public void configureTOTP(final String sub, final Result<EnrollTOTPMFAResponseEntity> enrollresult) {
        try {
            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl = result.get("DomainURL");

                    if (sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                        final String finalBaseurl = baseurl;

                        String logoUrl = "https://docs.cidaas.de/assets/logoss.png";
                        SetupTOTPMFARequestEntity setupTOTPMFARequestEntity = new SetupTOTPMFARequestEntity();
                        setupTOTPMFARequestEntity.setClient_id(result.get("ClientId"));
                        setupTOTPMFARequestEntity.setLogoUrl(logoUrl);
                        TOTPConfigurationController.getShared(context).configureTOTP(sub,finalBaseurl, setupTOTPMFARequestEntity, enrollresult);

                    }
                    else
                    {
                        String errorMessage="Sub or TOTP cannot be null";
                        enrollresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,errorMessage,
                                HttpStatusCode.EXPECTATION_FAILED));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }
            });

        }
        catch (Exception e){
            enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }


    @Override
    public void loginWithTOTP(final String email, final String sub, final String mobile, final String requestId, final String trackId,
                              final String usageType,
                              final Result<LoginCredentialsResponseEntity> loginresult) {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    if ( usageType != null && usageType != "" && requestId != null && requestId!="") {

                        if(baseurl == null || baseurl.equals("") &&  clientId == null || clientId!=""){
                            String errorMessage="baseurl or clientId  must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if((sub == null || sub.equals("") && email==null || email.equals("") && mobile==null || mobile.equals("")))
                        {
                            String errorMessage="sub or email or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if(usageType.equals(UsageType.MFA))
                        {
                            if(trackId==null || trackId=="" )
                            {
                                String errorMessage="trackId must not be empty";

                                loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                            }
                        }

                        InitiateTOTPMFARequestEntity initiateTOTPMFARequestEntity=new InitiateTOTPMFARequestEntity();
                        initiateTOTPMFARequestEntity.setSub(sub);
                        initiateTOTPMFARequestEntity.setUsageType(usageType);
                        initiateTOTPMFARequestEntity.setEmail(email);
                        initiateTOTPMFARequestEntity.setMobile(mobile);

                        //Todo check for email or sub or mobile


                        TOTPConfigurationController.getShared(context).LoginWithTOTP(baseurl,clientId,trackId,requestId,
                                initiateTOTPMFARequestEntity,loginresult);
                    }
                    else
                    {
                        String errorMessage="UsageType or TOTPCode or requestId must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }



    // ****** TODO LOGIN WITH voice *****-------------------------------------------------------------------------------------------------------



    @Override
    public void configureVoiceRecognition(final File VoiceaudioFile, final String sub, final Result<EnrollVoiceMFAResponseEntity> enrollresult) {
        try {
            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl = savedProperties.get("DomainURL");


                    if ( sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                        String logoUrl= "https://docs.cidaas.de/assets/logoss.png";

                        SetupVoiceMFARequestEntity setupVoiceMFARequestEntity=new SetupVoiceMFARequestEntity();
                        setupVoiceMFARequestEntity.setClient_id( savedProperties.get("ClientId"));
                        setupVoiceMFARequestEntity.setLogoUrl(logoUrl);



                        VoiceConfigurationController.getShared(context).configureVoice(sub,baseurl,VoiceaudioFile,setupVoiceMFARequestEntity,enrollresult);


                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    enrollresult.failure(error);
                }
            });

        }
        catch (Exception e){
            enrollresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }


    @Override
    public void loginWithVoiceRecognition(final File VoiceaudioFile, final String email, final String sub, final String mobile, final String requestId,
                                          final String trackId, final String usageType,
                               final Result<LoginCredentialsResponseEntity> loginresult) {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    if ( usageType != null && !usageType.equals("") && requestId != null && !requestId.equals("") && VoiceaudioFile!=null) {

                        if(baseurl == null || baseurl.equals("") &&  clientId == null || clientId!=""){
                            String errorMessage="baseurl or clientId must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if((sub == null || sub.equals("") && email==null || email.equals("") && mobile==null || mobile.equals("")))
                        {
                            String errorMessage="sub or email or mobile number must not be empty";

                            loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }

                        if (usageType.equals(UsageType.MFA)) {
                            if (trackId == null || trackId == "") {
                                String errorMessage = "trackId must not be empty";

                                loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                        errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                            }
                        }

                        InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity = new InitiateVoiceMFARequestEntity();
                        initiateVoiceMFARequestEntity.setSub(sub);
                        initiateVoiceMFARequestEntity.setUsageType(usageType);
                        initiateVoiceMFARequestEntity.setEmail(email);
                        initiateVoiceMFARequestEntity.setMobile(mobile);

                        //Todo check for email or sub or mobile


                        VoiceConfigurationController.getShared(context).LoginWithVoice(VoiceaudioFile, baseurl, clientId, trackId, requestId,
                                initiateVoiceMFARequestEntity, loginresult);
                    }
                    else
                    {
                        String errorMessage="Image File or RequestId or UsageType must not be empty";

                        loginresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }


                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    public void verifyVoice(File voice, String statusId, final Result<AuthenticateVoiceRequestEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    //todo call enroll Email

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }


    // ****** TODO LOGIN WITH FACE *****-------------------------------------------------------------------------------------------------------


    @Override
    public void getRegisterationFields(@NonNull final String requestId, final String acceptLanguage,
                                       final Result<RegistrationSetupResponseEntity> registerFieldsresult) {
       try{

           checkSavedProperties(new Result<Dictionary<String, String>>() {
               @Override
               public void success(Dictionary<String, String> result) {
                   String baseurl = savedProperties.get("DomainURL");
                   String clientId=savedProperties.get("ClientId");
                   String language;

                   if(!requestId.equals("")){

                       final RegistrationSetupRequestEntity registrationSetupRequestEntity;

                       registrationSetupRequestEntity = new RegistrationSetupRequestEntity();
                       registrationSetupRequestEntity.setRequestId(requestId);

                       if(acceptLanguage==null || acceptLanguage=="")
                       {
                         language = Locale.getDefault().getLanguage();
                         registrationSetupRequestEntity.setAcceptedLanguage(language);

                       }
                       else {
                           language=acceptLanguage;
                           registrationSetupRequestEntity.setAcceptedLanguage(language);
                       }

                       RegistrationController.getShared(context).getRegisterationFields(baseurl,registrationSetupRequestEntity,
                               new Result<RegistrationSetupResponseEntity>() {
                           @Override
                           public void success(RegistrationSetupResponseEntity result) {
                             registerFieldsresult.success(result);
                           }

                           @Override
                           public void failure(WebAuthError error) {
                            registerFieldsresult.failure(error);
                           }
                       });

                   }
                   else {
                       String errorMessage="RequestId must not be empty";

                       registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                               errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                   }

               }

               @Override
               public void failure(WebAuthError error) {
                   registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException());
               }
           });
       }
       catch (Exception e)
       {
           String errorMessage="Custom Exception"+e.getMessage();

           registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                   errorMessage,HttpStatusCode.EXPECTATION_FAILED));
       }
    }


    public void registerUser(@NonNull final String requestId, final RegistrationEntity registrationEntity,
                             final Result<RegisterNewUserResponseEntity> registerFieldsresult) {
        try{

            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    String language;

                    if(!requestId.equals("")){



                        final RegisterNewUserRequestEntity registerNewUserRequestEntity;

                        registerNewUserRequestEntity = new RegisterNewUserRequestEntity();
                        registerNewUserRequestEntity.setRequestId(requestId);
                        registrationEntity.setProvider("self");
                        registerNewUserRequestEntity.setRegistrationEntity(registrationEntity);


                        RegistrationController.getShared(context).registerNewUser(baseurl,registerNewUserRequestEntity,
                                new Result<RegisterNewUserResponseEntity>() {
                                    @Override
                                    public void success(RegisterNewUserResponseEntity result) {
                                        registerFieldsresult.success(result);
                                    }

                                    @Override
                                    public void failure(WebAuthError error) {
                                        registerFieldsresult.failure(error);
                                    }
                                });

                    }
                    else {
                        String errorMessage="RequestId must not be empty";

                        registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    registerFieldsresult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e)
        {
            String errorMessage="Custom Exception"+e.getMessage();

            registerFieldsresult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
        }
    }

    public void initiateAccountVerificationByEmail(@NonNull final String sub, @NonNull final String requestId,
                                            final Result<RegisterUserAccountInitiateResponseEntity> Result){
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    RegisterUserAccountInitiateRequestEntity registerUserAccountInitiateRequestEntity=new RegisterUserAccountInitiateRequestEntity();
                    registerUserAccountInitiateRequestEntity.setProcessingType("CODE");

                 /*   if(verificationMedium.equals("") || verificationMedium.equals(null))
                    {
                        registerUserAccountInitiateRequestEntity.setVerificationMedium("email");
                    }
                    else
                    {
                        registerUserAccountInitiateRequestEntity.setVerificationMedium(verificationMedium);
                    }*/
                    registerUserAccountInitiateRequestEntity.setVerificationMedium("email");

                    registerUserAccountInitiateRequestEntity.setSub(sub);
                    registerUserAccountInitiateRequestEntity.setRequestId(requestId);

                    RegistrationController.getShared(context).initiateAccountVerificationService(baseurl,registerUserAccountInitiateRequestEntity,Result);

                }

                @Override
                public void failure(WebAuthError error) {
                    Result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            Result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }


    public void initiateAccountVerificationBySMS(@NonNull final String sub, @NonNull final String requestId,
                                                   final Result<RegisterUserAccountInitiateResponseEntity> Result){
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    RegisterUserAccountInitiateRequestEntity registerUserAccountInitiateRequestEntity=new RegisterUserAccountInitiateRequestEntity();
                    registerUserAccountInitiateRequestEntity.setProcessingType("CODE");
                    registerUserAccountInitiateRequestEntity.setVerificationMedium("sms");
                    registerUserAccountInitiateRequestEntity.setSub(sub);
                    registerUserAccountInitiateRequestEntity.setRequestId(requestId);
                 /*   if(verificationMedium.equals("") || verificationMedium.equals(null))
                    {
                        registerUserAccountInitiateRequestEntity.setVerificationMedium("email");
                    }
                    else
                    {
                        registerUserAccountInitiateRequestEntity.setVerificationMedium(verificationMedium);
                    }*/


                    RegistrationController.getShared(context).initiateAccountVerificationService(baseurl,registerUserAccountInitiateRequestEntity,Result);


                }

                @Override
                public void failure(WebAuthError error) {
                    Result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            Result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }

    public void initiateAccountVerificationByIVR(@NonNull final String sub, @NonNull final String requestId,
                                                   final Result<RegisterUserAccountInitiateResponseEntity> Result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    RegisterUserAccountInitiateRequestEntity registerUserAccountInitiateRequestEntity=new RegisterUserAccountInitiateRequestEntity();
                    registerUserAccountInitiateRequestEntity.setProcessingType("CODE");
                    registerUserAccountInitiateRequestEntity.setVerificationMedium("ivr");
                    registerUserAccountInitiateRequestEntity.setSub(sub);
                    registerUserAccountInitiateRequestEntity.setRequestId(requestId);

                 /*   if(verificationMedium.equals("") || verificationMedium.equals(null))
                    {
                        registerUserAccountInitiateRequestEntity.setVerificationMedium("email");
                    }
                    else
                    {
                        registerUserAccountInitiateRequestEntity.setVerificationMedium(verificationMedium);
                    }*/


                    RegistrationController.getShared(context).initiateAccountVerificationService(baseurl,registerUserAccountInitiateRequestEntity,Result);


                }

                @Override
                public void failure(WebAuthError error) {
                    Result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            Result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }

    public void verifyAccount(@NonNull final String code, final Result<RegisterUserAccountVerifyResponseEntity> result){

        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    RegistrationController.getShared(context).verifyAccountVerificationService(baseurl,code,result);

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }


    //----------------------------------DEDEUPLICATION------------------------------------------------------------------------------------------------------

     public void getDeduplicationDetails(@NonNull final String trackId, final Result<DeduplicationResponseEntity> deduplicaionResult){
         try {
             checkSavedProperties(new Result<Dictionary<String, String>>() {
                 @Override
                 public void success(Dictionary<String, String> result) {
                     String baseurl = savedProperties.get("DomainURL");
                     String clientId=savedProperties.get("ClientId");
                     if(trackId!=null && trackId!="")
                     {
                         DeduplicationController.getShared(context).getDeduplicationList(baseurl,trackId,deduplicaionResult);
                     }
                     else
                     {

                     }
                 }

                 @Override
                 public void failure(WebAuthError error) {
                     deduplicaionResult.failure(WebAuthError.getShared(context).propertyMissingException());
                 }
             });
         }
         catch (Exception e){
             deduplicaionResult.failure(WebAuthError.getShared(context).propertyMissingException());
         }
     }


     public void registerDeduplication(@NonNull final String trackId, final Result<RegisterDeduplicationEntity> deduplicaionResult){
         try {
             checkSavedProperties(new Result<Dictionary<String, String>>() {
                 @Override
                 public void success(Dictionary<String, String> result) {
                     String baseurl = savedProperties.get("DomainURL");
                     String clientId=savedProperties.get("ClientId");
                     if(trackId!=null && trackId!="")
                     {
                         DeduplicationController.getShared(context).registerDeduplication(baseurl,trackId,deduplicaionResult);
                     }
                     else
                     {

                     }
                 }

                 @Override
                 public void failure(WebAuthError error) {
                     deduplicaionResult.failure(WebAuthError.getShared(context).propertyMissingException());
                 }
             });
         }
         catch (Exception e){
             deduplicaionResult.failure(WebAuthError.getShared(context).propertyMissingException());
         }
     }

    public void loginDeduplication(@NonNull final String sub,@NonNull final String password, final Result<LoginDeduplicationResponseEntity> deduplicaionResult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                    if(sub!=null && sub!="" && password!=null && password!="")
                    {
                        DeduplicationController.getShared(context).loginDeduplication(baseurl,sub,password,deduplicaionResult);
                    }
                    else
                    {

                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    deduplicaionResult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            deduplicaionResult.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }


    //----------------------------------------------------------------------------------------------------------------------------------------


    public void initiateResetPassword(final String requestId, final ResetPasswordRequestEntity resetPasswordRequestEntity,
                                      final Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult)
    { try {
        checkSavedProperties(new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                String baseurl = savedProperties.get("DomainURL");
                String clientId=savedProperties.get("ClientId");/**/

                resetPasswordRequestEntity.setProcessingType("CODE");
                resetPasswordRequestEntity.setRequestId(requestId);

                ResetPasswordController.getShared(context).initiateresetPasswordService(baseurl,resetPasswordRequestEntity,resetPasswordResponseEntityResult);
            }

            @Override
            public void failure(WebAuthError error) {
                resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        });
    }
    catch (Exception e){
        resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).propertyMissingException());
    }

    }

    public void handleResetPassword(@NonNull final String verificationCode, final Result<ResetPasswordValidateCodeResponseEntity> resetpasswordResult)
    {
        try {
        checkSavedProperties(new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                String baseurl = savedProperties.get("DomainURL");
                String clientId=savedProperties.get("ClientId");

                if(verificationCode!=null && verificationCode!="") {

                    ResetPasswordController.getShared(context).resetPasswordValidateCode(baseurl,verificationCode,resetpasswordResult);

                }
                else
                {
                    resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException());
                }

            }

            @Override
            public void failure(WebAuthError error) {
                resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        });
    }
    catch (Exception e){
        resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException());
    }

    }


    public void resetPassword(@NonNull final String password, @NonNull final String confirmPassword, final Result<ResetNewPasswordResponseEntity> resetpasswordResult)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");

                    if(password!=null && !password.equals("") && confirmPassword!=null && !confirmPassword.equals(""))
                    {
                        if(password.equals(confirmPassword))
                        {
                            ResetPasswordController.getShared(context).resetNewPassword(baseurl,password,confirmPassword,resetpasswordResult);
                        }
                        else
                        {
                            String errorMessage="Password and confirmpassword must  be same";

                            resetpasswordResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                        }
                    }
                    else
                    {
                        String errorMessage="Password or confirmpassword must not be empty";

                        resetpasswordResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                                errorMessage,HttpStatusCode.EXPECTATION_FAILED));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    resetpasswordResult.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            String errorMessage=e.getMessage();

            resetpasswordResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                    errorMessage,HttpStatusCode.EXPECTATION_FAILED));


        }
    }


    //----------------------------------------------------------------------------------------------------------------------------------------

    public void changePassword(final ChangePasswordRequestEntity changePasswordRequestEntity, final Result<ChangePasswordResponseEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> lpresult) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");


                    ChangePasswordController.getShared(context).changePassword(baseurl,changePasswordRequestEntity,result);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }



/*
    public void getUserInfo(@NonNull String AccessToken,)*/
    //To Open Browser
    @Override
    public void loginWithBrowser(@Nullable String color, Result<AccessTokenEntity> callbacktoMain)
    {
        try {


            logincallback = callbacktoMain;
            if (loginURL != null) {
                String url = loginURL;
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();


                if(color!=null)
                {

                    builder.setToolbarColor(Color.parseColor(color));
                }

                CustomTabsIntent customTabsIntent = builder.build();

                customTabsIntent.launchUrl(context, Uri.parse(url));
            }
            else
            {
                //TODo callback Failure
                String loggerMessage = "LoginURL failure : " + "Error Code - ";
                // +error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " +  error.statusCode;
                LogFile.addRecordToLog(loggerMessage);
            }
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());// TODO: Handle Exception
        }
    }



    //Todo sConsult and Create a  new Resume if the loginCallback is null
    //Get Code By URl
    @Override
    public void getLoginCode(String url,Result<AccessTokenEntity> callback) {
       try {
           showLoader();
           String code = getCodeFromUrl(url);
           if (code != null) {
               hideLoader();

               getAccessTokenByCode(code, callback);
           } else {
               hideLoader();
               String loggerMessage = "Request-Id params to dictionary conversion failure : " + "Error Code - ";
               //+error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " +  error.statusCode;
               LogFile.addRecordToLog(loggerMessage);
           }
       }
       catch (Exception e)
       {
           Timber.d(e.getMessage()); //Todo handle Exception
       }
    }

    @Override
    public void getAccessTokenByCode(String code, Result<AccessTokenEntity> result) {

    }

    @Override
    public void getAccessToken(String sub, Result<AccessTokenEntity> result) {
      try
      {
          AccessTokenController.getShared(context).getAccessToken(sub,result);
      }
      catch (Exception e)
      {
          String errorMessage="Access Token Exception"+e.getMessage();
          result.failure(WebAuthError.getShared(context).customException(417,errorMessage,417));
      }
    }

    @Override
    public void getAccessTokenByRefreshToken(String refershtoken, Result<AccessTokenEntity> result) {

    }








    // --------------------------------------------------------------------------------------------------

//Done Configure pattern by Passing the pattern String Directly
    // 1. Done Check For Local Variable or Read properties from file
    // 2. Done Check For NotNull Values
    // 3. Done Call configure Pattern From Pattern Controller and return the result
    // 4. Done Maintain logs based on flags

    public  void checkSavedProperties(final Result<Dictionary<String, String> > result){

        if(savedProperties==null){
            //Read from file if localDB is null
            readFromFile(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    savedProperties=loginProperties;
                    if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                        webAuthError = webAuthError.propertyMissingException();
                        String loggerMessage = "Setup Pattern MFA readProperties failure : " + "Error Code - "
                                + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage + ", Status Code - " + webAuthError.statusCode;
                        LogFile.addRecordToLog(loggerMessage);
                        result.failure(webAuthError);
                    }
                    if (savedProperties.get("ClientId").equals("") || savedProperties.get("ClientId") == null || savedProperties == null) {
                        webAuthError = webAuthError.propertyMissingException();
                        String loggerMessage = "Accept Consent readProperties failure : " + "Error Code - "
                                + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage + ", Status Code - " + webAuthError.statusCode;

                        LogFile.addRecordToLog(loggerMessage);
                        result.failure(webAuthError);
                    }
                    result.success(savedProperties);

                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });
        }
        else
        {
            result.success(savedProperties);
        }


    }

    @Override
    public void loginWithFIDO(String usageType, String email, String sub, String trackId,final Result<LoginCredentialsResponseEntity> result)
    {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }


// -----------------------------------------------------------------------------------------------------------------------------------------------------
    //Private Methods
    private String getCodeFromUrl(String url) {
    try {
        String code = null;
        if (url.contains("code=")) {
            String[] codeComponents = url.split("code=");
            if (codeComponents.length > 1) {
                codeComponents = codeComponents[1].split("&");
                if (codeComponents.length > 0) {
                    code = codeComponents[0];
                    return code;
                } else {
                    return code;
                }
            }
        }
        return code;
    }
    catch (Exception e)
    {
        Timber.d(e.getMessage());
        return null;
        //Todo Handle Exception
    }
}

    //Method to check the pkce flow and save it to DB
    private void checkPKCEFlow(Dictionary<String,String> loginproperties,Result<Dictionary<String,String>> savedResult){
        try{

            webAuthError=WebAuthError.getShared(context);
            // Global Checking
            //Check all the login Properties are Correct
            if (loginproperties.get("DomainURL") == null || loginproperties.get("DomainURL") == ""
                    || !((Hashtable) loginproperties).containsKey("DomainURL")) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Request-Id readProperties failure : " + "Error Code - " +webAuthError.errorCode + ", Error Message - "
                        + webAuthError.ErrorMessage + ", Status Code - " +  webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                savedResult.failure(webAuthError);

                return;
            }
            if (loginproperties.get("ClientId").equals(null) || loginproperties.get("ClientId").equals("")
                    || !((Hashtable) loginproperties).containsKey("ClientId")) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Request-Id readProperties failure : " + "Error Code - "
                        +webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage + ", Status Code - " +  webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                savedResult.failure(webAuthError);return;
            }
            if (!((Hashtable) loginproperties).containsKey("RedirectURL") || loginproperties.get("RedirectURL").equals(null)
                    || loginproperties.get("RedirectURL").equals("")) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Request-Id readProperties failure : " + "Error Code - "
                        +webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage + ", Status Code - " +  webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                savedResult.failure(webAuthError);return;
            }


            savedProperties = loginproperties;
            //Get enable Pkce Flag
            ENABLE_PKCE = DBHelper.getShared().getEnablePKCE();

            // Check Client Secret if the PKCE Flow is Disabled
            if (!ENABLE_PKCE) {
                if (loginproperties.get("ClientSecret") == null || loginproperties.get("ClientSecret") == "" ||
                        !((Hashtable) loginproperties).containsKey("ClientSecret")) {
                    webAuthError = webAuthError.propertyMissingException();
                    savedResult.failure(webAuthError);
                } else {
                    savedProperties.put("ClientSecret", loginproperties.get("ClientSecret"));
                }
            }/* else {
                //Create Challenge And Verifier
                OAuthChallengeGenerator generator = new OAuthChallengeGenerator();
                savedProperties.put("Verifier", generator.getCodeVerifier());
                savedProperties.put("Challenge", generator.getCodeChallenge(savedProperties.get("Verifier")));
                savedProperties.put("Method", generator.codeChallengeMethod);
            }*/
            DBHelper.getShared().addLoginProperties(savedProperties);
            savedResult.success(savedProperties);
        }
        catch (Exception e)
        {
            Timber.e("Request-Id service exception : "+e.getMessage());
            savedResult.failure(webAuthError);
        }
    }


    //Get code From URL

    //Get userinfo Based on Access Token
    @Override
    public void getUserInfo(String access_token, final Result<UserinfoEntity> callback) {
        try {
            showLoader();
            OauthService.getShared(context).getUserinfo(access_token, new Result<UserinfoEntity>() {
                @Override
                public void success(UserinfoEntity result) {
                    hideLoader();
                    callback.success(result);
                }

                @Override
                public void failure(WebAuthError error) {
                    hideLoader();
                    callback.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage()); //Todo Handle Exception
        }
    }

    //Get Login URL without any Argument
    @Override
    public void getLoginURL(final Result<String> callback) {
        try {
            getRequestId(new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {
                    getLoginURL(result.getData().getRequestId().toString(), callback);
                }

                @Override
                public void failure(WebAuthError error) {
                    callback.failure(error);

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
        }
    }

    //Get Login URL Method by  passing RequestId
    @Override
    public void getLoginURL(@NonNull String RequestId, final Result<String> callback) {
        try
        {
            //Check requestId is not null
            if(RequestId != "") {
                OauthService.getShared(context).getLoginUrl(RequestId, new Result<String>() {
                    @Override
                    public void success(String result) {
                        callback.success(result);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        callback.failure(error);
                        String loggerMessage = "Login URL service failure : " + "Error Code - "
                                +error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " +  error.statusCode;
                        LogFile.addRecordToLog(loggerMessage);
                    }
                });
            }
        }
        catch (Exception ex) {
            //Todo Handle Error
            Timber.d(ex.getMessage());
        }
    }


    //get loginurl by passing Domain URl.......as Arguments

    public void getLoginURL(@NonNull String DomainUrl, @NonNull String ClientId, @NonNull String RedirectURL,
                            @NonNull String ClientSecret, final Result<String> callback) {
        try {
            //Get Request ID
            if (ClientId != null && !Objects.equals(ClientId, "") && DomainUrl != null && !Objects.equals(DomainUrl, "")
                    && RedirectURL != null && !Objects.equals(RedirectURL, "")) {
                getRequestId(DomainUrl, ClientId, RedirectURL,ClientSecret, new Result<AuthRequestResponseEntity>() {
                    @Override
                    public void success(AuthRequestResponseEntity result) {
                        // if request ID is valid we call get LoginURl
                        getLoginURL(result.getData().getRequestId(), callback);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        callback.failure(error);
                    }
                });
            }
        }
        catch (Exception ex)
        {
            Timber.d(ex.getMessage());
            //Todo Handle Exception
        }
    }

    //Get LoginURL by Passing Dictionary
    @Override
    public void getLoginURL(@NonNull Dictionary<String, String> loginproperties, final Result<String> callback) {
        try {
            //Get Request ID
            RequestIdController.getShared(context). getRequestId(loginproperties, new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {
                    //Call Loginurl if requestId is Valid
                    getLoginURL(result.getData().getRequestId(), callback);
                }

                @Override
                public void failure(WebAuthError error) {
                    callback.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());    //Todo Handle Exception
        }
    }

    //ReadFromXML File
    private void readFromFile(final Result<Dictionary<String,String>> loginPropertiesResult){
        FileHelper fileHelper = FileHelper.getShared(context);
        fileHelper.readProperties(context.getAssets(), "Cidaas.xml", new Result<Dictionary<String, String>>() {
            @Override
            public void success(final Dictionary<String, String> loginProperties) {

                //on successfully completion of file reading add it to LocalDB(shared Preference) and call requestIdByloginProperties
                checkPKCEFlow(loginProperties, new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> savedLoginProperties) {


                        loginPropertiesResult.success(savedLoginProperties);

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        loginPropertiesResult.failure(error);
                        String loggerMessage = "Request-Id PKCE FLOW readProperties failure : " + "Error Code - " +error.errorCode +
                                ", Error Message - " + error.ErrorMessage + ", Status Code - " +  error.statusCode;
                        LogFile.addRecordToLog(loggerMessage);
                    }
                });


            }

            @Override
            public void failure(WebAuthError error) {
                //Return File Reading Error
                String loggerMessage = "Request-Id readProperties failure : "
                        + "Error Code - " + error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " + error.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                loginPropertiesResult.failure(error);
            }
        });
    }

    //To Show Loader
    private void showLoader(){
       try {


           if (loader != null) {
               // handle already running
               if (!displayLoader) {
                   loader.showLoader();
                   displayLoader = true;
               }
           }
       }
       catch (Exception e)
       {
           //Todo Handle Exception
       }

    }
    //To Hide Loader
    private void hideLoader() {
        try {
            if (loader != null) {
                //handle already hiding
                if (displayLoader) {
                    loader.hideLoader();
                    displayLoader = false;
                }
            }
        }
        catch (Exception ex)
        {
            Timber.d(ex.getMessage());  //Todo handle Exception
        }
    }


    //Resume After open App From Broswer
    public void resume(String code)/*,Result<AccessTokenEntity>... callbacktoMain*/
    {
        if(logincallback!=null) {
            getLoginCode(code, logincallback);
        }
      /* else if(callbacktoMain!=null)
       {
        *//*logincallback=()callbacktoMain;
        getLoginCode(code,callbacktoMain);*//*
       }*/
        //Todo Handle Else part and give Exception

    }













    /*

    public void resumeLogin(@NonNull final ResumeLoginRequestEntity resumeLoginRequestEntity , @NonNull final Result<AccessTokenEntity> loginresult)
    {
        try{

            checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId = savedProperties.get("ClientId");

                    if (baseurl != null && !baseurl.equals("") && clientId != null && clientId!="" ) {

                         resumeLoginRequestEntity.setClient_id(clientId);
                        LoginController.getShared(context).resumeLogin(baseurl,resumeLoginRequestEntity,loginresult);
                    }

                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(error);
                }
            });
        }
        catch (Exception e){
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }
*/


    @Override
    public void configureFIDO(String sub, final Result<EnrollFIDOMFAResponseEntity> result) {
        try {
            checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = savedProperties.get("DomainURL");
                    String clientId=savedProperties.get("ClientId");
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(WebAuthError.getShared(context).propertyMissingException());
                }
            });
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }

    }




}
