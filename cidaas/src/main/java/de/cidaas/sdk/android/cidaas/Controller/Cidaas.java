package de.cidaas.sdk.android.cidaas.Controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cidaasv2.BuildConfig;

import java.io.File;
import java.util.HashMap;

import de.cidaas.sdk.android.cidaas.Controller.Repository.AccessToken.AccessTokenController;
import de.cidaas.sdk.android.cidaas.Controller.Repository.DocumentScanner.DocumentScannnerController;
import de.cidaas.sdk.android.cidaas.Controller.Repository.LocalAuthentication.LocalAuthenticationController;
import de.cidaas.sdk.android.cidaas.Controller.Repository.Login.LoginController;
import de.cidaas.sdk.android.cidaas.Controller.Repository.UserLoginInfo.UserLoginInfoController;
import de.cidaas.sdk.android.cidaas.Controller.Repository.UserProfile.UserProfileController;
import de.cidaas.sdk.android.cidaas.Helper.Entity.LocalAuthenticationEntity;
import de.cidaas.sdk.android.cidaas.Helper.Entity.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.cidaas.Helper.Entity.SocialAccessTokenEntity;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Helper.Genral.CidaasHelper;
import de.cidaas.sdk.android.cidaas.Helper.Genral.DBHelper;
import de.cidaas.sdk.android.cidaas.Helper.Loaders.ICustomLoader;
import de.cidaas.sdk.android.cidaas.Library.BiometricAuthentication.BiometricCallback;
import de.cidaas.sdk.android.cidaas.Library.BiometricAuthentication.BiometricEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.AccessToken.AccessTokenEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.DocumentScanner.DocumentScannerServiceResultEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.UserLoginInfo.UserLoginInfoEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.UserLoginInfo.UserLoginInfoResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.UserinfoEntity;

/*import com.example.cidaasv2.Interface.IOAuthWebLogin;*/

/**
 * Created by widasrnarayanan on 16/1/18.
 */
/*
implements IOAuthWebLogin*/

public class Cidaas {

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

    //saved Properties for Global Access
    //  Dictionary<String, String> savedProperties;

    //To check the Loader
    private boolean displayLoader = false;

    // Confirm it must be a static one
    //Extra parameter that is passed in URL
    public static HashMap<String, String> extraParams = new HashMap<>();

    //Create a Shared Instance
    private static Cidaas cidaasInstance;

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

    public void setENABLE_PKCE(boolean ENABLE_PKCE) {
        CidaasHelper.getShared(context).setENABLE_PKCE(ENABLE_PKCE);
    }

    //enableLog

    public boolean isLogEnable() {
        return CidaasHelper.getShared(context).isLogEnable();
    }

    public String enableLog() {
        return CidaasHelper.getShared(context).enableLog();
    }

    public String disableLog() {
        return CidaasHelper.getShared(context).disableLog();
    }

    // ****** LOGIN WITH Document *****-------------------------------------------------------------------------------------------------------
    public void VerifyDocument(final File photo, final String sub, final Result<DocumentScannerServiceResultEntity> resultEntityResult) {
        DocumentScannnerController.getShared(context).sendtoServicecall(photo, sub, resultEntityResult);
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

    //For Authenticator App
    public void setAccessToken(final AccessTokenEntity accessTokenEntity, final Result<LoginCredentialsResponseEntity> result) {
        AccessTokenController.getShared(context).setAccessToken(accessTokenEntity, result);
    }

    //Get userinfo Based on Access Token
    // @Override
    public void getUserInfo(String sub, final Result<UserinfoEntity> callback) {
        UserProfileController.getShared(context).getUserProfile(sub, callback);
    }

    //Resume After open App From Broswer
    public void handleToken(String code) {   /*,Result<AccessTokenEntity> callbacktoMain*/

        LoginController.getShared(context).handleToken(code);
    }

    // Custom Tab
    public void loginWithBrowser(@NonNull final Context activityContext, @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
        LoginController.getShared(context).loginWithBrowser(activityContext, color, callbacktoMain);
    }

    //Get Login URL
    public void getLoginURL(final Result<String> callback) {
        LoginController.getShared(context).getLoginURL(callback);
    }

    //Get Registration URL
    public void getRegistrationURL(final Result<String> callback) {
        LoginController.getShared(context).getRegistrationURL(callback);
    }

    // Custom Tab
    public void RegisterWithBrowser(@NonNull final Context activityContext, @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
        LoginController.getShared(context).registerWithBrowser(activityContext, color, callbacktoMain);
    }
    //------------------------------------------------------------------------------------------Local Authentication----------------------------------------

    //Cidaas Set OnActivity Result For Handling Device Authentication
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LocalAuthenticationController.getShared(context).onActivityResult(requestCode, resultCode, data);
    }

    //Show the Alert Dilog Which is go to settings
    private void showDialogToSetupLock(final Activity activity, Result<LocalAuthenticationEntity> result) {
        LocalAuthenticationController.getShared(context).showDialogToSetupLock(activity, result);
    }

    //Method for Local Authentocation

    public void localAuthentication(final Activity activity, Result<LocalAuthenticationEntity> result) {
        LocalAuthenticationController.getShared(context).localAuthentication(activity, result);
    }


    //Method for Local Biometric Authentocation
    @TargetApi(Build.VERSION_CODES.P)
    public void localBiometricAuthentication(final BiometricEntity biometricBuilder, BiometricCallback callback) {
        LocalAuthenticationController.getShared(context).localBiometricAuthenticate(biometricBuilder, callback);
    }

    //------------------------------------------------------------------------------------------XXXXXXX----------------------------------------

    public static String getSDKVersion() {
        String version = "";
        try {

            version = "(" + BuildConfig.VERSION_NAME + ")";
        } catch (Exception e) {
            return "";
        }

        return version;
    }

    public String getUserAgent() {
        return DBHelper.getShared().getUserAgent();
    }


    //----------------------------------LocationHistory------------------------------------------------------------------------------------------------------
    // Add Logs
    public void getUserLoginInfo(final UserLoginInfoEntity userLoginInfoEntity, final Result<UserLoginInfoResponseEntity> result) {
        UserLoginInfoController.getShared(context).getUserLoginInfo(userLoginInfoEntity, result);
    }

    // Ask Ganehs
    public void loginWithSocial(@NonNull final Context activityContext, @NonNull final String requestId, @NonNull final String provider,
                                @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
        LoginController.getShared(context).loginWithSocial(activityContext, requestId, provider, color, callbacktoMain);
    }

    //Get Social Login URL
    public void getSocialLoginURL(final String requestId, final String provider, final Result<String> callback) {
        LoginController.getShared(context).getSocialLoginURL(provider, requestId, callback);
    }

}

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