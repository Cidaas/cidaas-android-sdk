package com.example.cidaasv2.Controller.Repository.Configuration.BackupCode;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.ResumeLogin.ResumeLogin;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.BackupCode.BackupCodeVerificationService;

import java.util.Dictionary;

import androidx.annotation.NonNull;

public class BackupCodeConfigurationController {

    private String UsageTypeFromBackupCode,TrackId,RequestId,Sub;
    private Context context;

    public static BackupCodeConfigurationController shared;

    public BackupCodeConfigurationController(Context contextFromCidaas) {

        UsageTypeFromBackupCode="";
        context=contextFromCidaas;
        TrackId="";
        RequestId="";
        Sub="";

    }

    public static BackupCodeConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new BackupCodeConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            LogFile.getShared(contextFromCidaas).addFailureLog("EmailConfigurationController instance Creation Exception:-"+e.getMessage());
        }
        return shared;
    }


    // --------------------------------------------------***** BackupCode MFA *****-----------------------------------------------------------------------------------------------------------------------
    public void configureBackupCode(@NonNull final String sub, @NonNull final Result<SetupBackupCodeMFAResponseEntity> result){
        try{
            LogFile.getShared(context).addInfoLog("Info of BackupcodeConfiguration Controller :configureBackupCode()", " Info Sub:-"+sub);

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl=loginPropertiesResult.get("DomainURL");
                    Sub=sub;
                    if (sub != null && !sub.equals("")) {
                        callBackupCodeService(baseurl,sub,result);
                    }
                    else
                    {
                        result.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be null","Error :Backupcode Controller :configureBackupCode()"));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                  result.failure(error);
                }
            });

        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException("Exception :Backupcode Controller :configureBackupCode()",WebAuthErrorCode.ENROLL_BACKUPCODE_MFA_FAILURE,e.getMessage()));
        }
    }

    // --------------------------------------------------***** BackupCode Service *****-----------------------------------------------------------------------------------------------------------------------

    private void callBackupCodeService(final String baseurl,String sub,@NonNull final Result<SetupBackupCodeMFAResponseEntity> result) {
        try {

            LogFile.getShared(context).addInfoLog("Info :Backupcode Configuration Controller :callBackupCodeService()","Info sub"+sub+"baseurl"+baseurl);
            AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                @Override
                public void success(final AccessTokenEntity accessTokenresult) {
                    // Service call
                    LogFile.getShared(context).addSuccessLog("Success :Backupcode Configuration Controller :callBackupCodeService()",
                            "AccessToken"+accessTokenresult.getAccess_token()+"RefreshToken"+accessTokenresult.getRefresh_token()+
                                    "ExpiresIn"+accessTokenresult.getExpires_in());
                    BackupCodeVerificationService.getShared(context).setupBackupCodeMFA(baseurl, accessTokenresult.getAccess_token(), null,
                            result);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException("Exception :Backupcode Configuration Controller :callBackupCodeService()",
                    WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE,e.getMessage()));
        }
    }

    // --------------------------------------------------*****Login With BackupCode *****-----------------------------------------------------------------------------------------------------------------------

    public void loginWithBackupCode(@NonNull final String code, final PasswordlessEntity passwordlessEntity, final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{
            final InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity=getInitiateBackupCodeMFAEntity(passwordlessEntity, loginresult);

            if (initiateBackupCodeMFARequestEntity==null) {
                return;
            }

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(final Dictionary<String, String> loginProertiesResult) {
                    final String clientId=loginProertiesResult.get("ClinetId");
                    final String baseurl=loginProertiesResult.get("DomainURL");

                    initiateBackupCodeVerification(code, baseurl, initiateBackupCodeMFARequestEntity,new Result<AuthenticateBackupCodeResponseEntity>() {
                        @Override
                        public void success(AuthenticateBackupCodeResponseEntity result) {
                            LogFile.getShared(context).addSuccessLog("Success :Backupcode Configuration Controller :loginWithBackupCode()",
                                    "Success code:-"+code+" baseurl:-"+baseurl+" Sub"+result.getData().getSub());

                            ResumeLogin.getShared(context).resumeLoginAfterSuccessfullAuthentication(result.getData().getSub(),
                                    result.getData().getTrackingCode(), AuthenticationType.BACKUPCODE,
                                    initiateBackupCodeMFARequestEntity.getUsageType(),clientId,passwordlessEntity.getRequestId()
                                    ,passwordlessEntity.getTrackId(),baseurl,loginresult);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                          loginresult.failure(error);
                        }
                    });
                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).methodException("Exception :Backupcode Configuration Controller :loginWithBackupCode()",WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE,e.getMessage()));
        }
    }

    // --------------------------------------------------***** Initiate BackupCode Verification *****-----------------------------------------------------------------------------------------------------------------------

    private void initiateBackupCodeVerification(final String code, final String baseurl, final InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity,
                                                final Result<AuthenticateBackupCodeResponseEntity> loginresult) {
        try {

            LogFile.getShared(context).addInfoLog("Info :Backupcode Configuration Controller :initiateBackupCodeVerification()",
                    "Info code:-"+code+" baseurl:-"+baseurl);

            BackupCodeVerificationService.getShared(context).initiateBackupCodeMFA(baseurl, initiateBackupCodeMFARequestEntity,
                    null, new Result<InitiateBackupCodeMFAResponseEntity>() {
                @Override
                public void success(final InitiateBackupCodeMFAResponseEntity serviceresult) {

                    if (serviceresult.getData().getStatusId() != null && !serviceresult.getData().getStatusId().equals("") && code != null
                            && !code.equals("")) {
                        AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity = new AuthenticateBackupCodeRequestEntity();
                        authenticateBackupCodeRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                        authenticateBackupCodeRequestEntity.setVerifierPassword(code);

                        BackupCodeVerificationService.getShared(context).authenticateBackupCodeMFA(baseurl, authenticateBackupCodeRequestEntity,
                                null, loginresult);
                    } else {
                        loginresult.failure(WebAuthError.getShared(context).propertyMissingException("Status Id or Code must not be empty",
                                "Error :Backupcode Configuration Controller :initiateBackupCodeVerification()"));
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
            loginresult.failure(WebAuthError.getShared(context).methodException("Exception :Backupcode Configuration Controller :initiateBackupCodeVerification()",
                    WebAuthErrorCode.INITIATE_BACKUPCODE_MFA_FAILURE,e.getMessage()));
        }
    }

    // --------------------------------------------------***** BackupCode Entity *****-----------------------------------------------------------------------------------------------------------------------


    private InitiateBackupCodeMFARequestEntity getInitiateBackupCodeMFAEntity(PasswordlessEntity passwordlessEntity, Result<LoginCredentialsResponseEntity> result) {
    try {

//Todo check for mobile email or sub
    if(passwordlessEntity.getUsageType() != null && !passwordlessEntity.getUsageType().equals("")) {

        if (passwordlessEntity.getUsageType().equals(UsageType.MFA)) {
            if (passwordlessEntity.getTrackId() == null || passwordlessEntity.getTrackId() == "") {
                String errorMessage = "trackId must not be empty";

                result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,
                        "Error :Backupcode Configuration Controller :getInitiateBackupCodeMFAEntity()"));
                return null;
            }
            else {
                //Log Success Message
                LogFile.getShared(context).addInfoLog("Info of BackupCode Controller :getInitiateBackupCodeMFAEntity()",
                        " Info UsageType:-"+passwordlessEntity.getUsageType()+"refreshToken:-"+passwordlessEntity.getTrackId()+
                                "sub :-"+passwordlessEntity.getSub());
            }

        }

        TrackId = passwordlessEntity.getTrackId();
        RequestId = passwordlessEntity.getRequestId();
        UsageTypeFromBackupCode = passwordlessEntity.getUsageType();

        InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity = new InitiateBackupCodeMFARequestEntity();
        initiateBackupCodeMFARequestEntity.setSub(passwordlessEntity.getSub());
        initiateBackupCodeMFARequestEntity.setUsageType(passwordlessEntity.getUsageType());
        initiateBackupCodeMFARequestEntity.setVerificationType("BACKUPCODE");

        return initiateBackupCodeMFARequestEntity;


    } else {

        result.failure(WebAuthError.getShared(context).propertyMissingException("Sub or Usage Type must not be empty",
                "Error :Backupcode Configuration Controller :getInitiateBackupCodeMFAEntity()"));
        return null;
    }
    }
    catch (Exception e)
    {
        result.failure(WebAuthError.getShared(context).methodException("Exception :Backupcode Configuration Controller :getInitiateBackupCodeMFAEntity()",
                WebAuthErrorCode.INITIATE_BACKUPCODE_MFA_FAILURE,e.getMessage()));
        return null;
    }
    }

}
