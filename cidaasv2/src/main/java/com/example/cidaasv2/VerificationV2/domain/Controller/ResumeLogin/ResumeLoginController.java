package com.example.cidaasv2.VerificationV2.domain.Controller.ResumeLogin;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.VerificationV2.data.Entity.ResumeLogin.ResumeLoginEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.ResumeLogin.ResumeLoginResponse;
import com.example.cidaasv2.VerificationV2.data.Service.Helper.VerificationURLHelper;
import com.example.cidaasv2.VerificationV2.domain.Controller.ResumeLogin.ResumeLoginController;
import com.example.cidaasv2.VerificationV2.domain.Service.ResumeLogin.ResumeLoginService;

import java.util.Dictionary;
import java.util.Map;

public class ResumeLoginController {
    //Local Variables
    private Context context;


    public static ResumeLoginController shared;

    public ResumeLoginController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static ResumeLoginController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ResumeLoginController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("ResumeLoginController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //--------------------------------------------ResumeLogin--------------------------------------------------------------
    public void resumeLoginVerification(final ResumeLoginEntity resumeLoginEntity, final Result<LoginCredentialsResponseEntity> resumeLoginResponseResult)
    {
        checkResumeLoginEntity(resumeLoginEntity,resumeLoginResponseResult);
    }


    //-------------------------------------checkResumeLoginEntity-----------------------------------------------------------
    private void checkResumeLoginEntity(final ResumeLoginEntity resumeLoginEntity, final Result<LoginCredentialsResponseEntity> ResumeLoginResult)
    {
        String methodName = "ResumeLoginController:-checkResumeLoginEntity()";
        try {
            if (resumeLoginEntity.getVerificationType() != null && !resumeLoginEntity.getVerificationType().equals("") && resumeLoginEntity.getSub() != null &&
                    !resumeLoginEntity.getSub().equals("")&& resumeLoginEntity.getStatus_id() != null && !resumeLoginEntity.getStatus_id().equals("")) {

                addProperties(resumeLoginEntity,ResumeLoginResult);
            }
            else
            {
                ResumeLoginResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub or Status_id must not be null",
                        "Error:"+methodName));
                return;
            }
        }
        catch (Exception e) {
            ResumeLoginResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------Add Device info and pushnotificationId-------------------------------------------------------
    private void addProperties(final ResumeLoginEntity resumeLoginEntity, final Result<LoginCredentialsResponseEntity> resumeLoginResponseResult)
    {
        String methodName = "ResumeLoginController:-addProperties()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(final Dictionary<String, String> loginPropertiesResult) {
                    final String baseurl = loginPropertiesResult.get("DomainURL");
                    final String clientId=loginPropertiesResult.get("ClientId");


                    //App properties
                    if(resumeLoginEntity.getRequestId()==null || resumeLoginEntity.getRequestId().equals(""))
                    {
                        Cidaas.getInstance(context).getRequestId(new Result<AuthRequestResponseEntity>() {
                            @Override
                            public void success(AuthRequestResponseEntity result) {
                                resumeLoginEntity.setRequestId(result.getData().getRequestId());
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                resumeLoginResponseResult.failure(error);
                            }
                        });
                    }

                    //call ResumeLogin call
                    callResumeLogin(baseurl,resumeLoginEntity,resumeLoginResponseResult);
                }
                @Override
                public void failure(WebAuthError error) {
                    resumeLoginResponseResult.failure(error);
                }
            });

        }
        catch (Exception e) {
            resumeLoginResponseResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                    e.getMessage()));
        }
    }

    //-------------------------------------------Call ResumeLogin Service-----------------------------------------------------------
    private void callResumeLogin(String baseurl,final ResumeLoginEntity ResumeLoginEntity, final Result<LoginCredentialsResponseEntity> resumeLoginResult)
    {
        String methodName = "ResumeLoginController:-callResumeLogin()";
        try
        {
            String ResumeLoginUrl= VerificationURLHelper.getShared().getPasswordlessContinueUrl(baseurl);

            //headers Generation
            Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

            //ResumeLogin Service call
            ResumeLoginService.getShared(context).callResumeLoginService(ResumeLoginUrl, headers, ResumeLoginEntity, new Result<ResumeLoginResponseEntity>() {
                @Override
                public void success(ResumeLoginResponseEntity result) {

                    AccessTokenController.getShared(context).getAccessTokenByCode(result.getData().getCode(), new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity accessTokenresult) {
                            LoginCredentialsResponseEntity loginCredentialsResponseEntity = new LoginCredentialsResponseEntity();
                            loginCredentialsResponseEntity.setData(accessTokenresult);
                            loginCredentialsResponseEntity.setStatus(200);
                            loginCredentialsResponseEntity.setSuccess(true);
                            resumeLoginResult.success(loginCredentialsResponseEntity);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            resumeLoginResult.failure(error);
                        }
                    });

                }

                @Override
                public void failure(WebAuthError error) {
                    resumeLoginResult.failure(error);
                }
            });
        }
        catch (Exception e) {
            resumeLoginResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                    e.getMessage()));
        }
    }
}
