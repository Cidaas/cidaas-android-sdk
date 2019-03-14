package com.example.cidaasv2.Service.Repository.Verification.Device;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceRequestEntity;
import com.example.cidaasv2.Service.Entity.ValidateDevice.ValidateDeviceResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
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
            service=new CidaassdkService();
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
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
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
            headers.put("long",LocationDetails.getShared(context).getLongitude());




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
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<ValidateDeviceResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Validate Device service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Validate Device Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Validate Device Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.DEVICE_VERIFICATION_FAILURE));
            Timber.e("Validate Device Service exception"+e.getMessage());
        }
    }


}
