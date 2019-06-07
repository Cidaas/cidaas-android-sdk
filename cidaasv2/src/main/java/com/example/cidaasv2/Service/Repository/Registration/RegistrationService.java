package com.example.cidaasv2.Service.Repository.Registration;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUser.RegisterNewUserResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountInitiateResponseEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyRequestEntity;
import com.example.cidaasv2.Service.Register.RegisterUserAccountVerification.RegisterUserAccountVerifyResponseEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupRequestEntity;
import com.example.cidaasv2.Service.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    //-----------------------------------------------------Get Regsiteration Details-------------------------------------------------------------------
    public void getRegistrationSetup(String baseurl, final RegistrationSetupRequestEntity registrationSetupRequestEntity,
                                     DeviceInfoEntity deviceInfoEntityFromParam, final Result<RegistrationSetupResponseEntity> callback)
    {
        //Local Variables

        String methodName = "RegistrationService :getRegistrationSetup()";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                String RegistrationUrl=baseurl+ URLHelper.getShared().getRegistrationSetup(registrationSetupRequestEntity.getAcceptedLanguage(),
                        registrationSetupRequestEntity.getRequestId());



            //Call Service-getRequestId
            Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,null);

            serviceForGetRegistrationSetup(RegistrationUrl, headers, callback);

            }
            else
            {
              callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),"Error :"+methodName));
                return;
            }


        }
        catch (Exception e)
        {
          callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,e.getMessage()));
        }
    }

    private void serviceForGetRegistrationSetup(String registrationUrl, Map<String, String> headers, final Result<RegistrationSetupResponseEntity> callback)
    {
        final String methodName="RegistrationService :getRegistrationSetup()";
        try
        {
            final ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getRegistrationSetup(registrationUrl, headers).enqueue(new Callback<RegistrationSetupResponseEntity>() {
            @Override
            public void onResponse(Call<RegistrationSetupResponseEntity> call, Response<RegistrationSetupResponseEntity> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        callback.success(response.body());
                    } else {
                        callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,
                                response.code(), "Error :"+methodName));
                    }
                } else {
                    callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE, response
                            , "Error :"+methodName));
                }
            }

             @Override
             public void onFailure(Call<RegistrationSetupResponseEntity> call, Throwable t) {
                callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE, t.getMessage(),
                      "Error :"+methodName));
             }
            });
        }
        catch (Exception e)
        {
          callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,e.getMessage()));
        }
    }


    //----------------------------------------------------------Register New User--------------------------------------------------------------------------
    public void registerNewUser(String baseurl,final RegisterNewUserRequestEntity registerNewUserRequestEntity,final Result<RegisterNewUserResponseEntity> callback)
    {
        //Local Variables

        String methodName = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){

                //Construct URL For RequestId
               String RegisterNewUserUrl=baseurl+URLHelper.getShared().getRegisterNewUserurl();

               //Header Generation
               Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,URLHelper.contentTypeJson,
                       registerNewUserRequestEntity.getRequestId());

               //service
              serviceForRegisterNewUser(RegisterNewUserUrl, registerNewUserRequestEntity, headers, callback);

            }
            else
            {
              callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :"+methodName));
                return;
            }


        }
        catch (Exception e)
        {
          callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,e.getMessage()));
        }
    }

    private void serviceForRegisterNewUser(String registerNewUserUrl, RegisterNewUserRequestEntity registerNewUserRequestEntity, Map<String, String> headers,
                                           final Result<RegisterNewUserResponseEntity> callback) {
        final String methodName = "RegistrationService :registerNewUser()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.registerNewUser(registerNewUserUrl, headers, registerNewUserRequestEntity.getRegistrationEntity()).enqueue(
                    new Callback<RegisterNewUserResponseEntity>() {
                @Override
                public void onResponse(Call<RegisterNewUserResponseEntity> call, Response<RegisterNewUserResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE
                                    , response.code(), "Error :"+methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,
                                response, "Error :"+methodName));

                    }
                }

                @Override
                public void onFailure(Call<RegisterNewUserResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.REGISTRATION_SETUP_FAILURE, t.getMessage(),
                            "Error :"+methodName));
                }
            });
        }
        catch (Exception e)
        {
         callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.REGISTRATION_SETUP_FAILURE,e.getMessage()));
        }
    }


    //----------------------------------------------------Register New User initiate  Account Verification--------------------------------------------------
    public void initiateAccountVerification(String baseurl, final RegisterUserAccountInitiateRequestEntity registerUserAccountInitiateRequestEntity,
                                            final Result<RegisterUserAccountInitiateResponseEntity> callback)
    {
        //Local Variables

        String methodName = "RegistrationService :initiateAccountVerification()";
        try{

            if(baseurl!=null || !baseurl.equals("")){
                //Construct URL For RequestId
               String initiateAccountVerificationUrl=baseurl+URLHelper.getShared().getRegisterUserAccountInitiate();

               Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,URLHelper.contentTypeJson);

               serviceForInitiateAccountVerification(initiateAccountVerificationUrl, registerUserAccountInitiateRequestEntity, headers, callback);
            }
            else
            {
              callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :"+methodName));
                return;
            }

        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForInitiateAccountVerification(String initiateAccountVerificationUrl,
                                                       RegisterUserAccountInitiateRequestEntity registerUserAccountInitiateRequestEntity,
                                                       Map<String, String> headers, final Result<RegisterUserAccountInitiateResponseEntity> callback) {
        final String methodName = "RegistrationService :verifyAccountVerification()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateAccountVerification(initiateAccountVerificationUrl, headers, registerUserAccountInitiateRequestEntity)
                    .enqueue(new Callback<RegisterUserAccountInitiateResponseEntity>() {
                        @Override
                        public void onResponse(Call<RegisterUserAccountInitiateResponseEntity> call, Response<RegisterUserAccountInitiateResponseEntity> response)
                        {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                                             response.code(),  "Error :"+methodName));
                                }
                            } else {
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                                        response, "Error :"+methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterUserAccountInitiateResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                                    t.getMessage(), "Error :"+methodName));
                        }
                    });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.INITIATE_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    //-----------------------------------------------------Register New User verify  Account Verification----------------------------------------------------
    public void verifyAccountVerification(String baseurl, final RegisterUserAccountVerifyRequestEntity registerUserAccountVerifyRequestEntity,
                                          final Result<RegisterUserAccountVerifyResponseEntity> callback)
    {
        //Local Variables

        String methodName = "RegistrationService :verifyAccountVerification()";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                String   verifyAccountVerificationUrl=baseurl+URLHelper.getShared().getRegisterUserAccountVerify();

                //Header
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

                //Service
                serviceForVerifyAccountVerification(verifyAccountVerificationUrl, registerUserAccountVerifyRequestEntity, headers, callback);
            }
            else
            {
             callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :"+methodName));
                return;
            }

        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForVerifyAccountVerification(String verifyAccountVerificationUrl, RegisterUserAccountVerifyRequestEntity
            registerUserAccountVerifyRequestEntity, Map<String, String> headers, final Result<RegisterUserAccountVerifyResponseEntity> callback)
    {
        final String methodName="RegistrationService :verifyAccountVerification()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.verifyAccountVerification(verifyAccountVerificationUrl, headers, registerUserAccountVerifyRequestEntity)
                    .enqueue(new Callback<RegisterUserAccountVerifyResponseEntity>() {
                        @Override
                        public void onResponse(Call<RegisterUserAccountVerifyResponseEntity> call, Response<RegisterUserAccountVerifyResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                            response.code(), "Error :"+methodName));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                        response, "Error :"+methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterUserAccountVerifyResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                                    t.getMessage(),"Error :"+methodName));
                        }
                    });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.VERIFY_ACCOUNT_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

}
