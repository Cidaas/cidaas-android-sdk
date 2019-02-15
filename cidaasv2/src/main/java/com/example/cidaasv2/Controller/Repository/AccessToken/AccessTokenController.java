package com.example.cidaasv2.Controller.Repository.AccessToken;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Converter.EntityToModelConverter;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Models.DBModel.AccessTokenModel;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.SocialProvider.SocialProviderEntity;
import com.example.cidaasv2.Service.Repository.AccessToken.AccessTokenService;

import java.util.Dictionary;

import timber.log.Timber;

public class AccessTokenController {

    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    //saved Properties for Global Access
       public static AccessTokenController shared;

    public AccessTokenController(Context contextFromCidaas) {
        sub="";
        statusId="";
        verificationType="";
        context=contextFromCidaas;
        authenticationType="";
        //Todo setValue for authenticationType

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

            Cidaas.getInstance(context).checkSavedProperties(new Result<Dictionary<String, String> >() {
                @Override
                public void success(Dictionary<String, String>  result) {
                    String baseurl=result.get("DomainURL");
                    //todo Check notnull
                    AccessTokenService.getShared(context).getAccessTokenByCode(baseurl, code, null,null,null,new Result<AccessTokenEntity>()
                    {
                        @Override
                        public void success(final AccessTokenEntity result) {

                            EntityToModelConverter.getShared().accessTokenEntityToAccessTokenModel(result, result.getSub(), new Result<AccessTokenModel>() {
                                @Override
                                public void success(AccessTokenModel modelresult) {
                                    DBHelper.getShared().setAccessToken(modelresult);
                                    callback.success(result);
                                    //Toast.makeText(context, "Access Token Saved", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void failure(WebAuthError error) {
                                    WebAuthError error1=new WebAuthError(context);
                                    error1.setErrorMessage("Access Token Failed");
                                    callback.failure(error1);
                                }
                            });

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
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).customException(417,"Exception :AccessToken Controller :getAccessTokenByCode() :- "+e.getMessage(),400));

            //Todo Handle Exception
        }
    }
    // Get Access Token by userId

    public void getAccessToken(String sub, final Result<AccessTokenEntity> callback)
    {
        try
        {
            if(sub!=null&&sub!="")
            {
                final AccessTokenModel accessTokenModel=DBHelper.getShared().getAccessToken(sub);
                if(accessTokenModel!=null)
                {
                    long milliseconds=System.currentTimeMillis();
                    long currentSeconds=milliseconds/1000;
                    long timeToExpire=accessTokenModel.getExpires_in()+accessTokenModel.getSeconds()-10;
                    if(timeToExpire>currentSeconds)
                    {
                        EntityToModelConverter.getShared().accessTokenModelToAccessTokenEntity(accessTokenModel, sub, new Result<AccessTokenEntity>() {
                            @Override
                            public void success(AccessTokenEntity accessTokenEntity) {

                                callback.success(accessTokenEntity);

                            }

                            @Override
                            public void failure(WebAuthError error) {

                                callback.failure(error);
                            }
                        });
                    }
                    else
                    {
                        getAccessTokenByRefreshToken(accessTokenModel.getRefresh_token(),callback);
                    }
                }
                else
                {
                    //todo log error

                    callback.failure(WebAuthError.getShared(context).noUserFoundException());
                }

            }

        }
        catch (Exception e)
        {

            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).customException(417,"Exception :AccessToken Controller :getAccessToken() :- "+e.getMessage(),400));

            //Todo Handle Exception
        }
    }

    //Get Access Token by Refresh Token

    public void getAccessTokenByRefreshToken(final String refreshToken, final Result<AccessTokenEntity> callback)
    {
        try {

            Cidaas.getInstance(context).checkSavedProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    AccessTokenService.getShared(context).getAccessTokenByRefreshToken(refreshToken,result,null,null, new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity result) {
                            EntityToModelConverter.getShared().accessTokenEntityToAccessTokenModel(result, result.getSub(), new Result<AccessTokenModel>() {
                                @Override
                                public void success(AccessTokenModel result) {
                                    DBHelper.getShared().setAccessToken(result);
                                }

                                @Override
                                public void failure(WebAuthError error) {
                                     //Todo Handle Error
                                }
                            });
                            callback.success(result);
                        }

                        @Override
                        public void failure(WebAuthError error) {

                            callback.failure(error);
                        }
                    });

                }

                @Override
                public void failure(WebAuthError error)
                {
                    callback.failure(error);
                }
            });


         }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).customException(417,"Exception :AccessToken Controller :getAccessTokenByRefreshToken() :- "+e.getMessage(),400));
            Timber.d(e.getMessage()); //done Handle Exception
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
                    getAccessTokenByCode(result.getCode(), new Result<AccessTokenEntity>() {
                        @Override
                        public void success(AccessTokenEntity result) {
                            accessTokenEntityResult.success(result);
                        }

                        @Override
                        public void failure(WebAuthError error) {
                            accessTokenEntityResult.failure(error);
                        }
                    });
                }

                @Override
                public void failure(WebAuthError error) {
                    accessTokenEntityResult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
            accessTokenEntityResult.failure(WebAuthError.getShared(context).customException(417,"Exception: AccessToken Controller: getAccessTokenBySocial() :- "+e.getMessage(),400));
            Timber.d(e.getMessage());
        }
    }
}
