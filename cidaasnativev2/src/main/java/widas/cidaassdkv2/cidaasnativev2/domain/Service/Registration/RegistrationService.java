package widas.cidaassdkv2.cidaasnativev2.domain.Service.Registration;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Register.RegisterUser.RegisterNewUserRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Register.RegisterUser.RegisterNewUserResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.AccountVerification.InitiateAccountVerificationRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.AccountVerification.InitiateAccountVerificationResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.AccountVerification.VerifyAccountRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.AccountVerification.VerifyAccountResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Register.RegistrationSetup.RegistrationSetupRequestEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Entity.Register.RegistrationSetup.RegistrationSetupResponseEntity;
import widas.cidaassdkv2.cidaasnativev2.data.Service.CidaasNativeService;
import widas.cidaassdkv2.cidaasnativev2.data.Service.Helper.NativeURLHelper;
import widas.cidaassdkv2.cidaasnativev2.data.Service.ICidaasNativeService;

public class RegistrationService {
    CidaasNativeService service;
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
            service=new CidaasNativeService(context);
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
                String RegistrationUrl=baseurl+ NativeURLHelper.getShared().getRegistrationSetup(registrationSetupRequestEntity.getAcceptedLanguage(),
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
            final ICidaasNativeService cidaasNativeService = service.getInstance();
            cidaasNativeService.getRegistrationSetup(registrationUrl, headers).enqueue(new Callback<RegistrationSetupResponseEntity>() {
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
    public void registerNewUser(String baseurl, final RegisterNewUserRequestEntity registerNewUserRequestEntity, final Result<RegisterNewUserResponseEntity> callback)
    {
        //Local Variables

        String methodName = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){

                //Construct URL For RequestId
               String RegisterNewUserUrl=baseurl+NativeURLHelper.getShared().getRegisterNewUserurl();

               //Header Generation
               Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,NativeURLHelper.contentTypeJson,
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
            ICidaasNativeService cidaasNativeService = service.getInstance();
            cidaasNativeService.registerNewUser(registerNewUserUrl, headers, registerNewUserRequestEntity.getRegistrationEntity()).enqueue(
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
    public void initiateAccountVerification(String baseurl, final InitiateAccountVerificationRequestEntity initiateAccountVerificationRequestEntity,
                                            final Result<InitiateAccountVerificationResponseEntity> callback)
    {
        //Local Variables

        String methodName = "RegistrationService :initiateAccountVerification()";
        try{

            if(baseurl!=null || !baseurl.equals("")){
                //Construct URL For RequestId
               String initiateAccountVerificationUrl=baseurl+NativeURLHelper.getShared().getRegisterUserAccountInitiate();

               Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,NativeURLHelper.contentTypeJson);

               serviceForInitiateAccountVerification(initiateAccountVerificationUrl, initiateAccountVerificationRequestEntity, headers, callback);
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
                                                       InitiateAccountVerificationRequestEntity initiateAccountVerificationRequestEntity,
                                                       Map<String, String> headers, final Result<InitiateAccountVerificationResponseEntity> callback) {
        final String methodName = "RegistrationService :verifyAccountVerification()";
        try {
            //Call Service-getRequestId
            ICidaasNativeService cidaasNativeService = service.getInstance();

            cidaasNativeService.initiateAccountVerification(initiateAccountVerificationUrl, headers, initiateAccountVerificationRequestEntity)
                    .enqueue(new Callback<InitiateAccountVerificationResponseEntity>() {
                        @Override
                        public void onResponse(Call<InitiateAccountVerificationResponseEntity> call, Response<InitiateAccountVerificationResponseEntity> response)
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
                        public void onFailure(Call<InitiateAccountVerificationResponseEntity> call, Throwable t) {
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
    public void verifyAccountVerification(String baseurl, final VerifyAccountRequestEntity verifyAccountRequestEntity,
                                          final Result<VerifyAccountResponseEntity> callback)
    {
        //Local Variables

        String methodName = "RegistrationService :verifyAccountVerification()";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                String   verifyAccountVerificationUrl=baseurl+ NativeURLHelper.getShared().getRegisterUserAccountVerify();

                //Header
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,false, NativeURLHelper.contentTypeJson);

                //Service
                serviceForVerifyAccountVerification(verifyAccountVerificationUrl, verifyAccountRequestEntity, headers, callback);
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

    private void serviceForVerifyAccountVerification(String verifyAccountVerificationUrl, VerifyAccountRequestEntity
            verifyAccountRequestEntity, Map<String, String> headers, final Result<VerifyAccountResponseEntity> callback)
    {
        final String methodName="RegistrationService :verifyAccountVerification()";
        try {
            //Call Service-getRequestId
            ICidaasNativeService cidaasNativeService = service.getInstance();

            cidaasNativeService.verifyAccountVerification(verifyAccountVerificationUrl, headers, verifyAccountRequestEntity)
                    .enqueue(new Callback<VerifyAccountResponseEntity>() {
                        @Override
                        public void onResponse(Call<VerifyAccountResponseEntity> call, Response<VerifyAccountResponseEntity> response) {
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
                        public void onFailure(Call<VerifyAccountResponseEntity> call, Throwable t) {
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
