package com.example.cidaasv2.Service.Repository.LocationHistory;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
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
import com.example.cidaasv2.Service.Entity.UserLoginInfo.UserLoginInfoEntity;
import com.example.cidaasv2.Service.Entity.UserLoginInfo.UserLoginInfoResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class UserLoginInfoService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables

    private Context context;

    public static UserLoginInfoService shared;

    public UserLoginInfoService(Context contextFromCidaas) {

        context=contextFromCidaas;


        if(service==null) {
            service=new CidaassdkService();
        }

        //Todo setValue for authenticationType

    }


    public static UserLoginInfoService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new UserLoginInfoService(contextFromCidaas);
            }

        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void getUserLoginInfoService(String baseurl, String accessToken, UserLoginInfoEntity userLoginInfoEntity, DeviceInfoEntity deviceInfoEntityFromparam, final Result<UserLoginInfoResponseEntity> callback)
    {
        String UserLoginInfoURL = "";
        try
        {

                if(baseurl!=null && !baseurl.equals("")){
                    //Construct URL For RequestId

                    //Todo Chnage URL Global wise
                    UserLoginInfoURL=baseurl+ URLHelper.getShared().getUserLoginInfoURL();
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
                headers.put("lon",LocationDetails.getShared(context).getLongitude());
                headers.put("access_token",accessToken);

                //Call Service-getRequestId
                ICidaasSDKService cidaasSDKService = service.getInstance();
                cidaasSDKService.getUserLoginInfoService(UserLoginInfoURL,headers, userLoginInfoEntity).enqueue(new Callback<UserLoginInfoResponseEntity>() {
                    @Override
                    public void onResponse(Call<UserLoginInfoResponseEntity> call, Response<UserLoginInfoResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if(response.code()==200) {
                                callback.success(response.body());
                            }
                            else if(response.code()==204) {
                                UserLoginInfoResponseEntity userLoginInfoResponseEntity=new UserLoginInfoResponseEntity();
                                userLoginInfoResponseEntity.setStatus(response.code());
                                userLoginInfoResponseEntity.setSuccess(response.isSuccessful());
                                callback.success(userLoginInfoResponseEntity);
                            }
                            else {
                                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE,
                                        "Service failure but successful response" , 400,null,null));
                            }
                        }
                        else {
                            assert response.errorBody() != null;
                            callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE,response));
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLoginInfoResponseEntity> call, Throwable t) {
                        Timber.e("Failure in Request id service call"+t.getMessage());
                        callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE,
                                t.getMessage(), 400,null,null));

                    }
                });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.USER_LOGIN_INFO_SERVICE_FAILURE,e.getMessage(), HttpStatusCode.BAD_REQUEST));
        }
    }

}
