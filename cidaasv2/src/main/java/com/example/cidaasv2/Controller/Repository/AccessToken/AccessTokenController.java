package com.example.cidaasv2.Controller.Repository.AccessToken;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Converter.EntityToModelConverter;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Models.DBModel.AccessTokenModel;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.SocialProvider.SocialProviderEntity;
import com.example.cidaasv2.Service.Repository.AccessToken.AccessTokenService;

import java.util.Dictionary;

import timber.log.Timber;

public class AccessTokenController {


    private Context context;

    //saved Properties for Global Access
    public static AccessTokenController shared;
    public AccessTokenController(Context contextFromCidaas) {
        context=contextFromCidaas;
    }

    public static AccessTokenController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new AccessTokenController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //Get AccessToken By Code
    public void getAccessTokenByCode(final String code, final Result<AccessTokenEntity> callback)
    {
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl=result.get("DomainURL");
                    //todo Check notnull
                    getAccessWithCode(baseurl,code,callback);
                }

                @Override
                public void failure(WebAuthError error) {
                    callback.failure(error);
                }
            });

        }
        catch (Exception e)
        {
           callback.failure(WebAuthError.getShared(context).serviceException("Exception :AccessToken Controller :getAccessTokenByCode()",
                    WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));
        }
    }

    private void getAccessWithCode(String baseurl,final String code, final Result<AccessTokenEntity> callback) {
       try{
        AccessTokenService.getShared(context).getAccessTokenByCode(baseurl, code,
                null,null,null,new Result<AccessTokenEntity>()
                {
                    @Override
                    public void success(final AccessTokenEntity result) {
                        accessTokenConversion(result,callback);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        callback.failure(error);
                    }
                });
        }
        catch (Exception e)
        {
        callback.failure(WebAuthError.getShared(context).serviceException("Exception :AccessToken Controller :getAccessWithCode()",
                WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));

        }
    }


    private void accessTokenConversion(final AccessTokenEntity result,final Result<AccessTokenEntity> callback) {
     try
     {
        EntityToModelConverter.getShared(context).accessTokenEntityToAccessTokenModel(result, result.getSub(), new Result<AccessTokenModel>() {
            @Override
            public void success(AccessTokenModel modelresult) {
                callback.success(result);
            }

            @Override
            public void failure(WebAuthError error) {
                callback.failure(error);
            }
        });
     }
     catch (Exception e)
     {
         callback.failure(WebAuthError.getShared(context).serviceException("Exception :AccessToken Controller :accessTokenConversion()",
                 WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));
     }

    }

    // Get Access Token by userId
    public void getAccessToken(String sub, final Result<AccessTokenEntity> callback)
    {
        try
        {
            if(sub!=null&& !sub.equals(""))
            {
                final AccessTokenModel accessTokenModel=DBHelper.getShared().getAccessToken(sub);
                if(accessTokenModel!=null)
                {
                    getAccessToken(sub, callback, accessTokenModel);
                }
                else
                {
                    callback.failure(WebAuthError.getShared(context).noUserFoundException());
                }
            }

        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).serviceException("Exception :AccessToken Controller :getAccessToken()",
                    WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));
        }
    }

    private void getAccessToken(String sub, Result<AccessTokenEntity> callback, AccessTokenModel accessTokenModel) {
       try{
        long milliseconds=System.currentTimeMillis();
        long currentSeconds=milliseconds/1000;
        long timeToExpire=accessTokenModel.getExpires_in()+accessTokenModel.getSeconds()-10;

        if(timeToExpire>currentSeconds)
        {
            EntityToModelConverter.getShared(context).accessTokenModelToAccessTokenEntity(accessTokenModel, sub, callback);
        }
        else
        {
            getAccessTokenByRefreshToken(accessTokenModel.getRefresh_token(),callback);
        }
       }
       catch (Exception e)
       {
           callback.failure(WebAuthError.getShared(context).serviceException("Exception :AccessToken Controller :getAccessToken()",
                   WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));
       }
    }

    //Get Access Token by Refresh Token
    public void getAccessTokenByRefreshToken(final String refreshToken, final Result<AccessTokenEntity> callback) {
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    AccessTokenService.getShared(context).getAccessTokenByRefreshToken(refreshToken, result,
                            null, null, new Result<AccessTokenEntity>() {
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
            callback.failure(WebAuthError.getShared(context).serviceException("Exception :AccessToken Controller :getAccessTokenByRefreshToken() "
                    , WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE, e.getMessage()));
        }
    }
    //Get access Token by Social
    public void getAccessTokenBySocial(String tokenOrCode, String provider, String givenType, String requestId, String viewType,Dictionary<String,String> loginProperties, final Result<AccessTokenEntity> accessTokenEntityResult)
    {
        try
        {
            AccessTokenService.getShared(context).getAccessTokenBySocial(tokenOrCode, provider, givenType, requestId, viewType,loginProperties, new Result<SocialProviderEntity>() {
                @Override
                public void success(SocialProviderEntity result) {
                    getAccessTokenByCode(result.getCode(), accessTokenEntityResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    accessTokenEntityResult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            accessTokenEntityResult.failure(WebAuthError.getShared(context).serviceException("Exception :AccessToken Controller :getAccessTokenBySocial()",
                    WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));
        }
    }

    public void setAccessToken(final AccessTokenEntity accessTokenEntity, final Result<LoginCredentialsResponseEntity> result){
        try
        {

            if(accessTokenEntity.getSub()!=null && !accessTokenEntity.getSub().equals("") &&
                    accessTokenEntity.getAccess_token()!=null && !accessTokenEntity.getAccess_token().equals("") &&
                    accessTokenEntity.getRefresh_token()!=null && !accessTokenEntity.getRefresh_token().equals("")) {

                conversionToAccessTokenModel(accessTokenEntity, result);
            }
            else
            {
                result.failure(WebAuthError.getShared(context).propertyMissingException("Sub or accessToken or refreshToken must not be null"));
            }
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).serviceException("Exception :AccessToken Controller :setAccessToken()",WebAuthErrorCode.SET_ACCESS_TOKEN,e.getMessage()));
        }
    }

    private void conversionToAccessTokenModel(final AccessTokenEntity accessTokenEntity, final Result<LoginCredentialsResponseEntity> result) {
       try{
        EntityToModelConverter.getShared(context).accessTokenEntityToAccessTokenModel(accessTokenEntity, accessTokenEntity.getSub(), new Result<AccessTokenModel>() {
            @Override
            public void success(AccessTokenModel accessTokenModel) {
                generateLoginCredentials(accessTokenEntity,accessTokenModel,result);
            }

            @Override
            public void failure(WebAuthError error) {
              result.failure(error);
            }
        });

       }
        catch (Exception e){
        result.failure(WebAuthError.getShared(context).serviceException("Exception :AccessToken Controller :conversionToAccessTokenModel()",WebAuthErrorCode.SET_ACCESS_TOKEN,e.getMessage()));
       }
    }


    private void generateLoginCredentials(AccessTokenEntity accessTokenEntity,AccessTokenModel accessTokenModel,final Result<LoginCredentialsResponseEntity> result) {
      try{
        DBHelper.getShared().setAccessToken(accessTokenModel);
        LoginCredentialsResponseEntity loginCredentialsResponseEntity=new LoginCredentialsResponseEntity();
        loginCredentialsResponseEntity.setData(accessTokenEntity);
        loginCredentialsResponseEntity.setStatus(200);
        loginCredentialsResponseEntity.setSuccess(true);
        result.success(loginCredentialsResponseEntity);
    }
        catch (Exception e){
        result.failure(WebAuthError.getShared(context).serviceException("Exception :AccessToken Controller :generateLoginCredentials()",WebAuthErrorCode.SET_ACCESS_TOKEN,e.getMessage()));
     }
    }
}
