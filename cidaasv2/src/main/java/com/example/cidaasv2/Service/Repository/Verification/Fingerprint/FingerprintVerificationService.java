package com.example.cidaasv2.Service.Repository.Verification.Fingerprint;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Fingerprint.EnrollFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Fingerprint.InitiateFingerprintMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Fingerprint.SetupFingerprintMFAResponseEntity;
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

public class FingerprintVerificationService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static FingerprintVerificationService shared;

    public FingerprintVerificationService(Context contextFromCidaas) {
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



    public static FingerprintVerificationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new FingerprintVerificationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void scannedFingerprint(final String baseurl, ScannedRequestEntity scannedRequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                                   final Result<ScannedResponseEntity> callback)
    {
        String scannedFingerprintUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                scannedFingerprintUrl=baseurl+ URLHelper.getShared().getScannedFingerprintURL();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty",
                        "Error :FingerprintVerificationService :scannedFingerprint()"));
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

            cidaasSDKService.scanned(scannedFingerprintUrl,headers, scannedRequestEntity).enqueue(new Callback<ScannedResponseEntity>() {
                @Override
                public void onResponse(Call<ScannedResponseEntity> call, Response<ScannedResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            DBHelper.getShared().setUserDeviceId(response.body().getData().getUserDeviceId(),baseurl);
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SCANNED_FINGERPRINT_MFA_FAILURE,
                                    response.code(),"Error :FingerprintVerificationService :scannedFingerprint()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_FINGERPRINT_MFA_FAILURE,response,
                                "Error :FingerprintVerificationService :scannedFingerprint()"));
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_FINGERPRINT_MFA_FAILURE,
                            t.getMessage(), "Error :FingerprintVerificationService :scannedFingerprint()"));
                }
            });


        }
        catch (Exception e)
        {
             callback.failure(WebAuthError.getShared(context).methodException("Exception :FingerprintVerificationService :scannedFingerprint()",
                     WebAuthErrorCode.SCANNED_FINGERPRINT_MFA_FAILURE,e.getMessage()));
        }
    }


    //setupFingerprintMFA
    public void setupFingerprint(String baseurl, String accessToken, SetupFingerprintMFARequestEntity setupFingerprintMFARequestEntity,
                                 DeviceInfoEntity deviceInfoEntityFromParam,final Result<SetupFingerprintMFAResponseEntity> callback)
    {
        String setupFingerprintMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                setupFingerprintMFAUrl=baseurl+URLHelper.getShared().getSetupFingerprintMFA();
            }
            else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty",
                        "Error :FingerprintVerificationService :setupFingerprint()"));
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
            setupFingerprintMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupFingerprintMFA(setupFingerprintMFAUrl,headers, setupFingerprintMFARequestEntity).enqueue(
                    new Callback<SetupFingerprintMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupFingerprintMFAResponseEntity> call, Response<SetupFingerprintMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());

                            //Todo Listen For The Push notification to recieve

                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SETUP_FINGERPRINT_MFA_FAILURE,
                                    response.code(),"Error :FingerprintVerificationService :setupFingerprint()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_FINGERPRINT_MFA_FAILURE,response,
                                "Error :FingerprintVerificationService :setupFingerprint()"));
                    }
                }

                @Override
                public void onFailure(Call<SetupFingerprintMFAResponseEntity> call, Throwable t) {
                  callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SETUP_FINGERPRINT_MFA_FAILURE,
                          t.getMessage(), "Error :FingerprintVerificationService :setupFingerprint()"));
                }
            });


        }
        catch (Exception e)
        {
           callback.failure(WebAuthError.getShared(context).methodException("Exception :FingerprintVerificationService :setupFingerprint()",
                   WebAuthErrorCode.SETUP_FINGERPRINT_MFA_FAILURE,e.getMessage()));
        }
    }


    //enrollFingerprintMFA
    public void enrollFingerprint(String baseurl, String accessToken, EnrollFingerprintMFARequestEntity enrollFingerprintMFARequestEntity,
                                  DeviceInfoEntity deviceInfoEntityFromParam, final Result<EnrollFingerprintMFAResponseEntity> callback)
    {
        String enrollFingerprintMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                enrollFingerprintMFAUrl=baseurl+URLHelper.getShared().getEnrollFingerprintMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty",
                        "Error :FingerprintVerificationService :enrollFingerprint()"));
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
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }


            enrollFingerprintMFARequestEntity.setDeviceInfo(deviceInfoEntity);
            enrollFingerprintMFARequestEntity.setVerifierPassword(deviceInfoEntity.getDeviceId());

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.enrollFingerprintMFA(enrollFingerprintMFAUrl,headers, enrollFingerprintMFARequestEntity).enqueue(
                    new Callback<EnrollFingerprintMFAResponseEntity>() {
                @Override
                public void onResponse(Call<EnrollFingerprintMFAResponseEntity> call, Response<EnrollFingerprintMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,
                                    response.code(),"Error :FingerprintVerificationService :enrollFingerprint()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,response,
                                "Error :FingerprintVerificationService :enrollFingerprint()"));
                    }
                }

                @Override
                public void onFailure(Call<EnrollFingerprintMFAResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,
                            t.getMessage(), "Error :FingerprintVerificationService :enrollFingerprint()"));
                }
            });


        }
        catch (Exception e)
        {
           callback.failure(WebAuthError.getShared(context).methodException("Exception :FingerprintVerificationService :enrollFingerprint()",
                   WebAuthErrorCode.ENROLL_FINGERPRINT_MFA_FAILURE,e.getMessage()));
        }
    }

    //initiateFingerprintMFA
    public void initiateFingerprint(String baseurl, InitiateFingerprintMFARequestEntity initiateFingerprintMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                                       final Result<InitiateFingerprintMFAResponseEntity> callback)
    {
        String initiateFingerprintMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                initiateFingerprintMFAUrl=baseurl+URLHelper.getShared().getInitiateFingerprintMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty",
                        "Error :FingerprintVerificationService :initiateFingerprint()"));
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
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }


            initiateFingerprintMFARequestEntity.setDeviceInfo(deviceInfoEntity);


            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateFingerprintMFA(initiateFingerprintMFAUrl,headers, initiateFingerprintMFARequestEntity).enqueue(
                    new Callback<InitiateFingerprintMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiateFingerprintMFAResponseEntity> call, Response<InitiateFingerprintMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.INITIATE_FINGERPRINT_MFA_FAILURE,
                                    response.code(),
                                    "Error :FingerprintVerificationService :initiateFingerprint()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_FINGERPRINT_MFA_FAILURE,response,
                                "Error :FingerprintVerificationService :initiateFingerprint()"));
                    }
                }

                @Override
                public void onFailure(Call<InitiateFingerprintMFAResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_FINGERPRINT_MFA_FAILURE,
                            t.getMessage(), "Error :FingerprintVerificationService :initiateFingerprint()"));
                }
            });


        }
        catch (Exception e)
        {
             callback.failure(WebAuthError.getShared(context).methodException("Exception :FingerprintVerificationService :initiateFingerprint()",
                     WebAuthErrorCode.INITIATE_FINGERPRINT_MFA_FAILURE,e.getMessage()));
        }
    }


    //AuthenticateFingerprintMFA
    public void authenticateFingerprint(String baseurl, AuthenticateFingerprintRequestEntity authenticateFingerprintRequestEntity,
                                        DeviceInfoEntity deviceInfoEntityFromParam, final Result<AuthenticateFingerprintResponseEntity> callback)
    {
        String authenticateFingerprintMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                authenticateFingerprintMFAUrl=baseurl+URLHelper.getShared().getAuthenticateFingerprintMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("baseUrl must not be empty",
                        "Error :FingerprintVerificationService :authenticateFingerprint()"));
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
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }


            authenticateFingerprintRequestEntity.setDeviceInfo(deviceInfoEntity);
            authenticateFingerprintRequestEntity.setVerifierPassword(deviceInfoEntity.getDeviceId());

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticateFingerprintMFA(authenticateFingerprintMFAUrl,headers, authenticateFingerprintRequestEntity).enqueue(
                    new Callback<AuthenticateFingerprintResponseEntity>() {
                @Override
                public void onResponse(Call<AuthenticateFingerprintResponseEntity> call, Response<AuthenticateFingerprintResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.AUTHENTICATE_FINGERPRINT_MFA_FAILURE,
                                    response.code(),
                                    "Error :FingerprintVerificationService :authenticateFingerprint()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_FINGERPRINT_MFA_FAILURE,response
                        ,"Error :FingerprintVerificationService :authenticateFingerprint()"));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateFingerprintResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.AUTHENTICATE_FINGERPRINT_MFA_FAILURE,
                            t.getMessage(), "Error :FingerprintVerificationService :authenticateFingerprint()"));
                }
            });


        }
        catch (Exception e)
        {
           callback.failure(WebAuthError.getShared(context).methodException("Exception :FingerprintVerificationService :authenticateFingerprint()",
                   WebAuthErrorCode.AUTHENTICATE_FINGERPRINT_MFA_FAILURE,e.getMessage()));
        }
    }
}
