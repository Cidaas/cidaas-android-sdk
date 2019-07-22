package com.example.cidaasv2.Service.Repository.Verification.TOTP;

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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP.AuthenticateTOTPRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.TOTP.AuthenticateTOTPResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.TOTP.EnrollTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.TOTP.InitiateTOTPMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.TOTP.SetupTOTPMFAResponseEntity;
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

public class TOTPVerificationService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static TOTPVerificationService shared;

    public TOTPVerificationService(Context contextFromCidaas) {
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



    public static TOTPVerificationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new TOTPVerificationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    public void scannedTOTP(final String baseurl, ScannedRequestEntity scannedRequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                            final Result<ScannedResponseEntity> callback)
    {
        String scannedTOTPUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                scannedTOTPUrl=baseurl+ URLHelper.getShared().getScannedTOTPURL();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :TOTPVerificationService :scannedTOTP()"));
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

            cidaasSDKService.scanned(scannedTOTPUrl,headers, scannedRequestEntity).enqueue(new Callback<ScannedResponseEntity>() {
                @Override
                public void onResponse(Call<ScannedResponseEntity> call, Response<ScannedResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            DBHelper.getShared().setUserDeviceId(response.body().getData().getUserDeviceId(),baseurl);
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SCANNED_TOTP_MFA_FAILURE,
                                    response.code(),"Error :TOTPVerificationService :scannedTOTP()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_TOTP_MFA_FAILURE,response,
                                "Error :TOTPVerificationService :scannedTOTP()"));
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_TOTP_MFA_FAILURE,t.getMessage(),
                            "Error :TOTPVerificationService :scannedTOTP()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :TOTPVerificationService :scannedTOTP()",WebAuthErrorCode.SCANNED_TOTP_MFA_FAILURE,e.getMessage()));
        }
    }


    //setupTOTPMFA
    public void setupTOTP(String baseurl, String accessToken, SetupTOTPMFARequestEntity setupTOTPMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<SetupTOTPMFAResponseEntity> callback)
    {
        String setupTOTPMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                setupTOTPMFAUrl=baseurl+URLHelper.getShared().getSetupTOTPMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :TOTPVerificationService :setupTOTP()"));
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
            setupTOTPMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupTOTPMFA(setupTOTPMFAUrl,headers, setupTOTPMFARequestEntity).enqueue(new Callback<SetupTOTPMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupTOTPMFAResponseEntity> call, Response<SetupTOTPMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());

                            //Todo Listen For The Push notification to recieve

                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SETUP_TOTP_MFA_FAILURE,
                                    response.code(),"Error :TOTPVerificationService :setupTOTP()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_TOTP_MFA_FAILURE,response,
                                "Error :TOTPVerificationService :setupTOTP()"));
                    }
                }

                @Override
                public void onFailure(Call<SetupTOTPMFAResponseEntity> call, Throwable t) {
                   callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SETUP_TOTP_MFA_FAILURE,t.getMessage()
                           , "Error :TOTPVerificationService :setupTOTP()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :TOTPVerificationService :setupTOTP()",WebAuthErrorCode.SETUP_TOTP_MFA_FAILURE,e.getMessage()));
        }
    }


    //enrollTOTPMFA
    public void enrollTOTP(String baseurl, String accessToken, EnrollTOTPMFARequestEntity enrollTOTPMFARequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,final Result<EnrollTOTPMFAResponseEntity> callback)
    {
        String enrollTOTPMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                enrollTOTPMFAUrl=baseurl+URLHelper.getShared().getEnrollTOTPMFA();
            }
            else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty",
                        "Error :TOTPVerificationService :enrollTOTP()"));
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


            enrollTOTPMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.enrollTOTPMFA(enrollTOTPMFAUrl,headers, enrollTOTPMFARequestEntity).enqueue(new Callback<EnrollTOTPMFAResponseEntity>() {
                @Override
                public void onResponse(Call<EnrollTOTPMFAResponseEntity> call, Response<EnrollTOTPMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.ENROLL_TOTP_MFA_FAILURE,
                                    response.code(),"Error :TOTPVerificationService :enrollTOTP()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_TOTP_MFA_FAILURE,response
                                ,"Error :TOTPVerificationService :enrollTOTP()"));
                    }
                }

                @Override
                public void onFailure(Call<EnrollTOTPMFAResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_TOTP_MFA_FAILURE,t.getMessage(),
                            "Error :TOTPVerificationService :enrollTOTP()"));
                }
            });


        }
        catch (Exception e)
        {
           callback.failure(WebAuthError.getShared(context).methodException("Exception :TOTPVerificationService :enrollTOTP()"
                   ,WebAuthErrorCode.ENROLL_TOTP_MFA_FAILURE,e.getMessage()));
        }
    }


    //initiateTOTPMFA
    public void initiateTOTP(String baseurl,String codeChallenge, InitiateTOTPMFARequestEntity initiateTOTPMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<InitiateTOTPMFAResponseEntity> callback)
    {
        String initiateTOTPMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                initiateTOTPMFAUrl=baseurl+URLHelper.getShared().getInitiateTOTPMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty",
                        "Error :TOTPVerificationService :initiateTOTP()"));
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


            initiateTOTPMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateTOTPMFA(initiateTOTPMFAUrl,headers, initiateTOTPMFARequestEntity).enqueue(new Callback<InitiateTOTPMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiateTOTPMFAResponseEntity> call, Response<InitiateTOTPMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.INITIATE_TOTP_MFA_FAILURE,
                                    response.code(),"Error :TOTPVerificationService :initiateTOTP()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_TOTP_MFA_FAILURE,response,
                                "Error :TOTPVerificationService :initiateTOTP()"));
                    }
                }

                @Override
                public void onFailure(Call<InitiateTOTPMFAResponseEntity> call, Throwable t) {
                   callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_TOTP_MFA_FAILURE,t.getMessage()
                           ,"Error :TOTPVerificationService :initiateTOTP()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :TOTPVerificationService :initiateTOTP()",WebAuthErrorCode.INITIATE_TOTP_MFA_FAILURE,e.getMessage()));
        }
    }


    //authenticateTOTPMFA
    public void authenticateTOTP(String baseurl, AuthenticateTOTPRequestEntity authenticateTOTPRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<AuthenticateTOTPResponseEntity> callback)
    {
        String authenticateTOTPMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                authenticateTOTPMFAUrl=baseurl+URLHelper.getShared().getAuthenticateTOTPMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty",
                        "Error :TOTPVerificationService :authenticateTOTP()"));
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


            authenticateTOTPRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticateTOTPMFA(authenticateTOTPMFAUrl,headers, authenticateTOTPRequestEntity).enqueue(new Callback<AuthenticateTOTPResponseEntity>() {
                @Override
                public void onResponse(Call<AuthenticateTOTPResponseEntity> call, Response<AuthenticateTOTPResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.AUTHENTICATE_TOTP_MFA_FAILURE,
                                    response.code(),"Error :TOTPVerificationService :authenticateTOTP()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_TOTP_MFA_FAILURE,response
                                ,"Error :TOTPVerificationService :authenticateTOTP()"));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateTOTPResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.AUTHENTICATE_TOTP_MFA_FAILURE,t.getMessage(),
                            "Error :TOTPVerificationService :authenticateTOTP()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :TOTPVerificationService :authenticateTOTP()",
                    WebAuthErrorCode.AUTHENTICATE_TOTP_MFA_FAILURE,e.getMessage()));
        }
    }

}
