package com.example.cidaasv2.Service.Repository.Verification.Device;

import android.content.Context;

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
                //Todo Chaange to FCM acceptence now it is in Authenticator
                 deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

           /*     deviceInfoEntity.setPushNotificationId("emwuJgX9_5g:APA91bGyW8Tgl7p68siQvJc8qtS_lkc6ffTPnk1-6Mq2PWjbzmDJMaT30uCRx9F" +
                        "aiJnmkRc9nWUIaGXtB9khMThBfGsNaHo-N_iW-8Tt1GUn5fY9yurjq94mQNfd45P10XcvUJBF4WZwfV-N6IEmBQWRHKmyII1QLA");
           */ }

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
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        try {

                            // Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);

                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,e.getMessage(), 400,null,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ValidateDeviceResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("acceptConsent Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("acceptConsent Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("acceptConsent Service exception"+e.getMessage());
        }
    }


}
