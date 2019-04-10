package com.example.cidaasv2.Controller.Repository.ResumeLogin;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.Login.LoginController;
import com.example.cidaasv2.Helper.Entity.PasswordlessEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Repository.Consent.ConsentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import timber.log.Timber;

public class ResumeLogin {

    public static ResumeLogin shared;
    private Context context;

    private ObjectMapper objectMapper=new ObjectMapper();

    public  ResumeLogin(Context contextFromCidaas) {
        context=contextFromCidaas;
    }


    public static ResumeLogin getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  ResumeLogin(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            // Timber.i(e.getMessage());
        }
        return shared;
    }

    public void resumeLoginAfterSuccessfullAuthentication(String sub, String trackingCode, String verificationType, String usageType,
                                                          String clientId,String requestId,String trackId, String baseURL,
                                                          Result<LoginCredentialsResponseEntity> loginresult) {
        try {

            ResumeLoginRequestEntity resumeLoginRequestEntity = new ResumeLoginRequestEntity();

            //Todo Check not Null values
            resumeLoginRequestEntity.setSub(sub);
            resumeLoginRequestEntity.setTrackingCode(trackingCode);
            resumeLoginRequestEntity.setVerificationType(verificationType);
            resumeLoginRequestEntity.setUsageType(usageType);
            resumeLoginRequestEntity.setClient_id(clientId);
            resumeLoginRequestEntity.setRequestId(requestId);

            if (usageType.equals(UsageType.MFA)) {

                resumeLoginRequestEntity.setTrack_id(trackId);
                LoginController.getShared(context).continueMFA(baseURL, resumeLoginRequestEntity, loginresult);

            } else if (usageType.equals(UsageType.PASSWORDLESS)) {

                resumeLoginRequestEntity.setTrack_id("");
                LoginController.getShared(context).continuePasswordless(baseURL, resumeLoginRequestEntity, loginresult);

            }
        }
        catch (Exception e)
        {
            loginresult.failure(WebAuthError.getShared(context).serviceException("Exception :ResumeLogin Controller :resumeLoginAfterSuccessfullAuthentication()",WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage()));
        }

    }


    public void resumeLoginAfterSuccessfullAuthentication(String sub, String trackingCode, String verificationType, PasswordlessEntity passwordlessEntity,
                                                          String clientId, String baseURL,
                                                          Result<LoginCredentialsResponseEntity> loginresult) {
        resumeLoginAfterSuccessfullAuthentication(sub,trackingCode,verificationType,passwordlessEntity.getUsageType(),clientId,passwordlessEntity.getRequestId()
        ,passwordlessEntity.getTrackId(),baseURL,loginresult);

    }


    public void resumeLoginAfterConsent(String baseurl,String sub,String trackId,String consentName,String consentVersion,String clientId,final Result<LoginCredentialsResponseEntity> loginresult)
    {
        try
        {
            ResumeConsentRequestEntity resumeConsentRequestEntity = new ResumeConsentRequestEntity();

            resumeConsentRequestEntity.setTrack_id(trackId);
            resumeConsentRequestEntity.setSub(sub);
            resumeConsentRequestEntity.setName(consentName);
            resumeConsentRequestEntity.setVersion(consentVersion);
            resumeConsentRequestEntity.setClient_id(clientId);


            ConsentService.getShared(context).resumeConsent(baseurl, resumeConsentRequestEntity, null, new Result<ResumeConsentResponseEntity>() {
                @Override
                public void success(ResumeConsentResponseEntity result) {

                    AccessTokenController.getShared(context).getAccessTokenByCode(result.getData().getCode(), new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity result) {
                            LoginCredentialsResponseEntity loginCredentialsResponseEntity = new LoginCredentialsResponseEntity();
                            loginCredentialsResponseEntity.setSuccess(true);
                            loginCredentialsResponseEntity.setStatus(200);
                            loginCredentialsResponseEntity.setData(result);
                            loginresult.success(loginCredentialsResponseEntity);
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
            loginresult.failure(WebAuthError.getShared(context).serviceException("Exception :ResumeLogin Controller :resumeLoginAfterConsent()",WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage()));
        }
    }
}
