package com.example.cidaasv2.Service.Repository.Client;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ClientInfo.ClientInfoEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ClientService {
    //Get Client info
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables

    private Context context;

    public static  ClientService shared;

    public  ClientService(Context contextFromCidaas) {

        if(service==null) {
            service=new CidaassdkService();
        }
        context=contextFromCidaas;
        if(service==null) {
            service=new CidaassdkService();

            service.setContext(context);
        }


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

    public void getClientInfo(String requestId, String baseurl, final Result<ClientInfoEntity> callback)
    {
        //Local Variables
        String clienttUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                if(requestId!=null && !requestId.equals("")){
                    //Construct URL For RequestId

                    clienttUrl=baseurl+ URLHelper.getShared().getClientUrl(requestId);
                }
                else {
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REQUEST_ID_MISSING,context.getString(R.string.REQUEST_ID_MISSING),
                            400,null,null));
                    return;
                }
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,context.getString(R.string.PROPERTY_MISSING),
                        400,null,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getClientInfo(clienttUrl,headers).enqueue(new Callback<ClientInfoEntity>() {
                @Override
                public void onResponse(Call<ClientInfoEntity> call, Response<ClientInfoEntity> response) {
                    if (response.isSuccessful()) {
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
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.CLIENT_INFO_FAILURE,response));                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ClientInfoEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CLIENT_INFO_FAILURE,t.getMessage(), 400,null,null));

                }
            });
        }
        catch (Exception e)
        {

            Timber.d(e.getMessage());
            LogFile.getShared(context).addRecordToLog(e.getMessage()+WebAuthErrorCode.CLIENT_INFO_FAILURE);
            callback.failure(WebAuthError.getShared(context).serviceException("Exception :ClientService :getClientInfo()",WebAuthErrorCode.CLIENT_INFO_FAILURE,e.getMessage()));
        }
    }


}
