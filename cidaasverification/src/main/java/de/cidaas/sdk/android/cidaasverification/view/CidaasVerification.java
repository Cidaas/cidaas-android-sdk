package de.cidaas.sdk.android.cidaasverification.view;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Dictionary;

import de.cidaas.sdk.android.cidaasverification.data.entity.authenticate.AuthenticateEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.authenticate.AuthenticateResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory.AuthenticatedHistoryEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory.AuthenticatedHistoryResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.delete.DeleteEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.delete.DeleteResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.enduser.configurerequest.ConfigurationRequest;
import de.cidaas.sdk.android.cidaasverification.data.entity.enduser.loginrequest.LoginRequest;
import de.cidaas.sdk.android.cidaasverification.data.entity.enroll.EnrollEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.enroll.EnrollResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.initiate.InitiateEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.initiate.InitiateResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushacknowledge.PushAcknowledgeEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushacknowledge.PushAcknowledgeResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushallow.PushAllowEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushallow.PushAllowResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushreject.PushRejectEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushreject.PushRejectResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.ScannedEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.ScannedResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.settings.configuredmfalist.ConfiguredMFAList;
import de.cidaas.sdk.android.cidaasverification.data.entity.settings.pendingnotification.PendingNotificationResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.setup.SetupEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.setup.SetupResponse;
import de.cidaas.sdk.android.cidaasverification.domain.controller.authenticatehistory.AuthenticatedHistoryController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.authenticationflow.authenticate.AuthenticateController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.authenticationflow.initiate.InitiateController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.authenticationflow.login.PasswordlessLoginController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.authenticationflow.push.pushacknowledge.PushAcknowledgeController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.authenticationflow.push.pushallow.PushAllowController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.authenticationflow.push.pushreject.PushRejectController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.configrationflow.configuration.ConfigurationController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.configrationflow.enroll.EnrollController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.configrationflow.scanned.ScannedController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.delete.DeleteController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.pendingnotification.PendingNotificationController;
import de.cidaas.sdk.android.cidaasverification.domain.controller.settings.SettingsController;
import de.cidaas.sdk.android.controller.LoginController;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.helper.AuthenticationType;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.helper.general.DBHelper;


public class CidaasVerification {

    private Context context;
    public static CidaasVerification cidaasverificationInstance;

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
        //3.add devi0ce info and FCM token
        //4.Set Base url

    }

    //------------------------------------------CALL FOR AUTHENTICATOR AND WEB TO MOBILE FLOW ONLY----------------------------------------------------------------------

    //-------------------------------------------------------SCANNED CALL COMMON--------------------------------------------------------------
    public void scanned(ScannedEntity scannedEntity, EventResult<ScannedResponse> scannedResult) {
        ScannedController.getShared(context).scannedVerification(scannedEntity, scannedResult);
    }

    //-------------------------------------------------------ENROLL CALL COMMON--------------------------------------------------------------

    public void enroll(@NonNull final EnrollEntity enrollEntity, final EventResult<EnrollResponse> enrollResponseResult) {
        EnrollController.getShared(context).enrollVerification(enrollEntity, enrollResponseResult);
    }

    //-------------------------------------------------------SMARTPUSH  CALL COMMON--------------------------------------------------------------

    //---------------------Acknowledge-------------------------
    public void pushAcknowledge(PushAcknowledgeEntity pushAcknowledgeEntity, EventResult<PushAcknowledgeResponse> pushAcknowledgeResult) {
        PushAcknowledgeController.getShared(context).pushAcknowledgeVerification(pushAcknowledgeEntity, pushAcknowledgeResult);
    }

    //---------------------Allow-------------------------
    public void pushAllow(PushAllowEntity pushAllowEntity, EventResult<PushAllowResponse> pushAllowResponseResult) {
        PushAllowController.getShared(context).pushAllowVerification(pushAllowEntity, pushAllowResponseResult);
    }

    //---------------------Reject-------------------------
    public void pushReject(PushRejectEntity pushRejectEntity, EventResult<PushRejectResponse> pushRejectResponseResult) {
        PushRejectController.getShared(context).pushRejectVerification(pushRejectEntity, pushRejectResponseResult);
    }


    //-------------------------------------------------------AUTHENTICATE CALL COMMON--------------------------------------------------------------
    public void authenticate(AuthenticateEntity authenticateEntity, EventResult<AuthenticateResponse> authenticateResponseResult) {
        AuthenticateController.getShared(context).authenticateVerification(authenticateEntity, authenticateResponseResult);
    }

    //-------------------------------------------------------PENDING NOTIFICATION LIST CALL --------------------------------------------------------------
    public void getPendingNotificationList(String sub, EventResult<PendingNotificationResponse> pendingNotificationResponse) {
        PendingNotificationController.getShared(context).getPendingNotification(sub, pendingNotificationResponse);
    }

    //-------------------------------------------------------SETURL CALL COMMON--------------------------------------------------------------

    //FOR MULTIPLE TENANT AND MULTIPLE USER(ONLY FOR AUTHENTICATOR)
    public void setURL(@NonNull final Dictionary<String, String> loginproperties, EventResult<String> result, String methodName) {
        LoginController.getShared(context).setURL(loginproperties, result, methodName);
    }

    //------------------------------------------CALL FOR BOTH SDK AND AUTHENTICATOR----------------------------------------------------------------------


    //-------------------------------------------------------DELETE CALL COMMON--------------------------------------------------------------
    //warning IF TOTP IS DELETED IT MUST BE DELETED FROM BY WEBPAGE ONLY
    public void delete(DeleteEntity deleteEntity, EventResult<DeleteResponse> deleteResponseResult) {
        DeleteController.getShared(context).deleteVerification(deleteEntity, deleteResponseResult);
    }

    //---------------------DELETE ALL CALL ------------------------------
    public void deleteAll(EventResult<DeleteResponse> deleteResponseResult) {
        DeleteController.getShared(context).deleteAllVerification(deleteResponseResult);
    }

    //--------------------------------------------CONFIGURED MFA LIST CALL --------------------------------------------------------------
    public void getConfiguredMFAList(String sub, EventResult<ConfiguredMFAList> configuredMFAListResult) {
        SettingsController.getShared(context).getConfiguredMFAList(sub, configuredMFAListResult);
    }

    //-------------------------------------------------------AUTHENTICATED HISTORY CALL--------------------------------------------------------------
    public void getAuthenticatedHistory(AuthenticatedHistoryEntity authenticatedHistoryEntity,
                                        EventResult<AuthenticatedHistoryResponse> authenticatedHistoryResult) {
        AuthenticatedHistoryController.getShared(context).getauthenticatedHistoryList(authenticatedHistoryEntity, authenticatedHistoryResult);
    }

    //-------------------------------------------------------UPDATE FCMTOKEN CALL--------------------------------------------------------------
    public void updateFCMToken(String FCMToken) {
        SettingsController.getShared(context).updateFCMToken(FCMToken);
    }
    //------------------------------------------CALL FOR SDK ONLY----------------------------------------------------------------------

    //------------------------------------------SETUP CALL--------------------------------------------------------------

    //EMAIL
    public void setupEmail(String sub, EventResult<SetupResponse> setupResponseResult) {
        SetupEntity setupEntity = new SetupEntity(sub, AuthenticationType.EMAIL);
        ConfigurationController.getShared(context).setup(setupEntity, setupResponseResult);
    }

    //SMS
    public void setupSMS(String sub, EventResult<SetupResponse> setupResponseResult) {
        SetupEntity setupEntity = new SetupEntity(sub, AuthenticationType.SMS);
        ConfigurationController.getShared(context).setup(setupEntity, setupResponseResult);
    }

    //IVR
    public void setupIVR(String sub, EventResult<SetupResponse> setupResponseResult) {
        SetupEntity setupEntity = new SetupEntity(sub, AuthenticationType.IVR);
        ConfigurationController.getShared(context).setup(setupEntity, setupResponseResult);
    }

    //BackupCode
    public void setupBackupCode(String sub, EventResult<SetupResponse> setupResponseResult) {
        SetupEntity setupEntity = new SetupEntity(sub, AuthenticationType.BACKUPCODE);
        ConfigurationController.getShared(context).setup(setupEntity, setupResponseResult);
    }


    //Enroll Email
    public void enrollEmail(String verificationCode, String sub, String exchange_id, final EventResult<EnrollResponse> enrollResponseResult) {
        EnrollEntity enrollEntity = new EnrollEntity();
        enrollEntity.setExchange_id(exchange_id);
        enrollEntity.setSub(sub);
        enrollEntity.setVerificationType(AuthenticationType.EMAIL);
        enrollEntity.setPass_code(verificationCode);

        enroll(enrollEntity, enrollResponseResult);
    }

    //Enroll SMS
    public void enrollSMS(String verificationCode, String sub, String exchange_id, final EventResult<EnrollResponse> enrollResponseResult) {
        EnrollEntity enrollEntity = new EnrollEntity();
        enrollEntity.setExchange_id(exchange_id);
        enrollEntity.setSub(sub);
        enrollEntity.setVerificationType(AuthenticationType.SMS);
        enrollEntity.setPass_code(verificationCode);

        enroll(enrollEntity, enrollResponseResult);
    }

    //Enroll IVR
    public void enrollIVR(String verificationCode, String sub, String exchange_id, final EventResult<EnrollResponse> enrollResponseResult) {
        EnrollEntity enrollEntity = new EnrollEntity();
        enrollEntity.setExchange_id(exchange_id);
        enrollEntity.setSub(sub);
        enrollEntity.setVerificationType(AuthenticationType.IVR);
        enrollEntity.setPass_code(verificationCode);

        enroll(enrollEntity, enrollResponseResult);
    }


    public void configurePattern(final ConfigurationRequest configurationRequest, final EventResult<EnrollResponse> enrollResponseResult) {
        configure(configurationRequest, AuthenticationType.PATTERN, enrollResponseResult);
    }

    public void configureSmartPush(final ConfigurationRequest configurationRequest, final EventResult<EnrollResponse> enrollResponseResult) {
        configure(configurationRequest, AuthenticationType.SMARTPUSH, enrollResponseResult);
    }

    public void configureFaceRecognition(final ConfigurationRequest configurationRequest, final EventResult<EnrollResponse> enrollResponseResult) {
        configure(configurationRequest, AuthenticationType.FACE, enrollResponseResult);
    }

    public void configureVoiceRecognition(final ConfigurationRequest configurationRequest, final EventResult<EnrollResponse> enrollResponseResult) {
        configure(configurationRequest, AuthenticationType.VOICE, enrollResponseResult);
    }

    public void configureTOTP(final ConfigurationRequest configurationRequest, final EventResult<EnrollResponse> enrollResponseResult) {
        configure(configurationRequest, AuthenticationType.TOTP, enrollResponseResult);
    }

    public void configureFingerprint(final ConfigurationRequest configurationRequest, final EventResult<EnrollResponse> enrollResponseResult) {
        configure(configurationRequest, AuthenticationType.FINGERPRINT, enrollResponseResult);
    }

    //-------------------------------------------------------SCANNED CALL COMMON--------------------------------------------------------------
    private void configure(final ConfigurationRequest configurationRequest, final String verificationType, final EventResult<EnrollResponse> enrollResponseResult) {
        ConfigurationController.getShared(context).configureVerification(configurationRequest, verificationType, enrollResponseResult);
    }

    public void initiateIVR(LoginRequest loginRequest, EventResult<InitiateResponse> initiateResult) {
        InitiateEntity initiateEntity = new InitiateEntity(loginRequest.getSub(), loginRequest.getRequestId(), loginRequest.getUsageType(),
                AuthenticationType.IVR);
        InitiateController.getShared(context).initiateVerification(initiateEntity, initiateResult);
    }

    public void initiateEmail(LoginRequest loginRequest, EventResult<InitiateResponse> initiateResult) {
        InitiateEntity initiateEntity = new InitiateEntity(loginRequest.getSub(), loginRequest.getRequestId(), loginRequest.getUsageType(),
                AuthenticationType.SMS);
        InitiateController.getShared(context).initiateVerification(initiateEntity, initiateResult);
    }

    public void initiateSMS(LoginRequest loginRequest, EventResult<InitiateResponse> initiateResult) {
        InitiateEntity initiateEntity = new InitiateEntity(loginRequest.getSub(), loginRequest.getRequestId(), loginRequest.getUsageType(),
                AuthenticationType.EMAIL);
        InitiateController.getShared(context).initiateVerification(initiateEntity, initiateResult);
    }

    //Onlu For Native ... Can we
    public void verifyCode(String code, String exchange_id, String verificationType, String requestId, String usageType, EventResult<LoginCredentialsResponseEntity> loginResult) {
        AuthenticateEntity authenticateEntity = new AuthenticateEntity(exchange_id, code, verificationType);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsageType(usageType);
        PasswordlessLoginController.getShared(context).authenticateVerification(authenticateEntity, verificationType, requestId, loginRequest, loginResult);
    }

    public void loginWithPattern(final LoginRequest loginRequest, final EventResult<LoginCredentialsResponseEntity> authenticateResponseResult) {
        login(loginRequest, AuthenticationType.PATTERN, authenticateResponseResult);
    }

    public void loginWithSmartPush(final LoginRequest loginRequest, final EventResult<LoginCredentialsResponseEntity> authenticateResponseResult) {
        login(loginRequest, AuthenticationType.SMARTPUSH, authenticateResponseResult);
    }

    public void loginWithFaceRecognition(final LoginRequest loginRequest, final EventResult<LoginCredentialsResponseEntity> authenticateResponseResult) {
        login(loginRequest, AuthenticationType.FACE, authenticateResponseResult);
    }

    public void loginWithVoice(final LoginRequest loginRequest, final EventResult<LoginCredentialsResponseEntity> authenticateResponseResult) {
        login(loginRequest, AuthenticationType.VOICE, authenticateResponseResult);
    }

    //  To Handle TOTP
    public void loginWithTOTP(final LoginRequest loginRequest, final EventResult<LoginCredentialsResponseEntity> authenticateResponseResult) {
        login(loginRequest, AuthenticationType.TOTP, authenticateResponseResult);
    }

    public void loginWithFingerprint(final LoginRequest loginRequest, final EventResult<LoginCredentialsResponseEntity> authenticateResponseResult) {
        login(loginRequest, AuthenticationType.FINGERPRINT, authenticateResponseResult);
    }
    //-------------------------------------------------------LOGIN CALL COMMON--------------------------------------------------------------

    private void login(final LoginRequest loginRequest, final String verificationType, final EventResult<LoginCredentialsResponseEntity> loginCredentialsResult) {
        PasswordlessLoginController.getShared(context).loginVerification(loginRequest, verificationType, loginCredentialsResult);
    }

    //Set FCM Token For Update
    public void setFCMToken(String FCMToken) {
        //Store Device info for Later Purposes
        DBHelper.getShared().setFCMToken(FCMToken);
    }

}
