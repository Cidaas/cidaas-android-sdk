package com.example.cidaasv2.Service.Repository.Tenant;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.TenantInfo.TenantInfoEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TenantService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables

    private Context context;

    public static TenantService shared;

    public TenantService(Context contextFromCidaas) {

        context=contextFromCidaas;


        if(service==null) {
            service=new CidaassdkService();
        }

        //Todo setValue for authenticationType

    }


    public static TenantService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new TenantService(contextFromCidaas);
            }
        }
        catch (Exception e)
        { Timber.i("Exception"+e.getMessage());
        }
        return shared;
    }


    //Get Tenant info
    public void getTenantInfo(String baseurl, final Result<TenantInfoEntity> callback)
    {
        //Local Variables
        String TenantUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId

                //Todo Chnage URL Global wise
                TenantUrl=baseurl+ URLHelper.getShared().getTenantUrl();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }


            Map<String, String> headers = new Hashtable<>();
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getTenantInfo(TenantUrl,headers).enqueue(new Callback<TenantInfoEntity>() {
                @Override
                public void onResponse(Call<TenantInfoEntity> call, Response<TenantInfoEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.TENANT_INFO_FAILURE,
                                    "Service failure but successful response" , 400,null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.TENANT_INFO_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<TenantInfoEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.TENANT_INFO_FAILURE,t.getMessage(), 400,null,null));

                }
            });
        }
        catch (Exception e)
        { String loggerMessage = "TenantService-getTenantInfoException: Error Message - " + e.getMessage();
          LogFile.getShared(context).addRecordToLog(loggerMessage); Timber.d(e.getMessage());
         callback.failure(WebAuthError.getShared(context).serviceException("Exception :TenantService :getTenantInfo()",WebAuthErrorCode.TENANT_INFO_FAILURE,e.getMessage()));
        }
    }

}
