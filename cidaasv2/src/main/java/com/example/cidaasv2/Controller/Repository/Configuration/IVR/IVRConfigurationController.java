package com.example.cidaasv2.Controller.Repository.Configuration.IVR;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.IVR.IVRVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.IVR.IVRVerificationService;

import timber.log.Timber;

public class IVRConfigurationController {


    private String UsageTypeFromIVR,TrackId,RequestId,StatusId,Sub;
    private Context context;

    public static IVRConfigurationController shared;

    public IVRConfigurationController(Context contextFromCidaas) {

        UsageTypeFromIVR="";
        context=contextFromCidaas;
        TrackId="";
        RequestId="";
        StatusId="";
        Sub="";

        //Todo setValue for authenticationType

    }

    String codeVerifier, codeChallenge;
    // Generate Code Challenge and Code verifier
    public void generateChallenge(){
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge= generator.getCodeChallenge(codeVerifier);

    }

    public static IVRConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new IVRConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    // --------------------------------------------------***** IVR MFA *****-----------------------------------------------------------------------------------------------------------------------



    public void configureIVR(@NonNull String sub, @NonNull final String baseurl, @NonNull final Result<SetupIVRMFAResponseEntity> result){
        try{

            Sub=sub;
            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {

                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {
                        //Todo Service call
                        IVRVerificationService.getShared(context).setupIVRMFA(baseurl, accessTokenresult.getAccess_token(),
                                new Result<SetupIVRMFAResponseEntity>()
                                {
                                    @Override
                                    public void success(SetupIVRMFAResponseEntity serviceresult) {
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

    //Service call To enrollIVRMFA
    public void enrollIVRMFA(@NonNull final String code,@NonNull final String baseurl, @NonNull final Result<EnrollIVRMFAResponseEntity> result)
    {
        try{

            if(Sub!="" && StatusId!="")
            {
                final EnrollIVRMFARequestEntity enrollIVRMFARequestEntity=new EnrollIVRMFARequestEntity();
                enrollIVRMFARequestEntity.setCode(code);
                enrollIVRMFARequestEntity.setStatusId(StatusId);

                AccessTokenController.getShared(context).getAccessToken(Sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(AccessTokenEntity accessresult) {

                        if (enrollIVRMFARequestEntity.getSub() != null && enrollIVRMFARequestEntity.getStatusId()  != null &&
                                baseurl != null && !baseurl.equals("") && accessresult.getAccess_token() != null && !accessresult.getAccess_token().equals(""))
                        {
                            //Done Service call
                            IVRVerificationService.getShared(context).enrollIVRMFA(baseurl, accessresult.getAccess_token(), enrollIVRMFARequestEntity,
                                    new Result<EnrollIVRMFAResponseEntity>() {
                                        @Override
                                        public void success(EnrollIVRMFAResponseEntity serviceresult) {
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

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            }


        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }



    public void loginWithIVR(@NonNull final String baseurl, @NonNull final String trackId, @NonNull final String clientId,
                             @NonNull final String requestId, @NonNull InitiateIVRMFARequestEntity initiateIVRMFARequestEntity,
                             final Result<InitiateIVRMFAResponseEntity> result)
    {
        try{

            TrackId=trackId;
            RequestId=requestId;
            UsageTypeFromIVR=initiateIVRMFARequestEntity.getUsageType();

            //Todo call Inititate

            if ( initiateIVRMFARequestEntity.getUsageType() != null && initiateIVRMFARequestEntity.getUsageType() != "" &&
                    initiateIVRMFARequestEntity.getSub() != null && initiateIVRMFARequestEntity.getSub() != "" &&
                    initiateIVRMFARequestEntity.getVerificationType() != null && initiateIVRMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                IVRVerificationService.getShared(context).initiateIVRMFA(baseurl, initiateIVRMFARequestEntity, new Result<InitiateIVRMFAResponseEntity>() {
                    @Override
                    public void success(InitiateIVRMFAResponseEntity serviceresult) {
                        StatusId=serviceresult.getData().getStatusId();
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





    public void verifyIVR(@NonNull final String baseurl, @NonNull final String clientId, final AuthenticateIVRRequestEntity authenticateIVRRequestEntity,
                          final Result<LoginCredentialsResponseEntity> result){
        try{

            if(StatusId!=null && StatusId!="") {
                authenticateIVRRequestEntity.setStatusId(StatusId);

            }
            else {
                String errorMessage="StatusId must not be empty";

                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                        errorMessage, HttpStatusCode.EXPECTATION_FAILED));
            }

            if ( baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                IVRVerificationService.getShared(context).authenticateIVRMFA(baseurl, authenticateIVRRequestEntity, new Result<AuthenticateIVRResponseEntity>() {
                    @Override
                    public void success(AuthenticateIVRResponseEntity serviceresult) {


                        //Todo decide to move to MFA or Paswword Less Based on usageType
                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();
                        resumeLoginRequestEntity.setSub(serviceresult.getData().getSub());
                        resumeLoginRequestEntity.setTrack_id(TrackId);
                        resumeLoginRequestEntity.setTrackingCode(serviceresult.getData().getTrackingCode());
                        resumeLoginRequestEntity.setUsageType(UsageTypeFromIVR);
                        resumeLoginRequestEntity.setRequestId(RequestId);
                        resumeLoginRequestEntity.setVerificationType("IVR");
                        resumeLoginRequestEntity.setClient_id(clientId);

                        if(UsageTypeFromIVR.equals(UsageType.PASSWORDLESS)){

                            LoginController.getShared(context).continuePasswordless(baseurl,resumeLoginRequestEntity,result);
                        }

                        else if(UsageTypeFromIVR.equals(UsageType.MFA)) {


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















































    /*   //setupIVRMFA
    public void setupIVRMFA(@NonNull String sub, @NonNull final Result<SetupIVRMFAResponseEntity> result){
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
                String loggerMessage = "Setup IVR MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
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
                            setupIVRMFAService(accesstokenresult.getAccess_token(), finalBaseurl,result);
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

    //Service call To SetupIVRMFA
    private void setupIVRMFAService(@NonNull String AccessToken,@NonNull String baseurl, @NonNull final Result<SetupIVRMFAResponseEntity> result){
        try{

            if (baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                OauthService.getShared(context).setupIVRMFA(baseurl, AccessToken, new Result<SetupIVRMFAResponseEntity>() {
                    @Override
                    public void success(SetupIVRMFAResponseEntity serviceresult) {
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
                webAuthError=webAuthError.propertyMissingException();
                webAuthError.ErrorMessage="one of the Login properties missing";
                result.failure(webAuthError);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }


    //enrollIVRMFA
    public void enrollIVRMFA(@NonNull final EnrollIVRMFARequestEntity enrollIVRMFARequestEntity, @NonNull final Result<EnrollIVRMFAResponseEntity> result){
        try {
            String baseurl="";
            if(savedProperties==null){

                savedProperties=DBHelper.getShared().getLoginProperties();
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
                String loggerMessage = "Setup IVR MFA readProperties failure : " + "Error Code - " + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage
                        + ", Status Code - " + webAuthError.statusCode;
                LogFile.addRecordToLog(loggerMessage);
                result.failure(webAuthError);
            } else {
                baseurl = savedProperties.get("DomainURL");

                if ( enrollIVRMFARequestEntity.getCode() != null && !enrollIVRMFARequestEntity.getCode().equals("") &&
                        enrollIVRMFARequestEntity.getSub() != null && enrollIVRMFARequestEntity.getSub()  != null &&
                        enrollIVRMFARequestEntity.getStatusId() != null && enrollIVRMFARequestEntity.getStatusId()  != null &&
                        baseurl != null && !baseurl.equals("")) {

                    final String finalBaseurl = baseurl;
                    getAccessToken(enrollIVRMFARequestEntity.getSub(), new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accesstokenresult) {
                            enrollIVRMFAService(accesstokenresult.getAccess_token(), finalBaseurl,enrollIVRMFARequestEntity,result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            result.failure(error);
                        }
                    });


                }
                else {
                    webAuthError=webAuthError.propertyMissingException();
                    webAuthError.ErrorMessage="one of the Login properties missing";
                    result.failure(webAuthError);
                }

            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent exception"+e.getMessage());
            Timber.e("acceptConsent exception"+e.getMessage());
        }
    }

    //Service call To enrollIVRMFA
    private void enrollIVRMFAService(@NonNull String AccessToken,@NonNull String baseurl,
                                     @NonNull final EnrollIVRMFARequestEntity enrollIVRMFARequestEntity, @NonNull final Result<EnrollIVRMFAResponseEntity> result){
        try{

            if (enrollIVRMFARequestEntity.getCode() != null && !enrollIVRMFARequestEntity.getCode().equals("") &&
                    enrollIVRMFARequestEntity.getSub() != null && enrollIVRMFARequestEntity.getSub()  != null &&
                    enrollIVRMFARequestEntity.getStatusId() != null && enrollIVRMFARequestEntity.getStatusId()  != null &&
                    baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                OauthService.getShared(context).enrollIVRMFA(baseurl, AccessToken, enrollIVRMFARequestEntity,new Result<EnrollIVRMFAResponseEntity>() {
                    @Override
                    public void success(EnrollIVRMFAResponseEntity serviceresult) {
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
                webAuthError=webAuthError.propertyMissingException();
                webAuthError.ErrorMessage="one of the Login properties missing";
                result.failure(webAuthError);
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
        }
    }
*/
/*
 public void initiateIVRMFA(@NonNull String baseurl, @NonNull InitiateIVRMFARequestEntity initiateIVRMFARequestEntity, final Result<InitiateIVRMFAResponseEntity> result){
     try{

         if ( initiateIVRMFARequestEntity.getUsageType() != null && initiateIVRMFARequestEntity.getUsageType() != "" &&
                 initiateIVRMFARequestEntity.getSub() != null && initiateIVRMFARequestEntity.getSub() != "" &&
                 initiateIVRMFARequestEntity.getUserDeviceId() != null && initiateIVRMFARequestEntity.getUserDeviceId() != "" &&
                 initiateIVRMFARequestEntity.getVerificationType() != null && initiateIVRMFARequestEntity.getVerificationType() != ""&&
                 baseurl != null && !baseurl.equals("")) {
             //Todo Service call
             IVRVerificationService.getShared(context).initiateIVRMFA(baseurl, initiateIVRMFARequestEntity, new Result<InitiateIVRMFAResponseEntity>() {
                 @Override
                 public void success(InitiateIVRMFAResponseEntity serviceresult) {
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



    public void loginWithIVR(@NonNull final String baseurl, @NonNull final String trackId, @NonNull final String clientId, @NonNull final String usageType, @NonNull AuthenticateIVRRequestEntity authenticateIVRRequestEntity,
                               final Result<AccessTokenEntity> result){
        try{

            if (authenticateIVRRequestEntity.getCode() != null && authenticateIVRRequestEntity.getCode() != "" &&
                    authenticateIVRRequestEntity.getStatusId() != null && authenticateIVRRequestEntity.getStatusId() != "" &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                IVRVerificationService.getShared(context).authenticateIVRMFA(baseurl, authenticateIVRRequestEntity, new Result<AuthenticateIVRResponseEntity>() {
                    @Override
                    public void success(AuthenticateIVRResponseEntity serviceresult) {

                        ResumeLoginRequestEntity resumeLoginRequestEntity=new ResumeLoginRequestEntity();
                        resumeLoginRequestEntity.setSub(serviceresult.getData().getSub());
                        resumeLoginRequestEntity.setTrack_id(trackId);
                        resumeLoginRequestEntity.setTrackingCode(serviceresult.getData().getTrackingCode());
                        resumeLoginRequestEntity.setUsageType(usageType);
                        resumeLoginRequestEntity.setVerificationType("IVR");
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
