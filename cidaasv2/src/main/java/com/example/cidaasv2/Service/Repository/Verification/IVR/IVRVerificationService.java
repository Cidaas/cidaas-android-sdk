package com.example.cidaasv2.Service.Repository.Verification.IVR;

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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.IVR.AuthenticateIVRResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.IVR.EnrollIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.IVR.InitiateIVRMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.IVR.SetupIVRMFAResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class IVRVerificationService {

        CidaassdkService service;
        private ObjectMapper objectMapper=new ObjectMapper();
        //Local variables
        private String statusId;
        private String authenticationType;
        private String sub;
        private String verificationType;
        private Context context;

        public static IVRVerificationService shared;

        public IVRVerificationService(Context contextFromCidaas) {
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



        public static IVRVerificationService getShared(Context contextFromCidaas )
        {
            try {

                if (shared == null) {
                    shared = new IVRVerificationService(contextFromCidaas);
                }
            }
            catch (Exception e)
            {
                Timber.i(e.getMessage());
            }
            return shared;
        }



        //setupIVRMFA
    public void setupIVRMFA(String baseurl, String accessToken,String phoneNumber,DeviceInfoEntity deviceInfoEntityFromParam, final Result<SetupIVRMFAResponseEntity> callback){
        String setupIVRMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                setupIVRMFAUrl=baseurl+ URLHelper.getShared().getSetupIVRMFA();
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
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("long",LocationDetails.getShared(context).getLongitude());


            SetupIVRMFARequestEntity setupIVRMFARequestEntity=new SetupIVRMFARequestEntity();
            setupIVRMFARequestEntity.setDeviceInfo(deviceInfoEntity);
            setupIVRMFARequestEntity.setPhone(phoneNumber);


            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupIVRMFA(setupIVRMFAUrl,headers, setupIVRMFARequestEntity).enqueue(new Callback<SetupIVRMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupIVRMFAResponseEntity> call, Response<SetupIVRMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_IVR_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_IVR_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<SetupIVRMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Setup IVR service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Setup IVR Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_IVR_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Setup IVR Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SETUP_IVR_MFA_FAILURE));
            Timber.e("Setup IVR Service exception"+e.getMessage());
        }
    }

    //enrollIVRMFA
    public void enrollIVRMFA(String baseurl, String accessToken, EnrollIVRMFARequestEntity enrollIVRMFARequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,final Result<EnrollIVRMFAResponseEntity> callback){
        String enrollIVRMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                enrollIVRMFAUrl=baseurl+URLHelper.getShared().getEnrollIVRMFA();
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("long",LocationDetails.getShared(context).getLongitude());


            enrollIVRMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.enrollIVRMFA(enrollIVRMFAUrl,headers, enrollIVRMFARequestEntity).enqueue(new Callback<EnrollIVRMFAResponseEntity>() {
                @Override
                public void onResponse(Call<EnrollIVRMFAResponseEntity> call, Response<EnrollIVRMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_IVR_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_IVR_MFA_FAILURE,response)); }
                }

                @Override
                public void onFailure(Call<EnrollIVRMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Enroll IVR service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Enroll IVR Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_IVR_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Enroll IVR Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.ENROLL_IVR_MFA_FAILURE));
            Timber.e("Enroll IVR Service exception"+e.getMessage());
        }
    }

    //initiateIVRMFA
    public void initiateIVRMFA(String baseurl, InitiateIVRMFARequestEntity initiateIVRMFARequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,final Result<InitiateIVRMFAResponseEntity> callback){
            String initiateIVRMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                initiateIVRMFAUrl=baseurl+URLHelper.getShared().getInitiateIVRMFA();
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("long",LocationDetails.getShared(context).getLongitude());

            initiateIVRMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateIVRMFA(initiateIVRMFAUrl,headers, initiateIVRMFARequestEntity).enqueue(new Callback<InitiateIVRMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiateIVRMFAResponseEntity> call, Response<InitiateIVRMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_IVR_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_IVR_MFA_FAILURE,response)); }
                }

                @Override
                public void onFailure(Call<InitiateIVRMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Initiate IVR MFA service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Initiate IVR MFA Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_IVR_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Initiate IVR MFA Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_IVR_MFA_FAILURE));
            Timber.e("Initiate IVR MFA Service exception"+e.getMessage());
        }
    }


    //authenticateIVRMFA
    public void authenticateIVRMFA(String baseurl, AuthenticateIVRRequestEntity authenticateIVRRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<AuthenticateIVRResponseEntity> callback){
        String authenticateIVRMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                authenticateIVRMFAUrl=baseurl+URLHelper.getShared().getAuthenticateIVRMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING)+":BaseURL must not be null", 400,null,null));
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("long",LocationDetails.getShared(context).getLongitude());

            authenticateIVRRequestEntity.setDeviceInfo(deviceInfoEntity);


            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticateIVRMFA(authenticateIVRMFAUrl,headers, authenticateIVRRequestEntity).enqueue(new Callback<AuthenticateIVRResponseEntity>() {
                @Override
                public void onResponse(Call<AuthenticateIVRResponseEntity> call, Response<AuthenticateIVRResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_IVR_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_IVR_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateIVRResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Authenticate IVR service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Authenticate IVR Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_IVR_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Authenticate IVR MFA Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.AUTHENTICATE_IVR_MFA_FAILURE));
            Timber.e("Authenticate IVR MFA Service exception"+e.getMessage());
        }
    }

}
