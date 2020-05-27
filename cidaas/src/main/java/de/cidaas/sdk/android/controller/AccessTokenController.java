package de.cidaas.sdk.android.controller;

import android.content.Context;

import java.util.Dictionary;

import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.entities.SocialAccessTokenEntity;
import de.cidaas.sdk.android.helper.converter.EntityToModelConverter;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.models.dbmodel.AccessTokenModel;
import de.cidaas.sdk.android.properties.CidaasProperties;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;
import de.cidaas.sdk.android.service.entity.socialprovider.SocialProviderEntity;
import de.cidaas.sdk.android.service.repository.AccessToken.AccessTokenService;

public class AccessTokenController {


    private Context context;

    //saved Properties for Global Access
    public static AccessTokenController shared;

    public AccessTokenController(Context contextFromCidaas) {
        context = contextFromCidaas;
    }

    public static AccessTokenController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new AccessTokenController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getInstance(contextFromCidaas).addFailureLog("AccessTokenController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //-------------------------------------------------------Get AccessToken By Code-------------------------------------------------------
    public void getAccessTokenByCode(final String code, final EventResult<AccessTokenEntity> callback) {
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    // Check notnull
                    getAccessWithCode(baseurl, code, callback);
                }

                @Override
                public void failure(WebAuthError error) {
                    callback.failure(error);
                }
            });

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :AccessToken Controller :getAccessTokenByCode()",
                    WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE, e.getMessage()));
        }
    }

    private void getAccessWithCode(String baseurl, final String code, final EventResult<AccessTokenEntity> callback) {
        String methodName = "AccessToken Controller :getAccessWithCode()";
        try {
            //Log File
            LogFile.getInstance(context).addInfoLog("Info of ", " Info Baseurl" + baseurl + " code" + code);
            AccessTokenService.getShared(context).getAccessTokenByCode(baseurl, code, new EventResult<AccessTokenEntity>() {
                @Override
                public void success(final AccessTokenEntity result) {
                    accessTokenConversion(result, callback);
                }

                @Override
                public void failure(WebAuthError error) {
                    callback.failure(error);
                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------------------- accessTokenConversion---------------------------------------------------------
    private void accessTokenConversion(final AccessTokenEntity accessTokenEntity, final EventResult<AccessTokenEntity> callback) {
        final String methodName = "AccessToken Controller :accessTokenConversion()";
        try {
            EntityToModelConverter.getShared(context).accessTokenEntityToAccessTokenModel(accessTokenEntity, accessTokenEntity.getSub(),
                    new EventResult<AccessTokenModel>() {
                        @Override
                        public void success(AccessTokenModel modelresult) {

                            callback.success(accessTokenEntity);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            callback.failure(error);
                        }
                    });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE, e.getMessage()));
        }
    }

    //-------------------------------------------------------- Get Access Token by userId---------------------------------------------------------
    public void getAccessToken(String sub, final EventResult<AccessTokenEntity> callback) {
        String methodName = "AccessToken Controller :getAccessToken()";
        try {
            LogFile.getInstance(context).addInfoLog("Info " + methodName, " Info Sub:-" + sub);

            if (sub != null && !sub.equals("")) {
                final AccessTokenModel accessTokenModel = DBHelper.getShared().getAccessToken(sub);
                if (accessTokenModel != null) {
                    getAccessToken(sub, callback, accessTokenModel);
                } else {
                    callback.failure(WebAuthError.getShared(context).noUserFoundException("Error:" + methodName));
                }
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE, e.getMessage()));
        }
    }

    private void getAccessToken(String sub, EventResult<AccessTokenEntity> callback, AccessTokenModel accessTokenModel) {
        String methodName = "AccessToken Controller :getAccessToken()";
        try {
            LogFile.getInstance(context).addInfoLog("Info " + methodName, " Info Sub:-" + sub + " AccessToken" + accessTokenModel.getAccess_token() +
                    "refreshToken:-" + accessTokenModel.getRefresh_token() + "ExpiresIn:-" + accessTokenModel.getExpires_in());

            long milliseconds = System.currentTimeMillis();
            long currentSeconds = milliseconds / 1000;
            long timeToExpire = accessTokenModel.getExpires_in() + accessTokenModel.getSeconds() - 10;

            if (timeToExpire > currentSeconds) {
                EntityToModelConverter.getShared(context).accessTokenModelToAccessTokenEntity(accessTokenModel, sub, callback);
            } else {
                getAccessTokenByRefreshToken(accessTokenModel.getRefresh_token(), callback);
            }
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE, e.getMessage()));
        }
    }

    //--------------------------------------------------------Get Access Token by Refresh Token-------------------------------------------------------
    public void getAccessTokenByRefreshToken(final String refreshToken, final EventResult<AccessTokenEntity> callback) {
        String methodName = "AccessToken Controller :getAccessTokenByRefreshToken()";
        try {
            //Log Info message
            LogFile.getInstance(context).addInfoLog("Info " + methodName, " Info RefreshToken:-" + refreshToken);

            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    AccessTokenService.getShared(context).getAccessTokenByRefreshToken(refreshToken, loginPropertiesResult, new EventResult<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity result) {
                            accessTokenConversion(result, callback);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            callback.failure(error);
                        }
                    });
                }

                @Override
                public void failure(WebAuthError error) {
                    callback.failure(error);
                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE, e.getMessage()));
        }
    }

    //----------------------------------------------------------Get access Token by Social-------------------------------------------------------------
    public void getAccessTokenBySocial(SocialAccessTokenEntity socialAccessTokenEntity, final EventResult<AccessTokenEntity> accessTokenEntityResult) {
        String methodName = "AccessToken Controller :getAccessTokenBySocial()";
        try {
            SocialAccessTokenEntity socialAccessTokenEntityAftercheck = checkNotNullForSocialEntity(socialAccessTokenEntity, accessTokenEntityResult);
            //Check For requestId

            if (socialAccessTokenEntityAftercheck != null) {
                Dictionary<String, String> loginProperties = DBHelper.getShared().getLoginProperties(socialAccessTokenEntity.getDomainURL());

                AccessTokenService.getShared(context).getAccessTokenBySocial(socialAccessTokenEntityAftercheck, loginProperties, new EventResult<SocialProviderEntity>() {
                    @Override
                    public void success(SocialProviderEntity result) {

                        getAccessTokenByCode(result.getCode(), accessTokenEntityResult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        accessTokenEntityResult.failure(error);
                    }
                });
            } else {
                accessTokenEntityResult.failure(WebAuthError.getShared(context).propertyMissingException("", "Error" + methodName));
            }
        } catch (Exception e) {
            accessTokenEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,
                    e.getMessage()));
        }
    }

    private SocialAccessTokenEntity checkNotNullForSocialEntity(final SocialAccessTokenEntity socialTokenEntity, final EventResult<AccessTokenEntity> accessTokenResult) {


        String methodName = "AccessToken Controller :checkNotNullForSocialEntity()";
        try {

//Log info Message
            LogFile.getInstance(context).addInfoLog("Info of AccessToken Controller :getAccessTokenBySocial()",
                    " Info TokenOrCode:-" + socialTokenEntity.getToken() + "provider:-" + socialTokenEntity.getProvider() + "requestId:-" + socialTokenEntity.getRequestId() +
                            "viewType:-" + socialTokenEntity.getViewType() + "DomainURL:-" + socialTokenEntity.getDomainURL());


            if (socialTokenEntity.getToken() == null || socialTokenEntity.getToken().equals("") ||
                    socialTokenEntity.getViewType() == null || socialTokenEntity.getViewType().equals("")) {
                accessTokenResult.failure(WebAuthError.getShared(context).propertyMissingException("Token or viewtype must not be empty", "Error" + methodName));
                return null;
            }


            if (socialTokenEntity.getDomainURL() == null || socialTokenEntity.getDomainURL().equals("") ||
                    socialTokenEntity.getProvider() == null || socialTokenEntity.getProvider().equals("")) {
                accessTokenResult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or provider must not be empty", "Error" + methodName));
                return null;
            }


            if (socialTokenEntity.getRequestId() == null || socialTokenEntity.getRequestId().equals("")) {

                accessTokenResult.failure(WebAuthError.getShared(context).propertyMissingException("RequestId must not be empty", "Error" + methodName));
                return null;
            }

            return socialTokenEntity;
        } catch (Exception e) {
            accessTokenResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,
                    e.getMessage()));
            return null;
        }

    }

    //----------------------------------------------------------setAccessToken----------------------------------------------------------------------
    public void setAccessToken(final AccessTokenEntity accessTokenEntity, final EventResult<LoginCredentialsResponseEntity> result) {
        String methodName = "AccessToken Controller :setAccessToken()";
        try {
            if (accessTokenEntity.getSub() != null && !accessTokenEntity.getSub().equals("") &&
                    accessTokenEntity.getAccess_token() != null && !accessTokenEntity.getAccess_token().equals("") &&
                    accessTokenEntity.getRefresh_token() != null && !accessTokenEntity.getRefresh_token().equals("")) {


                conversionToAccessTokenModel(accessTokenEntity, result);
            } else {
                result.failure(WebAuthError.getShared(context).propertyMissingException("Sub or accessToken or refreshToken must not be null", "Error :" + methodName));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.SET_ACCESS_TOKEN, e.getMessage()));
        }
    }

    //----------------------------------------------------------conversionToAccessTokenModel-------------------------------------------------------------
    private void conversionToAccessTokenModel(final AccessTokenEntity accessTokenEntity, final EventResult<LoginCredentialsResponseEntity> result) {
        String methodName = "AccessToken Controller :conversionToAccessTokenModel()";
        try {
            //Log Info message
            LogFile.getInstance(context).addInfoLog("Info " + methodName, " Info AccessToken:-" + accessTokenEntity.getAccess_token() +
                    "refreshToken:-" + accessTokenEntity.getRefresh_token() + "ExpiresIn:-" + accessTokenEntity.getExpires_in());


            //Entity converter
            EntityToModelConverter.getShared(context).accessTokenEntityToAccessTokenModel(accessTokenEntity, accessTokenEntity.getSub(), new EventResult<AccessTokenModel>() {
                @Override
                public void success(AccessTokenModel accessTokenModel) {
                    generateLoginCredentials(accessTokenEntity, accessTokenModel, result);
                }

                @Override
                public void failure(WebAuthError error) {
                    result.failure(error);
                }
            });

        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.SET_ACCESS_TOKEN, e.getMessage()));
        }
    }

    //----------------------------------------------------------generateLoginCredentials-------------------------------------------------------------
    private void generateLoginCredentials(AccessTokenEntity accessTokenEntity, AccessTokenModel accessTokenModel, final EventResult<LoginCredentialsResponseEntity> result) {
        String methodName = "AccessToken Controller :generateLoginCredentials()";

        try {
            //Log Success Message
            LogFile.getInstance(context).addInfoLog("Info" + methodName, " Success AccessToken:-" + accessTokenEntity.getAccess_token() +
                    "refreshToken:-" + accessTokenEntity.getRefresh_token() + "ExpiresIn:-" + accessTokenEntity.getExpires_in());

            //Save accessToken Locally
            DBHelper.getShared().setAccessToken(accessTokenModel);

            //Generate LoginCredentialsResponseEntity
            LoginCredentialsResponseEntity loginCredentialsResponseEntity = new LoginCredentialsResponseEntity();
            loginCredentialsResponseEntity.setData(accessTokenEntity);
            loginCredentialsResponseEntity.setStatus(200);
            loginCredentialsResponseEntity.setSuccess(true);

            result.success(loginCredentialsResponseEntity);
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.SET_ACCESS_TOKEN, e.getMessage()));
        }
    }
}
