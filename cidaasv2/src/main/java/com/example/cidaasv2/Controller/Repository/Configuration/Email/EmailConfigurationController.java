package com.example.cidaasv2.Controller.Repository.Configuration.Email;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Email.EmailVerificationService;

import timber.log.Timber;

public class EmailConfigurationController {


    private String TrackId,RequestId,UsageTypefromEmail,StatusId;
    private String Sub;
    private Context context;

    public static EmailConfigurationController shared;

    public EmailConfigurationController(Context contextFromCidaas) {

        StatusId="";
        TrackId="";
        UsageTypefromEmail="";
        context=contextFromCidaas;
        Sub="";
        RequestId="";
        //Todo setValue for authenticationType

    }

    String codeVerifier, codeChallenge;
    // Generate Code Challenge and Code verifier
    public void generateChallenge(){
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge= generator.getCodeChallenge(codeVerifier);

    }

    public static EmailConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new EmailConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    public void configureEmail(@NonNull String sub, @NonNull final String baseurl, @NonNull final Result<SetupEmailMFAResponseEntity> result){
        try{

            Sub=sub;
            if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {

                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {
                        //Todo Service call
                        EmailVerificationService.getShared(context).setupEmailMFA(baseurl, accessTokenresult.getAccess_token(),
                                new Result<SetupEmailMFAResponseEntity>()
                        {
                            @Override
                            public void success(SetupEmailMFAResponseEntity serviceresult) {
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

    //Service call To enrollemailMFA
    public void enrollEmailMFA(@NonNull final String code,@NonNull final String baseurl, @NonNull final Result<EnrollEmailMFAResponseEntity> result)
    {
        try{

            if(Sub!="" && StatusId!="")
            {
               final EnrollEmailMFARequestEntity enrollEmailMFARequestEntity=new EnrollEmailMFARequestEntity();
               enrollEmailMFARequestEntity.setCode(code);
               enrollEmailMFARequestEntity.setStatusId(StatusId);

                AccessTokenController.getShared(context).getAccessToken(Sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(AccessTokenEntity accessresult) {

                        if (enrollEmailMFARequestEntity.getSub() != null && enrollEmailMFARequestEntity.getStatusId()  != null &&
                                baseurl != null && !baseurl.equals("") && accessresult.getAccess_token() != null && !accessresult.getAccess_token().equals(""))
                        {
                            //Done Service call
                            EmailVerificationService.getShared(context).enrollEmailMFA(baseurl, accessresult.getAccess_token(), enrollEmailMFARequestEntity,
                                    new Result<EnrollEmailMFAResponseEntity>() {
                                @Override
                                public void success(EnrollEmailMFAResponseEntity serviceresult) {
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



    public void loginWithEmail(@NonNull final String baseurl, @NonNull final String trackId,
                               @NonNull final String requestId, @NonNull InitiateEmailMFARequestEntity initiateEmailMFARequestEntity,
                               final Result<InitiateEmailMFAResponseEntity> result)
    {
        try{

            TrackId=trackId;
            RequestId=requestId;
            UsageTypefromEmail=initiateEmailMFARequestEntity.getUsageType();

            //Todo call Inititate

            if ( initiateEmailMFARequestEntity.getUsageType() != null && initiateEmailMFARequestEntity.getUsageType() != "" &&
                    initiateEmailMFARequestEntity.getSub() != null && initiateEmailMFARequestEntity.getSub() != "" &&
                    /*initiateEmailMFARequestEntity.getUserDeviceId() != null && initiateEmailMFARequestEntity.getUserDeviceId() != "" &&*/
                    initiateEmailMFARequestEntity.getVerificationType() != null && initiateEmailMFARequestEntity.getVerificationType() != ""&&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                EmailVerificationService.getShared(context).initiateEmailMFA(baseurl, initiateEmailMFARequestEntity, new Result<InitiateEmailMFAResponseEntity>() {
                    @Override
                    public void success(InitiateEmailMFAResponseEntity serviceresult) {
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

    public void verifyEmail(@NonNull final String baseurl, @NonNull final String code, @NonNull final String clientId,
                            final AuthenticateEmailRequestEntity authenticateEmailRequestEntity, final Result<LoginCredentialsResponseEntity> result)
    {
        try{


            authenticateEmailRequestEntity.setStatusId(StatusId);

            if (authenticateEmailRequestEntity.getCode() != null && authenticateEmailRequestEntity.getCode() != "" &&
                    authenticateEmailRequestEntity.getStatusId() != null && authenticateEmailRequestEntity.getStatusId() != "" &&
                    baseurl != null && !baseurl.equals("")) {
                //Todo Service call
                EmailVerificationService.getShared(context).authenticateEmailMFA(baseurl, authenticateEmailRequestEntity,
                        new Result<AuthenticateEmailResponseEntity>() {

                    @Override
                    public void success(AuthenticateEmailResponseEntity serviceresult) {

                        //Todo decide to move to MFA or Paswword Less Based on usageType
                        ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();
                        resumeLoginRequestEntity.setSub(serviceresult.getData().getSub());
                        resumeLoginRequestEntity.setTrack_id(TrackId);
                        resumeLoginRequestEntity.setTrackingCode(serviceresult.getData().getTrackingCode());
                        resumeLoginRequestEntity.setUsageType(UsageTypefromEmail);
                        resumeLoginRequestEntity.setRequestId(RequestId);
                        resumeLoginRequestEntity.setVerificationType("email");
                        resumeLoginRequestEntity.setClient_id(clientId);

                        if(UsageTypefromEmail.equals(UsageType.PASSWORDLESS)){
                            //Todo Create new Entity
                            //TrackingCode,sub,
                            LoginController.getShared(context).continuePasswordless(baseurl,resumeLoginRequestEntity,result);
                        }

                        else if(UsageTypefromEmail.equals(UsageType.MFA)) {


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

                String errorMessage="Status Id Must not be null";
                result.failure(WebAuthError.getShared(context).customException(417,errorMessage,417));
            }
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }




}

