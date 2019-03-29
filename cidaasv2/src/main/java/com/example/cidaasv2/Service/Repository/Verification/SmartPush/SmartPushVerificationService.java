package com.example.cidaasv2.Service.Repository.Verification.SmartPush;

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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SmartPush.AuthenticateSmartPushResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SmartPush.EnrollSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SmartPush.InitiateSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SmartPush.SetupSmartPushMFAResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SmartPushVerificationService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static SmartPushVerificationService shared;

    public SmartPushVerificationService(Context contextFromCidaas) {
        sub="";
        statusId="";
        verificationType="";
        context=contextFromCidaas;
        authenticationType="";
        //Todo setValue for authenticationType
        if(service==null) {
            service=new CidaassdkService();
        }
    }



    public static SmartPushVerificationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new SmartPushVerificationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void scannedSmartPush(final String baseurl, ScannedRequestEntity scannedRequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                                 final Result<ScannedResponseEntity>  callback)
    {
        String scannedSmartPushUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                scannedSmartPushUrl=baseurl+ URLHelper.getShared().getScannedSmartPushURL();
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
            headers.put("verification_api_version","2");
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }


            scannedRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.scanned(scannedSmartPushUrl,headers, scannedRequestEntity).enqueue(new Callback<ScannedResponseEntity>() {
                @Override
                public void onResponse(Call<ScannedResponseEntity> call, Response<ScannedResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            DBHelper.getShared().setUserDeviceId(response.body().getData().getUserDeviceId(), baseurl);
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_SMARTPUSH_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_SMARTPUSH_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Scanned Smartpush service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Scanned Smartpush Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_SMARTPUSH_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Scanned smartpush Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SCANNED_SMARTPUSH_MFA_FAILURE));
            Timber.e("Scanned Smartpush Service exception"+e.getMessage());
        }
    }


    //setupSmartPushMFA
    public void setupSmartPush(String baseurl, String accessToken,  SetupSmartPushMFARequestEntity setupSmartPushMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<SetupSmartPushMFAResponseEntity> callback)
    {
        String setupSmartPushMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                setupSmartPushMFAUrl=baseurl+URLHelper.getShared().getSetupSmartPushMFA();
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
            headers.put("verification_api_version","2");
            headers.put("access_token",accessToken);
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
             }
            setupSmartPushMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupSmartPushMFA(setupSmartPushMFAUrl,headers, setupSmartPushMFARequestEntity).enqueue(new Callback<SetupSmartPushMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupSmartPushMFAResponseEntity> call, Response<SetupSmartPushMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());

                            //Todo Listen For The Push notification to recieve

                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_SMARTPUSH_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_SMARTPUSH_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<SetupSmartPushMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in set up Smartpush service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("setup Smartpush Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_SMARTPUSH_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Enroll Smartpush Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SETUP_SMARTPUSH_MFA_FAILURE));
            Timber.e("Enroll Service exception"+e.getMessage());
        }
    }


    //enrollSmartPushMFA
    public void enrollSmartPush(String baseurl, String accessToken, EnrollSmartPushMFARequestEntity enrollSmartPushMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<EnrollSmartPushMFAResponseEntity> callback)
    {
        String enrollSmartPushMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                enrollSmartPushMFAUrl=baseurl+URLHelper.getShared().getEnrollSmartPushMFA();
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
            headers.put("access_token",accessToken);
            headers.put("verification_api_version","2");
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Done Change to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

           }


            enrollSmartPushMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.enrollSmartPushMFA(enrollSmartPushMFAUrl,headers, enrollSmartPushMFARequestEntity).enqueue(new Callback<EnrollSmartPushMFAResponseEntity>() {
                @Override
                public void onResponse(Call<EnrollSmartPushMFAResponseEntity> call, Response<EnrollSmartPushMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<EnrollSmartPushMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Enroll Smartpush service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Enroll Smartpush Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Enroll Smartpush Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE));
            Timber.e("Enroll Smartpush Service exception"+e.getMessage());
        }
    }


    //initiateSmartPushMFA
    public void initiateSmartPush(String baseurl, final InitiateSmartPushMFARequestEntity initiateSmartPushMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<InitiateSmartPushMFAResponseEntity> callback)
    {
        String initiateSmartPushMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                initiateSmartPushMFAUrl=baseurl+URLHelper.getShared().getInitiateSmartPushMFA();
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
            headers.put("verification_api_version","2");
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

            }

            initiateSmartPushMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateSmartPushMFA(initiateSmartPushMFAUrl,headers, initiateSmartPushMFARequestEntity).enqueue(new Callback<InitiateSmartPushMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiateSmartPushMFAResponseEntity> call, Response<InitiateSmartPushMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_SMARTPUSH_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_SMARTPUSH_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<InitiateSmartPushMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Initiate SmartPush Service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Initiate SmartPush MFA Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_SMARTPUSH_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });

        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Initiate SmartPush Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_SMARTPUSH_MFA_FAILURE));
            Timber.e("Initiate SmartPush Service exception"+e.getMessage());
        }
    }


    //authenticateSmartPushMFA
    public void authenticateSmartPush(String baseurl, AuthenticateSmartPushRequestEntity authenticateSmartPushRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                                         final Result<AuthenticateSmartPushResponseEntity> callback)
    {
        String authenticateSmartPushMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                authenticateSmartPushMFAUrl=baseurl+URLHelper.getShared().getAuthenticateSmartPushMFA();
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
            headers.put("user-agent", "cidaas-android");
            headers.put("verification_api_version","2");
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            authenticateSmartPushRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticateSmartPushMFA(authenticateSmartPushMFAUrl,headers, authenticateSmartPushRequestEntity).enqueue(new Callback<AuthenticateSmartPushResponseEntity>() {
                @Override
                public void onResponse(Call<AuthenticateSmartPushResponseEntity> call, Response<AuthenticateSmartPushResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_SMARTPUSH_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_SMS_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateSmartPushResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Authenticate SmartPush  service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Authenticate SmartPush Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_SMARTPUSH_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Authenticate SmartPush Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.AUTHENTICATE_IVR_MFA_FAILURE));
            Timber.e("Authenticate SmartPush Service exception"+e.getMessage());
        }
    }

}
