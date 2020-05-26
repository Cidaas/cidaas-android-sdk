package de.cidaas.sdk.android.cidaasnative.domain.Controller.Login;

import android.content.Context;

import androidx.annotation.NonNull;

import de.cidaas.sdk.android.cidaas.Controller.Repository.AccessToken.AccessTokenController;
import de.cidaas.sdk.android.cidaas.Helper.CidaasProperties.CidaasProperties;
import de.cidaas.sdk.android.cidaas.Helper.Entity.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Service.Entity.AccessToken.AccessTokenEntity;

import java.util.Dictionary;

import timber.log.Timber;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Login.LoginCredentialsRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Login.LoginEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.Login.NativeLoginService;

public class NativeLoginController {
    private Context context;
    // public String loginURL;
    //public String DomainURL="";
    public String redirectUrl;
    public Result<AccessTokenEntity> logincallback;

    public static NativeLoginController shared;

    public NativeLoginController(Context contextFromCidaas) {

        //Set Callback Null;
        logincallback = null;
        context = contextFromCidaas;
        //Make Call back null
    }

    public static NativeLoginController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new NativeLoginController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //Service call for loginWithCredentials
    public void loginwithCredentials(@NonNull final String requestId, @NonNull final LoginEntity loginEntity,
                                     @NonNull final Result<LoginCredentialsResponseEntity> result) {
        String methodName = "LoginController :loginwithCredentials()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    LoginCredentialsRequestEntity loginCredentialsRequestEntity = getLoginCredentialsRequestEntity(requestId, loginEntity, result);
                    if (loginCredentialsRequestEntity != null) {
                        NativeLoginService.getShared(context).loginWithCredentials(loginPropertiesResult.get("DomainURL"), loginCredentialsRequestEntity,
                                new Result<LoginCredentialsResponseEntity>() {
                                    @Override
                                    public void success(LoginCredentialsResponseEntity serviceresult) {
                                        getAccessTokenAfterLogin(serviceresult, result);
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


        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE, e.getMessage()));
        }
    }

    //Get Login Credentials Request Entity
    private LoginCredentialsRequestEntity getLoginCredentialsRequestEntity(String requestId, LoginEntity loginEntity, final Result<LoginCredentialsResponseEntity> result) {

        String methodName = "LoginController :getLoginCredentialsRequestEntity()";
        try {
            LoginCredentialsRequestEntity loginCredentialsRequestEntity = new LoginCredentialsRequestEntity();
            if (loginEntity.getUsername_type() == null || loginEntity.getUsername_type().equals("")) {
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
                result.failure(WebAuthError.getShared(context).propertyMissingException(errorMessage, "Error :" + errorMessage));
                return null;
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE, e.getMessage()));
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

}
