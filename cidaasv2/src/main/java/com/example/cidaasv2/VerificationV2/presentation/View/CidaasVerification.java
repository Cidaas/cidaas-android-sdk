package com.example.cidaasv2.VerificationV2.presentation.View;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.CidaasHelper;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
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
import com.example.cidaasv2.VerificationV2.data.Entity.ResumeLogin.ResumeLoginEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Scanned.ScannedResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.ConfiguredMFAList.ConfiguredMFAList;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.PendingNotification.PendingNotificationResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;
import com.example.cidaasv2.VerificationV2.domain.Controller.Authenticate.AuthenticateController;
import com.example.cidaasv2.VerificationV2.domain.Controller.AuthenticateHistory.AuthenticatedHistoryController;
import com.example.cidaasv2.VerificationV2.data.Entity.EndUser.ConfigureRequest.ConfigureRequest;
import com.example.cidaasv2.VerificationV2.domain.Controller.Delete.DeleteController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Enroll.EnrollController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Initiate.InitiateController;
import com.example.cidaasv2.VerificationV2.domain.Controller.PendingNotification.PendingNotificationController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Push.PushAcknowledge.PushAcknowledgeController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Push.PushAllow.PushAllowController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Push.PushReject.PushRejectController;
import com.example.cidaasv2.VerificationV2.domain.Controller.ResumeLogin.ResumeLoginController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Scanned.ScannedController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Settings.SettingsController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Setup.SetupController;

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
    public void getAuthenticatedHistory(AuthenticatedHistoryEntity authenticatedHistoryEntity, Result<AuthenticatedHistoryResponse> authenticatedHistoryResult)
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
        setup(setupEntity,setupResponseResult);
    }

    //SMS
    public void setupSMS(String sub, Result<SetupResponse> setupResponseResult)
    {
        SetupEntity setupEntity=new SetupEntity(sub,AuthenticationType.SMS);
        setup(setupEntity,setupResponseResult);
    }

    //IVR
    public void setupIVR(String sub, Result<SetupResponse> setupResponseResult)
    {
        SetupEntity setupEntity=new SetupEntity(sub,AuthenticationType.IVR);
        setup(setupEntity,setupResponseResult);
    }

    //BackupCode
    public void setupBackupCode(String sub, Result<SetupResponse> setupResponseResult)
    {
        SetupEntity setupEntity=new SetupEntity(sub,AuthenticationType.BACKUPCODE);
        setup(setupEntity,setupResponseResult);
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


    private void setup(SetupEntity setupEntity, Result<SetupResponse> setupResponseResult)
    {
        SetupController.getShared(context).setupVerification(setupEntity,setupResponseResult);
    }


    public void configurePattern(final ConfigureRequest configureRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configureRequest,AuthenticationType.PATTERN,enrollResponseResult);
    }

    public void configureSmartPush(final ConfigureRequest configureRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configureRequest,AuthenticationType.SMARTPUSH,enrollResponseResult);
    }

    public void configureFaceRecognition(final ConfigureRequest configureRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configureRequest,AuthenticationType.FACE,enrollResponseResult);
    }

    public void configureVoiceRecognition(final ConfigureRequest configureRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configureRequest,AuthenticationType.VOICE,enrollResponseResult);
    }

    public void configureTOTP(final ConfigureRequest configureRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configureRequest,AuthenticationType.TOTP,enrollResponseResult);
    }

    public void configureFingerprint(final ConfigureRequest configureRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configureRequest,AuthenticationType.FINGERPRINT,enrollResponseResult);
    }



    //-------------------------------------------------------SCANNED CALL COMMON--------------------------------------------------------------
    private void configure(final ConfigureRequest configureRequest, final String verificationType, final Result<EnrollResponse> enrollResponseResult)
    {
        SetupEntity setupEntity=new SetupEntity(configureRequest.getSub(), verificationType);
        setup(setupEntity, new Result<SetupResponse>() {
            @Override
            public void success(SetupResponse setupResult) {

               ScannedEntity scannedEntity=new ScannedEntity(configureRequest.getSub(),setupResult.getData().getExchange_id().getExchange_id(),
                       verificationType);

                scanned(scannedEntity, new Result<ScannedResponse>() {
                    @Override
                    public void success(ScannedResponse scannedResult) {
                        // handle Enroll entity and call enroll
                        EnrollEntity enrollEntity;

                        switch (verificationType) {
                            case AuthenticationType.FACE:
                                enrollEntity = new EnrollEntity(scannedResult.getData().getExchange_id().getExchange_id(),verificationType,
                                        configureRequest.getFileToSend(),configureRequest.getAttempt());
                                break;

                            case AuthenticationType.VOICE:
                                enrollEntity = new EnrollEntity(scannedResult.getData().getExchange_id().getExchange_id(),verificationType,
                                        configureRequest.getFileToSend(),configureRequest.getAttempt());
                                break;

                            case AuthenticationType.FINGERPRINT:
                                enrollEntity = new EnrollEntity(scannedResult.getData().getExchange_id().getExchange_id(),verificationType,
                                        configureRequest.getFingerPrintEntity());
                                break;

                            case AuthenticationType.SMARTPUSH:
                                enrollEntity = new EnrollEntity(scannedResult.getData().getExchange_id().getExchange_id(),configureRequest.getPass_code(),verificationType);
                                break;

                            default:

                                enrollEntity = new EnrollEntity(scannedResult.getData().getExchange_id().getExchange_id(),configureRequest.getPass_code(),verificationType);
                        }

                        enroll(enrollEntity,enrollResponseResult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        enrollResponseResult.failure(error);
                    }
                });

            }

            @Override
            public void failure(WebAuthError error) {
               enrollResponseResult.failure(error);
            }
        });
    }

    //-------------------------------------------------------INITIATE CALL--------------------------------------------------------------
    public void initiate(InitiateEntity initiateEntity, Result<InitiateResponse> initiateResponseResult)
    {
        InitiateController.getShared(context).initiateVerification(initiateEntity,initiateResponseResult);
    }

    public void verifyEmail(String code,String exchange_id,String sub)
    {

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

   /* Ask how to Handle TOTP
   public void loginWithTOTP(final ConfigureRequest configureRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        configure(configureRequest,AuthenticationType.TOTP,enrollResponseResult);
    }
    */

    public void loginWithFingerprint(final LoginRequest loginRequest, final Result<LoginCredentialsResponseEntity> authenticateResponseResult)
    {
        login(loginRequest,AuthenticationType.FINGERPRINT,authenticateResponseResult);
    }
    //-------------------------------------------------------LOGIN CALL COMMON--------------------------------------------------------------

    public void login(final LoginRequest loginRequest, final String verificationType, final Result<LoginCredentialsResponseEntity> loginCredentialsResult)
    {
        //To get Request Id
        Cidaas.getInstance(context).getRequestId(new Result<AuthRequestResponseEntity>() {
            @Override
            public void success(final AuthRequestResponseEntity requestIdResult)
            {
                InitiateEntity initiateEntity=new InitiateEntity(loginRequest.getSub(),requestIdResult.getData().getRequestId(),loginRequest.getUsageType()
                        ,verificationType);

                initiate(initiateEntity, new Result<InitiateResponse>() {
                    @Override
                    public void success(InitiateResponse initiateResult) {
                        // handle Authenticate entity and call authenticate

                        AuthenticateEntity authenticateEntity;

                        switch (verificationType) {
                            case AuthenticationType.FACE:
                                authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(),verificationType,
                                        loginRequest.getFileToSend(),loginRequest.getAttempt());
                                break;

                            case AuthenticationType.VOICE:
                                authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(),verificationType,
                                        loginRequest.getFileToSend(),loginRequest.getAttempt());
                                break;

                            case AuthenticationType.FINGERPRINT:
                                authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(),verificationType,
                                        loginRequest.getFingerPrintEntity());
                                break;

                            case AuthenticationType.SMARTPUSH:
                                authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(),loginRequest.getPass_code(),verificationType);
                                break;

                            default:

                                authenticateEntity = new AuthenticateEntity(initiateResult.getData().getExchange_id().getExchange_id(),
                                        loginRequest.getPass_code(),verificationType);
                        }

                        authenticate(authenticateEntity, new Result<AuthenticateResponse>() {
                            @Override
                            public void success(AuthenticateResponse result) {
                                //Sdk contiue Call
                                ResumeLoginEntity resumeLoginEntity=new ResumeLoginEntity(result.getData().getStatus_id(),
                                        result.getData().getSub(),requestIdResult.getData().getRequestId(),verificationType);
                                resumeLogin(resumeLoginEntity,loginCredentialsResult);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                loginCredentialsResult.failure(error);
                            }
                        });
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        loginCredentialsResult.failure(error);
                    }
                });
            }

            @Override
            public void failure(WebAuthError error) {
                loginCredentialsResult.failure(error);
            }
        });


    }

    private void resumeLogin(ResumeLoginEntity resumeLoginEntity,Result<LoginCredentialsResponseEntity> loginCredentialsResponseEntityResult) {
        try
        {

            ResumeLoginController.getShared(context).resumeLoginVerification(resumeLoginEntity,loginCredentialsResponseEntityResult);
        }
        catch (Exception e)
        {

        }
    }


}
