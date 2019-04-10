package com.example.cidaasv2.Controller.Repository.Configuration.BackupCode;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Controller.Repository.ResumeLogin.ResumeLogin;
import com.example.cidaasv2.Helper.AuthenticationType;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.BackupCode.BackupCodeVerificationService;

import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

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
            Timber.i(e.getMessage());
        }
        return shared;
    }


    // --------------------------------------------------***** BackupCode MFA *****-----------------------------------------------------------------------------------------------------------------------
    public void configureBackupCode(@NonNull final String sub, @NonNull final Result<SetupBackupCodeMFAResponseEntity> result){
        try{

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
                        result.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be null"));
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
            result.failure(WebAuthError.getShared(context).serviceException("Exception :Backupcode Controller :getClientInfo()",WebAuthErrorCode.ENROLL_BACKUPCODE_MFA_FAILURE,e.getMessage()));
        }
    }

    // --------------------------------------------------***** BackupCode Service *****-----------------------------------------------------------------------------------------------------------------------

    private void callBackupCodeService(final String baseurl,String sub,@NonNull final Result<SetupBackupCodeMFAResponseEntity> result) {
        AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
            @Override
            public void success(final AccessTokenEntity accessTokenresult) {
                //Todo Service call
                BackupCodeVerificationService.getShared(context).setupBackupCodeMFA(baseurl, accessTokenresult.getAccess_token(),null,
                        result);
            }

            @Override
            public void failure(WebAuthError error) {
                result.failure(error);
            }
        });
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
            loginresult.failure(WebAuthError.getShared(context).serviceException("Exception :Backupcode Configuration Controller :getClientInfo()",WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE,e.getMessage()));
        }
    }

    // --------------------------------------------------***** Initiate BackupCode Verification *****-----------------------------------------------------------------------------------------------------------------------

    private void initiateBackupCodeVerification(final String code, final String baseurl, final InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity,
                                                final Result<AuthenticateBackupCodeResponseEntity> loginresult) {

        BackupCodeVerificationService.getShared(context).initiateBackupCodeMFA(baseurl, initiateBackupCodeMFARequestEntity, null,new Result<InitiateBackupCodeMFAResponseEntity>() {
            @Override
            public void success(final InitiateBackupCodeMFAResponseEntity serviceresult) {

                if(serviceresult.getData().getStatusId()!=null && !serviceresult.getData().getStatusId().equals("") && code!=null
                        && !code.equals("")) {
                    AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity = new AuthenticateBackupCodeRequestEntity();
                    authenticateBackupCodeRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                    authenticateBackupCodeRequestEntity.setVerifierPassword(code);

                    BackupCodeVerificationService.getShared(context).authenticateBackupCodeMFA(baseurl, authenticateBackupCodeRequestEntity, null, loginresult);
                }
                else
                {
                    loginresult.failure(WebAuthError.getShared(context).propertyMissingException("Status Id or Code must not be empty"));
                }
            }
            @Override
            public void failure(WebAuthError error) {
                loginresult.failure(error);
            }
        });
    }

    // --------------------------------------------------***** BackupCode Entity *****-----------------------------------------------------------------------------------------------------------------------


    private InitiateBackupCodeMFARequestEntity getInitiateBackupCodeMFAEntity(PasswordlessEntity passwordlessEntity, Result<LoginCredentialsResponseEntity> result) {

        if (passwordlessEntity.getSub() != null && !passwordlessEntity.getSub().equals("") &&
                (passwordlessEntity.getUsageType() != null && !passwordlessEntity.getUsageType().equals(""))) {

            if (passwordlessEntity.getUsageType().equals(UsageType.MFA)) {
                if (passwordlessEntity.getTrackId() == null || passwordlessEntity.getTrackId() == "") {
                    String errorMessage = "trackId must not be empty";

                    result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                            errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                    return null;
                }
            }

            TrackId=passwordlessEntity.getTrackId();
            RequestId=passwordlessEntity.getRequestId();
            UsageTypeFromBackupCode=passwordlessEntity.getUsageType();

            InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity = new InitiateBackupCodeMFARequestEntity();
            initiateBackupCodeMFARequestEntity.setSub(passwordlessEntity.getSub());
            initiateBackupCodeMFARequestEntity.setUsageType(passwordlessEntity.getUsageType());
            initiateBackupCodeMFARequestEntity.setVerificationType("BACKUPCODE");

            return initiateBackupCodeMFARequestEntity;


        } else {

            result.failure(WebAuthError.getShared(context).propertyMissingException("Sub or Usage Type must not be empty"));
            return null;
        }

    }




/*

    public void initiateBackupCodeMFA(@NonNull String baseurl, @NonNull InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity, final Result<InitiateBackupCodeMFAResponseEntity> result){
        try{

            if ( initiateBackupCodeMFARequestEntity.getUsageType() != null && initiateBackupCodeMFARequestEntity.getUsageType() != "" &&
                    initiateBackupCodeMFARequestEntity.getSub() != null && initiateBackupCodeMFARequestEntity.getSub() != "" &&
                    initiateBackupCodeMFARequestEntity.getUserDeviceId() != null && initiateBackupCodeMFARequestEntity.getUserDeviceId() != "" &&
                    initiateBackupCodeMFARequestEntity.getVerificationType() != null && initiateBackupCodeMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                BackupCodeVerificationService.getShared(context).initiateBackupCodeMFA(baseurl, initiateBackupCodeMFARequestEntity, new Result<InitiateBackupCodeMFAResponseEntity>() {
                    @Override
                    public void success(InitiateBackupCodeMFAResponseEntity serviceresult) {
                        result.success(serviceresult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }
*/


    /*   //setupBackupCodeMFA
    public void setupBackupCodeMFA(@NonNull String sub, @NonNull final Result<SetupBackupCodeMFAResponseEntity> result){
        try {
            String baseurl="";
            if(savedProperties==null){

                savedProperties= DBHelper.getShared().getLoginProperties();
            }
            if(savedProperties==null){
                //Read from file if localDB is null
                readFromFile(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> loginProperties) {
                        savedProperties=loginProperties;
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }

            if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                webAuthError = webAuthError.propertyMissingException();
                String loggerMessage = "Setup BackupCode MFA readProperties failure : "
                + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if ( sub != null && !sub.equals("") && baseurl != null && !baseurl.equals("")) {

                    final String finalBaseurl = baseurl;
                    getAccessToken(sub, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accesstokenresult) {
                            setupBackupCodeMFAService(accesstokenresult.getAccess_token(), finalBaseurl,result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            result.failure(error);
                        }
                    });


                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }
*/
    //Service call To SetupBackupCodeMFA
   /* public void setupBackupCode(@NonNull String AccessToken,@NonNull String baseurl, @NonNull final Result<SetupBackupCodeMFAResponseEntity> result){
        try{

            if (baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                BackupCodeVerificationService.getShared(context).setupBackupCodeMFA(baseurl, AccessToken, new Result<SetupBackupCodeMFAResponseEntity>() {
                    @Override
                    public void success(SetupBackupCodeMFAResponseEntity serviceresult) {
                        result.success(serviceresult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }



    public void loginWithBackupCode(@NonNull final String baseurl, @NonNull final String trackId, @NonNull final String clientId,
                                    @NonNull final String usageType, @NonNull AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity,
                               final Result<AccessTokenEntity> result){
        try{

            if (authenticateBackupCodeRequestEntity.getVerifierPassword() != null && authenticateBackupCodeRequestEntity.getVerifierPassword() != "" &&
                    authenticateBackupCodeRequestEntity.getStatusId() != null && authenticateBackupCodeRequestEntity.getStatusId() != "" &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                BackupCodeVerificationService.getShared(context).authenticateBackupCodeMFA(baseurl, authenticateBackupCodeRequestEntity,
                        new Result<AuthenticateBackupCodeResponseEntity>() {
                    @Override
                    public void success(AuthenticateBackupCodeResponseEntity serviceresult) {

                        ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();
                        resumeLoginRequestEntity.setSub(serviceresult.getData().getSub());
                        resumeLoginRequestEntity.setTrack_id(trackId);
                        resumeLoginRequestEntity.setTrackingCode(serviceresult.getData().getTrackingCode());
                        resumeLoginRequestEntity.setUsageType(usageType);
                        resumeLoginRequestEntity.setVerificationType("BackupCode");
                        resumeLoginRequestEntity.setClient_id(clientId);

                        LoginController.getShared(context).resumeLogin(baseurl,resumeLoginRequestEntity,result);

                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }


*/
}
