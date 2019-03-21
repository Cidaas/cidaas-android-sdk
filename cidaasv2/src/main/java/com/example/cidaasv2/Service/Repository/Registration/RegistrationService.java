package com.example.cidaasv2.Service.Repository.Registration;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupErrorEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupRequestEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RegistrationService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static  RegistrationService shared;

    public  RegistrationService(Context contextFromCidaas) {
        sub="";
        statusId="";
        verificationType="";
        context=contextFromCidaas;
        authenticationType="";


        if(service==null) {
            service=new CidaassdkService();
        }

        //Todo setValue for authenticationType

    }


    public static  RegistrationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  RegistrationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //Get Regsiteration Details
    public void getRegistrationSetup(String baseurl, final RegistrationSetupRequestEntity registrationSetupRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<RegistrationSetupResponseEntity> callback)
    {
        //Local Variables

        String RegistrationUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                RegistrationUrl=baseurl+ URLHelper.getShared().getRegistrationSetup(registrationSetupRequestEntity.getAcceptedLanguage(),registrationSetupRequestEntity.getRequestId());
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }


            //Call Service-getRequestId
            Map<String, String> headers = new Hashtable<>();
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.getRegistrationSetup(RegistrationUrl,headers).enqueue(new Callback<RegistrationSetupResponseEntity>() {
                @Override
                public void onResponse(Call<RegistrationSetupResponseEntity> call, Response<RegistrationSetupResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                     /*   try {

                            // Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();
                            RegistrationSetupErrorEntity registrationSetupErrorEntity;
                            registrationSetupErrorEntity=objectMapper.readValue(errorResponse,RegistrationSetupErrorEntity.class);

                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,
                                    registrationSetupErrorEntity.getError().getError(), registrationSetupErrorEntity.getStatus(),registrationSetupErrorEntity.getError(),null));
                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,e.getMessage(), 400,null,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());*/
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<RegistrationSetupResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            LogFile.getShared(context).addRecordToLog(e.getMessage()+WebAuthErrorCode.REGISTRATION_SETUP_FAILURE);
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE));
        }
    }


    //Register New User
    public void registerNewUser(String baseurl, final RegisterNewUserRequestEntity registerNewUserRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<RegisterNewUserResponseEntity> callback)
    {
        //Local Variables

        String RegisterNewUserUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                RegisterNewUserUrl=baseurl+URLHelper.getShared().getRegisterNewUserurl();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,context.getString(R.string.PROPERTY_MISSING), 400,null,null));
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
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());
            headers.put("requestId", registerNewUserRequestEntity.getRequestId());
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.registerNewUser(RegisterNewUserUrl,headers,registerNewUserRequestEntity.getRegistrationEntity()).enqueue(new Callback<RegisterNewUserResponseEntity>() {
                @Override
                public void onResponse(Call<RegisterNewUserResponseEntity> call, Response<RegisterNewUserResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,response));

                    }
                }

                @Override
                public void onFailure(Call<RegisterNewUserResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Register new User service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,t.getMessage(), 400,null,null));
                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            LogFile.getShared(context).addRecordToLog(e.getMessage()+WebAuthErrorCode.REGISTRATION_SETUP_FAILURE);
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE));
        }
    }

    //Register New User initiate  Account Verification
    public void initiateAccountVerification(String baseurl, final RegisterUserAccountInitiateRequestEntity registerUserAccountInitiateRequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,final Result<RegisterUserAccountInitiateResponseEntity> callback)
    {
        //Local Variables

        String initiateAccountVerificationUrl = "";
        try{

            if(baseurl!=null || !baseurl.equals("")){
                //Construct URL For RequestId
                initiateAccountVerificationUrl=baseurl+URLHelper.getShared().getRegisterUserAccountInitiate();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,context.getString(R.string.PROPERTY_MISSING), 400,null,null));
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
            headers.put("deviceId", deviceInfoEntity.getDeviceId());
            headers.put("deviceMake", deviceInfoEntity.getDeviceMake());
            headers.put("deviceModel", deviceInfoEntity.getDeviceModel());
            headers.put("deviceVersion", deviceInfoEntity.getDeviceVersion());
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateAccountVerification(initiateAccountVerificationUrl,headers,registerUserAccountInitiateRequestEntity)
                    .enqueue(new Callback<RegisterUserAccountInitiateResponseEntity>() {
                        @Override
                        public void onResponse(Call<RegisterUserAccountInitiateResponseEntity> call, Response<RegisterUserAccountInitiateResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if(response.code()==200) {
                                    callback.success(response.body());
                                }
                                else {
                                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                                            "Service failure but successful response" , response.code(),null,null));
                                }
                            }
                            else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,response));
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterUserAccountInitiateResponseEntity> call, Throwable t) {
                            Timber.e("Failure in Register new User service call"+t.getMessage());
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,t.getMessage(), 400,null,null));
                        }
                    });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            LogFile.getShared(context).addRecordToLog(e.getMessage()+WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE);
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE));
        }
    }

    //Register New User verify  Account Verification
    public void verifyAccountVerification(String baseurl, final RegisterUserAccountVerifyRequestEntity registerUserAccountVerifyRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                                          final Result<RegisterUserAccountVerifyResponseEntity> callback)
    {
        //Local Variables

        String verifyAccountVerificationUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                verifyAccountVerificationUrl=baseurl+URLHelper.getShared().getRegisterUserAccountVerify();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,context.getString(R.string.PROPERTY_MISSING), 400,null,null));
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
            headers.put("deviceId", deviceInfoEntity.getDeviceId());
            headers.put("deviceMake", deviceInfoEntity.getDeviceMake());
            headers.put("deviceModel", deviceInfoEntity.getDeviceModel());
            headers.put("deviceVersion", deviceInfoEntity.getDeviceVersion());
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.verifyAccountVerification(verifyAccountVerificationUrl,headers,registerUserAccountVerifyRequestEntity)
                    .enqueue(new Callback<RegisterUserAccountVerifyResponseEntity>() {
                        @Override
                        public void onResponse(Call<RegisterUserAccountVerifyResponseEntity> call, Response<RegisterUserAccountVerifyResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if(response.code()==200) {
                                    callback.success(response.body());
                                }
                                else {
                                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                            "Service failure but successful response" , response.code(),null,null));
                                }
                            }
                            else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,response));
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterUserAccountVerifyResponseEntity> call, Throwable t) {
                            Timber.e("Failure in Register new User service call"+t.getMessage());
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,t.getMessage(), 400,null,null));
                        }
                    });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE));
            LogFile.getShared(context).addRecordToLog(e.getMessage()+WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE);
        }
    }

}
