package com.example.cidaasv2.Service.Repository.Verification.Device;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceRequestEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DeviceVerificationService {

    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables

    private Context context;


    public static DeviceVerificationService shared;

    public DeviceVerificationService(Context contextFromCidaas) {

        context=contextFromCidaas;
        if(service==null) {
            service=new CidaassdkService(context);
        }


        //Todo setValue for authenticationType

    }


    public static DeviceVerificationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new DeviceVerificationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //ValidateDevice
    public void validateDevice(String baseurl, String intermediateId,String statusId,String codeVerifier,DeviceInfoEntity deviceInfoEntityFromParam,
                               final Result<ValidateDeviceResponseEntity> callback)
    {
        String validateDeviceUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                validateDeviceUrl=baseurl+ URLHelper.getShared().getValidateDeviceURL();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),"Error :DeviceVerificationService :validateDevice()"));
                return;
            }

            Map<String, String> headers = new Hashtable<>();

            // Get Device Information
            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromParam==null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromParam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromParam;
            }
            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers

            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());




            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {
               // Chaange to FCM acceptence now it is in Authenticator
                 deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            ValidateDeviceRequestEntity validateDeviceRequestEntity=new ValidateDeviceRequestEntity();
            validateDeviceRequestEntity.setIntermediate_verifiation_id(intermediateId);
            validateDeviceRequestEntity.setStatusId(statusId);
            validateDeviceRequestEntity.setAccess_verifier(codeVerifier);
            validateDeviceRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.validateDevice(validateDeviceUrl,headers, validateDeviceRequestEntity).enqueue(new Callback<ValidateDeviceResponseEntity>() {
                @Override
                public void onResponse(Call<ValidateDeviceResponseEntity> call, Response<ValidateDeviceResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());

                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE,
                                    response.code(),"Error :DeviceVerificationService :validateDevice()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE,response
                                ,"Error :DeviceVerificationService :validateDevice()"));
                    }
                }

                @Override
                public void onFailure(Call<ValidateDeviceResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE,t.getMessage(),
                            "Error :DeviceVerificationService :validateDevice()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :DeviceVerificationService :validateDevice()",
                    WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE,e.getMessage()));

        }
    }


}
