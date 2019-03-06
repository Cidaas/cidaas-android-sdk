package com.example.cidaasv2.Service.Repository.LocationHistory;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.LocationHistory.LocationHistoryRequestEntity;
import com.example.cidaasv2.Service.Entity.LocationHistory.LocationHistoryResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LocationHistoryService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables

    private Context context;

    public static LocationHistoryService shared;

    public LocationHistoryService(Context contextFromCidaas) {

        context=contextFromCidaas;


        if(service==null) {
            service=new CidaassdkService();
        }

        //Todo setValue for authenticationType

    }


    public static LocationHistoryService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new LocationHistoryService(contextFromCidaas);
            }

        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void getLocationHistoryService(String baseurl, String accessToken, LocationHistoryRequestEntity locationHistoryRequestEntity, DeviceInfoEntity deviceInfoEntityFromparam, final Result<LocationHistoryResponseEntity> callback)
    {
        String locationHistoryURL = "";
        try
        {

                if(baseurl!=null && baseurl!=""){
                    //Construct URL For RequestId

                    //Todo Chnage URL Global wise
                    locationHistoryURL=baseurl+ URLHelper.getShared().getLocationHistory();
                }
                else {
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                            context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                    return;
                }


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


                //Todo - check Construct Headers pending,Null Checking Pending

                //Add headers
                headers.put("Content-Type", URLHelper.contentTypeJson);
                headers.put("lat", LocationDetails.getShared(context).getLatitude());
                headers.put("long",LocationDetails.getShared(context).getLongitude());
                headers.put("access_token",accessToken);

                //Call Service-getRequestId
                ICidaasSDKService cidaasSDKService = service.getInstance();
                cidaasSDKService.getLocationHistoryDetails(locationHistoryURL,headers,locationHistoryRequestEntity).enqueue(new Callback<LocationHistoryResponseEntity>() {
                    @Override
                    public void onResponse(Call<LocationHistoryResponseEntity> call, Response<LocationHistoryResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if(response.code()==200) {
                                callback.success(response.body());
                            }
                            else {
                                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.LOCATION_HISTORY_SERVICE_FAILURE,
                                        "Service failure but successful response" , 400,null,null));
                            }
                        }
                        else {
                            assert response.errorBody() != null;
                            String errorResponse = null;
                            try {

                                //Todo Handle proper error message
                                errorResponse=response.errorBody().source().readByteString().utf8();

                                CommonErrorEntity commonErrorEntity;
                                commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                                String errorMessage="";
                                ErrorEntity errorEntity=new ErrorEntity();
                                if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("")
                                        && commonErrorEntity.getError() instanceof  String) {
                                    errorMessage=commonErrorEntity.getError().toString();
                                }
                                else
                                {
                                    errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                    errorEntity.setCode( ((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                    errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                    errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                    errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                    errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                    errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                                }

                                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.LOCATION_HISTORY_SERVICE_FAILURE,
                                        errorMessage, commonErrorEntity.getStatus(),
                                        commonErrorEntity.getError(),errorEntity));
                            } catch (Exception e) {
                                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.LOCATION_HISTORY_SERVICE_FAILURE,
                                        "Unexpected Error"+errorResponse, 417,null,null));
                            }
                            Timber.e("response"+response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<LocationHistoryResponseEntity> call, Throwable t) {
                        Timber.e("Failure in Request id service call"+t.getMessage());
                        callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.LOCATION_HISTORY_SERVICE_FAILURE,
                                t.getMessage(), 400,null,null));

                    }
                });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.LOCATION_HISTORY_SERVICE_FAILURE,e.getMessage(), HttpStatusCode.BAD_REQUEST));
        }
    }

}
