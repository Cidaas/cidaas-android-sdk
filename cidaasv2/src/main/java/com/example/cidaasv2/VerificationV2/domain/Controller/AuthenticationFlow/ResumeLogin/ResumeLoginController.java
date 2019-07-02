package com.example.cidaasv2.VerificationV2.domain.Controller.AuthenticationFlow.ResumeLogin;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.UsageType;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.VerificationV2.data.Entity.ResumeLogin.ResumeLoginEntity;
import com.example.cidaasv2.VerificationV2.data.Service.Helper.VerificationURLHelper;
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
            if (resumeLoginEntity.getVerificationType() != null && !resumeLoginEntity.getVerificationType().equals("") &&
                    resumeLoginEntity.getSub() != null && !resumeLoginEntity.getSub().equals("")) {

                if(resumeLoginEntity.getStatus_id() == null || resumeLoginEntity.getStatus_id().equals("")
                        && resumeLoginEntity.getRequestId()==null || resumeLoginEntity.getRequestId().equals(""))
                {
                    ResumeLoginResult.failure(WebAuthError.getShared(context).propertyMissingException("Status_id or RequestId must not be null",
                            "Error:"+methodName));
                    return;
                }

                if(resumeLoginEntity.getUsageType().equals(UsageType.MFA))
                {
                    if(resumeLoginEntity.getTrackId()==null ||resumeLoginEntity.getTrackId().equals(""))
                    {
                        ResumeLoginResult.failure(WebAuthError.getShared(context).propertyMissingException("TrackId must not be null",
                                "Error:"+methodName));
                        return;
                    }
                }

                addProperties(resumeLoginEntity,ResumeLoginResult);
            }
            else
            {
                ResumeLoginResult.failure(WebAuthError.getShared(context).propertyMissingException("VerificationType or Sub must not be null",
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

                  if(resumeLoginEntity.getUsageType().equals(UsageType.MFA)) {
                      //call ResumeLogin call
                      String resumeLoginUrl= VerificationURLHelper.getShared().getMfaContinueCallUrl(baseurl,resumeLoginEntity.getTrackId());
                      callResumeLogin(resumeLoginUrl, resumeLoginEntity, resumeLoginResponseResult);
                  }
                  else if(resumeLoginEntity.getUsageType().equals(UsageType.PASSWORDLESS))
                  {
                      String resumeLoginUrl= VerificationURLHelper.getShared().getPasswordlessContinueUrl(baseurl);
                      callResumeLogin(resumeLoginUrl, resumeLoginEntity, resumeLoginResponseResult);
                  }
                }
                @Override
                public void failure(WebAuthError error) {
                    resumeLoginResponseResult.failure(error);
                }
            });
        }
        catch (Exception e) {
            resumeLoginResponseResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,
                    WebAuthErrorCode.RESUME_LOGIN_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------Call ResumeLogin Service-----------------------------------------------------------
    private void callResumeLogin(String resumeURL,final ResumeLoginEntity ResumeLoginEntity, final Result<LoginCredentialsResponseEntity> resumeLoginResult)
    {
        String methodName = "ResumeLoginController:-callPasswordlessResumeLogin()";
        try
        {
            //headers Generation
            Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

            //ResumeLogin Service call
            ResumeLoginService.getShared(context).callResumeLoginService(resumeURL, headers, ResumeLoginEntity, new Result<ResumeLoginResponseEntity>() {
                @Override
                public void success(ResumeLoginResponseEntity result) {
                    getAccessTokenFromCode(result,resumeLoginResult);
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

    public void getAccessTokenFromCode(ResumeLoginResponseEntity result,final Result<LoginCredentialsResponseEntity> resumeLoginResult) {
        String methodName = "ResumeLoginController:-getAccessTokenFromCode()";
        try {
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
        catch (Exception e)
        {
            resumeLoginResult.failure(WebAuthError.getShared(context).methodException("Exception:-" + methodName,WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                e.getMessage()));
        }
    }

}
