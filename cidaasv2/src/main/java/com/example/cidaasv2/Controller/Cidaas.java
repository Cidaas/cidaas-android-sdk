package com.example.cidaasv2.Controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.nfc.tech.IsoDep;
import android.os.Build;
import android.provider.Settings;

import com.example.cidaasv2.BuildConfig;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.ChangePassword.ChangePasswordController;
import com.example.cidaasv2.Controller.Repository.Client.ClientController;
import com.example.cidaasv2.Controller.Repository.Configuration.BackupCode.BackupCodeConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.Email.EmailConfigurationController;
import com.example.cidaasv2.Controller.Repository.Configuration.FIDO.FIDOConfigurationController;
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
import com.example.cidaasv2.Controller.Repository.DocumentScanner.DocumentScannnerController;
import com.example.cidaasv2.Controller.Repository.LocalAuthentication.LocalAuthenticationController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Controller.Repository.MFASettings.VerificationSettingsController;
import com.example.cidaasv2.Controller.Repository.Registration.RegistrationController;
import com.example.cidaasv2.Controller.Repository.RequestId.RequestIdController;
import com.example.cidaasv2.Controller.Repository.ResetPassword.ResetPasswordController;
import com.example.cidaasv2.Controller.Repository.Tenant.TenantController;
import com.example.cidaasv2.Controller.Repository.UserLoginInfo.UserLoginInfoController;
import com.example.cidaasv2.Controller.Repository.UserProfile.UserProfileController;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.ConsentEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.FingerPrintEntity;
import com.example.cidaasv2.Helper.Entity.LocalAuthenticationEntity;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Entity.RegistrationEntity;
import com.example.cidaasv2.Helper.Entity.SocialAccessTokenEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.FileHelper;
import com.example.cidaasv2.Helper.Loaders.ICustomLoader;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Interface.IOAuthWebLogin;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.DeduplicationResponseEntity;
import com.example.cidaasv2.Service.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import com.example.cidaasv2.Service.Entity.DocumentScanner.DocumentScannerServiceResultEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey.AuthenticateFIDOResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey.FidoSignTouchResponse;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.DeleteMFA.DeleteMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.EnrollFIDOMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.FIDOTouchResponse;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.DenyNotification.DenyNotificationResponseEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.NotificationEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetPasswordEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.Entity.UserList.ConfiguredMFAListEntity;
import com.example.cidaasv2.Service.Entity.UserLoginInfo.UserLoginInfoEntity;
import com.example.cidaasv2.Service.Entity.UserLoginInfo.UserLoginInfoResponseEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;
import com.example.cidaasv2.VerificationV2.domain.Controller.Enroll.EnrollController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Scanned.ScannedController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Setup.SetupController;

import java.io.File;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import timber.log.Timber;

import static android.os.Build.MODEL;
import static android.os.Build.VERSION;

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class Cidaas implements IOAuthWebLogin {

    private static final int MY_SCAN_REQUEST_CODE = 3;
    private static final int LOCAL_AUTH_REQUEST_CODE = 303;
    private static final int LOCAL_REQUEST_CODE = 302;
    private static final int RESULT_OK = -1;



    Result<LocalAuthenticationEntity> localAuthenticationEntityCallback;


    public Context context;
    public Activity activityFromCidaas;

    public static String usagePass = "";
    public static ICustomLoader loader;
    public static String baseurl = "";
    public static final String FIDO_VERSION = "U2F_V2";

    public WebAuthError webAuthError = null;


    public static String logoURLlocal="https://cdn.shortpixel.ai/client/q_glossy,ret_img/https://www.cidaas.com/wp-content/uploads/2018/02/logo.png";
    public static String APP_NAME = "cidaas";
    public static String APP_VERSION = "";




    //saved Properties for Global Access
  //  Dictionary<String, String> savedProperties;

    //To check the Loader
    private boolean displayLoader = false;

    //Todo Confirm it must be a static one
    //Extra parameter that is passed in URL
    public static HashMap<String, String> extraParams = new HashMap<>();

//Create a Shared Instance

    private static Cidaas cidaasInstance;

    public  static SetupController verification;
   // =SetupController.getShared()



    public static SetupController getVerification(Context YourActivitycontext) {
        if (verification == null) {
            verification = new SetupController(YourActivitycontext);
        }

        return verification;
    }


    public static Cidaas getInstance(Context YourActivitycontext) {
        if (cidaasInstance == null) {
            cidaasInstance = new Cidaas(YourActivitycontext);
        }

        return cidaasInstance;
    }

    public static boolean ENABLE_PKCE;
    public boolean ENABLE_LOG;

    public DeviceInfoEntity deviceInfoEntity;


    public boolean isENABLE_PKCE() {
        ENABLE_PKCE = DBHelper.getShared().getEnablePKCE();
        return ENABLE_PKCE;
    }

    public void setENABLE_PKCE(boolean ENABLE_PKCE)
    {
        this.ENABLE_PKCE = ENABLE_PKCE;
        DBHelper.getShared().setEnablePKCE(ENABLE_PKCE);
    }


    //enableLog

    public boolean isLogEnable()
    {
        ENABLE_LOG = DBHelper.getShared().getEnableLog();
        return ENABLE_LOG;
    }

    public String enableLog()
    {
        String messsage="";
        //Check permission For marshmallow and above
        if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                messsage= enableLogWithPermission();
                return messsage;
            }
            else
            {
               messsage="Storge permission is not given, please request storage permisson to enable log";
               return messsage;
            }
        }
        else
        {
           messsage= enableLogWithPermission();
           return messsage;
        }
    }


    private String enableLogWithPermission()
    {
        // Enable Log
        this.ENABLE_LOG = true;
        DBHelper.getShared().setEnableLog(ENABLE_LOG);
        return "Log Successfully Enabled";
    }



    public Cidaas(Context yourActivityContext) {
        this.context = yourActivityContext;

        //Initialise Shared Preferences
        DBHelper.setConfig(context);

        //Default Value;
        ENABLE_PKCE = true;

        //Default Log Value
        ENABLE_LOG = false;

        //Add Device info
        deviceInfoEntity = new DeviceInfoEntity();
        deviceInfoEntity.setDeviceId(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID));
        deviceInfoEntity.setDeviceModel(MODEL);
        deviceInfoEntity.setDeviceVersion(String.valueOf(VERSION.RELEASE));
        deviceInfoEntity.setDeviceMake(Build.MANUFACTURER);

        if (DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {
            deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
        }


        Cidaas.baseurl="";


        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = null;

        try {
            applicationInfo = packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
           APP_VERSION= context.getPackageManager().getPackageInfo(context.getApplicationInfo().packageName, 0).versionName;
        } catch (final PackageManager.NameNotFoundException e) {
        }

        if(applicationInfo!=null) {

            APP_NAME = packageManager.getApplicationLabel(applicationInfo).toString();
        }
        else
        {
            APP_NAME="UNKNOWN";
        }



        //Store Device info for Later Purposes
        DBHelper.getShared().addDeviceInfo(deviceInfoEntity);

        CidaasProperties.getShared(context).saveCidaasProperties(new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                Cidaas.baseurl=result.get("DomainURL");
            }

            @Override
            public void failure(WebAuthError error) {

            }
        });

    }



    //Set FCM Token For Update
    public void setFCMToken(String FCMToken) {

        //Store Device info for Later Purposes
        DBHelper.getShared().setFCMToken(FCMToken);

    }

    //Get the remote messages from the Push notification
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

    }

    // Get the instance ID
    public String getInstanceId() {
        if (usagePass != null && !usagePass.equals("")) {
            return usagePass;
        } else {
            return null;
        }
    }

    // -----------------------------------------------------***** REQUEST ID CONTROLLER SENDER *****---------------------------------------------------------------

    public void getRequestId(final Dictionary<String, String> loginproperties ,final Result<AuthRequestResponseEntity> Primaryresult,
                             @Nullable HashMap<String, String>... extraParams) {
        RequestIdController.getShared(context).getRequestId(loginproperties, Primaryresult, extraParams);
    }


    // -----------------------------------------------------***** TENANT INFO *****---------------------------------------------------------------
    @Override
    public void getTenantInfo(final Result<TenantInfoEntity> tenantresult) {
        TenantController.getShared(context).getTenantInfo(baseurl, tenantresult);
    }

    // -----------------------------------------------------***** CLIENT INFO *****-------------------------------------------------------------------------

    //ClientId With RequestId
    @Override
    public void getClientInfo(final String requestId, final Result<ClientInfoEntity> clientInfoEntityResult) {
        ClientController.getShared(context).getClientInfo(requestId, clientInfoEntityResult);
    }

    // -----------------------------------------------------***** LOGIN WITH CREDENTIALS *****---------------------------------------------------------------
    @Override
    public void loginWithCredentials(final String requestId, final LoginEntity loginEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        LoginController.getShared(context).loginwithCredentials(requestId, loginEntity, loginresult);
    }

    // -----------------------------------------------------***** CONSENT MANAGEMENT *****---------------------------------------------------------------
    @Override
    public void getConsentDetails(@NonNull final String consentName, final Result<ConsentDetailsResultEntity> consentResult) {
        ConsentController.getShared(context).getConsentDetails(consentName, consentResult);
    }

    @Override
    public void loginAfterConsent(@NonNull final ConsentEntity consentEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        ConsentController.getShared(context).acceptConsent(consentEntity,loginresult);
    }

    // -----------------------------------------------------***** GET MFA LIST *****---------------------------------------------------------------

    @Override
    public void getMFAList(final String sub, final Result<MFAListResponseEntity> mfaresult) {
        VerificationSettingsController.getShared(context).getmfaList(sub, mfaresult);
    }

    // -----------------------------------------------------***** PASSWORD LESS LOGIN AND MFA SERVICE CALL *****---------------------------------------------------------------


    // ****** DONE LOGIN WITH EMAIL AND VERIFY EMAIL *****----------------------------------------------------------------------


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

    //---------------------------------------DELETE CALL-------------------------------------------------------------------------------------------------
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
    }

    // ****** LOGIN WITH Document *****-------------------------------------------------------------------------------------------------------
    public void VerifyDocument(final File photo, final String sub, final Result<DocumentScannerServiceResultEntity> resultEntityResult) {
        DocumentScannnerController.getShared(context).sendtoServicecall( photo, sub,resultEntityResult);
    }


    // ****** GET REGISTERATION *****-------------------------------------------------------------------------------------------------------
    @Override
    public void getRegistrationFields(@NonNull final String requestId, final String locale, final Result<RegistrationSetupResponseEntity> registerFieldsresult) {
        RegistrationController.getShared(context).getRegisterationFields(requestId, locale,registerFieldsresult);
    }

    @Override
    public void registerUser(@NonNull final String requestId, final RegistrationEntity registrationEntity,
                             final Result<RegisterNewUserResponseEntity> registerFieldsresult) {
        RegistrationController.getShared(context).registerNewUser(requestId, registrationEntity,registerFieldsresult);
    }

    @Override
    public void initiateEmailVerification(@NonNull final String sub, @NonNull final String requestId,
                                          final Result<RegisterUserAccountInitiateResponseEntity> Result) {
        RegistrationController.getShared(context).initiateAccountVerificationService(sub, requestId,"email", Result);
    }

    @Override
    public void initiateSMSVerification(@NonNull final String sub,@NonNull final String requestId,
                                        final Result<RegisterUserAccountInitiateResponseEntity> Result) {
        RegistrationController.getShared(context).initiateAccountVerificationService(sub, requestId,"sms", Result);
    }

    @Override
    public void initiateIVRVerification(@NonNull final String sub, @NonNull final String requestId, final Result<RegisterUserAccountInitiateResponseEntity> Result) {
        RegistrationController.getShared(context).initiateAccountVerificationService(sub, requestId,"ivr", Result);
    }


    @Override
    public void verifyAccount(@NonNull final String code, @NonNull final String accvid, final Result<RegisterUserAccountVerifyResponseEntity> result) {
        RegistrationController.getShared(context).verifyAccountVerificationService(code, accvid, result);
    }


    //----------------------------------DEDEUPLICATION------------------------------------------------------------------------------------------------------

    @Override
    public void getDeduplicationDetails(@NonNull final String trackId, final Result<DeduplicationResponseEntity> deduplicaionResult) {
        DeduplicationController.getShared(context).getDeduplicationList(trackId, deduplicaionResult);
    }

    @Override
    public void registerUser(@NonNull final String trackId, final Result<RegisterDeduplicationEntity> deduplicaionResult) {
        DeduplicationController.getShared(context).registerDeduplication(baseurl, trackId, deduplicaionResult);
    }

    @Override
    public void loginWithDeduplication(final String requestId, @NonNull final String sub, @NonNull final String password,
                                       final Result<LoginCredentialsResponseEntity> deduplicaionResult) {
        DeduplicationController.getShared(context).loginDeduplication( requestId, sub, password, deduplicaionResult);

    }

    // Todo change
    @Override
    public void initiateResetPasswordByEmail(final String requestId, final String email,
                                             final Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult) {
        ResetPasswordController.getShared(context).initiateresetPasswordService(requestId, email, "email",resetPasswordResponseEntityResult);


    }

    @Override
    public void initiateResetPasswordBySMS(final String requestId, final String mobileNumber,
                                           final Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult) {
        ResetPasswordController.getShared(context).initiateresetPasswordService(requestId, mobileNumber, "sms",resetPasswordResponseEntityResult);
    }

    @Override
    public void handleResetPassword(@NonNull final String verificationCode, final String rprq,
                                    final Result<ResetPasswordValidateCodeResponseEntity> resetpasswordResult) {
        ResetPasswordController.getShared(context).resetPasswordValidateCode(verificationCode, rprq, resetpasswordResult);
    }

    //Todo Change to entity
    @Override
    public void resetPassword(@NonNull final ResetPasswordEntity resetPasswordEntity,
                              final Result<ResetNewPasswordResponseEntity> resetpasswordResult) {
        ResetPasswordController.getShared(context).resetNewPassword(resetPasswordEntity, resetpasswordResult);

    }

    //----------------------------------------------------------------------------------------------------------------------------------------

    //Todo change to Sub and Identity id
    public void changePassword(String sub, final ChangePasswordRequestEntity changePasswordRequestEntity, final Result<ChangePasswordResponseEntity> result) {
        ChangePasswordController.getShared(context).changePassword(changePasswordRequestEntity, result);
    }

    @Override
    public void getAccessToken(String sub, Result<AccessTokenEntity> result) {
        AccessTokenController.getShared(context).getAccessToken(sub, result);
    }

    //Get userinfo Based on Access Token
    @Override
    public void getUserInfo(String sub, final Result<UserinfoEntity> callback) {
        UserProfileController.getShared(context).getUserProfile(sub,callback);
    }

    @Override
    public void renewToken(String refershtoken, Result<AccessTokenEntity> result) {
        //TODO
    }

    public void loginWithBrowser(@NonNull final Context activityContext, @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
       LoginController.getShared(context).loginWithBrowser(activityContext,color,callbacktoMain);
    }

    public void loginWithSocial(@NonNull final Context activityContext,@NonNull final String requestId, @NonNull final String provider,
                                @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
        LoginController.getShared(context).loginWithSocial(activityContext,requestId,provider,color,callbacktoMain);
    }

    //Get Social Login URL
    public void getSocialLoginURL(final String requestId, final String provider, final Result<String> callback) {
        LoginController.getShared(context).getSocialLoginURL(provider,requestId, callback);
    }


    //get loginurl by passing Domain URl.......as Arguments

    public void getLoginURL(@NonNull String DomainUrl, @NonNull String ClientId, @NonNull String RedirectURL,
                            @NonNull String ClientSecret, final Result<String> callback) {
        try {
            //Get Request ID
            if ( DomainUrl != null && !Objects.equals(DomainUrl, "")
                    && RedirectURL != null && !Objects.equals(RedirectURL, "")) {
                getRequestId(DomainUrl, ClientId, RedirectURL, ClientSecret, new Result<AuthRequestResponseEntity>() {
                    @Override
                    public void success(AuthRequestResponseEntity result) {
                        // if request ID is valid we call get LoginURl
                        //getLoginURL(result.getData().getRequestId(), callback);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        callback.failure(error);
                    }
                });
            }
        } catch (Exception ex) {
            Timber.d(ex.getMessage());
            //Todo Handle Exception
        }
    }

    //Resume After open App From Broswer
    public void handleToken(String code){   /*,Result<AccessTokenEntity> callbacktoMain*/

       LoginController.getShared(context).handleToken(code);

    }

    public void setURL(@NonNull final Dictionary<String, String> loginproperties)
    {
        LoginController.getShared(context).setURL(loginproperties);
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
    public void localAuthentication(final Activity activity, Result<LocalAuthenticationEntity> result) {
        LocalAuthenticationController.getShared(context).localAuthentication(activity,result);
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



    public void setAccessToken(final AccessTokenEntity accessTokenEntity, final Result<LoginCredentialsResponseEntity> result){
      AccessTokenController.getShared(context).setAccessToken(accessTokenEntity,result);
    }

    //----------------------------------LocationHistory------------------------------------------------------------------------------------------------------
   //Todo Add Logs
    public void getUserLoginInfo(final UserLoginInfoEntity userLoginInfoEntity, final Result<UserLoginInfoResponseEntity> result)
    {
        UserLoginInfoController.getShared(context).getUserLoginInfo(userLoginInfoEntity,result);
    }

    public void updateFCMToken(@NonNull final String sub,@NonNull final String FCMToken)
    {
        VerificationSettingsController.getShared(context).updateFCMToken(sub ,FCMToken, new Result<Object>() {
            @Override
            public void success(Object result) {
                LogFile.getShared(context).addFailureLog("Update FCM Token Success");
            }

            @Override
            public void failure(WebAuthError error) {
                LogFile.getShared(context).addFailureLog("Update FCM Token Error" + error.getMessage());
            }
        });
    }

//------------------------------------------Methods without requestID and other Suppoertive methods-------------------------------------------------------------------------------
//Get Request Id By passing loginProperties as an Object
    // 1. Read properties from file
    // 2. Call request id from dictionary method
    // 3. Maintain logs based on flags

    // -----------------------------------------------------***** REQUEST ID *****---------------------------------------------------------------

    //Get Request Id By Passing loginProperties as Value in parameters with Client Secret
    @Override
    public void getRequestId(@NonNull String DomainUrl, @NonNull String ClientId, @NonNull String RedirectURL, @NonNull String ClientSecret,
                             final Result<AuthRequestResponseEntity> result)
    {
        try {
            FileHelper.getShared(context).paramsToDictionaryConverter(DomainUrl, ClientId, RedirectURL, ClientSecret, new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginproperties) {

                    getRequestId(loginproperties, result,extraParams);
                }

                @Override
                public void failure(WebAuthError error) { result.failure(error); }
            });

        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getRequestId()",WebAuthErrorCode.READ_PROPERTIES_ERROR,e.getMessage()));
        }
    }


    //Get Request Id By Passing loginProperties as Value in parameters

    public void getRequestId(@NonNull final String DomainUrl, @NonNull String ClientId, @NonNull String RedirectURL,
                             final Result<AuthRequestResponseEntity> Primaryresult,final HashMap<String, String>... extraParams) {
        try {
            FileHelper.getShared(context).paramsToDictionaryConverter(DomainUrl, ClientId, RedirectURL, new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesresult) {
                    getRequestId(loginPropertiesresult, Primaryresult,extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    Primaryresult.failure(error);
                }
            });

        } catch (Exception e) {
          Primaryresult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getRequestId()",WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,e.getMessage()));
        }
    }

    //Get Request Id without passing any value

    @Override
    public void getRequestId( final Result<AuthRequestResponseEntity> resulttoReturn,@Nullable final HashMap<String, String>... extraParams) {
        try {


            CidaasProperties.getShared(context).saveCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {

                    getRequestId(loginPropertiesResult,resulttoReturn);
                }

                @Override
                public void failure(WebAuthError error) {
                    resulttoReturn.failure(error);
                }
            });

        } catch (Exception e) {
            resulttoReturn.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getRequestId()",WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,e.getMessage()));
        }
    }


   // -----------------------------------------------------------*******clientID****---------------------------------------
    //Client Id With out Passing RequestId
    public void getClientInfo(final Result<ClientInfoEntity> clientInfoEntityResult,final HashMap<String,String>... extraParams)
    {
        try
        {
            getRequestId( new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {
                    getClientInfo(result.getData().getRequestId(),clientInfoEntityResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    clientInfoEntityResult.failure(error);
                }
            },extraParams);
        }
        catch (Exception e)
        {
            clientInfoEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getClientInfo()",WebAuthErrorCode.CLIENT_INFO_FAILURE,e.getMessage()));
        }
    }


    public void loginWithCredentials(final LoginEntity loginEntity,
                                     final Result<LoginCredentialsResponseEntity> loginresult,final HashMap<String,String>... extraParams)
    {
        try
        {
            getRequestId(new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {
                    loginWithCredentials(result.getData().getRequestId(),loginEntity,loginresult);
                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(error);
                }
            },extraParams);
        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :loginWithCredentials()",WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,e.getMessage()));
        }
    }


    public void getRegistrationFields( final String locale, final Result<RegistrationSetupResponseEntity> registerFieldsresult,
                                       final HashMap<String,String>... extraParams)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            getRegistrationFields(result.getData().getRequestId(),locale,registerFieldsresult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            registerFieldsresult.failure(error);
                        }
                    },extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    registerFieldsresult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            registerFieldsresult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :getRegistrationFields()",WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,e.getMessage()));
        }
    }

    // ****** DONE Verification *****-------------------------------------------------------------------------------------------------------


    public void scanned(@NonNull final String statusId, @NonNull final String sub,final String secret, @NonNull final String verificationType, final Result<ScannedResponseEntity> scannedResult)
    {
        if(verificationType.equalsIgnoreCase("PATTERN"))
        {
            scannedPattern(statusId,scannedResult);
        }
        else if(verificationType.equalsIgnoreCase("TOUCHID"))
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
        else if(verificationType.equalsIgnoreCase("PUSH"))
        {
            scannedSmartPush(statusId,scannedResult);
        }
        else if(verificationType.equalsIgnoreCase("FIDOU2F"))
        {
            scannedFIDO(statusId,scannedResult);
        }

    }

    public void setupv2(@NonNull final SetupEntity setupEntity, final Result<SetupResponse> setupResponseResult)
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


    public void registerUser( final RegistrationEntity registrationEntity, final Result<RegisterNewUserResponseEntity> registerFieldsresult,
                              final HashMap<String,String>... extraParams)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            registerUser(result.getData().getRequestId(),registrationEntity,registerFieldsresult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            registerFieldsresult.failure(error);
                        }
                    },extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    registerFieldsresult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            registerFieldsresult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas : registerUser()",WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,e.getMessage()));
        }
    }

    public void initiateEmailVerification( @NonNull final String sub,  final Result<RegisterUserAccountInitiateResponseEntity> Result,
                                           final HashMap<String,String>... extraParams)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateEmailVerification(sub,result.getData().getRequestId(),Result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Result.failure(error);
                        }
                    },extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    Result.failure(error);
                }
            });
        }
        catch (Exception e)
        {
              Result.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :initiateEmailVerification()",WebAuthErrorCode.INITIATE_EMAIL_MFA_FAILURE,e.getMessage()));
        }
    }

    public void initiateSMSVerification( @NonNull final String sub,  final Result<RegisterUserAccountInitiateResponseEntity> Result,
                                         final HashMap<String,String>... extraParams) {
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateSMSVerification(sub, result.getData().getRequestId(), Result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            Result.failure(error);
                        }
                    }, extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    Result.failure(error);
                }
            });
        } catch (Exception e) {
            Result.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :initiateSMSVerification()",WebAuthErrorCode.INITIATE_SMS_MFA_FAILURE,e.getMessage()));
        }
    }

    public void initiateIVRVerification( @NonNull final String sub,  final Result<RegisterUserAccountInitiateResponseEntity> Result,
        final HashMap<String,String>... extraParams)
        {
            try
            {
                CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginProperties) {
                        getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                            @Override
                            public void success(AuthRequestResponseEntity result) {
                                initiateIVRVerification(sub,result.getData().getRequestId(),Result);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                Result.failure(error);
                            }
                        },extraParams);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        Result.failure(error);
                    }
                });
            }
            catch (Exception e)
            {
                Result.failure(WebAuthError.getShared(context).methodException("initiateIVRVerification",WebAuthErrorCode.INITIATE_IVR_MFA_FAILURE,e.getMessage()));
            }
        }

    public void loginWithDeduplication(@NonNull final String sub, @NonNull final String password,
                                       final Result<LoginCredentialsResponseEntity> loginresult,final HashMap<String,String>... extraParams)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            loginWithDeduplication(result.getData().getRequestId(),sub,password,loginresult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            loginresult.failure(error);
                        }
                    },extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty","Error :Cidaas :loginWithDeduplication()"));
                }
            });
        }
        catch (Exception e)
        {

        }
    }

    //----------------------------------------------------------------------------------------------------------------------------------------
    public void initiateResetPasswordByEmail(final String email,
                                             final Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult,final HashMap<String,String>... extraParams)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateResetPasswordByEmail(result.getData().getRequestId(),email,resetPasswordResponseEntityResult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            resetPasswordResponseEntityResult.failure(error);
                        }
                    },extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty","Error :Cidaas :initiateResetPasswordByEmail()"));
                }
            });
        }
        catch (Exception e)
        {
            resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :initiateResetPasswordByEmail()",WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE,e.getMessage()));
        }
    }

    public void initiateResetPasswordBySMS(final String mobileNumber,
                                           final Result<ResetPasswordResponseEntity> resetPasswordResponseEntityResult,final HashMap<String,String>... extraParams)
    {
        try
        {
            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginProperties) {
                    getRequestId(loginProperties, new Result<AuthRequestResponseEntity>() {
                        @Override
                        public void success(AuthRequestResponseEntity result) {
                            initiateResetPasswordBySMS(result.getData().getRequestId(),mobileNumber,resetPasswordResponseEntityResult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            resetPasswordResponseEntityResult.failure(error);
                        }
                    },extraParams);
                }

                @Override
                public void failure(WebAuthError error) {
                    resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or ClientId or RedirectURL must not be empty","Exception :Cidaas :initiateResetPasswordBySMS()"));
                }
            });
        }
        catch (Exception e)
        {
            resetPasswordResponseEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :Cidaas :initiateResetPasswordBySMS()",WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE,e.getMessage()));
        }
    }

    public void getAccessTokenBySocial(final SocialAccessTokenEntity socialAccessTokenEntity, final Result<AccessTokenEntity> accessTokenCallback,
                                       final HashMap<String,String>... extraParams)
    {
        try
        {
           // Cidaas.baseurl=socialAccessTokenEntity.getDomainURL();

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
    }

}