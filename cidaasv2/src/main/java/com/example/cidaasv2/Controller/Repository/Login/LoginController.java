package com.example.cidaasv2.Controller.Repository.Login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;
import com.example.cidaasv2.Service.Repository.Login.LoginService;

import timber.log.Timber;

public class LoginController {

    private Context context;

    public static LoginController shared;

    public LoginController(Context contextFromCidaas) {

        context=contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static LoginController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new LoginController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }



   /* //Login with credentials username and password
    public void loginWithCredentials(@NonNull LoginCredentialsRequestEntity loginEntity, @NonNull final Result<LoginCredentialsResponseEntity> result) {
        try {
            //Check local DB
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

            else {
                String baseurl = "";
                final LoginCredentialsRequestEntity loginCredentialsRequestEntity;
                if (savedProperties.get("DomainURL").equals("") || savedProperties.get("DomainURL") == null || savedProperties == null) {
                    webAuthError = webAuthError.propertyMissingException();
                    String loggerMessage = "Request-Id readProperties failure : " + "Error Code - "
                            + webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage + ", Status Code - " + webAuthError.statusCode;
                    LogFile.addRecordToLog(loggerMessage);
                    result.failure(webAuthError);
                } else {
                    baseurl = savedProperties.get("DomainURL");

                   *//* loginCredentialsRequestEntity = new LoginCredentialsRequestEntity();
                    loginCredentialsRequestEntity.setUsername(loginEntity.getUsername());
                    loginCredentialsRequestEntity.setPassword(loginEntity.getPassword());
                    loginCredentialsRequestEntity.setRequestId(loginEntity.getRequestId());
                    loginCredentialsRequestEntity.setUsername_type(loginEntity.getUsernametype());*//*

                    //Call Private serice call Method
                    loginwithCredentialService(baseurl,loginEntity,result);

                }
            }
        }
        catch (Exception e){
            Timber.e("login with credentials"+e.getMessage());
        }
    }
*/

    //Service call for loginWithCredentials
    public void loginwithCredentials(@NonNull String baseurl,@NonNull LoginCredentialsRequestEntity loginEntity,
                                     @NonNull final Result<LoginCredentialsResponseEntity> result) {
        try {
            if (baseurl!=null) {
                //Todo Service call
                LoginService.getShared(context).loginWithCredentials(baseurl, loginEntity, new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity serviceresult) {
                        result.success(serviceresult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        result.failure(error);
                    }
                });
            } else {

                result.failure(WebAuthError.getShared(context).propertyMissingException());
            }

        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }


    //Service call for Continue MFA
    public void continueMFA(@NonNull String baseurl,@NonNull ResumeLoginRequestEntity resumeLoginRequestEntity,
                                    @NonNull final Result<LoginCredentialsResponseEntity> result)
    {
        try {
            if (resumeLoginRequestEntity.getTrack_id() != null && !resumeLoginRequestEntity.getTrack_id().equals("") &&
                    resumeLoginRequestEntity.getClient_id() != null && !resumeLoginRequestEntity.getClient_id().equals("") &&
                    resumeLoginRequestEntity.getSub() != null && !resumeLoginRequestEntity.getSub().equals("") &&
                    resumeLoginRequestEntity.getTrackingCode() != null && !resumeLoginRequestEntity.getTrackingCode().equals("") &&
                    resumeLoginRequestEntity.getVerificationType() != null && !resumeLoginRequestEntity.getVerificationType().equals("") &&
                    baseurl != null && !baseurl.equals("")) {

                //Done Service call
                LoginService.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, new Result<ResumeLoginResponseEntity>() {
                    @Override
                    public void success(final ResumeLoginResponseEntity serviceresult) {

                        //Done get Access Token by Code
                        AccessTokenController.getShared(context).getAccessTokenByCode(serviceresult.getData().getCode(), new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessTokenresult) {
                                LoginCredentialsResponseEntity loginCredentialsResponseEntity=new LoginCredentialsResponseEntity();
                                 loginCredentialsResponseEntity.setData(accessTokenresult);
                                 loginCredentialsResponseEntity.setStatus(200);
                                 loginCredentialsResponseEntity.setSuccess(true);
                                result.success(loginCredentialsResponseEntity);
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
            Timber.e("Service call Excception");
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }


    //Service call for Continue PasswordLess
    public void continuePasswordless(@NonNull String baseurl,@NonNull ResumeLoginRequestEntity resumeLoginRequestEntity,
                            @NonNull final Result<LoginCredentialsResponseEntity> result)
    {
        try {
            if ( resumeLoginRequestEntity.getClient_id() != null && !resumeLoginRequestEntity.getClient_id().equals("") &&
                    resumeLoginRequestEntity.getSub() != null && !resumeLoginRequestEntity.getSub().equals("") &&
                    resumeLoginRequestEntity.getTrackingCode() != null && !resumeLoginRequestEntity.getTrackingCode().equals("") &&
                    resumeLoginRequestEntity.getVerificationType() != null && !resumeLoginRequestEntity.getVerificationType().equals("") &&
                    baseurl != null && !baseurl.equals("")) {

                //Done Service call
                LoginService.getShared(context).continuePasswordless(baseurl, resumeLoginRequestEntity, new Result<ResumeLoginResponseEntity>() {
                    @Override
                    public void success(final ResumeLoginResponseEntity serviceresult) {

                        //Done get Access Token by Code
                        AccessTokenController.getShared(context).getAccessTokenByCode(serviceresult.getData().getCode(), new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessTokenresult) {

                                LoginCredentialsResponseEntity loginCredentialsResponseEntity=new LoginCredentialsResponseEntity();
                                loginCredentialsResponseEntity.setData(accessTokenresult);
                                loginCredentialsResponseEntity.setStatus(200);
                                loginCredentialsResponseEntity.setSuccess(true);
                                result.success(loginCredentialsResponseEntity);
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
            Timber.e("Service call Excception");
            result.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

}
