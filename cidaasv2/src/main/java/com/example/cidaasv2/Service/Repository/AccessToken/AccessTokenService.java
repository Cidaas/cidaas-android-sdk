package com.example.cidaasv2.Service.Repository.AccessToken;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.example.cidaasv2.Service.Repository.RequestId.RequestIdService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class AccessTokenService {


    private Context context;

    public static AccessTokenService shared;

    CidaassdkService service;
    public ObjectMapper objectMapper=new ObjectMapper();

    public AccessTokenService(Context contextFromCidaas) {
        context=contextFromCidaas;
        if(service==null) {
            service=new CidaassdkService();
        }

        //Todo setValue for authenticationType

    }

    public static AccessTokenService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new AccessTokenService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //Get Access Token by code
    public void getAccessTokenByCode(String baseurl,String Code, final Result<AccessTokenEntity> acessTokencallback)
    {
        try {

            //Local Variables
            String url = "";
            String getAccessTokenUrl = "";
            Dictionary<String, String> loginProperties = DBHelper.getShared().getLoginProperties();
            Dictionary<String, String> challengeProperties = DBHelper.getShared().getChallengeProperties();

            if (loginProperties == null) {
                // callback.failure(.loginURLMissingException());
            }

            baseurl=loginProperties.get("DomainURL");
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                getAccessTokenUrl=baseurl+ URLHelper.getShared().getTokenUrl();
            }
            else {
                acessTokencallback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            Map<String, String> querymap = new Hashtable<>();

            //get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentType);
            headers.put("user-agent", "cidaas-android");
            headers.put("deviceId", deviceInfoEntity.getDeviceId());
            headers.put("deviceMake", deviceInfoEntity.getDeviceMake());
            headers.put("deviceModel", deviceInfoEntity.getDeviceModel());
            headers.put("deviceVersion", deviceInfoEntity.getDeviceVersion());


            //Get Properties From DB




            //Add Body Parameter
            //TODO generate Body Parameter
            querymap.put("grant_type", "authorization_code");
            querymap.put("code", Code);
            querymap.put("redirect_uri", loginProperties.get("RedirectURL"));
            querymap.put("client_id", loginProperties.get("ClientId"));
            querymap.put("code_verifier", challengeProperties.get("Verifier"));


            //Assign Url
            //TOdo Perform Null Check
            //  url = loginProperties.get("TokenURL");


            //call service
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getAccessTokenByCode(getAccessTokenUrl,headers,querymap).enqueue(new Callback<AccessTokenEntity>() {
                @Override
                public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            acessTokencallback.success(response.body());
                        }
                        else {
                            acessTokencallback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,
                                    "Service failure but successful response" , 400,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();

                            CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);

                            String errorMessage="";
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }



                            acessTokencallback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,errorMessage, 400,null));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                    Timber.e("Faliure in getAccessTokenByCode id call"+t.getMessage());
                    acessTokencallback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,t.getMessage(), 400,null));
                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());

            acessTokencallback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage(), 400,null));
        }
    }


    //Get Access Token by Clientid and Client Secret
    public void getAccessTokenByIdAndSecret(String ClientId,String ClientSecret,Result<AccessTokenEntity> callback)
    {
        //Todo Perform Callback
    }


    public void getAccessTokenByRefreshToken(String refreshToken, final Result<AccessTokenEntity> callback)
    {
        try {
            //Local Variables
            String url = "";
            String baseUrl = "";

            Map<String, String> headers = new Hashtable<>();
            Map<String, String> querymap = new Hashtable<>();

            //get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentType);
            headers.put("user-agent", "cidaas-android");
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());


            //Get Properties From DB


          Cidaas.getInstance(context).checkSavedProperties(new Result<Dictionary<String, String>>() {
              @Override
              public void success(Dictionary<String, String> result) {
               //loginProperties=result;

              }

              @Override
              public void failure(WebAuthError error) {

              }
          });

            Dictionary<String, String> loginProperties= DBHelper.getShared().getLoginProperties();

            //Todo read From File


            if (loginProperties == null) {
                callback.failure(WebAuthError.getShared(context).loginURLMissingException());
            }


            //Add Body Parameter
            //TODO generate Body Parameter
            querymap.put("grant_type", "refresh_token");
            querymap.put("redirect_uri", loginProperties.get("RedirectURL"));
            querymap.put("client_id", loginProperties.get("ClientId"));
            querymap.put("refresh_token", refreshToken);


            //Assign Url
            //TOdo Perform Null Check
            url = querymap.get("TokenURL");


            //call service
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getAccessTokenByRefreshToken(url,headers,querymap).enqueue(new Callback<AccessTokenEntity>() {
                @Override
                public void onResponse(Call<AccessTokenEntity> call, Response<AccessTokenEntity> response) {
                    if (response.isSuccessful()) {

                        //todo save the accesstoken in Storage helper
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CLIENT_INFO_FAILURE,
                                    "Service failure but successful response" , 400,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();

                            CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);

                            String errorMessage="";
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }


                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CLIENT_INFO_FAILURE,errorResponse, 400,null));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                    Timber.e("Faliure in getAccessTokenByCode id call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,t.getMessage(), 400,null));

                }
            });

        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
        }
    }
}
