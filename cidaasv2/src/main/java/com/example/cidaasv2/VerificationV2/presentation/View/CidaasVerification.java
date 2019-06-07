package com.example.cidaasv2.VerificationV2.presentation.View;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.CidaasHelper;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory.AuthenticatedHistoryEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.AuthenticatedHistory.AuthenticatedHistoryResponse;
import com.example.cidaasv2.VerificationV2.data.Entity.Delete.DeleteEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Delete.DeleteResponse;
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
import com.example.cidaasv2.VerificationV2.domain.Controller.Authenticate.AuthenticateController;
import com.example.cidaasv2.VerificationV2.domain.Controller.AuthenticateHistory.AuthenticatedHistoryController;
import com.example.cidaasv2.VerificationV2.domain.Controller.ConfigureRequest.ConfigureRequest;
import com.example.cidaasv2.VerificationV2.domain.Controller.Delete.DeleteController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Enroll.EnrollController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Initiate.InitiateController;
import com.example.cidaasv2.VerificationV2.domain.Controller.PendingNotification.PendingNotificationController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Push.PushAcknowledge.PushAcknowledgeController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Push.PushAllow.PushAllowController;
import com.example.cidaasv2.VerificationV2.domain.Controller.Push.PushReject.PushRejectController;
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


    public void setup(SetupEntity setupEntity, Result<SetupResponse> setupResponseResult)
    {
        SetupController.getShared(context).setupVerification(setupEntity,setupResponseResult);
    }


    public void scanned(ScannedEntity scannedEntity, Result<ScannedResponse> scannedResult)
    {
        ScannedController.getShared(context).scannedVerification(scannedEntity,scannedResult);
    }

    public void enroll(@NonNull final EnrollEntity enrollEntity, final Result<EnrollResponse> enrollResponseResult)
    {
        EnrollController.getShared(context).enrollVerification(enrollEntity,enrollResponseResult);
    }

    public void pushAcknowledge(PushAcknowledgeEntity pushAcknowledgeEntity, Result<PushAcknowledgeResponse> pushAcknowledgeResult)
    {
        PushAcknowledgeController.getShared(context).pushAcknowledgeVerification(pushAcknowledgeEntity,pushAcknowledgeResult);
    }

    public void pushAllow(PushAllowEntity pushAllowEntity, Result<PushAllowResponse> pushAllowResponseResult)
    {
        PushAllowController.getShared(context).pushAllowVerification(pushAllowEntity,pushAllowResponseResult);
    }

    public void pushReject(PushRejectEntity pushRejectEntity, Result<PushRejectResponse> pushRejectResponseResult)
    {
        PushRejectController.getShared(context).pushRejectVerification(pushRejectEntity,pushRejectResponseResult);
    }

    public void initiate(InitiateEntity initiateEntity, Result<InitiateResponse> initiateResponseResult)
    {
        InitiateController.getShared(context).initiateVerification(initiateEntity,initiateResponseResult);
    }

    public void authenticate(AuthenticateEntity authenticateEntity, Result<AuthenticateResponse> authenticateResponseResult)
    {
        AuthenticateController.getShared(context).authenticateVerification(authenticateEntity,authenticateResponseResult);
    }

    public void delete(DeleteEntity deleteEntity, Result<DeleteResponse> deleteResponseResult)
    {
        DeleteController.getShared(context).deleteVerification(deleteEntity,deleteResponseResult);
    }

    public void deleteAll(Result<DeleteResponse> deleteResponseResult)
    {
        DeleteController.getShared(context).deleteAllVerification(deleteResponseResult);
    }

    public void getConfiguredMFAList(String sub,Result<ConfiguredMFAList> configuredMFAListResult)
    {
        SettingsController.getShared(context).getConfiguredMFAList(sub,configuredMFAListResult);
    }

    public void getPendingNotificationList(String sub,Result<PendingNotificationResponse> pendingNotificationResponse)
    {
        PendingNotificationController.getShared(context).getPendingNotification(sub,pendingNotificationResponse);
    }

    public void getAuthenticatedHistory(AuthenticatedHistoryEntity authenticatedHistoryEntity, Result<AuthenticatedHistoryResponse> authenticatedHistoryResult)
    {
        AuthenticatedHistoryController.getShared(context).getauthenticatedHistoryList(authenticatedHistoryEntity,authenticatedHistoryResult);
    }

    public void updateFCMToken(String FCMToken)
    {
        SettingsController.getShared(context).updateFCMToken(FCMToken);
    }

    public void setURL(@NonNull final Dictionary<String, String> loginproperties, Result<String> result,String methodName)
    {
        LoginController.getShared(context).setURL(loginproperties,result,methodName);
    }


    public void configure(final ConfigureRequest configureRequest, final Result<EnrollResponse> enrollResponseResult)
    {
        SetupEntity setupEntity=new SetupEntity(configureRequest.getSub(), configureRequest.getVerificationType());
        setup(setupEntity, new Result<SetupResponse>() {
            @Override
            public void success(SetupResponse setupResult) {

               ScannedEntity scannedEntity=new ScannedEntity(configureRequest.getSub(),setupResult.getData().getExchange_id().getExchange_id(),
                       configureRequest.getVerificationType());

                scanned(scannedEntity, new Result<ScannedResponse>() {
                    @Override
                    public void success(ScannedResponse scannedResult) {
                        //Todo handle Enroll entity and call enroll\
                        EnrollEntity enrollEntity;
                        if(configureRequest.getVerificationType().equals(AuthenticationType.TOUCHID)) {
                            enrollEntity = new EnrollEntity(scannedResult.getData().getExchange_id().getExchange_id(),configureRequest.getVerificationType(),
                                    configureRequest.getFingerPrintEntity());
                        }
                        else if((configureRequest.getVerificationType().equals(AuthenticationType.FACE)) || (configureRequest.getVerificationType().equals(AuthenticationType.VOICE)))
                        {
                            enrollEntity = new EnrollEntity(scannedResult.getData().getExchange_id().getExchange_id(),configureRequest.getVerificationType(),
                                    configureRequest.getFileToSend(),configureRequest.getFace_attempt());
                        }
                        else
                        {
                            enrollEntity = new EnrollEntity(configureRequest.getPass_code(),scannedResult.getData().getExchange_id().getExchange_id(),configureRequest.getVerificationType());
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


    public void login(AuthenticateEntity authenticateEntity, final Result<AuthenticateResponse> authenticateResponseResult)
    {
        InitiateEntity initiateEntity=new InitiateEntity();
        initiate(initiateEntity, new Result<InitiateResponse>() {
            @Override
            public void success(InitiateResponse result) {
                //Todo handle Authenticate entity and call authenticate
                AuthenticateEntity authenticateEntity=new AuthenticateEntity();
                authenticate(authenticateEntity,authenticateResponseResult);
            }

            @Override
            public void failure(WebAuthError error) {
                authenticateResponseResult.failure(error);
            }
        });
    }
}
