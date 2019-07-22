package com.example.cidaasv2.Service.Repository.Verification.SmartPush;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
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
            service=new CidaassdkService(context);
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
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :SmartPushVerificationService :scannedSmartPush()"));
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
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SCANNED_SMARTPUSH_MFA_FAILURE,
                                    response.code(),"Error :SmartPushVerificationService :scannedSmartPush()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_SMARTPUSH_MFA_FAILURE,response
                                ,"Error :SmartPushVerificationService :scannedSmartPush()"));
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_SMARTPUSH_MFA_FAILURE,
                            t.getMessage(), "Error :SmartPushVerificationService :scannedSmartPush()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushVerificationService :scannedSmartPush()",
                    WebAuthErrorCode.SCANNED_SMARTPUSH_MFA_FAILURE,e.getMessage()));
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
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :SmartPushVerificationService :enrollSmartPush()"));
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
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SETUP_SMARTPUSH_MFA_FAILURE,
                                    response.code(),"Error :SmartPushVerificationService :enrollSmartPush()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_SMARTPUSH_MFA_FAILURE,response,
                                "Error :SmartPushVerificationService :enrollSmartPush()"));
                    }
                }

                @Override
                public void onFailure(Call<SetupSmartPushMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in set up Smartpush service call"+t.getMessage());
                    LogFile.getShared(context).addFailureLog("callSetup Smartpush Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(
                            WebAuthErrorCode.SETUP_SMARTPUSH_MFA_FAILURE,t.getMessage(), "Error :SmartPushVerificationService :enrollSmartPush()"));
                }
            });


        }
        catch (Exception e)
        {
             callback.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushVerificationService :setupSmartPush()",WebAuthErrorCode.SETUP_SMARTPUSH_MFA_FAILURE,e.getMessage()));

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
                callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), "Error :SmartPushVerificationService :enrollSmartPush()"));
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
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,
                                    response.code(),"Error :SmartPushVerificationService :enrollSmartPush()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,
                                response,"Error :SmartPushVerificationService :enrollSmartPush()"));
                    }
                }

                @Override
                public void onFailure(Call<EnrollSmartPushMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Enroll Smartpush service call"+t.getMessage());
                    LogFile.getShared(context).addFailureLog("Enroll Smartpush Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,
                            t.getMessage(), "Error :SmartPushVerificationService :enrollSmartPush()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushVerificationService :enrollSmartPush()",WebAuthErrorCode.ENROLL_SMARTPUSH_MFA_FAILURE,e.getMessage()));
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
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :SmartPushVerificationService :initiateSmartPush()"));
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
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.INITIATE_SMARTPUSH_MFA_FAILURE,
                                    response.code(),"Error :SmartPushVerificationService :initiateSmartPush()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_SMARTPUSH_MFA_FAILURE,
                                response,"Error :SmartPushVerificationService :initiateSmartPush()"));
                    }
                }

                @Override
                public void onFailure(Call<InitiateSmartPushMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Initiate SmartPush Service call"+t.getMessage());
                    LogFile.getShared(context).addFailureLog("Initiate SmartPush MFA Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_SMARTPUSH_MFA_FAILURE,t.getMessage(),
                            "Error :SmartPushVerificationService :initiateSmartPush()"));
                }
            });

        }
        catch (Exception e)
        {
             callback.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushVerificationService :initiateSmartPush()",WebAuthErrorCode.INITIATE_SMARTPUSH_MFA_FAILURE,e.getMessage()));
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
                callback.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :SmartPushVerificationService :authenticateSmartPush()"));
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
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.AUTHENTICATE_SMARTPUSH_MFA_FAILURE,
                                    response.code(),"Error :SmartPushVerificationService :authenticateSmartPush()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_SMS_MFA_FAILURE,response
                        ,"Error :SmartPushVerificationService :authenticateSmartPush()"));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateSmartPushResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.AUTHENTICATE_SMARTPUSH_MFA_FAILURE,t.getMessage(),
                            "Error :SmartPushVerificationService :authenticateSmartPush()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :SmartPushVerificationService :authenticateSmartPush()",
                    WebAuthErrorCode.AUTHENTICATE_IVR_MFA_FAILURE,e.getMessage()));
        }
    }

}
