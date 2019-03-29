package com.example.cidaasv2.Controller.Repository.Login;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.CustomTab.Helper.CustomTabHelper;
import com.example.cidaasv2.Helper.Entity.LoginEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;
import com.example.cidaasv2.Service.Repository.Login.LoginService;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import timber.log.Timber;

public class LoginController {

    private Context context;
    public String loginURL;
    //public String DomainURL="";
    public String redirectUrl;
    public Result<AccessTokenEntity> logincallback;

    public static LoginController shared;

    public LoginController(Context contextFromCidaas) {

        //Set Callback Null;
        logincallback = null;
        context = contextFromCidaas;
        //Todo setValue for authenticationType

        //Make Call back null

    }

    public static LoginController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new LoginController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //Service call for loginWithCredentials
    public void loginwithCredentials(@NonNull final String requestId, @NonNull final LoginEntity loginEntity,
                                     @NonNull final Result<LoginCredentialsResponseEntity> result) {
        try {


            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    LoginCredentialsRequestEntity loginCredentialsRequestEntity = getLoginCredentialsRequestEntity(requestId, loginEntity, result);
                    if (loginCredentialsRequestEntity != null) {
                        LoginService.getShared(context).loginWithCredentials(loginPropertiesResult.get("DomainURL"), loginCredentialsRequestEntity, new Result<LoginCredentialsResponseEntity>() {
                            @Override
                            public void success(LoginCredentialsResponseEntity serviceresult) {
                                getAccessTokenAfterLogin(serviceresult, result);
                            }

                            @Override
                            public void failure(WebAuthError error) {

                                result.failure(error);
                            }
                        });
                    } else {
                        result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE));
                    }
                }


                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });


        } catch (Exception e) {
            String errorMessage = "Login with Credentials Exception" + e.getMessage();
            LogFile.getShared(context).addRecordToLog("Login with credentials" + errorMessage + WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE);
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE));

        }
    }

    //Get Login Credentials Request Entity
    private LoginCredentialsRequestEntity getLoginCredentialsRequestEntity(String requestId, LoginEntity loginEntity, final Result<LoginCredentialsResponseEntity> result) {
        LoginCredentialsRequestEntity loginCredentialsRequestEntity = new LoginCredentialsRequestEntity();
        if (loginEntity.getUsername_type() == null && loginEntity.getUsername_type().equals("")) {
            loginEntity.setUsername_type("email");
        }

        if (loginEntity.getPassword() != null && !loginEntity.getPassword().equals("") && loginEntity.getUsername() != null
                && !loginEntity.getUsername().equals("") && requestId != null && !requestId.equals("")) {


            loginCredentialsRequestEntity.setUsername(loginEntity.getUsername());
            loginCredentialsRequestEntity.setUsername_type(loginEntity.getUsername_type());
            loginCredentialsRequestEntity.setPassword(loginEntity.getPassword());
            loginCredentialsRequestEntity.setRequestId(requestId);
            return loginCredentialsRequestEntity;

        } else {

            String errorMessage = "Username or password or RequestId must not be empty";
            result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage));
            return null;

        }

    }


    private void getAccessTokenAfterLogin(LoginCredentialsResponseEntity serviceresult, final Result<LoginCredentialsResponseEntity> result) {
        //Access Token After Login
        AccessTokenController.getShared(context).getAccessTokenByCode(serviceresult.getData().getCode(), new Result<AccessTokenEntity>() {
            @Override
            public void success(AccessTokenEntity accessTokenresult) {

                LoginCredentialsResponseEntity loginCredentialsResponseEntity = new LoginCredentialsResponseEntity();
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


    //Service call for Continue MFA
    public void continueMFA(@NonNull String baseurl, @NonNull ResumeLoginRequestEntity resumeLoginRequestEntity,
                            @NonNull final Result<LoginCredentialsResponseEntity> result) {
        try {
            if (resumeLoginRequestEntity.getTrack_id() != null && !resumeLoginRequestEntity.getTrack_id().equals("") &&
                    resumeLoginRequestEntity.getClient_id() != null && !resumeLoginRequestEntity.getClient_id().equals("") &&
                    resumeLoginRequestEntity.getSub() != null && !resumeLoginRequestEntity.getSub().equals("") &&
                    resumeLoginRequestEntity.getTrackingCode() != null && !resumeLoginRequestEntity.getTrackingCode().equals("") &&
                    resumeLoginRequestEntity.getVerificationType() != null && !resumeLoginRequestEntity.getVerificationType().equals("") &&
                    baseurl != null && !baseurl.equals("")) {

                //Done Service call
                LoginService.getShared(context).continueMFA(baseurl, resumeLoginRequestEntity, null, new Result<ResumeLoginResponseEntity>() {
                    @Override
                    public void success(final ResumeLoginResponseEntity serviceresult) {

                        //Done get Access Token by Code
                        AccessTokenController.getShared(context).getAccessTokenByCode(serviceresult.getData().getCode(), new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessTokenresult) {
                                LoginCredentialsResponseEntity loginCredentialsResponseEntity = new LoginCredentialsResponseEntity();
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
                result.failure(WebAuthError.getShared(context).propertyMissingException("Tracking code, verification type or baseurl must not be empty"));
            }

        } catch (Exception e) {
            Timber.e("Service call Excception" + e.getMessage());
            LogFile.getShared(context).addRecordToLog("Continue MFA Exception:" + e.getMessage() + WebAuthErrorCode.RESUME_LOGIN_FAILURE);
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.RESUME_LOGIN_FAILURE));
        }
    }


    //Service call for Continue PasswordLess
    public void continuePasswordless(@NonNull String baseurl, @NonNull ResumeLoginRequestEntity resumeLoginRequestEntity,
                                     @NonNull final Result<LoginCredentialsResponseEntity> result) {
        try {
            if (resumeLoginRequestEntity.getClient_id() != null && !resumeLoginRequestEntity.getClient_id().equals("") &&
                    resumeLoginRequestEntity.getSub() != null && !resumeLoginRequestEntity.getSub().equals("") &&
                    resumeLoginRequestEntity.getTrackingCode() != null && !resumeLoginRequestEntity.getTrackingCode().equals("") &&
                    baseurl != null && !baseurl.equals("")) {

                //Done Service call
                LoginService.getShared(context).continuePasswordless(baseurl, resumeLoginRequestEntity, null, new Result<ResumeLoginResponseEntity>() {
                    @Override
                    public void success(final ResumeLoginResponseEntity serviceresult) {

                        //Done get Access Token by Code
                        AccessTokenController.getShared(context).getAccessTokenByCode(serviceresult.getData().getCode(), new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessTokenresult) {

                                LoginCredentialsResponseEntity loginCredentialsResponseEntity = new LoginCredentialsResponseEntity();
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

                result.failure(WebAuthError.getShared(context).propertyMissingException("ClientId , sub , Track code, base url must not be empty"));
            }

        } catch (Exception e) {
            Timber.e("Service call Excception");
            LogFile.getShared(context).addRecordToLog("Continue Passwordless Exception:" + e.getMessage() + WebAuthErrorCode.RESUME_LOGIN_FAILURE);
            result.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.RESUME_LOGIN_FAILURE));
        }
    }


    //Get login URL for Custom browser and Webview
    public void getLoginURL(@NonNull final String baseurl, Dictionary<String, String> loginProperties,
                            final Dictionary<String, String> challengePropertiesfromparam, @NonNull final Result<String> callbackResult) {
        try {
            LoginService.getShared(context).getURLList(baseurl, new Result<Object>() {
                @Override
                public void success(Object result) {

                    LinkedHashMap<String, String> urlList = new LinkedHashMap<>();
                    urlList = (LinkedHashMap<String, String>) result;

                    Dictionary<String, String> loginProperties = DBHelper.getShared().getLoginProperties(baseurl);

                    //////////////////This is for testing purpose
                    Dictionary<String, String> challengeProperties = new Hashtable<>();

                    if (challengePropertiesfromparam == null) {
                        challengeProperties = DBHelper.getShared().getChallengeProperties();
                    } else if (challengePropertiesfromparam != null) {
                        challengeProperties = challengePropertiesfromparam;
                    } else {
                        challengeProperties = new Hashtable<>();
                    }


                    String authzURL = urlList.get("authorization_endpoint");
                    String clientId = loginProperties.get("ClientId");
                    String redirectURL = loginProperties.get("RedirectURL");
                    String challenge = challengeProperties.get("Challenge");

                    if (clientId != null && !clientId.equals("") && redirectURL != null && !redirectURL.equals("") && challenge != null && !challenge.equals("")) {

                        String finalURL = URLHelper.getShared().constructLoginURL(authzURL, clientId, redirectURL, challenge, "login");
                        if (finalURL != null && !finalURL.equals("")) {
                            callbackResult.success(finalURL);
                        } else {
                            callbackResult.failure(WebAuthError.getShared(context).loginURLMissingException());
                        }
                    } else {
                        callbackResult.failure(WebAuthError.getShared(context)
                                .customException(WebAuthErrorCode.PROPERTY_MISSING, "ClientId or RedirectURL or Challenge must not be empty"
                                        , HttpStatusCode.EXPECTATION_FAILED));
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    callbackResult.failure(error);
                }
            });
        } catch (Exception e) {
            callbackResult.failure(WebAuthError.getShared(context)
                    .customException(WebAuthErrorCode.GET_LOGIN_URL_FAILURE, e.getMessage()
                            , HttpStatusCode.EXPECTATION_FAILED));
        }
    }


    //Get login URL for Custom browser
    public void getSocialLoginURL(@NonNull final String baseurl, String provider, String requestId, @NonNull final Result<String> callbackResult) {
        try {
            if (baseurl != null && !baseurl.equals("") && provider != null && !provider.equals("") && requestId != null && !requestId.equals("")) {
                String finalURL = URLHelper.getShared().constructSocialURL(baseurl, provider, requestId);

                if (finalURL != null && !finalURL.equals("")) {
                    callbackResult.success(finalURL);
                } else {
                    callbackResult.failure(WebAuthError.getShared(context).loginURLMissingException());
                }

            } else {
                callbackResult.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.GET_SOCIAL_LOGIN_URL_FAILURE, "BaseURL or provider or requestid must not be null"
                        , HttpStatusCode.EXPECTATION_FAILED));
            }
        } catch (Exception e) {
            callbackResult.failure(WebAuthError.getShared(context)
                    .customException(WebAuthErrorCode.GET_SOCIAL_LOGIN_URL_FAILURE, e.getMessage()
                            , HttpStatusCode.EXPECTATION_FAILED));
        }
    }


    //Get Login With Browser
    public void loginWithBrowser(@NonNull final Context activityContext, @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
        try {

            getLoginURL(new Result<String>() {
                @Override
                public void success(String result) {
                    loginURL = result;
                    logincallback = callbacktoMain;
                    if (loginURL != null) {
                        String url = loginURL;
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        builder.setShowTitle(true);//TO show title

                        //  CustomTabsClient.getPackageName()
                        builder.setStartAnimations(activityContext, android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                        if (color != null) {

                            builder.setToolbarColor(Color.parseColor(color));
                        }

                        CustomTabsIntent customTabsIntent = builder.build();

                        String packageName = CustomTabHelper.getShared().getPackageNameToUse(context);

                        if (packageName != null && !packageName.equals("")) {
                            customTabsIntent.intent.setPackage(packageName);
                        }

                        customTabsIntent.launchUrl(activityContext, Uri.parse(url));
                    } else {
                        //TODo callback Failure
                        String loggerMessage = "LoginURL failure : " + "Error Code - ";
                        // +error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " +  error.statusCode;
                        LogFile.getShared(context).addRecordToLog(loggerMessage);
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    callbacktoMain.failure(error);
                }
            });


        } catch (Exception e) {
            Timber.d(e.getMessage());// TODO: Handle Exception
        }


    }

    public void loginWithSocial(@NonNull final Context activityContext, @NonNull final String requestId, @NonNull final String provider,
                                @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    getSocialLoginURL(result.get("DomainURL"),requestId, provider, new Result<String>() {
                        @Override
                        public void success(String socialLoginURL) {
                            logincallback = callbacktoMain;
                            if (socialLoginURL != null) {
                                String url = socialLoginURL;
                                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                builder.setShowTitle(true);//TO show title

                                //  CustomTabsClient.getPackageName()
                                builder.setStartAnimations(activityContext, android.R.anim.slide_in_left, android.R.anim.slide_out_right);


                                if (color != null) {

                                    builder.setToolbarColor(Color.parseColor(color));
                                }


                                CustomTabsIntent customTabsIntent = builder.build();

                                String packageName = CustomTabHelper.getShared().getPackageNameToUse(context);

                                if (packageName != null && !packageName.equals("")) {
                                    customTabsIntent.intent.setPackage(packageName);
                                }

                                customTabsIntent.launchUrl(activityContext, Uri.parse(url));
                            } else {
                                //TODo callback Failure
                                String loggerMessage = "LoginURL failure : " + "Error Code - ";
                                // +error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " +  error.statusCode;
                                LogFile.getShared(context).addRecordToLog(loggerMessage);
                            }
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            callbacktoMain.failure(error);
                        }
                    });

                }

                @Override
                public void failure(WebAuthError error) {
                    callbacktoMain.failure(error);
                }
            });


        } catch (Exception e) {
            Timber.d(e.getMessage());// TODO: Handle Exception
        }



    }

    //Private Methods
    private String getCodeFromUrl(String url) {
        try {
            String code = null;
            if (url.contains("code=")) {
                String[] codeComponents = url.split("code=");
                if (codeComponents.length > 1) {
                    codeComponents = codeComponents[1].split("&");
                    if (codeComponents.length > 0) {
                        code = codeComponents[0];
                        return code;
                    } else {
                        return code;
                    }
                }
            }
            return code;
        } catch (Exception e) {
            Timber.d(e.getMessage());
            return null;
            //Todo Handle Exception
        }
    }



    public void getLoginCode(String url, Result<AccessTokenEntity> callback) {
        try {
            // showLoader();
            String code = getCodeFromUrl(url);
            if (code != null) {
                //  hideLoader();

                AccessTokenController.getShared(context).getAccessTokenByCode(code, callback);
            } else {
                // hideLoader();
                String loggerMessage = "Request-Id params to dictionary conversion failure : " + "Error Code - ";
                //+error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " +  error.statusCode;
                LogFile.getShared(context).addRecordToLog(loggerMessage);
            }
        } catch (Exception e) {
            Timber.d(e.getMessage()); //Todo handle Exception
        }
    }

    public void handleToken(String code){   /*,Result<AccessTokenEntity> callbacktoMain*/

        if (logincallback != null) {

            getLoginCode(code, logincallback);
        }


        /* else if(callbacktoMain!=null)
       {
        *//*logincallback=()callbacktoMain;
        getLoginCode(code,callbacktoMain);*//*
       }*/
        //Todo Handle Else part and give Exception

    }

    //Get Login URL without any Argument
    public void getLoginURL( final Result<String> callback) {
        try {
            //Check requestId is not null

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {

                    //This is to generate the Code Challenge

                    /////This is for testing purpose
                    Dictionary<String,String> challengeProperties=DBHelper.getShared().getChallengeProperties();

                    if(challengeProperties.size()==0) {

                        /*getRequestId(new Result<AuthRequestResponseEntity>() {
                            @Override
                            public void success(AuthRequestResponseEntity result) {

                            }

                            @Override
                            public void failure(WebAuthError error) {

                            }
                        });*/
                    }


                    LoginController.getShared(context).getLoginURL(Cidaas.baseurl,result,null, new Result<String>() {
                        @Override
                        public void success(String result) {

                            callback.success(result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            callback.failure(error);
                            String loggerMessage = "Login URL service failure : " + "Error Code - "
                                    + error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " + error.statusCode;
                            LogFile.getShared(context).addRecordToLog(loggerMessage);
                        }
                    });
                }

                @Override
                public void failure(WebAuthError error) {
                    callback.failure(error);
                }
            });


        } catch (Exception ex) {
            //Todo Handle Error
            Timber.d(ex.getMessage());
        }
    }


  /*  public void loginWithSocial(@NonNull final Context activityContext,@NonNull final String requestId, @NonNull final String provider,
                                @Nullable final String color, final Result<AccessTokenEntity> callbacktoMain) {
        try {



            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    getSocialLoginURL(requestId,provider,new Result<String>() {
                        @Override
                        public void success(String socialLoginURL){
                            logincallback = callbacktoMain;
                            if (socialLoginURL != null) {
                                String url = socialLoginURL;
                                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                builder.setShowTitle(true);//TO show title

                                //  CustomTabsClient.getPackageName()
                                builder.setStartAnimations(activityContext, android.R.anim.slide_in_left, android.R.anim.slide_out_right);



                                if (color != null) {

                                    builder.setToolbarColor(Color.parseColor(color));
                                }


                                CustomTabsIntent customTabsIntent = builder.build();

                                String packageName= CustomTabHelper.getShared().getPackageNameToUse(context);

                                if(packageName!=null && !packageName.equals(""))
                                {
                                    customTabsIntent.intent.setPackage(packageName);
                                }

                                customTabsIntent.launchUrl(activityContext, Uri.parse(url));
                            } else {
                                //TODo callback Failure
                                String loggerMessage = "LoginURL failure : " + "Error Code - ";
                                // +error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " +  error.statusCode;
                                LogFile.getShared(context).addRecordToLog(loggerMessage);
                            }
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            callbacktoMain.failure(error);
                        }
                    });

                }

                @Override
                public void failure(WebAuthError error) {
                    callbacktoMain.failure(error);
                }
            });



        } catch (Exception e) {
            Timber.d(e.getMessage());// TODO: Handle Exception
        }
    }
*/

}
