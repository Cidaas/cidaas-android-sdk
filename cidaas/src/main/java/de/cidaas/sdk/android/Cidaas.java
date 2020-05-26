package de.cidaas.sdk.android;

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

import de.cidaas.sdk.android.controller.AccessTokenController;
import de.cidaas.sdk.android.controller.DocumentScannnerController;
import de.cidaas.sdk.android.controller.LocalAuthenticationController;
import de.cidaas.sdk.android.controller.LoginController;
import de.cidaas.sdk.android.controller.UserLoginInfoController;
import de.cidaas.sdk.android.controller.UserProfileController;
import de.cidaas.sdk.android.entities.LocalAuthenticationEntity;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.entities.SocialAccessTokenEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.loaders.ICustomLoader;
import de.cidaas.sdk.android.library.biometricauthentication.BiometricCallback;
import de.cidaas.sdk.android.library.biometricauthentication.BiometricEntity;
import de.cidaas.sdk.android.service.entity.UserinfoEntity;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;
import de.cidaas.sdk.android.service.entity.documentscanner.DocumentScannerServiceResultEntity;
import de.cidaas.sdk.android.service.entity.userlogininfo.UserLoginInfoEntity;
import de.cidaas.sdk.android.service.entity.userlogininfo.UserLoginInfoResponseEntity;

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


    EventResult<LocalAuthenticationEntity> localAuthenticationEntityCallback;


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
    public void VerifyDocument(final File photo, final String sub, final EventResult<DocumentScannerServiceResultEntity> resultEntityResult) {
        DocumentScannnerController.getShared(context).sendtoServicecall(photo, sub, resultEntityResult);
    }

    //----------------------------------------------------------------------------------------------------------------------------------------

    //@Override
    public void getAccessToken(String sub, EventResult<AccessTokenEntity> result) {
        AccessTokenController.getShared(context).getAccessToken(sub, result);
    }

    public void getAccessTokenFromRefreshToken(String refershtoken, EventResult<AccessTokenEntity> result) {
        AccessTokenController.getShared(context).getAccessToken(refershtoken, result);
    }

    public void getAccessTokenBySocial(SocialAccessTokenEntity socialAccessTokenEntity, EventResult<AccessTokenEntity> result) {
        AccessTokenController.getShared(context).getAccessTokenBySocial(socialAccessTokenEntity, result);
    }

    //For Authenticator App
    public void setAccessToken(final AccessTokenEntity accessTokenEntity, final EventResult<LoginCredentialsResponseEntity> result) {
        AccessTokenController.getShared(context).setAccessToken(accessTokenEntity, result);
    }

    //Get userinfo Based on Access Token
    // @Override
    public void getUserInfo(String sub, final EventResult<UserinfoEntity> callback) {
        UserProfileController.getShared(context).getUserProfile(sub, callback);
    }

    //Resume After open App From Broswer
    public void handleToken(String code) {   /*,Result<AccessTokenEntity> callbacktoMain*/

        LoginController.getShared(context).handleToken(code);
    }

    // Custom Tab
    public void loginWithBrowser(@NonNull final Context activityContext, @Nullable final String color, final EventResult<AccessTokenEntity> callbacktoMain) {
        LoginController.getShared(context).loginWithBrowser(activityContext, color, callbacktoMain);
    }

    //Get Login URL
    public void getLoginURL(final EventResult<String> callback) {
        LoginController.getShared(context).getLoginURL(callback);
    }

    //Get Registration URL
    public void getRegistrationURL(final EventResult<String> callback) {
        LoginController.getShared(context).getRegistrationURL(callback);
    }

    // Custom Tab
    public void RegisterWithBrowser(@NonNull final Context activityContext, @Nullable final String color, final EventResult<AccessTokenEntity> callbacktoMain) {
        LoginController.getShared(context).registerWithBrowser(activityContext, color, callbacktoMain);
    }
    //------------------------------------------------------------------------------------------Local Authentication----------------------------------------

    //Cidaas Set OnActivityEventResult For Handling Device Authentication
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LocalAuthenticationController.getShared(context).onActivityResult(requestCode, resultCode, data);
    }

    //Show the Alert Dilog Which is go to settings
    private void showDialogToSetupLock(final Activity activity, EventResult<LocalAuthenticationEntity> result) {
        LocalAuthenticationController.getShared(context).showDialogToSetupLock(activity, result);
    }

    //Method for Local Authentocation

    public void localAuthentication(final Activity activity, EventResult<LocalAuthenticationEntity> result) {
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
    public void getUserLoginInfo(final UserLoginInfoEntity userLoginInfoEntity, final EventResult<UserLoginInfoResponseEntity> result) {
        UserLoginInfoController.getShared(context).getUserLoginInfo(userLoginInfoEntity, result);
    }

    // Ask Ganehs
    public void loginWithSocial(@NonNull final Context activityContext, @NonNull final String requestId, @NonNull final String provider,
                                @Nullable final String color, final EventResult<AccessTokenEntity> callbacktoMain) {
        LoginController.getShared(context).loginWithSocial(activityContext, requestId, provider, color, callbacktoMain);
    }

    //Get Social Login URL
    public void getSocialLoginURL(final String requestId, final String provider, final EventResult<String> callback) {
        LoginController.getShared(context).getSocialLoginURL(provider, requestId, callback);
    }

}

// -----------------------------------------------------***** GET MFA LIST *****---------------------------------------------------------------
/*
    @Override
    public void getMFAList(final String sub, finalEventResult<MFAListResponseEntity> mfaresult) {
        VerificationSettingsController.getShared(context).getmfaList(sub, mfaresult);
    }


    public void getMFAListByEmail(final String email, finalEventResult<MFAListResponseEntity> mfaresult) {
        VerificationSettingsController.getShared(context).getmfaListByEmail(email, mfaresult);
    }
 */   // -----------------------------------------------------***** PASSWORD LESS LOGIN AND MFA SERVICE CALL *****---------------------------------------------------------------


// ****** DONE LOGIN WITH EMAIL AND VERIFY EMAIL *****----------------------------------------------------------------------

/*

  /*  //---------------------------------------DELETE CALL-------------------------------------------------------------------------------------------------
    //Delete call
    public void deleteVerificationByType(@NonNull final String verificationType, @NonNull final String sub, finalEventResult<DeleteMFAResponseEntity> deleteResult)
    {
        VerificationSettingsController.getShared(context).deleteMFA(verificationType,sub,deleteResult);
    }

    //Delete call
    public void deleteVerificationByDevice( @NonNull final String sub,finalEventResult<DeleteMFAResponseEntity> result)
    {
        VerificationSettingsController.getShared(context).deleteAllMFA(sub,result);
    }

    //Deny Call
    public void denyNotification(@NonNull final String sub, @NonNull final String reason, @NonNull final String statusId, finalEventResult<DenyNotificationResponseEntity> result)
    {
        VerificationSettingsController.getShared(context).denyNotification(sub,reason,statusId, result);
    }

    //Get Pending Notification
    public void getPendingNotificationList(@NonNull final String sub,  finalEventResult<NotificationEntity> result)
    {
        VerificationSettingsController.getShared(context).getPendingNotification(sub, result);
    }

    //Get user List Notification
    public void getConfigurationList(@NonNull final String sub,  finalEventResult<ConfiguredMFAListEntity> result,final String... baseURL)
    {
        VerificationSettingsController.getShared(context).getConfiguredMFAList(sub,result,baseURL);
    }*/