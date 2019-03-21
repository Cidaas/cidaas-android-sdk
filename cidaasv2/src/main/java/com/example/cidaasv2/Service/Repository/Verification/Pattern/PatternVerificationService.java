package com.example.cidaasv2.Service.Repository.Verification.Pattern;

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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Pattern.AuthenticatePatternResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Pattern.EnrollPatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Pattern.InitiatePatternMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Pattern.SetupPatternMFAResponseEntity;
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
            service = new CidaassdkService();
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

//Done setup Pattern Webservice
    // 1. Check For NotNull Values
    // 2.  Create header and Scanned Request entity
    // 3.  Call Webservice and return the result
    // 4.  Maintain logs based on flags


    //setupPatternMFA
    public void setupPattern(String baseurl, String accessToken,  SetupPatternMFARequestEntity setupPatternMFARequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                             final Result<SetupPatternMFAResponseEntity> callback) {
        String setupPatternMFAUrl = "";
        try {
            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId
                setupPatternMFAUrl = baseurl + URLHelper.getShared().getSetupPatternMFA();
            } else {
                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400, null, null));
                return;
            }


            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
            //This is only for testing purpose
            if (deviceInfoEntityFromParam == null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            } else if (deviceInfoEntityFromParam != null) {
                deviceInfoEntity = deviceInfoEntityFromParam;
            }
            //Done - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("access_token", accessToken);
            headers.put("verification_api_version","2");
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            if (DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Done Change to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

            }
            setupPatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupPatternMFA(setupPatternMFAUrl, headers, setupPatternMFARequestEntity)
                    .enqueue(new Callback<SetupPatternMFAResponseEntity>() {
                        @Override
                        public void onResponse(Call<SetupPatternMFAResponseEntity> call, Response<SetupPatternMFAResponseEntity> response) {
                            if (response.isSuccessful()) {

                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,
                                            "Service failure but successful response", response.code(), null, null));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,response));
                            }
                        }

                        @Override
                        public void onFailure(Call<SetupPatternMFAResponseEntity> call, Throwable t) {
                            Timber.e("Failure in Setup pattern service call" + t.getMessage());
                            LogFile.getShared(context).addRecordToLog("Setup pattern Service Failure" + t.getMessage());
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,
                                    t.getMessage(), 400, null, null));
                        }
                    });


        } catch (Exception e) {
            LogFile.getShared(context).addRecordToLog("Setup Pattern Service exception" + e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE));
            Timber.e("Setup Pattern Service exception" + e.getMessage());
        }
    }

//Done scanned Pattern Webservice
    // 1. Done Check For NotNull Values
    // 2. Done Create header and Scanned Request entity
    // 3. done Call Webservice and return the result
    // 4. done  Maintain logs based on flags

    //Scanned Pattern
    public void scannedPattern(final String baseurl, ScannedRequestEntity scannedRequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                               final Result<ScannedResponseEntity> callback) {
        String scannedPatternUrl = "";
        try {
            if (baseurl != null && !baseurl.equals("")) {  //&& scannedRequestEntity.getClient_id() != null && scannedRequestEntity.getClient_id() != ""
                //Construct URL For RequestId
                scannedPatternUrl = baseurl + URLHelper.getShared().getScannedPatternURL();
            } else {
                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400, null, null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
            //This is only for testing purpose
            if (deviceInfoEntityFromParam == null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            } else if (deviceInfoEntityFromParam != null) {
                deviceInfoEntity = deviceInfoEntityFromParam;
            }

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
          /*  headers.put("access_token", AccessToken);*/
            headers.put("verification_api_version","2");
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            if (DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            scannedRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.scanned(scannedPatternUrl, headers, scannedRequestEntity).enqueue(new Callback<ScannedResponseEntity>() {
                @Override
                public void onResponse(Call<ScannedResponseEntity> call, Response<ScannedResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            DBHelper.getShared().setUserDeviceId(response.body().getData().getUserDeviceId(),baseurl);
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,
                                    "Service failure but successful response", response.code(), null, null));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Scanned Pattern service call" + t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Pattern verification Service Failure" + t.getMessage());
                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,
                            t.getMessage(), 400, null, null));
                }
            });


        } catch (Exception e) {
            LogFile.getShared(context).addRecordToLog("Pattern Scanned Service exception" + e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE));
            Timber.e("Pattern Scanned Service exception" + e.getMessage());
        }
    }


//Done Enroll Pattern Webservice
    // 1. Check For NotNull Values
    // 2.  Create header and Scanned Request entity
    // 3.  Call Webservice and return the result
    // 4.  Maintain logs based on flags

    //enrollPatternMFA
    public void enrollPattern(String baseurl, String accessToken, EnrollPatternMFARequestEntity enrollPatternMFARequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                              final Result<EnrollPatternMFAResponseEntity> callback) {
        String enrollPatternMFAUrl = "";
        try {
            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId
                enrollPatternMFAUrl = baseurl + URLHelper.getShared().getEnrollPatternMFA();
            } else {
                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400, null, null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();
            //This is only for testing purpose
            if (deviceInfoEntityFromParam == null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            } else if (deviceInfoEntityFromParam != null) {
                deviceInfoEntity = deviceInfoEntityFromParam;
            }

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("access_token", accessToken);
            headers.put("verification_api_version","2");
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            if (DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Done Change to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

          }

            enrollPatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

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
                                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                                            "Service failure but successful response", response.code(), null, null));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,response));
                            }
                        }

                        @Override
                        public void onFailure(Call<EnrollPatternMFAResponseEntity> call, Throwable t) {
                            Timber.e("Failure in Login with pattern service call" + t.getMessage());
                            LogFile.getShared(context).addRecordToLog("Login pattern Service Failure" + t.getMessage());
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                                    t.getMessage(), 400, null, null));
                        }
                    });


        } catch (Exception e) {
            LogFile.getShared(context).addRecordToLog("pattern Login Service exception" + e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE));

            Timber.e("pattern Login Service exception" + e.getMessage());
        }
    }


    //initiatePatternMFA
    public void initiatePattern(String baseurl,InitiatePatternMFARequestEntity initiatePatternMFARequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                                final Result<InitiatePatternMFAResponseEntity> callback) {
        String initiatePatternMFAUrl = "";
        try {
            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId
                initiatePatternMFAUrl = baseurl + URLHelper.getShared().getInitiatePatternMFA();
            } else {
                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400, null, null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();

            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();

            //This is only for testing purpose
            if (deviceInfoEntityFromParam == null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            } else if (deviceInfoEntityFromParam != null) {
                deviceInfoEntity = deviceInfoEntityFromParam;
            }

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("verification_api_version", "2");
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            if (DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Done Chaange to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

            }

            initiatePatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiatePatternMFA(initiatePatternMFAUrl, headers, initiatePatternMFARequestEntity)
                    .enqueue(new Callback<InitiatePatternMFAResponseEntity>() {
                        @Override
                        public void onResponse(Call<InitiatePatternMFAResponseEntity> call, Response<InitiatePatternMFAResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                            "Service failure but successful response", response.code(), null, null));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,response));
                            }
                        }

                        @Override
                        public void onFailure(Call<InitiatePatternMFAResponseEntity> call, Throwable t) {
                            Timber.e("Failure in Initiate Pattern MFA service call" + t.getMessage());
                            LogFile.getShared(context).addRecordToLog("Initiate Pattern MFA Service Failure" + t.getMessage());
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                    t.getMessage(), 400, null, null));
                        }
                    });


        } catch (Exception e) {
            LogFile.getShared(context).addRecordToLog("Initiate Pattern MFA Service exception" + e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE));
            Timber.e("Initiate Pattern MFA Service exception" + e.getMessage());
        }
    }


    //authenticatePatternMFA
    public void authenticatePattern(String baseurl, AuthenticatePatternRequestEntity authenticatePatternRequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                                    final Result<AuthenticatePatternResponseEntity> callback) {
        String authenticatePatternMFAUrl = "";
        try {
            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId
                authenticatePatternMFAUrl = baseurl + URLHelper.getShared().getAuthenticatePatternMFA();
            } else {
                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400, null, null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();

            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = new DeviceInfoEntity();

            //This is only for testing purpose
            if (deviceInfoEntityFromParam == null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            } else if (deviceInfoEntityFromParam != null) {
                deviceInfoEntity = deviceInfoEntityFromParam;
            }

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("verification_api_version", "2");
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            if (DBHelper.getShared().getFCMToken() != null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

            }

            authenticatePatternRequestEntity.setDeviceInfo(deviceInfoEntity);

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
                                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                                            "Service failure but successful response", response.code(), null, null));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,response));
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthenticatePatternResponseEntity> call, Throwable t) {
                            Timber.e("Failure in Authenticate Pattern service call" + t.getMessage());
                            LogFile.getShared(context).addRecordToLog("Authenticate Pattern Service Failure" + t.getMessage());
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                                    t.getMessage(), 400, null, null));
                        }
                    });


        } catch (Exception e) {
            LogFile.getShared(context).addRecordToLog("Authenticate Pattern MFA Service exception" + e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE));
            Timber.e("Authenticate Pattern MFA Service exception" + e.getMessage());
        }
    }
}
