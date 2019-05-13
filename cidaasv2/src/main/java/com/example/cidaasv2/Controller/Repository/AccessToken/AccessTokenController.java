package com.example.cidaasv2.Controller.Repository.AccessToken;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.RequestId.RequestIdController;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Converter.EntityToModelConverter;
import com.example.cidaasv2.Helper.Entity.SocialAccessTokenEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Models.DBModel.AccessTokenModel;
import com.example.cidaasv2.Service.Entity.AccessToken.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.SocialProvider.SocialProviderEntity;
import com.example.cidaasv2.Service.Repository.AccessToken.AccessTokenService;

import java.util.Dictionary;

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
            LogFile.getShared(contextFromCidaas).addFailureLog("AccessTokenController instance Creation Exception:-"+e.getMessage());
        }
        return shared;
    }

    //-------------------------------------------------------Get AccessToken By Code-------------------------------------------------------
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
           callback.failure(WebAuthError.getShared(context).methodException("Exception :AccessToken Controller :getAccessTokenByCode()",
                    WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));
        }
    }

    private void getAccessWithCode(String baseurl,final String code, final Result<AccessTokenEntity> callback)
    {
        String methodName="AccessToken Controller :getAccessWithCode()";
        try
        {
            //Log File
            LogFile.getShared(context).addInfoLog("Info of ", " Info Baseurl"+baseurl+ " code"+code);
            AccessTokenService.getShared(context).getAccessTokenByCode(baseurl, code,new Result<AccessTokenEntity>()
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
        callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));
        }
    }

    //-------------------------------------------------------- accessTokenConversion---------------------------------------------------------
    private void accessTokenConversion(final AccessTokenEntity accessTokenEntity,final Result<AccessTokenEntity> callback)
    {
        final String methodName="AccessToken Controller :accessTokenConversion()";
        try
        {
             LogFile.getShared(context).addInfoLog("Info"+methodName, " Info AccessToken"+accessTokenEntity.getAccess_token()+
                 "refreshToken:-"+accessTokenEntity.getRefresh_token()+ "ExpiresIn:-"+accessTokenEntity.getExpires_in());

            EntityToModelConverter.getShared(context).accessTokenEntityToAccessTokenModel(accessTokenEntity,accessTokenEntity.getSub(),
            new Result<AccessTokenModel>()
            {
                @Override
                public void success(AccessTokenModel modelresult) {
                    LogFile.getShared(context).addSuccessLog("Success"+methodName, " Success AccessToken"+modelresult.getAccess_token()+
                        "refreshToken:-"+modelresult.getRefresh_token()+ "ExpiresIn:-"+modelresult.getExpires_in());
                    callback.success(accessTokenEntity);
                }

                @Override
                public void failure(WebAuthError error) {
                callback.failure(error);
            }
            });
        }
        catch (Exception e)
        {
         callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));
        }
    }

    //-------------------------------------------------------- Get Access Token by userId---------------------------------------------------------
    public void getAccessToken(String sub, final Result<AccessTokenEntity> callback)
    {
        String methodName="AccessToken Controller :getAccessToken()";
        try
        {
            LogFile.getShared(context).addInfoLog("Info "+methodName, " Info Sub:-"+sub);

            if(sub!=null&& !sub.equals(""))
            {
                final AccessTokenModel accessTokenModel=DBHelper.getShared().getAccessToken(sub);
                if(accessTokenModel!=null)
                {
                    getAccessToken(sub, callback, accessTokenModel);
                }
                else
                {
                    callback.failure(WebAuthError.getShared(context).noUserFoundException("Error:"+methodName));
                }
            }

        }
        catch (Exception e)
        {
          callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));
        }
    }

    private void getAccessToken(String sub, Result<AccessTokenEntity> callback, AccessTokenModel accessTokenModel)
    {
        String methodName="AccessToken Controller :getAccessToken()";
       try{
           LogFile.getShared(context).addInfoLog("Info "+methodName, " Info Sub:-"+sub+" AccessToken"+accessTokenModel.getAccess_token()+
                   "refreshToken:-"+accessTokenModel.getRefresh_token()+ "ExpiresIn:-"+accessTokenModel.getExpires_in());

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
           callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage()));
       }
    }

    //--------------------------------------------------------Get Access Token by Refresh Token-------------------------------------------------------
    public void getAccessTokenByRefreshToken(final String refreshToken, final Result<AccessTokenEntity> callback)
    {
        String methodName="AccessToken Controller :getAccessTokenByRefreshToken()";
        try {
            //Log Info message
            LogFile.getShared(context).addInfoLog("Info "+methodName, " Info RefreshToken:-"+refreshToken);

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> loginPropertiesResult) {
                    AccessTokenService.getShared(context).getAccessTokenByRefreshToken(refreshToken, loginPropertiesResult, new Result<AccessTokenEntity>() {
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
        }
        catch (Exception e)
        {
          callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE, e.getMessage()));
        }
    }

    //----------------------------------------------------------Get access Token by Social-------------------------------------------------------------
    public void getAccessTokenBySocial(SocialAccessTokenEntity socialAccessTokenEntity, final Result<AccessTokenEntity> accessTokenEntityResult)
    {
        String methodName="AccessToken Controller :getAccessTokenBySocial()";
        try
        {
            SocialAccessTokenEntity socialAccessTokenEntityAftercheck =checkNotNullForSocialEntity(socialAccessTokenEntity,accessTokenEntityResult);
            //Check For requestId

            if(socialAccessTokenEntityAftercheck !=null) {
                Dictionary<String, String> loginProperties=DBHelper.getShared().getLoginProperties(socialAccessTokenEntity.getDomainURL());

                AccessTokenService.getShared(context).getAccessTokenBySocial(socialAccessTokenEntityAftercheck, loginProperties, new Result<SocialProviderEntity>()
                {
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
            else
            {
                accessTokenEntityResult.failure(WebAuthError.getShared(context).propertyMissingException("","Error"+methodName));
            }
        }
        catch (Exception e)
        {
            accessTokenEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,
                    e.getMessage()));
        }
    }

    private SocialAccessTokenEntity checkNotNullForSocialEntity(final SocialAccessTokenEntity socialTokenEntity, final Result<AccessTokenEntity> accessTokenResult)
    {


        String methodName="AccessToken Controller :checkNotNullForSocialEntity()";
        try {

//Log info Message
            LogFile.getShared(context).addInfoLog("Info of AccessToken Controller :getAccessTokenBySocial()",
    " Info TokenOrCode:-"+socialTokenEntity.getToken() +"provider:-"+ socialTokenEntity.getProvider()+ "requestId:-"+socialTokenEntity.getRequestId()+
            "viewType:-"+socialTokenEntity.getViewType() +"DomainURL:-"+socialTokenEntity.getDomainURL());


            if(socialTokenEntity.getToken() == null || socialTokenEntity.getToken().equals("") ||
                    socialTokenEntity.getViewType() == null || socialTokenEntity.getViewType().equals(""))
            {
                accessTokenResult.failure(WebAuthError.getShared(context).propertyMissingException("Token or viewtype must not be empty","Error"+methodName));
                return null;
            }


            if(socialTokenEntity.getDomainURL() == null || socialTokenEntity.getDomainURL().equals("") ||
                    socialTokenEntity.getProvider() == null || socialTokenEntity.getProvider().equals(""))
            {
                accessTokenResult.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL or provider must not be empty","Error"+methodName));
                return null;
            }


            if (socialTokenEntity.getRequestId() == null || socialTokenEntity.getRequestId().equals("")) {


                RequestIdController.getShared(context).getRequestId(DBHelper.getShared().getLoginProperties(socialTokenEntity.getDomainURL()),
                        new Result<AuthRequestResponseEntity>() {
                            @Override
                            public void success(AuthRequestResponseEntity result) {
                                //
                                socialTokenEntity.setRequestId(result.getData().getRequestId());
                            }

                            @Override
                            public void failure(WebAuthError error) {
                                //
                                accessTokenResult.failure(error);
                            }
                        });
            }

            return socialTokenEntity;
        }
        catch (Exception e)
        {
            accessTokenResult.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,
                    e.getMessage()));
            return  null;
        }

    }

    //----------------------------------------------------------setAccessToken----------------------------------------------------------------------
    public void setAccessToken(final AccessTokenEntity accessTokenEntity, final Result<LoginCredentialsResponseEntity> result)
    {
        String methodName="AccessToken Controller :setAccessToken()";
        try
        {
            if(accessTokenEntity.getSub()!=null && !accessTokenEntity.getSub().equals("") &&
                    accessTokenEntity.getAccess_token()!=null && !accessTokenEntity.getAccess_token().equals("") &&
                    accessTokenEntity.getRefresh_token()!=null && !accessTokenEntity.getRefresh_token().equals("")) {


                conversionToAccessTokenModel(accessTokenEntity, result);
            }
            else
            {
              result.failure(WebAuthError.getShared(context).propertyMissingException("Sub or accessToken or refreshToken must not be null", "Error :"+methodName));
            }
        }
        catch (Exception e){
            result.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.SET_ACCESS_TOKEN,e.getMessage()));
        }
    }

    //----------------------------------------------------------conversionToAccessTokenModel-------------------------------------------------------------
    private void conversionToAccessTokenModel(final AccessTokenEntity accessTokenEntity, final Result<LoginCredentialsResponseEntity> result)
    {
        String methodName="AccessToken Controller :conversionToAccessTokenModel()";
       try{
           //Log Info message
           LogFile.getShared(context).addInfoLog("Info "+methodName, " Info AccessToken:-"+accessTokenEntity.getAccess_token()+
                   "refreshToken:-"+accessTokenEntity.getRefresh_token()+ "ExpiresIn:-"+accessTokenEntity.getExpires_in());


           //Entity converter
           EntityToModelConverter.getShared(context).accessTokenEntityToAccessTokenModel(accessTokenEntity,accessTokenEntity.getSub(),new Result<AccessTokenModel>()
           {
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
        result.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.SET_ACCESS_TOKEN,e.getMessage()));
       }
    }

    //----------------------------------------------------------generateLoginCredentials-------------------------------------------------------------
    private void generateLoginCredentials(AccessTokenEntity accessTokenEntity,AccessTokenModel accessTokenModel,final Result<LoginCredentialsResponseEntity> result)
    {
        String methodName="AccessToken Controller :generateLoginCredentials()";

        try
        {
          //Log Success Message
            LogFile.getShared(context).addInfoLog("Info"+methodName, " Success AccessToken:-"+accessTokenEntity.getAccess_token()+
                    "refreshToken:-"+accessTokenEntity.getRefresh_token()+ "ExpiresIn:-"+accessTokenEntity.getExpires_in());

            //Save accessToken Locally
            DBHelper.getShared().setAccessToken(accessTokenModel);

            //Generate LoginCredentialsResponseEntity
            LoginCredentialsResponseEntity loginCredentialsResponseEntity=new LoginCredentialsResponseEntity();
            loginCredentialsResponseEntity.setData(accessTokenEntity);
            loginCredentialsResponseEntity.setStatus(200);
            loginCredentialsResponseEntity.setSuccess(true);

            result.success(loginCredentialsResponseEntity);
    }
    catch (Exception e){
        result.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.SET_ACCESS_TOKEN,e.getMessage()));
     }
    }
}
