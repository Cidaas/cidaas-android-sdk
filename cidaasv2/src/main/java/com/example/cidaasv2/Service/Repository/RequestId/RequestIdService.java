package com.example.cidaasv2.Service.Repository.RequestId;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestEntity;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.Nullable;
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
            service.setContext(context);
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
    public void getRequestID( Dictionary<String,String> loginProperties, DeviceInfoEntity deviceInfoEntityFromparam, Dictionary<String,String> challengePropertiesfromparam,
                              final Result<AuthRequestResponseEntity> callback,@Nullable HashMap<String, String>... extraParams)
    {
        try {
            //Local Variables
            String baseUrl = "";
            String requestidURL="";
            final AuthRequestEntity authRequestEntity;

            URLHelper urlComponents;
            Map<String, String> headers = new Hashtable<>();
            // Get Device Information

            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromparam==null) {
               deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromparam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromparam;
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

            Timber.d("LAtitude"+LocationDetails.getShared(context).getLatitude());
            Timber.d("Longitude"+LocationDetails.getShared(context).getLongitude());

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentType);
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            //Todo Construct URl Checking,Add Parameter(FieldMap) pending
            urlComponents = new URLHelper();

            if (loginProperties.get("DomainURL") == null || loginProperties.get("DomainURL").equals("") || !((Hashtable) loginProperties).containsKey("DomainURL")) {
                //return Null
                baseUrl = "";
            } else {
                // baseUrl = urlComponents.constructURL(loginProperties.get("DomainURL"));

                baseUrl=loginProperties.get("DomainURL");

                //Construct URL For RequestId
                requestidURL=baseUrl+URLHelper.getShared().getRequest_id_url();
            }



            String codeChallenge="";
            String clientSecret=" ";

            if(challengeProperties.get("Challenge")!=null) {
             codeChallenge = challengeProperties.get("Challenge");
            }
            if(challengeProperties.get("ClientSecret")!=null) {
             clientSecret = challengeProperties.get("ClientSecret");
            }


            Map<String, String> authRequestEntityMap = new HashMap<>();

            authRequestEntityMap.put("client_id", URLEncoder.encode(loginProperties.get("ClientId"),"utf-8"));
            authRequestEntityMap.put("redirect_uri", URLEncoder.encode(loginProperties.get("RedirectURL"), "utf-8"));
            authRequestEntityMap.put("response_type","code");
            authRequestEntityMap.put("nonce",UUID.randomUUID().toString());
            authRequestEntityMap.put("client_secret",clientSecret);
            authRequestEntityMap.put("code_challenge",codeChallenge);
            authRequestEntityMap.put("code_challenge_method","S256");

            for(Map.Entry<String,String> entry : Cidaas.extraParams.entrySet()) {
                authRequestEntityMap.put(entry.getKey(),URLEncoder.encode(entry.getValue(),"utf-8"));
            }



            //Todo add codeChallenge and codeChallengeMethod and clientSecret
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();


            //Service call
            cidaasSDKService.getRequestId(requestidURL,headers,authRequestEntityMap).enqueue(new Callback<AuthRequestResponseEntity>() {
                @Override
                public void onResponse(Call<AuthRequestResponseEntity> call, Response<AuthRequestResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,
                                    "Service failure but successful response" , 400,null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<AuthRequestResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Request id service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,t.getMessage(), 400,null,null));
                }
            });

        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            LogFile.getShared(context).addRecordToLog(e.getMessage()+WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE);
            callback.failure(WebAuthError.getShared(context).serviceException("Exception :RequestIdService :getRequestID()",WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,e.getMessage()));
        }
    }

}
