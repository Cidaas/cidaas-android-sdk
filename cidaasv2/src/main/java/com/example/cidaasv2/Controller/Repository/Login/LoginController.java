package com.example.cidaasv2.Controller.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;
import com.example.cidaasv2.Service.Repository.Login.LoginService;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import androidx.annotation.NonNull;
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


    //Service call for loginWithCredentials
    public void loginwithCredentials(@NonNull String baseurl,@NonNull LoginCredentialsRequestEntity loginEntity,
                                     @NonNull final Result<LoginCredentialsResponseEntity> result) {
        try {
            if (baseurl!=null && !baseurl.equals("")) {
                //Todo Service call
                LoginService.getShared(context).loginWithCredentials(baseurl, loginEntity,null, new Result<LoginCredentialsResponseEntity>() {
                    @Override
                    public void success(LoginCredentialsResponseEntity serviceresult) {
                        //Resume Login
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
            } else {

                result.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl must not be null"));
            }

        } catch (Exception e) {
            String errorMessage="Login with Credentials Exception"+e.getMessage();
            LogFile.getShared(context).addRecordToLog("Login with credentials"+errorMessage+WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE);
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE));

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
                LoginService.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, null,new Result<ResumeLoginResponseEntity>() {
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
                result.failure(WebAuthError.getShared(context).propertyMissingException("Tracking code, verification type or baseurl must not be empty"));
            }

        }
        catch (Exception e)
        {
            Timber.e("Service call Excception"+e.getMessage());
            LogFile.getShared(context).addRecordToLog("Continue MFA Exception:"+e.getMessage()+WebAuthErrorCode.RESUME_LOGIN_FAILURE);
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.RESUME_LOGIN_FAILURE));
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
                     baseurl != null && !baseurl.equals("")) {

                //Done Service call
                LoginService.getShared(context).continuePasswordless(baseurl, resumeLoginRequestEntity,null, new Result<ResumeLoginResponseEntity>() {
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

                result.failure(WebAuthError.getShared(context).propertyMissingException("ClientId , sub , Track code, base url must not be empty"));
            }

        }
        catch (Exception e)
        {
            Timber.e("Service call Excception");
            LogFile.getShared(context).addRecordToLog("Continue Passwordless Exception:"+e.getMessage()+WebAuthErrorCode.RESUME_LOGIN_FAILURE);
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.RESUME_LOGIN_FAILURE));
        }
    }


    //Get login URL for Custom browser and Webview
    public void getLoginURL( @NonNull final String baseurl, Dictionary<String, String> loginProperties,
                             final Dictionary<String, String> challengePropertiesfromparam, @NonNull final Result<String> callbackResult)
    {
        try
        {
          LoginService.getShared(context).getURLList(baseurl, new Result<Object>() {
              @Override
              public void success(Object result) {

                  LinkedHashMap<String, String> urlList = new LinkedHashMap<>();
                  urlList = (LinkedHashMap<String, String>) result;

                  Dictionary<String,String> loginProperties= DBHelper.getShared().getLoginProperties(baseurl);

                  //////////////////This is for testing purpose
                  Dictionary<String,String> challengeProperties=new Hashtable<>();

                  if(challengePropertiesfromparam==null) {
                      challengeProperties=DBHelper.getShared().getChallengeProperties();
                  }
                  else if(challengePropertiesfromparam!=null)
                  {
                      challengeProperties=challengePropertiesfromparam;
                  }
                  else
                  {
                      challengeProperties=new Hashtable<>();
                  }




                  String authzURL=urlList.get("authorization_endpoint");
                  String clientId=loginProperties.get("ClientId");
                  String redirectURL=loginProperties.get("RedirectURL");
                  String challenge=challengeProperties.get("Challenge");

                  if(clientId!=null && !clientId.equals("") && redirectURL!=null && !redirectURL.equals("") && challenge!=null && !challenge.equals("")) {

                     String finalURL= URLHelper.getShared().constructLoginURL(authzURL, clientId, redirectURL, challenge, "login");
                    if(finalURL!=null && !finalURL.equals("")) {
                        callbackResult.success(finalURL);
                    }
                    else
                    {
                        callbackResult.failure(WebAuthError.getShared(context).loginURLMissingException());
                    }
                  }
                  else
                  {
                      callbackResult.failure(WebAuthError.getShared(context)
                              .customException(WebAuthErrorCode.PROPERTY_MISSING,"ClientId or RedirectURL or Challenge must not be empty"
                                      ,HttpStatusCode.EXPECTATION_FAILED));
                  }
              }

              @Override
              public void failure(WebAuthError error) {
                callbackResult.failure(error);
              }
          });
        }
        catch (Exception e)
        {
            callbackResult.failure(WebAuthError.getShared(context)
                    .customException(WebAuthErrorCode.GET_LOGIN_URL_FAILURE,e.getMessage()
                            ,HttpStatusCode.EXPECTATION_FAILED));
        }
    }


    //Get login URL for Custom browser
    public void getSocialLoginURL(@NonNull final String baseurl, String provider, String requestId, @NonNull final Result<String> callbackResult)
    {
        try
        {
          if(baseurl!=null && !baseurl.equals("") && provider!=null && !provider.equals("") &&  requestId!=null && !requestId.equals(""))
          {
              String finalURL= URLHelper.getShared().constructSocialURL(baseurl, provider, requestId);

              if(finalURL!=null && !finalURL.equals("")) {
                  callbackResult.success(finalURL);
              }
              else
              {
                  callbackResult.failure(WebAuthError.getShared(context).loginURLMissingException());
              }

          }
          else
          {
              callbackResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.GET_SOCIAL_LOGIN_URL_FAILURE,"BaseURL or provider or requestid must not be null"
                      ,HttpStatusCode.EXPECTATION_FAILED));
          }
        }
        catch (Exception e)
        {
            callbackResult.failure(WebAuthError.getShared(context)
                    .customException(WebAuthErrorCode.GET_SOCIAL_LOGIN_URL_FAILURE,e.getMessage()
                            ,HttpStatusCode.EXPECTATION_FAILED));
        }
    }



}
