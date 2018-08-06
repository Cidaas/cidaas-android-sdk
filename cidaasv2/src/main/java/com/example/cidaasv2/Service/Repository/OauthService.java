package com.example.cidaasv2.Service.Repository;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.Entity.UserinfoEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
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

/**
 * Created by widasrnarayanan on 16/1/18.
 */

public class OauthService {



    CidaassdkService service;

    public Context context;
    public static OauthService shared;
    public WebAuthError webAuthError;
    URLHelper urlHelper=new URLHelper();
    public ObjectMapper objectMapper=new ObjectMapper();



    //Todo Create Shared instances
    public static OauthService getShared(Context contextfromcidaas)
    {
        if(shared==null)
        {
            shared=new OauthService(contextfromcidaas);
        }
        return shared;
    }
    //Constructor
    OauthService(Context contextfromcidaas)
    {
        this.context=contextfromcidaas;
        if(service==null) {
             service=new CidaassdkService();
        }
        webAuthError=new WebAuthError(context);
    }

 //   -------------------------------------------------------------------------------------------------------------------------------------------------
/*

//Todo Setup pattern by Passing the setupPatternRequestEntity
    // 1. Todo Check For NotNull Value
    // 2.  Done Call Setup Pattern From Pattern verification Service and return the result
    // 3. Todo Maintain logs based on flags

    public void setupPatternMFA(String baseurl, String accessToken, String codeChallenge, SetupPatternMFARequestEntity setupPatternMFARequestEntity,
                                final Result<SetupPatternMFAResponseEntity> callback)
    {
     try{
         PatternVerificationService.getShared(context).setupPattern(baseurl,accessToken,codeChallenge,setupPatternMFARequestEntity,callback);
     }
     catch (Exception e)
     {
         //Todo Handle Errror
         callback.failure(WebAuthError.getShared(context).propertyMissingException());
     }
    }

    //   -------------------------------------------------------------------------------------------------------------------------------------------------

    //Todo Scanned pattern by Passing the setupPatternRequestEntity
    // 1. Todo Check For NotNull Value
    // 2.Done  Call scanned Pattern From Pattern verification Service and return the result
    // 3. Todo Maintain logs based on flags

    public void scannedPattern(String baseurl, String usagePass,String statusId,String AccessToken,
                               final Result<ScannedResponseEntity> callback)
    {
        try{
            PatternVerificationService.getShared(context).scannedPattern(baseurl,usagePass,statusId,AccessToken,callback);
        }
        catch (Exception e)
        {
            //Todo Handle Errror
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    //   -------------------------------------------------------------------------------------------------------------------------------------------------

    //Todo Enroll pattern by Passing the setupPatternRequestEntity
    // 1. Todo Check For NotNull Value
    // 2. Done Call Enroll Pattern From Pattern verification Service and return the result
    // 3. Todo Maintain logs based on flags

    public void enrollPattern(String baseurl, String accessToken, EnrollPatternMFARequestEntity enrollPatternMFARequestEntity,
                              final Result<EnrollPatternMFAResponseEntity> callback)
    {
        try{
            PatternVerificationService.getShared(context).enrollPattern(baseurl,accessToken,enrollPatternMFARequestEntity,callback);
        }
        catch (Exception e)
        {
            //Done Handle Errror
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    //   -------------------------------------------------------------------------------------------------------------------------------------------------

    //Todo Verify pattern by Passing the patternString
    // 1. Todo Check For NotNull Value
    // 2. Todo Call Initiate Pattern From Pattern verification Service and return the result
    // 3. Todo Maintain logs based on flags

    public void initiatePattern(String baseurl, InitiatePatternMFARequestEntity initiatePatternMFARequestEntity,
                              final Result<InitiatePatternMFAResponseEntity> callback)
    {
        try{
            PatternVerificationService.getShared(context).initiatePattern(baseurl,initiatePatternMFARequestEntity,callback);
        }
        catch (Exception e)
        {
            // Handle Errror
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    //   -------------------------------------------------------------------------------------------------------------------------------------------------

    //Todo Verify pattern by Passing the patternString
    // 1. Todo Check For NotNull Value
    // 2. Todo Call Verify Pattern From Pattern verification Service and return the result
    // 3. Todo Maintain logs based on flags

    public void verifyPattern(String baseurl, AuthenticatePatternRequestEntity authenticatePatternRequestEntity,
                              final Result<AuthenticatePatternResponseEntity> callback)
    {
        try{
            PatternVerificationService.getShared(context).authenticatePattern(baseurl,authenticatePatternRequestEntity,callback);
        }
        catch (Exception e)
        {
            // Handle Errror
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

*/





































    // --------------------------------------------------***** CONSENT *****-----------------------------------------------------------------------------------------------------------------------


    // --------------------------------------------------***** EMAIL MFA *****-----------------------------------------------------------------------------------------------------------------------


    // --------------------------------------------------***** SMS MFA *****-----------------------------------------------------------------------------------------------------------------------


    // --------------------------------------------------***** IVR MFA *****-----------------------------------------------------------------------------------------------------------------------

    // --------------------------------------------------***** BACKUPCODE MFA *****-----------------------------------------------------------------------------------------------------------------------



    // --------------------------------------------------***** FACE MFA *****-----------------------------------------------------------------------------------------------------------------------

    // --------------------------------------------------***** FIDO MFA *****-----------------------------------------------------------------------------------------------------------------------


    // --------------------------------------------------***** FINGERPRINT MFA *****-----------------------------------------------------------------------------------------------------------------------

    // --------------------------------------------------***** PATTERN MFA *****-----------------------------------------------------------------------------------------------------------------------





    // --------------------------------------------------***** SMART PUSH MFA *****-----------------------------------------------------------------------------------------------------------------------


    // --------------------------------------------------***** TOTP MFA *****-----------------------------------------------------------------------------------------------------------------------


    // --------------------------------------------------***** VOICE MFA *****-----------------------------------------------------------------------------------------------------------------------


    // --------------------------------------------------*****  LOGIN *****-----------------------------------------------------------------------------------------------------------------------




    // --------------------------------------------------*****  REGISTER  *****-----------------------------------------------------------------------------------------------------------------------


    // --------------------------------------------------*****  LOGIN *****-----------------------------------------------------------------------------------------------------------------------





    //Get MFA List

    public void getmfaList(String sub, String baseurl,String userDeviceID, final Result<MFAListResponseEntity> callback)
    {
        //Local Variables
        String mfalistUrl = "";
        try{

            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                if(sub!=null && sub!=""){
                    //Construct URL For RequestId

                    mfalistUrl=baseurl+URLHelper.getShared().getMfa_URL();
                }
                else {
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,context.getString(R.string.MFA_LIST_FAILURE),
                            400,null));
                    return;
                }
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,context.getString(R.string.PROPERTY_MISSING),
                        400,null));
                return;
            }


            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getmfaList(mfalistUrl,sub,userDeviceID).enqueue(new Callback<MFAListResponseEntity>() {
                @Override
                public void onResponse(Call<MFAListResponseEntity> call, Response<MFAListResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,
                                    "Service failure but successful response" ,response.code(),null));
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



                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,errorMessage,
                                    commonErrorEntity.getStatus(),commonErrorEntity.getError()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<MFAListResponseEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,t.getMessage(), 400,null));

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(webAuthError);
        }
    }


    //Get Login URL
    public void getLoginUrl(String requestId, final Result<String> callback)
    {
       try {
           //Local Variables
           String url = "";
           String baseUrl = "";

           URLHelper urlComponents;
           Map<String, String> headers = new Hashtable<>();
           // Get Device Information
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

           Dictionary<String, String> loginProperties = DBHelper.getShared().getLoginProperties();
           if (loginProperties == null) {
               callback.failure(webAuthError.loginURLMissingException());
           }
           //Assign base URL

           //Check the Domain URL is null or not
           if (loginProperties.get("DomainURL") == null || loginProperties.get("DomainURL") == "" || !((Hashtable) loginProperties).containsKey("DomainURL")) {
               //return Null
               baseUrl = "";
           } else {
               baseUrl = loginProperties.get("DomainURL");

           }
           //Todo Construct URl Checking,Add Parameter(FieldMap) pending
           urlComponents = new URLHelper();

           if (loginProperties.get("DomainURL") == null || loginProperties.get("DomainURL") == "" || !((Hashtable) loginProperties).containsKey("DomainURL")) {
               //return Null
               url = "";
           } else {
               url = urlComponents.constructURL(loginProperties.get("DomainURL"));

           }

           //Call Service-getRequestId
           ICidaasSDKService cidaasSDKService = service.getInstance();
           cidaasSDKService.getLoginUrl(url,headers).enqueue(new Callback<String>() {
               @Override
               public void onResponse(Call<String> call, Response<String> response) {
                   if (response.isSuccessful()) {
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
               public void onFailure(Call<String> call, Throwable t) {
                   Timber.e("Faliure in Request id service call"+t.getMessage());
                   callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CLIENT_INFO_FAILURE,t.getMessage(), 400,null));

               }
           });





          /* cidaasSDKService.getLoginUrl(url, headers).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
               @Override
               public void onCompleted() {

               }

               @Override
               public void onError(Throwable e) {
                   callback.failure(webAuthError.serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE, e.getMessage(), 400));
               }

               @Override
               public void onNext(String s) {
                   callback.success(s);
               }
           });*/
       }
       catch (Exception e)
       {
           Timber.d(e.getMessage());
           callback.failure(webAuthError);
       }
    }



    public void getUserinfo(String AccessToken, final Result<UserinfoEntity> callback) {
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
            headers.put("access_token", AccessToken);
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());


            //Get Properties From DB

            Dictionary<String, String> loginProperties = DBHelper.getShared().getLoginProperties();
            if (loginProperties == null) {
                callback.failure(webAuthError.loginURLMissingException());
            }


            querymap.put("UserInfoURL",loginProperties.get("UserInfoURL"));

            //Assign Url
            //TOdo Perform Null Check
            url = querymap.get("UserInfoURL");

            //call Service
            ICidaasSDKService cidaassdkService = service.getInstance();
          cidaassdkService.getUserInfo(url,headers).enqueue(new Callback<UserinfoEntity>() {
              @Override
              public void onResponse(Call<UserinfoEntity> call, Response<UserinfoEntity> response) {
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
              public void onFailure(Call<UserinfoEntity> call, Throwable t) {
                  Timber.e("Faliure in getAccessTokenByCode id call"+t.getMessage());
                  callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,t.getMessage(), 400,null));

              }
          });
          }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(webAuthError);
        }
    }

}
