package com.example.cidaasv2.Service.Repository.RequestId;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.URLHelper;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
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

public class RequestIdService {



    private Context context;

    public static RequestIdService shared;

    CidaassdkService service;
    public ObjectMapper objectMapper=new ObjectMapper();

    public RequestIdService(Context contextFromCidaas) {
        context=contextFromCidaas;
        if(service==null) {
            service=new CidaassdkService();
        }

        //Todo setValue for authenticationType

    }

    public static RequestIdService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new RequestIdService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //Get Request ID From Service
    public void getRequestID(Dictionary<String,String> loginProperties, final Result<AuthRequestResponseEntity> callback)
    {
        try {
            //Local Variables
            String url = "";
            String baseUrl = "";
            String requestidURL="";
            final AuthRequestEntity authRequestEntity;

            URLHelper urlComponents;
            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());


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
                baseUrl = "";
            } else {
                // baseUrl = urlComponents.constructURL(loginProperties.get("DomainURL"));

                baseUrl=loginProperties.get("DomainURL");

                //Construct URL For RequestId
                requestidURL=baseUrl+URLHelper.getShared().getRequest_id_url();
            }


            Dictionary<String,String> challengeProperties=DBHelper.getShared().getChallengeProperties();

            //Create Auth Request entity to get Request Id
            authRequestEntity=new AuthRequestEntity();
            authRequestEntity.setClient_id(loginProperties.get("ClientId"));
            authRequestEntity.setRedirect_uri(loginProperties.get("RedirectURL"));
            authRequestEntity.setResponse_type("code");
            authRequestEntity.setNonce("12345");
            authRequestEntity.setScope("openid profile email offline_access");
            authRequestEntity.setClient_secret(challengeProperties.get("ClientSecret"));
            authRequestEntity.setCode_challenge(challengeProperties.get("Challenge"));
            authRequestEntity.setCode_challenge_method("S256");

            //Todo add codeChallenge and codeChallengeMethod and clientSecret
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();


            //Service call
            cidaasSDKService.getRequestId(requestidURL,headers,authRequestEntity).enqueue(new Callback<AuthRequestResponseEntity>() {
                @Override
                public void onResponse(Call<AuthRequestResponseEntity> call, Response<AuthRequestResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,
                                    "Service failure but successful response" , 400,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            //Todo Handle proper error message
                            String errorResponse = response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);

                            String errorMessage="";
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }



                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<AuthRequestResponseEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,t.getMessage(), 400,null));
                }
            });

        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

}
