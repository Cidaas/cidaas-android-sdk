package com.example.cidaasv2.Service.Repository.AccessToken;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.GenralHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.SocialProvider.SocialProviderEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    public void getAccessTokenByCode(String baseurl,String Code, DeviceInfoEntity deviceInfoEntityFromParam, Dictionary<String, String> loginPropertiesfromParam, Dictionary<String, String> challengePropertiesfromparam,final Result<AccessTokenEntity> acessTokencallback)
    {
        try {

            //Local Variables
            String url = "";
            String getAccessTokenUrl = "";
            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromParam==null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromParam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromParam;
            }
            else
            {
                // deviceInfoEntity=new DeviceInfoEntity();
            }


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
                // challengeProperties=new Hashtable<>();
            }

            //////////////////This is for testing purpose
            Dictionary<String,String> loginProperties=new Hashtable<>();

            if(loginPropertiesfromParam==null) {
                loginProperties=DBHelper.getShared().getLoginProperties(baseurl);
            }
            else if(loginPropertiesfromParam!=null)
            {
                loginProperties=loginPropertiesfromParam;
            }
            else
            {
                // challengeProperties=new Hashtable<>();
            }


            if (loginProperties == null) {
                // callback.failure(.loginURLMissingException());
            }

          //  baseurl=loginProperties.get("DomainURL");
            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                getAccessTokenUrl=baseurl+ URLHelper.getShared().getTokenUrl();
            }
            else {
                acessTokencallback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            Map<String, String> querymap = new Hashtable<>();



            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
          //  headers.put("Content-Type", URLHelper.contentType);
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
                                    "Service failure but successful response" , 400,null,null));
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



                            acessTokencallback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,errorMessage, 400,null,null));
                        } catch (Exception e) {
                            e.printStackTrace();
                            acessTokencallback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,
                                    "AccessToken Service Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                    Timber.e("Faliure in getAccessTokenByCode id call"+t.getMessage());
                    acessTokencallback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,t.getMessage(), 400,null,null));
                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());

            acessTokencallback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCESSTOKEN_SERVICE_FAILURE,e.getMessage(), 400,null,null));
        }
    }


    //Get Access Token by Clientid and Client Secret
    public void getAccessTokenByIdAndSecret(String ClientId,String ClientSecret,Result<AccessTokenEntity> callback)
    {
        //Todo Perform Callback
    }


    public void getAccessTokenByRefreshToken(String refreshToken,Dictionary<String, String> loginProperties,DeviceInfoEntity deviceInfoEntityFromparam, Dictionary<String, String> challengePropertiesfromparam,final Result<AccessTokenEntity> callback)
    {
        try {
            //Local Variables
            String url = "";
            String baseurl = "";

            Map<String, String> headers = new Hashtable<>();
            Map<String, String> querymap = new Hashtable<>();

            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromparam==null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromparam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromparam;
            }
            else
            {
                // deviceInfoEntity=new DeviceInfoEntity();
            }


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
                // challengeProperties=new Hashtable<>();
            }
            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentType);
            headers.put("user-agent", "cidaas-android");
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());


            //Get Properties From DB


            if (loginProperties == null) {
                callback.failure(WebAuthError.getShared(context).loginURLMissingException());
                return;
            }


            if(challengeProperties.get("Verifier")==null)
            {
                challengeProperties.put("Verifier","");
            }


            //Add Body Parameter
            //TODO generate Body Parameter
            querymap.put("grant_type", "refresh_token");
            querymap.put("redirect_uri", loginProperties.get("RedirectURL"));
            querymap.put("client_id", loginProperties.get("ClientId"));
            querymap.put("code_verifier", challengeProperties.get("Verifier"));
            querymap.put("refresh_token", refreshToken);


            //Assign Url
            //TOdo Perform Null Check
           // url = querymap.get("TokenURL");
            baseurl=loginProperties.get("DomainURL");
            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                url=baseurl+ URLHelper.getShared().getTokenUrl();
            }

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
                                    "Service failure but successful response" , 400,null,null));
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


                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CLIENT_INFO_FAILURE,errorMessage, 400,null,null));
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.CLIENT_INFO_FAILURE,
                                    "Client Info failure Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<AccessTokenEntity> call, Throwable t) {
                    Timber.e("Faliure in getAccessTokenByCode id call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,t.getMessage(), 400,null,null));

                }
            });

        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
        }
    }


    //get Access Token by Social
    public void getAccessTokenBySocial(String tokenOrCode, String provider, String givenType, String requestId, String viewType,Dictionary<String,String> loginProperties, final Result<SocialProviderEntity> callback)
    {
       try
       {
           String baseURL;
           baseURL= GenralHelper.getShared().constructSocialServiceURl(tokenOrCode,provider,givenType,loginProperties,viewType);


           baseURL=baseURL+URLHelper.getShared().getPreAuthCode()+requestId;


           ICidaasSDKService cidaasSDKService = service.getInstance();
           cidaasSDKService.getAccessTokenBySocial(baseURL).enqueue(new Callback<SocialProviderEntity>() {

               @Override
               public void onResponse(Call<SocialProviderEntity> call, Response<SocialProviderEntity> response) {

                   if (response.isSuccessful()) {

                   //todo save the accesstoken in Storage helper
                   if(response.code()==200) {
                       callback.success(response.body());
                   }
                   else {
                       callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CLIENT_INFO_FAILURE,
                               "Service failure but successful response" , 400,null,null));
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


                       callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CLIENT_INFO_FAILURE,errorMessage, 400,null,null));
                   } catch (Exception e) {
                       e.printStackTrace();
                       callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.CLIENT_INFO_FAILURE,
                               "Client info Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
                   }
                   Timber.e("response"+response.message());
               }
               }

               @Override
               public void onFailure(Call<SocialProviderEntity> call, Throwable t) {
                   Timber.e("Faliure in getAccessTokenByCode id call"+t.getMessage());
                   callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,t.getMessage(), 400,null,null));

               }
           });


       }
       catch (Exception e)
       {

       }
    }


    /*public void getAccessTokenBySocial(String tokenOrCode, String provider, String givenType) {
        try {
            showLoader();
            CidaasSDKEntity.cidaasSDKEntityInstance.readInputs(GLOBAL_ACTIVITY.getApplicationContext());
            new ErrorEntity();
            String url = CidaasSDKHelper.constructSocialServiceURl(tokenOrCode, provider, givenType);
            if (GLOBAL_PRE_AUTH_CODE != "") {
                url = url + "&preAuthCode=" + GLOBAL_PRE_AUTH_CODE;
            } else {
                GLOBAL_INITIAL_CODE_VERIFIER = this.getCodeVerifier();
                GLOBAL_CODE_CHALLENGE = this.getCodeChallenge(GLOBAL_INITIAL_CODE_VERIFIER);
                url = url + "&code_challenge=" + GLOBAL_CODE_CHALLENGE + "&code_challenge_method=" + CidaasSDKHelper.codeChallengeMethod;
            }

            CidaasSDKService service = new CidaasSDKService();
            service.getAccessTokenBySocial(url, new ISocialEntity() {
                public void onSuccess(SocialProviderEntity socialEntity) {
                    if (socialEntity.getRedirectUrl() != null && socialEntity.getRedirectUrl() != "") {
                        CidaasSDK.this.webViewInstance.setVisibility(0);
                        CidaasSDK.this.webViewInstance.loadUrl(socialEntity.getRedirectUrl());
                    } else {
                        CidaasSDK.getAccessTokenByCode(socialEntity.getCode(), CidaasSDK.GLOBAL_CODE_VERIFIER);
                    }

                    CidaasLog.addRecordToSuccessLog("Successfully get access token by Social", CidaasSDK.ENABLE_LOG);
                }

                public void onError(ErrorEntity errorEntity) {
                    CidaasSDK.hideLoader();
                    CidaasSDK.iAccessTokenEntity.onError(errorEntity);
                    CidaasLog.addRecordToErrorLog(errorEntity.getMessage(), CidaasSDK.ENABLE_LOG);
                }
            });
        } catch (Exception var7) {
            CidaasLog.addRecordToErrorLog(var7.getMessage(), ENABLE_LOG);
        }
*/
    }


