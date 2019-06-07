package com.example.cidaasv2.Service.Repository.RequestId;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.HashMap;
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


    //----------------------------------------------------------Get Request ID From Service--------------------------------------------------------------
    public void getRequestID( Dictionary<String,String> loginProperties, final Result<AuthRequestResponseEntity> callback,
                              @Nullable HashMap<String, String>... extraParams)
    {
        String methodName="RequestIdService :getRequestID()";
        try
        {

            if (loginProperties.get("DomainURL") != null && !loginProperties.get("DomainURL").equals("")) {

            //Header Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,URLHelper.contentType);

            // Get Device Information
            Dictionary<String,String> challengeProperties=DBHelper.getShared().getChallengeProperties();

            //Construct URL For RequestId
            String requestidURL=loginProperties.get("DomainURL") +URLHelper.getShared().getRequest_id_url();

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
            serviceForGetRequestId(requestidURL, authRequestEntityMap, headers, callback);
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Error :"+methodName));
            }


        }
        catch (Exception e)
        {
      callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,e.getMessage()));
        }
    }

    public void serviceForGetRequestId(String requestidURL, Map<String, String> authRequestEntityMap, Map<String, String> headers,
                                       final Result<AuthRequestResponseEntity> callback)
    {
        final String methodName="RequestIdService :getRequestID()";
        try {
            //Todo add codeChallenge and codeChallengeMethod and clientSecret
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();


            //Service call
            cidaasSDKService.getRequestId(requestidURL, headers, authRequestEntityMap).enqueue(new Callback<AuthRequestResponseEntity>() {
                @Override
                public void onResponse(Call<AuthRequestResponseEntity> call, Response<AuthRequestResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,
                                    response.code(), "Error :"+methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE, response
                                , "Error :"+methodName));
                    }
                }

                @Override
                public void onFailure(Call<AuthRequestResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE, t.getMessage(),
                             "Exception :"+methodName));
                }
            });
        }
        catch (Exception e)
        {
      callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,e.getMessage()));
        }
    }

}
