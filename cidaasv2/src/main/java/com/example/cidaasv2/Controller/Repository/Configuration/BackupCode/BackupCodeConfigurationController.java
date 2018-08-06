package com.example.cidaasv2.Controller.Repository.Configuration.BackupCode;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.BackupCode.EnrollBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.BackupCode.EnrollBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.BackupCode.BackupCodeVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.BackupCode.BackupCodeVerificationService;

import timber.log.Timber;

public class BackupCodeConfigurationController {
    
    private String UsageTypeFromBackupCode,TrackId,RequestId,StatusId,Sub;
    private Context context;

    public static BackupCodeConfigurationController shared;

    public BackupCodeConfigurationController(Context contextFromCidaas) {

        UsageTypeFromBackupCode="";
        context=contextFromCidaas;
        TrackId="";
        RequestId="";
        StatusId="";
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


    public void configureBackupCode(@NonNull String sub, @NonNull final String baseurl, @NonNull final Result<SetupBackupCodeMFAResponseEntity> result){
        try{

            Sub=sub;
            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {

                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {
                        //Todo Service call
                        BackupCodeVerificationService.getShared(context).setupBackupCodeMFA(baseurl, accessTokenresult.getAccess_token(),
                                new Result<SetupBackupCodeMFAResponseEntity>()
                                {
                                    @Override
                                    public void success(SetupBackupCodeMFAResponseEntity serviceresult) {
                                        StatusId=serviceresult.getData().getStatusId();
                                        result.success(serviceresult);
                                    }

                                    @Override
                                    public void failure(WebAuthError error) {
                                        result.failure(error);
                                    }
                                });
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

    



    public void loginWithBackupCode(@NonNull final String code,@NonNull final String baseurl, @NonNull final String trackId, @NonNull final String clientId,
                             @NonNull final String requestId, @NonNull final InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity,
                             final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            TrackId=trackId;
            RequestId=requestId;
            UsageTypeFromBackupCode=initiateBackupCodeMFARequestEntity.getUsageType();

            //Todo call Inititate

            if ( initiateBackupCodeMFARequestEntity.getUsageType() != null && initiateBackupCodeMFARequestEntity.getUsageType() != "" &&
                    initiateBackupCodeMFARequestEntity.getSub() != null && initiateBackupCodeMFARequestEntity.getSub() != "" &&
                    initiateBackupCodeMFARequestEntity.getVerificationType() != null && initiateBackupCodeMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                BackupCodeVerificationService.getShared(context).initiateBackupCodeMFA(baseurl, initiateBackupCodeMFARequestEntity, new Result<InitiateBackupCodeMFAResponseEntity>() {
                    @Override
                    public void success(final InitiateBackupCodeMFAResponseEntity serviceresult) {
                        StatusId=serviceresult.getData().getStatusId();


                        AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity = new AuthenticateBackupCodeRequestEntity();
                        authenticateBackupCodeRequestEntity.setStatusId(serviceresult.getData().getStatusId());
                        authenticateBackupCodeRequestEntity.setVerifierPassword(code);


                        BackupCodeVerificationService.getShared(context).authenticateBackupCodeMFA(baseurl, authenticateBackupCodeRequestEntity, new Result<AuthenticateBackupCodeResponseEntity>() {
                            @Override
                            public void success(AuthenticateBackupCodeResponseEntity result) {
                                //Todo Call Resume with Login Service

                                ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

                                //Todo Check not Null values
                                resumeLoginRequestEntity.setSub(result.getData().getSub());
                                resumeLoginRequestEntity.setTrackingCode(result.getData().getTrackingCode());
                                resumeLoginRequestEntity.setUsageType(initiateBackupCodeMFARequestEntity.getUsageType());
                                resumeLoginRequestEntity.setVerificationType("backupcode");
                                resumeLoginRequestEntity.setClient_id(clientId);
                                resumeLoginRequestEntity.setRequestId(requestId);

                                if (initiateBackupCodeMFARequestEntity.getUsageType().equals(UsageType.MFA)) {
                                    resumeLoginRequestEntity.setTrack_id(trackId);
                                    LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, loginresult);
                                } else if (initiateBackupCodeMFARequestEntity.getUsageType().equals(UsageType.PASSWORDLESS)) {
                                    resumeLoginRequestEntity.setTrack_id("");
                                    LoginController.getShared(context).continuePasswordless(baseurl, resumeLoginRequestEntity, loginresult);

                                }
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
            else
            {
                loginresult.failure(WebAuthError.getShared(context).propertyMissingException());
            }


        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }





    public void verifyBackupCode(@NonNull final String baseurl, @NonNull final String clientId, final AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity,
                                 final Result<LoginCredentialsResponseEntity> result){
        try{

            if(StatusId!=null && StatusId!="") {
                authenticateBackupCodeRequestEntity.setStatusId(StatusId);

            }
            else {
                String errorMessage="StatusId must not be empty";

                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                        errorMessage, HttpStatusCode.EXPECTATION_FAILED));
            }

            if ( baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                BackupCodeVerificationService.getShared(context).authenticateBackupCodeMFA(baseurl, authenticateBackupCodeRequestEntity, new Result<AuthenticateBackupCodeResponseEntity>() {
                    @Override
                    public void success(AuthenticateBackupCodeResponseEntity serviceresult) {


                        //Todo decide to move to MFA or Paswword Less Based on usageType
                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();
                        resumeLoginRequestEntity.setSub(serviceresult.getData().getSub());
                        resumeLoginRequestEntity.setTrack_id(TrackId);
                        resumeLoginRequestEntity.setTrackingCode(serviceresult.getData().getTrackingCode());
                        resumeLoginRequestEntity.setUsageType(UsageTypeFromBackupCode);
                        resumeLoginRequestEntity.setRequestId(RequestId);
                        resumeLoginRequestEntity.setVerificationType("BackupCode");
                        resumeLoginRequestEntity.setClient_id(clientId);

                        if(UsageTypeFromBackupCode.equals(UsageType.PASSWORDLESS)){

                            LoginController.getShared(context).continuePasswordless(baseurl,resumeLoginRequestEntity,result);
                        }

                        else if(UsageTypeFromBackupCode.equals(UsageType.MFA)) {


                            LoginController.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, result);
                        }

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
            result.failure(WebAuthError.getShared(context).propertyMissingException());
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
