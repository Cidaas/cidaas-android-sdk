package com.example.cidaasv2.VerificationV2.presentation.View;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPConfigurationController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.CidaasHelper;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory.AuthenticatedHistoryEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory.AuthenticatedHistoryResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Delete.DeleteEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Delete.DeleteResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.EndUser.LoginRequest.LoginRequest;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Initiate.InitiateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Initiate.InitiateResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAcknowledge.PushAcknowledgeEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAcknowledge.PushAcknowledgeResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAllow.PushAllowEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushAllow.PushAllowResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushReject.PushRejectEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushReject.PushRejectResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.ConfiguredMFAList.ConfiguredMFAList;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.PendingNotification.PendingNotificationResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;
import com.example.cidaasv2.VerificationV2.domain.Controller.AuthenticationFlow.Authenticate.AuthenticateController;
import com.example.cidaasv2.VerificationV2.domain.Controller.AuthenticateHistory.AuthenticatedHistoryController;
import com.example.cidaasv2.VerificationV2.data.Entity.EndUser.ConfigureRequest.ConfigurationRequest;
import com.example.cidaasv2.VerificationV2.domain.Controller.AuthenticationFlow.Initiate.InitiateController;
import com.example.cidaasv2.VerificationV2.domain.Controller.AuthenticationFlow.Login.PasswordlessLoginController;
import com.example.cidaasv2.VerificationV2.domain.Controller.ConfigrationFlow.Configuration.ConfigurationController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Delete.DeleteController;
import com.example.cidaasv2.VerificationV2.domain.Controller.ConfigrationFlow.Enroll.EnrollController;
import com.example.cidaasv2.VerificationV2.domain.Controller.PendingNotification.PendingNotificationController;
import com.example.cidaasv2.VerificationV2.domain.Controller.AuthenticationFlow.Push.PushAcknowledge.PushAcknowledgeController;
import com.example.cidaasv2.VerificationV2.domain.Controller.AuthenticationFlow.Push.PushAllow.PushAllowController;
import com.example.cidaasv2.VerificationV2.domain.Controller.AuthenticationFlow.Push.PushReject.PushRejectController;
import com.example.cidaasv2.VerificationV2.domain.Controller.ConfigrationFlow.Scanned.ScannedController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Settings.SettingsController;

import java.util.Dictionary;

public class CidaasVerification {

    private  Context context;
    public  static CidaasVerification cidaasverificationInstance;

    public static CidaasVerification getInstance(Context YourActivitycontext) {
        if (cidaasverificationInstance == null) {
            cidaasverificationInstance = new CidaasVerification(YourActivitycontext);
        }

        return cidaasverificationInstance;
    }

    public CidaasVerification(Context yourActivityContext) {
        this.context = yourActivityContext;
        CidaasHelper.getShared(yourActivityContext).initialiseObject();

        //1.Initialise DB ie shared preference
        //2.Enable log
        //3.add device info and FCM token
        //4.Set Base url

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


    //-----------------------------------------END_OF_Common For Cidaas Instances-------------------------------------------------------------------------


    //------------------------------------------CALL FOR AUTHENTICATOR AND WEB TO MOBILE FLOW ONLY----------------------------------------------------------------------


    //-------------------------------------------------------SCANNED CALL COMMON--------------------------------------------------------------
    public void scanned(ScannedEntity scannedEntity, Result<ScannedResponse> scannedResult)
    {
        ScannedController.getShared(context).scannedVerification(scannedEntity,scannedResult);
    }

    //-------------------------------------------------------ENROLL CALL COMMON--------------------------------------------------------------

    public void enroll(@NonNull final EnrollEntity enrollEntity, final Result<EnrollResponse> enrollResponseResult)
    {
        EnrollController.getShared(context).enrollVerification(enrollEntity,enrollResponseResult);
    }

    //-------------------------------------------------------SMARTPUSH  CALL COMMON--------------------------------------------------------------

                    //---------------------Acknowledge-------------------------
    public void pushAcknowledge(PushAcknowledgeEntity pushAcknowledgeEntity, Result<PushAcknowledgeResponse> pushAcknowledgeResult)
    {
        PushAcknowledgeController.getShared(context).pushAcknowledgeVerification(pushAcknowledgeEntity,pushAcknowledgeResult);
    }
                    //---------------------Allow-------------------------
    public void pushAllow(PushAllowEntity pushAllowEntity, Result<PushAllowResponse> pushAllowResponseResult)
    {
        PushAllowController.getShared(context).pushAllowVerification(pushAllowEntity,pushAllowResponseResult);
    }

                    //---------------------Reject-------------------------
    public void pushReject(PushRejectEntity pushRejectEntity, Result<PushRejectResponse> pushRejectResponseResult)
    {
        PushRejectController.getShared(context).pushRejectVerification(pushRejectEntity,pushRejectResponseResult);
    }



    //-------------------------------------------------------AUTHENTICATE CALL COMMON--------------------------------------------------------------
    public void authenticate(AuthenticateEntity authenticateEntity, Result<AuthenticateResponse> authenticateResponseResult)
    {
        AuthenticateController.getShared(context).authenticateVerification(authenticateEntity,authenticateResponseResult);
    }

    //-------------------------------------------------------PENDING NOTIFICATION LIST CALL --------------------------------------------------------------
    public void getPendingNotificationList(String sub,Result<PendingNotificationResponse> pendingNotificationResponse)
    {
        PendingNotificationController.getShared(context).getPendingNotification(sub,pendingNotificationResponse);
    }

    //-------------------------------------------------------SETURL CALL COMMON--------------------------------------------------------------

    //FOR MULTIPLE TENANT AND MULTIPLE USER(ONLY FOR AUTHENTICATOR)
    public void setURL(@NonNull final Dictionary<String, String> loginproperties, Result<String> result,String methodName)
    {
        LoginController.getShared(context).setURL(loginproperties,result,methodName);
    }

    //------------------------------------------CALL FOR BOTH SDK AND AUTHENTICATOR----------------------------------------------------------------------


    //-------------------------------------------------------DELETE CALL COMMON--------------------------------------------------------------
    //warning IF TOTP IS DELETED IT MUST BE DELETED FROM BY WEBPAGE ONLY
    public void delete(DeleteEntity deleteEntity, Result<DeleteResponse> deleteResponseResult)
    {
        DeleteController.getShared(context).deleteVerification(deleteEntity,deleteResponseResult);
    }

    //---------------------DELETE ALL CALL ------------------------------
    public void deleteAll(Result<DeleteResponse> deleteResponseResult)
    {
        DeleteController.getShared(context).deleteAllVerification(deleteResponseResult);
    }

    //--------------------------------------------CONFIGURED MFA LIST CALL --------------------------------------------------------------
    public void getConfiguredMFAList(String sub,Result<ConfiguredMFAList> configuredMFAListResult)
    {
        SettingsController.getShared(context).getConfiguredMFAList(sub,configuredMFAListResult);
    }

    //-------------------------------------------------------AUTHENTICATED HISTORY CALL--------------------------------------------------------------
    public void getAuthenticatedHistory(AuthenticatedHistoryEntity authenticatedHistoryEntity,
                                        Result<AuthenticatedHistoryResponse> authenticatedHistoryResult)
    {
        AuthenticatedHistoryController.getShared(context).getauthenticatedHistoryList(authenticatedHistoryEntity,authenticatedHistoryResult);
    }

    //-------------------------------------------------------UPDATE FCMTOKEN CALL--------------------------------------------------------------
    public void updateFCMToken(String FCMToken)
    {
        SettingsController.getShared(context).updateFCMToken(FCMToken);
    }



    //------------------------------------------CALL FOR SDK ONLY----------------------------------------------------------------------

    //------------------------------------------SETUP CALL--------------------------------------------------------------

    //EMAIL
    public void setupEmail(String sub, Result<SetupResponse> setupResponseResult)
    {
        SetupEntity setupEntity=new SetupEntity(sub,AuthenticationType.EMAIL);
        ConfigurationController.getShared(context).setup(setupEntity,setupResponseResult);
    }

    //SMS
    public void setupSMS(String sub, Result<SetupResponse> setupResponseResult)
    {
        SetupEntity setupEntity=new SetupEntity(sub,AuthenticationType.SMS);
        ConfigurationController.getShared(context).setup(setupEntity,setupResponseResult);
    }

    //IVR
    public void setupIVR(String sub, Result<SetupResponse> setupResponseResult)
    {
        SetupEntity setupEntity=new SetupEntity(sub,AuthenticationType.IVR);
        ConfigurationController.getShared(context).setup(setupEntity,setupResponseResult);
    }

    //BackupCode
    public void setupBackupCode(String sub, Result<SetupResponse> setupResponseResult)
    {
        SetupEntity setupEntity=new SetupEntity(sub,AuthenticationType.BACKUPCODE);
        ConfigurationController.getShared(context).setup(setupEntity,setupResponseResult);
    }


    //Enroll Email
    public void enrollEmail(String verificationCode,String sub,String exchange_id, final Result<EnrollResponse> enrollResponseResult)
    {
        EnrollEntity enrollEntity=new EnrollEntity();
        enrollEntity.setExchange_id(exchange_id);
        enrollEntity.setSub(sub);
        enrollEntity.setVerificationType(AuthenticationType.EMAIL);
        enrollEntity.setPass_code(verificationCode);

        enroll(enrollEntity,enrollResponseResult);
    }

    //Enroll SMS
    public void enrollSMS(String verificationCode,String sub,String exchange_id,final Result<EnrollResponse> enrollResponseResult)
    {
        EnrollEntity enrollEntity=new EnrollEntity();
        enrollEntity.setExchange_id(exchange_id);
        enrollEntity.setSub(sub);
        enrollEntity.setVerificationType(AuthenticationType.SMS);
        enrollEntity.setPass_code(verificationCode);

        enroll(enrollEntity,enrollResponseResult);
    }

    //Enroll IVR
    public void enrollIVR(String verificationCode,String sub,String exchange_id,final Result<EnrollResponse> enrollResponseResult)
    {
        EnrollEntity enrollEntity=new EnrollEntity();
        enrollEntity.setExchange_id(exchange_id);
        enrollEntity.setSub(sub);
        enrollEntity.setVerificationType(AuthenticationType.IVR);
        enrollEntity.setPass_code(verificationCode);

        enroll(enrollEntity,enrollResponseResult);
    }




    public void configurePattern(final ConfigurationRequest configurationRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configurationRequest,AuthenticationType.PATTERN,enrollResponseResult);
    }

    public void configureSmartPush(final ConfigurationRequest configurationRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configurationRequest,AuthenticationType.SMARTPUSH,enrollResponseResult);
    }

    public void configureFaceRecognition(final ConfigurationRequest configurationRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configurationRequest,AuthenticationType.FACE,enrollResponseResult);
    }

    public void configureVoiceRecognition(final ConfigurationRequest configurationRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configurationRequest,AuthenticationType.VOICE,enrollResponseResult);
    }

    public void configureTOTP(final ConfigurationRequest configurationRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configurationRequest,AuthenticationType.TOTP,enrollResponseResult);
    }

    public void configureFingerprint(final ConfigurationRequest configurationRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configurationRequest,AuthenticationType.FINGERPRINT,enrollResponseResult);
    }



    //-------------------------------------------------------SCANNED CALL COMMON--------------------------------------------------------------
    private void configure(final ConfigurationRequest configurationRequest,final String verificationType,final Result<EnrollResponse> enrollResponseResult)
    {
        ConfigurationController.getShared(context).configureVerification(configurationRequest,verificationType,enrollResponseResult);
    }

    public void loginWithIVR(LoginRequest loginRequest,Result<InitiateResponse> initiateResult)
    {
        InitiateEntity initiateEntity=new InitiateEntity(loginRequest.getSub(),loginRequest.getRequestId(),loginRequest.getUsageType(),
                AuthenticationType.IVR);
        InitiateController.getShared(context).initiateVerification(initiateEntity, initiateResult);
    }

    //
    public void verifyCode(String code,String exchange_id,String requestId,Result<LoginCredentialsResponseEntity> loginResult)
    {
        AuthenticateEntity authenticateEntity=new AuthenticateEntity(exchange_id,code,AuthenticationType.IVR);
       // PasswordlessLoginController.getShared(context).authenticateVerification(authenticateEntity,loginResult);
    }


    public void verifySMS(String code,String exchange_id)
    {

    }


    public void verifyIVR(String code,String exchange_id)
    {


    }

    public void loginWithBackupCode(String sub,String backupCode)
    {
      
    }


    public void loginWithPattern(final LoginRequest loginRequest, final Result<LoginCredentialsResponseEntity> authenticateResponseResult)
    {
        login(loginRequest,AuthenticationType.PATTERN,authenticateResponseResult);
    }

    public void loginWithSmartPush(final LoginRequest loginRequest, final Result<LoginCredentialsResponseEntity> authenticateResponseResult)
    {
        login(loginRequest,AuthenticationType.SMARTPUSH,authenticateResponseResult);
    }

    public void loginWithFaceRecognition(final LoginRequest loginRequest, final Result<LoginCredentialsResponseEntity> authenticateResponseResult)
    {
        login(loginRequest,AuthenticationType.FACE,authenticateResponseResult);
    }

    public void loginWithVoice(final LoginRequest loginRequest, final Result<LoginCredentialsResponseEntity> authenticateResponseResult)
    {
        login(loginRequest,AuthenticationType.VOICE,authenticateResponseResult);
    }

    //  To Handle TOTP
     public void loginWithTOTP(final LoginRequest loginRequest, final Result<LoginCredentialsResponseEntity> authenticateResponseResult)
    {
        login(loginRequest,AuthenticationType.TOTP,authenticateResponseResult);
    }

    public void loginWithFingerprint(final LoginRequest loginRequest, final Result<LoginCredentialsResponseEntity> authenticateResponseResult)
    {
        login(loginRequest,AuthenticationType.FINGERPRINT,authenticateResponseResult);
    }
    //-------------------------------------------------------LOGIN CALL COMMON--------------------------------------------------------------

    private void login(final LoginRequest loginRequest, final String verificationType, final Result<LoginCredentialsResponseEntity> loginCredentialsResult)
    {
        PasswordlessLoginController.getShared(context).loginVerification(loginRequest,verificationType,loginCredentialsResult);
    }

    public void listenTOTP(String sub) {
        TOTPConfigurationController.getShared(context).ListenTOTP(sub);
    }

    public void cancelListenTOTP() {
        TOTPConfigurationController.getShared(context).cancelTOTP();
    }


}
