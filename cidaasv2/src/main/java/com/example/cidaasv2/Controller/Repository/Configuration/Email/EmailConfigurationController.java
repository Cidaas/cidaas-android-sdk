package com.example.cidaasv2.Controller.Repository.Configuration.Email;

import android.content.Context;


import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.ResumeLogin.ResumeLogin;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Repository.Verification.Email.EmailVerificationService;

import java.util.Dictionary;

import androidx.annotation.NonNull;

public class EmailConfigurationController {


    private String TrackId,RequestId,UsageTypefromEmail;
    private String Sub;
    private Context context;

    public static EmailConfigurationController shared;

    public EmailConfigurationController(Context contextFromCidaas) {


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
            LogFile.getShared(contextFromCidaas).addFailureLog("EmailConfigurationController instance Creation Exception:-"+e.getMessage());
        }
        return shared;
    }


    public void configureEmail(@NonNull final String sub, @NonNull final Result<SetupEmailMFAResponseEntity> result){
        try{

            Sub=sub;
            LogFile.getShared(context).addInfoLog("Info of EmailConfiguration Controller :configureEmail()", " Info Sub:-"+sub);

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(final Dictionary<String, String> loginPropertiesresult) {
                    final String baseurl=loginPropertiesresult.get("DomainURL");

                    if (baseurl != null && !baseurl.equals("") && sub != null && !sub.equals("")) {

                        AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                            @Override
                            public void success(final AccessTokenEntity accessTokenresult) {
                                //done Service call
                                LogFile.getShared(context).addSuccessLog("Success :Email Configuration Controller :configureEmail()",
                                        "AccessToken"+accessTokenresult.getAccess_token()+"RefreshToken"+accessTokenresult.getRefresh_token()+
                                                "ExpiresIn"+accessTokenresult.getExpires_in());

                                EmailVerificationService.getShared(context).setupEmailMFA(baseurl, accessTokenresult.getAccess_token(),null, result);
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                result.failure(error);
                            }
                        });

                    }
                }

                @Override
                public void failure(WebAuthError error) {
                  result.failure(WebAuthError.getShared(context).CidaaspropertyMissingException(error.getErrorMessage(),
                          "Exception :Email configuration Controller :getClientInfo()"));
                }
            });
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException("Exception :Email configuration Controller :getClientInfo()"
                    ,WebAuthErrorCode.ENROLL_EMAIL_MFA_FAILURE,e.getMessage()));
        }
    }

    //Service call To enrollemailMFA
    public void enrollEmailMFA(@NonNull final String code,@NonNull final String statusId, @NonNull final Result<EnrollEmailMFAResponseEntity> result)
    {
        try{
            LogFile.getShared(context).addInfoLog("Info of EmailConfiguration Controller :enrollEmailMFA()",
                    " Info code:-"+code+"statusId:-"+statusId+"Sub:-"+Sub);

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(final Dictionary<String, String> loginPropertiesresult) {
                    final String baseurl=loginPropertiesresult.get("DomainURL");

                    if(!Sub.equals("")  && code != null && !code.equals("") || statusId != null && !statusId.equals(""))
                    {
                        final EnrollEmailMFARequestEntity enrollEmailMFARequestEntity=new EnrollEmailMFARequestEntity();
                        enrollEmailMFARequestEntity.setCode(code);
                        enrollEmailMFARequestEntity.setStatusId(statusId);

                        AccessTokenController.getShared(context).getAccessToken(Sub, new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessresult) {
                                LogFile.getShared(context).addInfoLog("Info of EmailConfiguration Controller :enrollEmailMFA()",
                                        " Info AccessToken"+accessresult.getAccess_token());

                                    //Done Service call
                                    EmailVerificationService.getShared(context).enrollEmailMFA(baseurl, accessresult.getAccess_token(),
                                            enrollEmailMFARequestEntity,null, result);

                            }

                            @Override
                            public void failure(WebAuthError error) {
                                result.failure(error);
                            }
                        });
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
            result.failure(WebAuthError.getShared(context).methodException("Exception :Email configuration Controller :enrollEmailMFA()",
                    WebAuthErrorCode.ENROLL_EMAIL_MFA_FAILURE,e.getMessage()));
        }
    }



    public void loginWithEmail(PasswordlessEntity passwordlessEntity, final Result<InitiateEmailMFAResponseEntity> result)
    {
        try{


            final InitiateEmailMFARequestEntity initiateEmailMFARequestEntity=getInitiateEmailMFAEntity(passwordlessEntity, result);

            if (initiateEmailMFARequestEntity==null) {
                return;
            }

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesresult) {
                    //Todo Service call

                    String baseurl=loginPropertiesresult.get("DomainURL");
                    EmailVerificationService.getShared(context).initiateEmailMFA(baseurl, initiateEmailMFARequestEntity, null,result);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException("Exception :Email configuration Controller :loginWithEmail()"
                    ,WebAuthErrorCode.AUTHENTICATE_EMAIL_MFA_FAILURE,e.getMessage()));
        }
    }

    private InitiateEmailMFARequestEntity getInitiateEmailMFAEntity(PasswordlessEntity passwordlessEntity, Result<InitiateEmailMFAResponseEntity> result) {

     try {
         if (passwordlessEntity.getUsageType() != null && !passwordlessEntity.getUsageType().equals("")) {

             if (((passwordlessEntity.getSub() == null || passwordlessEntity.getSub().equals("")) &&
                     (passwordlessEntity.getEmail() == null || passwordlessEntity.getEmail().equals("")) &&
                     (passwordlessEntity.getMobile() == null || passwordlessEntity.getMobile().equals("")))) {
                 String errorMessage = "sub or email or mobile number must not be empty";

                 result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,
                         "Error :Email configuration Controller :getInitiateEmailMFAEntity()"));
                 return null;
             }

             if (passwordlessEntity.getUsageType().equals(UsageType.MFA)) {
                 if (passwordlessEntity.getTrackId() == null || passwordlessEntity.getTrackId() == "") {
                     String errorMessage = "trackId must not be empty";

                     result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,"" +
                             "Error :Email configuration Controller :getInitiateEmailMFAEntity()"));
                     return null;
                 }
             }
        LogFile.getShared(context).addInfoLog("Info :Email configuration Controller :getInitiateEmailMFAEntity()",
        "UsageType:-"+passwordlessEntity.getUsageType()+" Sub:- "+passwordlessEntity.getSub()+" Email"+passwordlessEntity.getEmail()+
                " Mobile"+passwordlessEntity.getMobile()+" RequestId:-"+passwordlessEntity.getRequestId()+" TrackId:-"+passwordlessEntity.getTrackId());

             TrackId = passwordlessEntity.getTrackId();
             RequestId = passwordlessEntity.getRequestId();
             UsageTypefromEmail = passwordlessEntity.getUsageType();

             InitiateEmailMFARequestEntity initiateEmailMFARequestEntity = new InitiateEmailMFARequestEntity();
             initiateEmailMFARequestEntity.setSub(passwordlessEntity.getSub());
             initiateEmailMFARequestEntity.setUsageType(passwordlessEntity.getUsageType());
             initiateEmailMFARequestEntity.setVerificationType("email");
             initiateEmailMFARequestEntity.setEmail(passwordlessEntity.getEmail());
             initiateEmailMFARequestEntity.setMobile(passwordlessEntity.getMobile());
             return initiateEmailMFARequestEntity;


         } else {

             result.failure(WebAuthError.getShared(context).propertyMissingException("Sub or Usage Type must not be empty",
                     "Error :Email configuration Controller :getInitiateEmailMFAEntity()"));
             return null;
         }
     }
     catch (Exception e)
     {
         result.failure(WebAuthError.getShared(context).methodException("Exception :Email configuration Controller :getInitiateEmailMFAEntity()"
                 ,WebAuthErrorCode.INITIATE_EMAIL_MFA_FAILURE,e.getMessage()));
         return null;
     }
    }

    public void verifyEmail(@NonNull final String code,@NonNull final String statusId, final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{
            LogFile.getShared(context).addInfoLog("Info :Email configuration Controller :verifyEmail()",
                    "Code:-"+code+"statusId:-"+statusId);

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(final Dictionary<String, String> result) {
                    final String baseurl=result.get("DomainURL");
                    final String clientId=result.get("ClientId");

                    final AuthenticateEmailRequestEntity authenticateEmailRequestEntity = new AuthenticateEmailRequestEntity();

                    if (code != null && !code.equals("") && statusId!=null && !statusId.equals("")) {

                        authenticateEmailRequestEntity.setCode(code);
                        authenticateEmailRequestEntity.setStatusId(statusId);

                    } else {
                        loginresult.failure(WebAuthError.getShared(context).propertyMissingException("Code must not be empty",
                                "Error :Email configuration Controller :verifyEmail()"));
                    }


                    EmailVerificationService.getShared(context).authenticateEmailMFA(baseurl, authenticateEmailRequestEntity,null,
                            new Result<AuthenticateEmailResponseEntity>() {

                                @Override
                                public void success(AuthenticateEmailResponseEntity serviceresult) {
                                    LogFile.getShared(context).addSuccessLog("Success :Email configuration Controller :verifyEmail()",
                                            "Sub:-"+serviceresult.getData().getSub()+"TrackingCode"+serviceresult.getData().getTrackingCode());

                                    ResumeLogin.getShared(context).resumeLoginAfterSuccessfullAuthentication(serviceresult.getData().getSub(),
                                            serviceresult.getData().getTrackingCode(),"email",UsageTypefromEmail,clientId,RequestId,
                                            TrackId,baseurl,loginresult);
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
            loginresult.failure(WebAuthError.getShared(context).methodException("Exception :Email configuration Controller :verifyEmail()"
                    ,WebAuthErrorCode.AUTHENTICATE_EMAIL_MFA_FAILURE,e.getMessage()));
        }
    }
}

