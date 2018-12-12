package com.example.cidaasv2.Service.Repository.Verification.FIDO;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey.AuthenticateFIDORequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.FIDOKey.AuthenticateFIDOResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.EnrollFIDOMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey.EnrollFIDOMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey.InitiateFIDOMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.FIDOKey.InitiateFIDOMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.FIDO.SetupFIDOMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.FIDO.SetupFIDOMFAResponseEntity;
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

public class FIDOVerificationService {

    CidaassdkService service;
    private ObjectMapper objectMapper = new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static FIDOVerificationService shared;

    public FIDOVerificationService(Context contextFromCidaas) {
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


    public static FIDOVerificationService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new FIDOVerificationService(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //Scanned FIDO
    public void scannedFIDO(String baseurl, ScannedRequestEntity scannedRequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                                   final Result<ScannedResponseEntity> callback) {
        String scannedFIDOUrl = "";
        try {
            if (baseurl != null && baseurl != "") {
                //Construct URL For RequestId
                scannedFIDOUrl = baseurl + URLHelper.getShared().getScannedFIDOURL();
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


            if (DBHelper.getShared().getFCMToken() != null && DBHelper.getShared().getFCMToken() != "") {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }


            scannedRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.scanned(scannedFIDOUrl, headers, scannedRequestEntity).enqueue(new Callback<ScannedResponseEntity>() {
                @Override
                public void onResponse(Call<ScannedResponseEntity> call, Response<ScannedResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_FIDO_MFA_FAILURE,
                                    "Service failure but successful response", response.code(), null, null));
                        }
                    } else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        try {

                            // Handle proper error message
                            String errorResponse = response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity = objectMapper.readValue(errorResponse, CommonErrorEntity.class);

                            String errorMessage = "";
                            ErrorEntity errorEntity = new ErrorEntity();
                            if (commonErrorEntity.getError() != null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof String) {
                                errorMessage = commonErrorEntity.getError().toString();
                            } else {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_FIDO_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(), errorEntity));

                        } catch (Exception e) {
                            Timber.e("response" + response.message() + e.getMessage());
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_FIDO_MFA_FAILURE, e.getMessage(), 400, null, null));

                        }
                        Timber.e("response" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Scanned FIDO service call" + t.getMessage());
                    LogFile.addRecordToLog("Failure in Scanned FIDO Service Failure" + t.getMessage());
                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_FIDO_MFA_FAILURE, t.getMessage(), 400, null, null));
                }
            });


        } catch (Exception e) {
            LogFile.addRecordToLog("Failure in Scanned FIDO Service exception" + e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("Failure in Scanned FIDO Service exception" + e.getMessage());
        }
    }


    //setupFIDOMFA
    public void setupFIDO(String baseurl, String accessToken, SetupFIDOMFARequestEntity setupFIDOMFARequestEntity, DeviceInfoEntity deviceInfoEntityFromParam, final Result<SetupFIDOMFAResponseEntity> callback) {
        String setupFIDOMFAUrl = "";
        try {
            if (baseurl != null && baseurl != "") {
                //Construct URL For RequestId
                setupFIDOMFAUrl = baseurl + URLHelper.getShared().getSetupFIDOMFA();
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
            headers.put("access_token", accessToken);


            if (DBHelper.getShared().getFCMToken() != null && DBHelper.getShared().getFCMToken() != "") {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

                }

            setupFIDOMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupFIDOMFA(setupFIDOMFAUrl, headers, setupFIDOMFARequestEntity).enqueue(new Callback<SetupFIDOMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupFIDOMFAResponseEntity> call, Response<SetupFIDOMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());

                            //Todo Listen For The Push notification to recieve

                        } else {
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_FIDO_MFA_FAILURE,
                                    "Service failure but successful response", response.code(), null, null));
                        }
                    } else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        try {

                            // Handle proper error message
                            String errorResponse = response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity = objectMapper.readValue(errorResponse, CommonErrorEntity.class);

                            String errorMessage = "";
                            ErrorEntity errorEntity = new ErrorEntity();
                            if (commonErrorEntity.getError() != null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof String) {
                                errorMessage = commonErrorEntity.getError().toString();
                            } else {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_FIDO_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(), errorEntity));

                        } catch (Exception e) {
                            Timber.e("response" + response.message() + e.getMessage());
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_FIDO_MFA_FAILURE, e.getMessage(), 400, null, null));

                        }
                        Timber.e("response" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<SetupFIDOMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call" + t.getMessage());
                    LogFile.addRecordToLog("acceptConsent Service Failure" + t.getMessage());
                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_FIDO_MFA_FAILURE, t.getMessage(), 400, null, null));
                }
            });


        } catch (Exception e) {
            LogFile.addRecordToLog("acceptConsent Service exception" + e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("acceptConsent Service exception" + e.getMessage());
        }
    }


    //enrollFIDOMFA
    public void enrollFIDO(String baseurl, String accessToken, EnrollFIDOMFARequestEntity enrollFIDOMFARequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                                  final Result<EnrollFIDOMFAResponseEntity> callback) {
        String enrollFIDOMFAUrl = "";
        try {
            if (baseurl != null && baseurl != "") {
                //Construct URL For RequestId
                enrollFIDOMFAUrl = baseurl + URLHelper.getShared().getEnrollFIDOMFA();
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
            headers.put("verification_api_version", "2");

            if (DBHelper.getShared().getFCMToken() != null && DBHelper.getShared().getFCMToken() != "") {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }


            enrollFIDOMFARequestEntity.setDeviceInfo(deviceInfoEntity);


            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.enrollFIDOMFA(enrollFIDOMFAUrl, headers, enrollFIDOMFARequestEntity).enqueue(new Callback<EnrollFIDOMFAResponseEntity>() {
                @Override
                public void onResponse(Call<EnrollFIDOMFAResponseEntity> call, Response<EnrollFIDOMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,
                                    "Service failure but successful response", response.code(), null, null));
                        }
                    } else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        try {

                            // Handle proper error message
                            String errorResponse = response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity = objectMapper.readValue(errorResponse, CommonErrorEntity.class);

                            //Todo Handle Access Token Failure Error
                            String errorMessage = "";
                            ErrorEntity errorEntity = new ErrorEntity();
                            if (commonErrorEntity.getError() != null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof String) {
                                errorMessage = commonErrorEntity.getError().toString();
                            } else {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(), errorEntity));

                        } catch (Exception e) {
                            Timber.e("response" + response.message() + e.getMessage());
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE, e.getMessage(), 400, null, null));

                        }
                        Timber.e("response" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<EnrollFIDOMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call" + t.getMessage());
                    LogFile.addRecordToLog("acceptConsent Service Failure" + t.getMessage());
                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FIDO_MFA_FAILURE, t.getMessage(), 400, null, null));
                }
            });


        } catch (Exception e) {
            LogFile.addRecordToLog("acceptConsent Service exception" + e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("acceptConsent Service exception" + e.getMessage());
        }
    }

    //initiateFIDOMFA
    public void initiateFIDO(String baseurl, InitiateFIDOMFARequestEntity initiateFIDOMFARequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                                    final Result<InitiateFIDOMFAResponseEntity> callback) {
        String initiateFIDOMFAUrl = "";
        try {
            if (baseurl != null && baseurl != "") {
                //Construct URL For RequestId
                initiateFIDOMFAUrl = baseurl + URLHelper.getShared().getInitiateFIDOMFA();
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

            if (DBHelper.getShared().getFCMToken() != null && DBHelper.getShared().getFCMToken() != "") {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }


            initiateFIDOMFARequestEntity.setDeviceInfo(deviceInfoEntity);


            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateFIDOMFA(initiateFIDOMFAUrl, headers, initiateFIDOMFARequestEntity).enqueue(new Callback<InitiateFIDOMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiateFIDOMFAResponseEntity> call, Response<InitiateFIDOMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_FIDO_MFA_FAILURE,
                                    "Service failure but successful response", response.code(), null, null));
                        }
                    } else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        try {

                            // Handle proper error message
                            String errorResponse = response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity = objectMapper.readValue(errorResponse, CommonErrorEntity.class);


                            String errorMessage = "";
                            ErrorEntity errorEntity = new ErrorEntity();
                            if (commonErrorEntity.getError() != null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof String) {
                                errorMessage = commonErrorEntity.getError().toString();
                            } else {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_FIDO_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(), errorEntity));

                        } catch (Exception e) {
                            Timber.e("response" + response.message() + e.getMessage());
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_FIDO_MFA_FAILURE, e.getMessage(), 400, null, null));

                        }
                        Timber.e("response" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiateFIDOMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in InitiateSMSMFAResponseEntityservice call" + t.getMessage());
                    LogFile.addRecordToLog("InitiateFIDOMFAResponseEntity Service Failure" + t.getMessage());
                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_FIDO_MFA_FAILURE, t.getMessage(), 400, null, null));
                }
            });


        } catch (Exception e) {
            LogFile.addRecordToLog("InitiateFIDOMFAResponseEntity Service exception" + e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("InitiateFIDOMFAResponseEntity Service exception" + e.getMessage());
        }
    }


    //authenticateFIDOMFA
    public void authenticateFIDO(String baseurl, AuthenticateFIDORequestEntity authenticateFIDORequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,
                                        final Result<AuthenticateFIDOResponseEntity> callback) {
        String authenticateFIDOMFAUrl = "";
        try {
            if (baseurl != null && baseurl != "") {
                //Construct URL For RequestId
                authenticateFIDOMFAUrl = baseurl + URLHelper.getShared().getAuthenticateFIDOMFA();
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

            if (DBHelper.getShared().getFCMToken() != null && DBHelper.getShared().getFCMToken() != "") {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }


            authenticateFIDORequestEntity.setDeviceInfo(deviceInfoEntity);
            //authenticateFIDORequestEntity.setFidoSignTouchResponse(deviceInfoEntity.getDeviceId());

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticateFIDOMFA(authenticateFIDOMFAUrl, headers, authenticateFIDORequestEntity).enqueue(new Callback<AuthenticateFIDOResponseEntity>() {
                @Override
                public void onResponse(Call<AuthenticateFIDOResponseEntity> call, Response<AuthenticateFIDOResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FIDO_MFA_FAILURE,
                                    "Service failure but successful response", response.code(), null, null));
                        }
                    } else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        try {

                            // Handle proper error message
                            String errorResponse = response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity = objectMapper.readValue(errorResponse, CommonErrorEntity.class);


                            String errorMessage = "";

                            ErrorEntity errorEntity = new ErrorEntity();
                            if (commonErrorEntity.getError() != null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof String) {
                                errorMessage = commonErrorEntity.getError().toString();
                            } else {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FIDO_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(), errorEntity));

                        } catch (Exception e) {
                            Timber.e("response" + response.message() + e.getMessage());
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FIDO_MFA_FAILURE, e.getMessage(), 400, null, null));

                        }
                        Timber.e("response" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateFIDOResponseEntity> call, Throwable t) {
                    Timber.e("Failure in AuthenticateFIDOResponseEntity service call" + t.getMessage());
                    LogFile.addRecordToLog("AuthenticateFIDOResponseEntity Service Failure" + t.getMessage());
                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FIDO_MFA_FAILURE, t.getMessage(), 400, null, null));
                }
            });


        } catch (Exception e) {
            LogFile.addRecordToLog("authenticateFIDOMFA Service exception" + e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("authenticateFIDOMFA Service exception" + e.getMessage());
        }
    }
}
