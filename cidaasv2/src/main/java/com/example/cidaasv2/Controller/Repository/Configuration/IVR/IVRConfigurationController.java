package com.example.cidaasv2.Controller.Repository.Configuration.IVR;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.ResumeLogin.ResumeLogin;
import com.example.cidaasv2.Controller.Repository.UserProfile.UserProfileController;
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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.example.cidaasv2.Service.Repository.Verification.IVR.IVRVerificationService;

import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class IVRConfigurationController {


    private String UsageTypeFromIVR,TrackId,RequestId,Sub;
    private Context context;

    public static IVRConfigurationController shared;

    public IVRConfigurationController(Context contextFromCidaas) {

        UsageTypeFromIVR="";
        context=contextFromCidaas;
        TrackId="";
        RequestId="";
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
            LogFile.getShared(contextFromCidaas).addFailureLog("IVRConfigurationController instance Creation Exception:-"+e.getMessage());
        }
        return shared;
    }

    // --------------------------------------------------***** IVR MFA *****--------------------------------------------------------------------------------------



    public void configureIVR(@NonNull final String sub, @NonNull final Result<SetupIVRMFAResponseEntity> result){
        try{
            LogFile.getShared(context).addInfoLog("Info of IVRConfiguration Controller :configureIVR()", " Info Sub:-"+sub);

            if ( sub != null && !sub.equals("")) {
                Sub=sub;
                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {
                        //done Service call
                        LogFile.getShared(context).addSuccessLog("Success :IVR Configuration Controller :configureIVR()",
                                "AccessToken"+accessTokenresult.getAccess_token()+"RefreshToken"+accessTokenresult.getRefresh_token()+
                                        "ExpiresIn"+accessTokenresult.getExpires_in());

                        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                            @Override
                            public void success(Dictionary<String, String> loginPropertiesresult) {
                                final String baseurl=loginPropertiesresult.get("DomainURL");

                                UserProfileController.getShared(context).getUserProfile(sub, new Result<UserinfoEntity>() {
                                    @Override
                                    public void success(UserinfoEntity userresult) {

                                        if(userresult.getMobile_number()!=null && !userresult.getMobile_number().equals("")) {

                                            //Done add phone number
                                            IVRVerificationService.getShared(context).setupIVRMFA(baseurl, accessTokenresult.getAccess_token(),
                                                    userresult.getMobile_number(), null, result);
                                        }
                                        else
                                        {
                                            result.failure(WebAuthError.getShared(context).propertyMissingException("Mobile number must not be null",
                                                    "Error :IVRConfigurationController :configureIVR()"));
                                        }
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

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });

            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException("BaseURL or sub must not be null",
                        "Error :IVRConfigurationController :configureIVR()"));
            }
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException("Exception :IVRConfigurationController :configureIVR()",
                    WebAuthErrorCode.ENROLL_IVR_MFA_FAILURE,e.getMessage()));
        }
    }

    //Service call To enrollIVRMFA
    public void enrollIVRMFA(@NonNull final String code,String StatusId, @NonNull final Result<EnrollIVRMFAResponseEntity> result)
    {
        try{
            LogFile.getShared(context).addInfoLog("Info of IVRConfiguration Controller :enrollIVRMFA()",
                    " Info code:-"+code+"statusId:-"+StatusId+"Sub:-"+Sub);

            if(!Sub.equals("") && !StatusId.equals("") && !code.equals("")  && StatusId!=null && code!=null  )
            {
                final EnrollIVRMFARequestEntity enrollIVRMFARequestEntity=new EnrollIVRMFARequestEntity();
                enrollIVRMFARequestEntity.setCode(code);
                enrollIVRMFARequestEntity.setStatusId(StatusId);
                enrollIVRMFARequestEntity.setSub(Sub);

                AccessTokenController.getShared(context).getAccessToken(Sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessresult) {

                        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                            @Override
                            public void success(Dictionary<String, String> loginPropertiesResult) {
                                LogFile.getShared(context).addInfoLog("Info of IVRConfiguration Controller :enrollIVRMFA()",
                                        " Info AccessToken"+accessresult.getAccess_token());

                                IVRVerificationService.getShared(context).enrollIVRMFA(loginPropertiesResult.get("DomainURL"),
                                        accessresult.getAccess_token(), enrollIVRMFARequestEntity, null,result);
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
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException("Exception :IVRConfigurationController :enrollIVRMFA()",
                    WebAuthErrorCode.ENROLL_IVR_MFA_FAILURE,e.getMessage()));
        }
    }



    public void loginWithIVR(@NonNull final PasswordlessEntity passwordlessEntity, final Result<InitiateIVRMFAResponseEntity> result)
    {
        try{

            final InitiateIVRMFARequestEntity initiateIVRMFARequestEntity=getInitiateIVRMFAEntity(passwordlessEntity, result);

            if (initiateIVRMFARequestEntity==null) {
                return;
            }

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    IVRVerificationService.getShared(context).initiateIVRMFA(loginPropertiesResult.get("DomainURL"), initiateIVRMFARequestEntity,null, result);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });


        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
            LogFile.getShared(context).addFailureLog("Login With IVR"+e.getMessage()+WebAuthErrorCode.AUTHENTICATE_IVR_MFA_FAILURE);
            result.failure(WebAuthError.getShared(context).methodException("Exception :IVRConfigurationController :loginWithIVR()",WebAuthErrorCode.AUTHENTICATE_IVR_MFA_FAILURE,e.getMessage()));
        }
    }


    private InitiateIVRMFARequestEntity getInitiateIVRMFAEntity(PasswordlessEntity passwordlessEntity, Result<InitiateIVRMFAResponseEntity> result) {
        try {
            if (passwordlessEntity.getUsageType() != null && !passwordlessEntity.getUsageType().equals("")) {

                if (((passwordlessEntity.getSub() == null || passwordlessEntity.getSub().equals("")) &&
                        (passwordlessEntity.getEmail() == null || passwordlessEntity.getEmail().equals("")) &&
                        (passwordlessEntity.getMobile() == null || passwordlessEntity.getMobile().equals("")))) {
                    String errorMessage = "sub or email or mobile number must not be empty";

                    result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, "Error :IVRConfigurationController :getInitiateIVRMFAEntity()"));
                    return null;
                }

                if (passwordlessEntity.getUsageType().equals(UsageType.MFA)) {
                    if (passwordlessEntity.getTrackId() == null || passwordlessEntity.getTrackId() == "") {
                        String errorMessage = "trackId must not be empty";

                        result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage,"Error :IVRConfigurationController :getInitiateIVRMFAEntity()"));
                        return null;
                    }
                }

                LogFile.getShared(context).addInfoLog("Info :IVR configuration Controller :getInitiateIVRMFAEntity()",
                        "UsageType:-" + passwordlessEntity.getUsageType() + " Sub:- " + passwordlessEntity.getSub() + " Email" + passwordlessEntity.getEmail() +
                                " Mobile" + passwordlessEntity.getMobile() + " RequestId:-" + passwordlessEntity.getRequestId() +
                                " TrackId:-" + passwordlessEntity.getTrackId());

                TrackId = passwordlessEntity.getTrackId();
                RequestId = passwordlessEntity.getRequestId();
                UsageTypeFromIVR = passwordlessEntity.getUsageType();

                InitiateIVRMFARequestEntity initiateIVRMFARequestEntity = new InitiateIVRMFARequestEntity();
                initiateIVRMFARequestEntity.setSub(passwordlessEntity.getSub());
                initiateIVRMFARequestEntity.setUsageType(passwordlessEntity.getUsageType());
                initiateIVRMFARequestEntity.setVerificationType("IVR");
                return initiateIVRMFARequestEntity;


            } else {

                result.failure(WebAuthError.getShared(context).propertyMissingException("Sub or Usage Type must not be empty",
                        "Error :IVRConfigurationController :getInitiateIVRMFAEntity()"));
                return null;
            }

        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :IVRConfigurationController :getInitiateIVRMFAEntity()",
                    WebAuthErrorCode.AUTHENTICATE_IVR_MFA_FAILURE,e.getMessage()));
            return null;
        }
    }


    public void verifyIVR(@NonNull final String code, @NonNull final String statusId,final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{
            LogFile.getShared(context).addInfoLog("Info :IVR configuration Controller :verifyIVR()",
                    "Code:-"+code+"statusId:-"+statusId);

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(final Dictionary<String, String> result) {
                    final String baseurl=result.get("DomainURL");
                    final String clientId=result.get("ClientId");

                    final AuthenticateIVRRequestEntity authenticateIVRRequestEntity = new AuthenticateIVRRequestEntity();

                    if (code != null && !code.equals("") && statusId!=null && !statusId.equals("")) {

                        authenticateIVRRequestEntity.setCode(code);
                        authenticateIVRRequestEntity.setStatusId(statusId);

                    } else {
                        loginresult.failure(WebAuthError.getShared(context).propertyMissingException("Code must not be empty",
                                "Error :IVRConfigurationController :verifyIVR()"));
                    }


                    IVRVerificationService.getShared(context).authenticateIVRMFA(baseurl, authenticateIVRRequestEntity,null,
                            new Result<AuthenticateIVRResponseEntity>() {

                                @Override
                                public void success(AuthenticateIVRResponseEntity serviceresult) {
                                    LogFile.getShared(context).addSuccessLog("Success :IVR configuration Controller :verifyIVR()",
                                            "Sub:-"+serviceresult.getData().getSub()+"TrackingCode"+serviceresult.getData().getTrackingCode());

                                    ResumeLogin.getShared(context).resumeLoginAfterSuccessfullAuthentication(serviceresult.getData().getSub(),
                                            serviceresult.getData().getTrackingCode(),"IVR",UsageTypeFromIVR,clientId,RequestId,
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
             loginresult.failure(WebAuthError.getShared(context).methodException("Exception :IVRConfigurationController :verifyIVR()",
                     WebAuthErrorCode.AUTHENTICATE_IVR_MFA_FAILURE,e.getMessage()));
        }
    }


}
