package com.example.cidaasv2.Service.Repository.Verification.SMS;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.SMS.AuthenticateSMSResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.SMS.EnrollSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.SMS.InitiateSMSMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.SMS.SetupSMSMFAResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SMSVerificationService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables

    private Context context;

    public static SMSVerificationService shared;

    public SMSVerificationService(Context contextFromCidaas) {

        context=contextFromCidaas;
        if(service==null) {
            service=new CidaassdkService();
        }


        //Todo setValue for authenticationType

    }


    public static SMSVerificationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new SMSVerificationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //setupSMSMFA
    public void setupSMSMFA(String baseurl, String accessToken, String mobile,DeviceInfoEntity deviceInfoEntityFromParam,final Result<SetupSMSMFAResponseEntity> callback)
    {
        String setupSMSMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                setupSMSMFAUrl=baseurl+ URLHelper.getShared().getSetupSMSMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :SMSVerificationService :setupSMSMFA()"));
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
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            SetupSMSMFARequestEntity setupSMSMFARequestEntity=new SetupSMSMFARequestEntity();
            setupSMSMFARequestEntity.setDeviceInfo(deviceInfoEntity);
            setupSMSMFARequestEntity.setMobile(mobile);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupSMSMFA(setupSMSMFAUrl,headers, setupSMSMFARequestEntity).enqueue(new Callback<SetupSMSMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupSMSMFAResponseEntity> call, Response<SetupSMSMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SETUP_SMS_MFA_FAILURE,
                                    response.code(),"Error :SMSVerificationService :setupSMSMFA()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_SMS_MFA_FAILURE,response,"Error :SMSVerificationService :setupSMSMFA()"));
                    }
                }

                @Override
                public void onFailure(Call<SetupSMSMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Setup SMS service call"+t.getMessage());
                    LogFile.getShared(context).addFailureLog("Setup SMS Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SETUP_SMS_MFA_FAILURE,t.getMessage(),
                            "Error :SMSVerificationService :setupSMSMFA()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :SMSVerificationService :setupSMSMFA()",WebAuthErrorCode.SETUP_SMS_MFA_FAILURE,e.getMessage()));
        }
    }

    //enrollSMSMFA
    public void enrollSMSMFA(String baseurl, String accessToken, EnrollSMSMFARequestEntity enrollSMSMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<EnrollSMSMFAResponseEntity> callback){
        String enrollSMSMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                enrollSMSMFAUrl=baseurl+URLHelper.getShared().getEnrollSMSMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :SMSVerificationService :enrollSMSMFA()"));
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

            //Todo - check Construct Headers pending,null,null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("access_token",accessToken);
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            enrollSMSMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.enrollSMSMFA(enrollSMSMFAUrl,headers, enrollSMSMFARequestEntity).enqueue(new Callback<EnrollSMSMFAResponseEntity>() {
                @Override
                public void onResponse(Call<EnrollSMSMFAResponseEntity> call, Response<EnrollSMSMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.ENROLL_SMS_MFA_FAILURE,
                                    response.code(),"Error :SMSVerificationService :enrollSMSMFA()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_SMS_MFA_FAILURE,response
                                ,"Error :SMSVerificationService :enrollSMSMFA()"));
                    }
                }

                @Override
                public void onFailure(Call<EnrollSMSMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Enroll SMS service call"+t.getMessage());
                    LogFile.getShared(context).addFailureLog("Enroll SMS Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_SMS_MFA_FAILURE,t.getMessage(),
                            "Error :SMSVerificationService :enrollSMSMFA()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :SMSVerificationService :enrollSMSMFA()",WebAuthErrorCode.ENROLL_SMS_MFA_FAILURE,e.getMessage()));
        }
    }


    //initiateSMSMFA
    public void initiateSMSMFA(String baseurl, InitiateSMSMFARequestEntity initiateSMSMFARequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,final Result<InitiateSMSMFAResponseEntity> callback){
        String initiateSMSMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                initiateSMSMFAUrl=baseurl+URLHelper.getShared().getInitiateSMSMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :SMSVerificationService :initiateSMSMFA()"));
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
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            initiateSMSMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateSMSMFA(initiateSMSMFAUrl,headers, initiateSMSMFARequestEntity).enqueue(new Callback<InitiateSMSMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiateSMSMFAResponseEntity> call, Response<InitiateSMSMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.INITIATE_SMS_MFA_FAILURE,
                                    response.code(),"Error :SMSVerificationService :initiateSMSMFA()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_SMS_MFA_FAILURE,response,
                                "Error :SMSVerificationService :initiateSMSMFA()"));
                    }
                }

                @Override
                public void onFailure(Call<InitiateSMSMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Initiate SMS Service call"+t.getMessage());
                    LogFile.getShared(context).addFailureLog("Initiate SMS MFA Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_SMS_MFA_FAILURE,t.getMessage(),
                            "Error :SMSVerificationService :initiateSMSMFA()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :SMSVerificationService :initiateSMSMFA()",
                    WebAuthErrorCode.INITIATE_SMS_MFA_FAILURE,e.getMessage()));
        }
    }


    //authenticateSMSMFA
    public void authenticateSMSMFA(String baseurl, AuthenticateSMSRequestEntity authenticateSMSRequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,final Result<AuthenticateSMSResponseEntity> callback){
        String authenticateSMSMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                authenticateSMSMFAUrl=baseurl+URLHelper.getShared().getAuthenticateSMSMFA();
            }
            else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty",
                        "Error :SMSVerificationService :authenticateSMSMFA()"));
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
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            authenticateSMSRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticateSMSMFA(authenticateSMSMFAUrl,headers, authenticateSMSRequestEntity).enqueue(new Callback<AuthenticateSMSResponseEntity>() {
                @Override
                public void onResponse(Call<AuthenticateSMSResponseEntity> call, Response<AuthenticateSMSResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.AUTHENTICATE_SMS_MFA_FAILURE,
                                    response.code(),"Error :SMSVerificationService :authenticateSMSMFA()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_SMS_MFA_FAILURE,response,
                                "Error :SMSVerificationService :authenticateSMSMFA()"));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateSMSResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.AUTHENTICATE_SMS_MFA_FAILURE,t.getMessage(),
                            "Error :SMSVerificationService :authenticateSMSMFA()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :SMSVerificationService :authenticateSMSMFA()",
                    WebAuthErrorCode.AUTHENTICATE_SMS_MFA_FAILURE,e.getMessage()));
        }
    }

}
