package com.example.cidaasv2.Controller.Repository.AccessToken;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Controller.Repository.Configuration.Pattern.PatternConfigurationController;
import com.example.cidaasv2.Helper.Converter.EntityToModelConverter;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Models.DBModel.AccessTokenModel;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Repository.AccessToken.AccessTokenService;
import com.example.cidaasv2.Service.Repository.OauthService;

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
                    AccessTokenService.getShared(context).getAccessTokenByCode(baseurl, code, new Result<AccessTokenEntity>()
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

                }
            });

        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
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
                    long timeToExpire=accessTokenModel.getExpiresIn()+accessTokenModel.getSeconds()-10;
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
                        getAccessTokenByRefreshToken(accessTokenModel.getRefreshToken(),callback);
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
            //Todo Handle Exception
        }
    }

    //Get Access Token by Refresh Token

    public void getAccessTokenByRefreshToken(String refreshToken, final Result<AccessTokenEntity> callback)
    {
        try {

            AccessTokenService.getShared(context).getAccessTokenByRefreshToken(refreshToken, new Result<AccessTokenEntity>() {
                @Override
                public void success(AccessTokenEntity result) {

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
            Timber.d(e.getMessage()); //Todo Handle Exception
        }
    }
}
