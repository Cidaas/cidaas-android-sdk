package com.example.cidaasv2.Controller.Repository.Configuration.SMS;

import android.content.Context;
import android.util.Log;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Controller.Repository.ResumeLogin.ResumeLogin;
import com.example.cidaasv2.Controller.Repository.UserProfile.UserProfileController;
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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.example.cidaasv2.Service.Repository.Verification.IVR.IVRVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.SMS.SMSVerificationService;
import com.example.cidaasv2.Service.Repository.Verification.SMS.SMSVerificationService;

import java.util.Dictionary;

import androidx.annotation.NonNull;
import timber.log.Timber;

import static com.example.cidaasv2.Controller.Cidaas.baseurl;

public class SMSConfigurationController {
    private String TrackId,RequestId,UsageTypeFromSMS,Sub;
    private String verificationType;
    private Context context;

    public static SMSConfigurationController shared;

    public SMSConfigurationController(Context contextFromCidaas) {

        verificationType="";
        context=contextFromCidaas;
        TrackId="";
        RequestId="";
        UsageTypeFromSMS="";
        Sub="";
        //Todo setValue for authenticationType

    }



    public void configureSMS(@NonNull final String sub, @NonNull final Result<SetupSMSMFAResponseEntity> result){
        try{

            Sub=sub;
            if (sub != null && !sub.equals("")) {

                AccessTokenController.getShared(context).getAccessToken(sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessTokenresult) {
                        // Service call
                        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                            @Override
                            public void success(final Dictionary<String, String> loginPropertiesResult) {

                                UserProfileController.getShared(context).getUserProfile(sub, new Result<UserinfoEntity>() {
                                    @Override
                                    public void success(UserinfoEntity userresult) {

                                        if (userresult.getMobile_number() != null && !userresult.getMobile_number().equals("")) {

                                            //Done add phone number
                                            SMSVerificationService.getShared(context).setupSMSMFA(loginPropertiesResult.get("DomainURL"), accessTokenresult.getAccess_token(), userresult.getMobile_number(),null,
                                                    result);
                                        } else {
                                            result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.USER_INFO_SERVICE_FAILURE, "Mobile number must not be null", HttpStatusCode.EXPECTATION_FAILED));
                                        }
                                    }

                                    @Override
                                    public void failure(WebAuthError error) {
                                        result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.USER_INFO_SERVICE_FAILURE, "Mobile number must not be null", HttpStatusCode.EXPECTATION_FAILED));
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
                result.failure(WebAuthError.getShared(context).propertyMissingException("Sub must not be null"));
            }
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).serviceException("Exception :SMSConfigurationController :configureSMS()",WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,e.getMessage()));
        }
    }

    //Service call To enrollSMSMFA
    public void enrollSMSMFA(@NonNull final String code,String StatusId,@NonNull final Result<EnrollSMSMFAResponseEntity> result)
    {
        try{

            if(!Sub.equals("") && !StatusId.equals("") && !code.equals("")  && StatusId!=null && code!=null  )
            {
                final EnrollSMSMFARequestEntity enrollSMSMFARequestEntity=new EnrollSMSMFARequestEntity();
                enrollSMSMFARequestEntity.setCode(code);
                enrollSMSMFARequestEntity.setStatusId(StatusId);
                enrollSMSMFARequestEntity.setSub(Sub);

                AccessTokenController.getShared(context).getAccessToken(Sub, new Result<AccessTokenEntity>() {
                    @Override
                    public void success(final AccessTokenEntity accessresult) {

                        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                            @Override
                            public void success(Dictionary<String, String> loginPropertiesResult) {
                                SMSVerificationService.getShared(context).enrollSMSMFA(loginPropertiesResult.get("DomainURL"),
                                        accessresult.getAccess_token(), enrollSMSMFARequestEntity, null,result);
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
            result.failure(WebAuthError.getShared(context).serviceException("Exception :SMSConfigurationController :enrollSMSMFA()",WebAuthErrorCode.ENROLL_SMS_MFA_FAILURE,e.getMessage()));
           
        }
    }


    public static SMSConfigurationController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new SMSConfigurationController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }



    public void loginWithSMS(@NonNull final PasswordlessEntity passwordlessEntity, final Result<InitiateSMSMFAResponseEntity> result)
    {
        try{

            final InitiateSMSMFARequestEntity initiateSMSMFARequestEntity=getInitiateSMSMFAEntity(passwordlessEntity, result);

            if (initiateSMSMFARequestEntity==null) {
                return;
            }

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    SMSVerificationService.getShared(context).initiateSMSMFA(loginPropertiesResult.get("DomainURL"), initiateSMSMFARequestEntity,null, result);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });


        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).serviceException("Exception :SMSConfigurationController :loginWithSMS()",WebAuthErrorCode.AUTHENTICATE_SMS_MFA_FAILURE,e.getMessage()));
        }
    }


    private InitiateSMSMFARequestEntity getInitiateSMSMFAEntity(PasswordlessEntity passwordlessEntity, Result<InitiateSMSMFAResponseEntity> result) {

        if (passwordlessEntity.getUsageType() != null && !passwordlessEntity.getUsageType().equals("")) {

            if (((passwordlessEntity.getSub() == null || passwordlessEntity.getSub().equals("")) &&
                    (passwordlessEntity.getEmail() == null || passwordlessEntity.getEmail().equals("")) &&
                    (passwordlessEntity.getMobile() == null || passwordlessEntity.getMobile().equals("")))) {
                String errorMessage = "sub or email or mobile number must not be empty";

                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PROPERTY_MISSING,
                        errorMessage, HttpStatusCode.EXPECTATION_FAILED));
                return null;
            }

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
            UsageTypeFromSMS=passwordlessEntity.getUsageType();

            InitiateSMSMFARequestEntity initiateSMSMFARequestEntity = new InitiateSMSMFARequestEntity();
            initiateSMSMFARequestEntity.setSub(passwordlessEntity.getSub());
            initiateSMSMFARequestEntity.setEmail(passwordlessEntity.getEmail());
            initiateSMSMFARequestEntity.setMobile(passwordlessEntity.getMobile());
            initiateSMSMFARequestEntity.setUsageType(passwordlessEntity.getUsageType());
            initiateSMSMFARequestEntity.setVerificationType("SMS");

            return initiateSMSMFARequestEntity;


        } else {

            result.failure(WebAuthError.getShared(context).propertyMissingException("Sub or Usage Type must not be empty"));
            return null;
        }

    }


    public void verifySMS(@NonNull final String code, @NonNull final String statusId,final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try{

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(final Dictionary<String, String> result) {
                    final String baseurl=result.get("DomainURL");
                    final String clientId=result.get("ClientId");

                    final AuthenticateSMSRequestEntity authenticateSMSRequestEntity = new AuthenticateSMSRequestEntity();

                    if (code != null && !code.equals("") && statusId!=null && !statusId.equals("")) {

                        authenticateSMSRequestEntity.setCode(code);
                        authenticateSMSRequestEntity.setStatusId(statusId);

                    } else {
                        loginresult.failure(WebAuthError.getShared(context).propertyMissingException("Code must not be empty"));
                    }


                    SMSVerificationService.getShared(context).authenticateSMSMFA(baseurl, authenticateSMSRequestEntity,null,
                            new Result<AuthenticateSMSResponseEntity>() {

                                @Override
                                public void success(AuthenticateSMSResponseEntity serviceresult) {

                                    ResumeLogin.getShared(context).resumeLoginAfterSuccessfullAuthentication(serviceresult.getData().getSub(),
                                            serviceresult.getData().getTrackingCode(),"SMS",UsageTypeFromSMS,clientId,RequestId,
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
            loginresult.failure(WebAuthError.getShared(context).serviceException("Exception :SMSConfigurationController :verifySMS()",WebAuthErrorCode.AUTHENTICATE_SMS_MFA_FAILURE,e.getMessage()));
        }
    }


    /*//Service call To SetupSMSMFA
    public void setupSMSMFA(@NonNull String AccessToken, @NonNull String baseurl, @NonNull final Result<SetupSMSMFAResponseEntity> result){
        try{

            if (baseurl != null && !baseurl.equals("") && AccessToken != null && !AccessToken.equals("")) {
                //Todo Service call
                SMSVerificationService.getShared(context).setupSMSMFA(baseurl, AccessToken,null, result);
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException("BaseURL or AccessToken must not be empty"));
            }
        }
        catch (Exception e)
        {
            Timber.e(e.getMessage());
            LogFile.getShared(context).addRecordToLog(e.getMessage()+WebAuthErrorCode.SETUP_SMS_MFA_FAILURE);
            result.failure(WebAuthError.getShared(context).serviceException("Exception :SMSConfigurationController :setAccessToken()",WebAuthErrorCode.SETUP_SMS_MFA_FAILURE));

        }
    }*/
}
