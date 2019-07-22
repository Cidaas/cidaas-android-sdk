package com.example.cidaasv2.Service.Repository.Verification.Pattern;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFAResponseEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class PatternVerificationService {

    CidaassdkService service;
    public ObjectMapper objectMapper = new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static PatternVerificationService shared;

    public PatternVerificationService(Context contextFromCidaas) {
        sub = "";
        statusId = "";
        verificationType = "";
        context = contextFromCidaas;
        authenticationType = "";
        //Todo setValue for authenticationType
        if (service == null) {
            service = new CidaassdkService(context);
        }
    }


    public static PatternVerificationService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PatternVerificationService(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

//Done callSetup Pattern Webservice
    // 1. Check For NotNull Values
    // 2.  Create header and Scanned Request entity
    // 3.  Call Webservice and return the result
    // 4.  Maintain logs based on flags


    //-----------------------------------------------setupPattern----------------------------------------------------------------------
    public void setupPattern(String baseurl, String accessToken,  SetupPatternMFARequestEntity setupPatternMFARequestEntity, boolean verification_api_version,
                             final Result<SetupPatternMFAResponseEntity> callback)
    {
        String methodName="PatternVerificationService :setupPattern()";
        try {
            String setupPatternMFAUrl = "";

            if (baseurl != null && !baseurl.equals("") && DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Construct URL For getSetupPatternMFA
                setupPatternMFAUrl = baseurl + URLHelper.getShared().getSetupPatternMFA();

                // Get Device Information
                DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
                setupPatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

                //Generate Header
                Map<String, String> headers = Headers.getShared(context).getHeaders(accessToken,verification_api_version,URLHelper.contentTypeJson);

               //Service  call
                serviceForSetupPattern(setupPatternMFARequestEntity, callback, setupPatternMFAUrl, headers);
            }
            else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl or FCMToken must not be empty", "Error :"+methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,e.getMessage()));
        }
    }

    private void serviceForSetupPattern(SetupPatternMFARequestEntity setupPatternMFARequestEntity, final Result<SetupPatternMFAResponseEntity> callback,
                                        String setupPatternMFAUrl, Map<String, String> headers)
    {
        final String methodName="PatternVerificationService :serviceForSetupPattern()";
        try {

            //Call Service
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupPatternMFA(setupPatternMFAUrl, headers, setupPatternMFARequestEntity)
                    .enqueue(new Callback<SetupPatternMFAResponseEntity>() {
                        @Override
                        public void onResponse(Call<SetupPatternMFAResponseEntity> call, Response<SetupPatternMFAResponseEntity> response) {
                            if (response.isSuccessful()) {

                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,
                                            response.code(), "Error :"+methodName));
                                }
                            } else {
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE, response,
                                        "Error :"+methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<SetupPatternMFAResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,
                                    t.getMessage(),"Error :"+methodName));
                        }
                    });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+ methodName, WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,e.getMessage()));
        }
    }

//Done scanned Pattern Webservice
    // 1. Done Check For NotNull Values
    // 2. Done Create header and Scanned Request entity
    // 3. done Call Webservice and return the result
    // 4. done  Maintain logs based on flags

    //---------------------------------------------------Scanned Pattern---------------------------------------------------------------------------
    public void scannedPattern(final String baseurl, ScannedRequestEntity scannedRequestEntity, boolean verification_api_version,
                               final Result<ScannedResponseEntity> callback)
    {

        String methodName="PatternVerificationService :scannedPattern()";
        try {
            String scannedPatternUrl = "";
            if (baseurl != null && !baseurl.equals("") && DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Construct URL For getScannedPatternURL
                scannedPatternUrl = baseurl + URLHelper.getShared().getScannedPatternURL();

                // Get Device Information
                DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
                scannedRequestEntity.setDeviceInfo(deviceInfoEntity);

                //Generate header
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,verification_api_version, URLHelper.contentTypeJson);

                //Service call
                serviceForScannedPattern(baseurl, scannedPatternUrl,headers,scannedRequestEntity, callback);
            }
            else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl or FCMToken must not be empty","Error :"+methodName));
                return;
            }

        } catch (Exception e) {
           callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,e.getMessage()));
        }
    }

    private void serviceForScannedPattern(final String baseurl,String scannedPatternUrl, Map<String, String> headers, ScannedRequestEntity scannedRequestEntity,
                                          final Result<ScannedResponseEntity> callback)
    {
        final String methodName="PatternVerificationService :serviceForScannedPattern()";
        try {
            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.scanned(scannedPatternUrl, headers, scannedRequestEntity).enqueue(new Callback<ScannedResponseEntity>() {
                @Override
                public void onResponse(Call<ScannedResponseEntity> call, Response<ScannedResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            DBHelper.getShared().setUserDeviceId(response.body().getData().getUserDeviceId(), baseurl);
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE, response.code(),
                                    "Error :"+methodName));
                        }
                    } else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE, response,
                                "Error :"+methodName));
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE, t.getMessage(),
                            "Error :"+methodName));
                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,e.getMessage()));
        }
    }


//Done Enroll Pattern Webservice
    // 1. Check For NotNull Values
    // 2.  Create header and Scanned Request entity
    // 3.  Call Webservice and return the result
    // 4.  Maintain logs based on flags

    //---------------------------------------------------enrollPatternMFA--------------------------------------------------------------------------------
    public void enrollPattern(String baseurl, String accessToken, EnrollPatternMFARequestEntity enrollPatternMFARequestEntity, boolean verification_api_version,
                              final Result<EnrollPatternMFAResponseEntity> callback)
    {
        String methodName = "PatternVerificationService :enrollPattern()";
        try {
            if (baseurl != null && !baseurl.equals("") && DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {
                String enrollPatternMFAUrl = "";

                //Construct URL For RequestId
                enrollPatternMFAUrl = baseurl + URLHelper.getShared().getEnrollPatternMFA();

                // Get Device Information
                DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
                enrollPatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

                //Headers Generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(accessToken,verification_api_version,URLHelper.contentTypeJson);

                //Service call
                serviceForEnrollPattern(enrollPatternMFARequestEntity, callback, enrollPatternMFAUrl, headers);

            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl or FCMToken must not be empty", "Error :"+methodName));
                return;
            }

        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,e.getMessage()));
        }

    }

    private void serviceForEnrollPattern(EnrollPatternMFARequestEntity enrollPatternMFARequestEntity, final Result<EnrollPatternMFAResponseEntity> callback,
                                         String enrollPatternMFAUrl, Map<String, String> headers)
    {
        final String methodName="PatternVerificationService :serviceForEnrollPattern()";
        try {
            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.enrollPatternMFA(enrollPatternMFAUrl, headers, enrollPatternMFARequestEntity)
                    .enqueue(new Callback<EnrollPatternMFAResponseEntity>() {
                        @Override
                        public void onResponse(Call<EnrollPatternMFAResponseEntity> call, Response<EnrollPatternMFAResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                                            response.code(), "Error :"+methodName));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE, response,
                                        "Error :"+methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<EnrollPatternMFAResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                                    t.getMessage(),  "Error :"+methodName));
                        }
                    });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,e.getMessage()));
        }
    }


    //----------------------------------------------------initiatePatternMFA-----------------------------------------------------------------------------
    public void initiatePattern(String baseurl,InitiatePatternMFARequestEntity initiatePatternMFARequestEntity, boolean verification_api_version,
                                final Result<InitiatePatternMFAResponseEntity> callback) {
        final String methodName="PatternVerificationService :initiatePattern()";
        try {
            if (baseurl != null && !baseurl.equals("") && DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {

                String initiatePatternMFAUrl = "";

                //Construct URL For RequestId
                initiatePatternMFAUrl = baseurl + URLHelper.getShared().getInitiatePatternMFA();

                // Get Device Information
                DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
                initiatePatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

                //generate Header
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,verification_api_version,URLHelper.contentTypeJson);

                //Service call For Initiate Pattern
                serviceForInitiatePattern(initiatePatternMFARequestEntity, callback, initiatePatternMFAUrl, headers);

            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :"+methodName));
                return;
            }
        }
        catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,e.getMessage()));
        }
    }

    private void serviceForInitiatePattern(InitiatePatternMFARequestEntity initiatePatternMFARequestEntity, final Result<InitiatePatternMFAResponseEntity> callback,
                                           String initiatePatternMFAUrl, Map<String, String> headers) {
        final String methodName="PatternVerificationService :serviceForInitiatePattern()";
       try {
           //Call Service-getRequestId
           final ICidaasSDKService cidaasSDKService = service.getInstance();

           cidaasSDKService.initiatePatternMFA(initiatePatternMFAUrl, headers, initiatePatternMFARequestEntity).enqueue(
                   new Callback<InitiatePatternMFAResponseEntity>() {
                       @Override
                       public void onResponse(Call<InitiatePatternMFAResponseEntity> call, Response<InitiatePatternMFAResponseEntity> response) {
                           if (response.isSuccessful()) {
                               if (response.code() == 200) {
                                   callback.success(response.body());
                               } else {
                                   callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                            response.code(), "Error :"+methodName));
                               }
                           } else {

                               callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE, response,
                                       "Error :"+methodName));
                           }
                       }

                       @Override
                       public void onFailure(Call<InitiatePatternMFAResponseEntity> call, Throwable t) {
                           callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                   t.getMessage(), "Error :"+methodName));
                       }
                   });
       }
       catch (Exception e)
       {
           callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,e.getMessage()));
       }
    }


    //---------------------------------------------------authenticatePatternMFA-----------------------------------------------------------------------
    public void authenticatePattern(String baseurl, AuthenticatePatternRequestEntity authenticatePatternRequestEntity,boolean verification_api_version,
                                    final Result<AuthenticatePatternResponseEntity> callback) {
        final String methodName="PatternVerificationService :authenticatePattern()";
        try {
            if (baseurl != null && !baseurl.equals("")) {

                //Construct URL For RequestId
                String authenticatePatternMFAUrl = baseurl + URLHelper.getShared().getAuthenticatePatternMFA();

                // Get Device Information
                DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();


            Map<String, String> headers = Headers.getShared(context).getHeaders(null,verification_api_version,URLHelper.contentTypeJson);

            

            if (DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

            }

            authenticatePatternRequestEntity.setDeviceInfo(deviceInfoEntity);
            serviceForAuthenticatePattern(authenticatePatternRequestEntity,headers,authenticatePatternMFAUrl,callback);
            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty","Error :"+methodName));
                return;
            }


        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForAuthenticatePattern(AuthenticatePatternRequestEntity authenticatePatternRequestEntity,Map<String, String> headers,
                                               final String authenticatePatternMFAUrl, final Result<AuthenticatePatternResponseEntity> callback ) {
        final String methodName="PatternVerificationService :serviceForAuthenticatePattern()";
        try {
            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticatePatternMFA(authenticatePatternMFAUrl, headers, authenticatePatternRequestEntity)
                    .enqueue(new Callback<AuthenticatePatternResponseEntity>() {
                        @Override
                        public void onResponse(Call<AuthenticatePatternResponseEntity> call, Response<AuthenticatePatternResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                                             response.code(), "Error :" + methodName));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE, response
                                        , "Error :" + methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthenticatePatternResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                                    t.getMessage(), "Error :" + methodName));
                        }
                    });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                    e.getMessage()));
        }
    }
}
