package com.example.cidaasv2.Service.Repository.Client;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientService {
    //Get Client info
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables

    private Context context;

    public static  ClientService shared;

    public  ClientService(Context contextFromCidaas) {

        if(service==null) {
            service=new CidaassdkService(context);
        }
        context=contextFromCidaas;


        //Todo setValue for authenticationType

    }


    public static  ClientService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  ClientService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
           // Timber.i(e.getMessage());
        }
        return shared;
    }

    //---------------------------------------------------------getClientInfo------------------------------------------------------------------
    public void getClientInfo(String requestId, String baseurl, final Result<ClientInfoEntity> callback)
    {
        //Local Variables
        String methodName = "ClientService  :getClientInfo()";
        try{

            if(baseurl!=null && !baseurl.equals("") && requestId!=null && !requestId.equals(""))
            {
                //Construct URL For RequestId
                String clienttUrl=baseurl+ URLHelper.getShared().getClientUrl(requestId);

                //Header Generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,null);

                //Service call
                ServiceForClient( clienttUrl, headers,callback);
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("RequestId or baseurl must not be empty","Error :"+methodName));
                return;
            }

        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.CLIENT_INFO_FAILURE,e.getMessage()));
        }
    }

    private void ServiceForClient( String clienttUrl, Map<String, String> headers,final Result<ClientInfoEntity> callback)
    {
        final String methodName="Consent Service  :getClientInfo()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getClientInfo(clienttUrl, headers).enqueue(new Callback<ClientInfoEntity>() {
                @Override
                public void onResponse(Call<ClientInfoEntity> call, Response<ClientInfoEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.CLIENT_INFO_FAILURE, response.code(),
                                    "Error :"+methodName));
                        }
                    } else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.CLIENT_INFO_FAILURE, response,
                                "Error :"+methodName));

                    }
                }

                @Override
                public void onFailure(Call<ClientInfoEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.CLIENT_INFO_FAILURE, t.getMessage(),
                            "Error :"+methodName));

                }
            });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException(methodName,WebAuthErrorCode.CLIENT_INFO_FAILURE,e.getMessage()));
        }
    }


}
