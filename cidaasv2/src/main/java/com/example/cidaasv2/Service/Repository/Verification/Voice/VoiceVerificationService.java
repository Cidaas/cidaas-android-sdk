package com.example.cidaasv2.Service.Repository.Verification.Voice;

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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Voice.AuthenticateVoiceResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Voice.EnrollVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Voice.InitiateVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Voice.SetupVoiceMFAResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.example.cidaasv2.Service.Scanned.ScannedRequestEntity;
import com.example.cidaasv2.Service.Scanned.ScannedResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class VoiceVerificationService {

    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static VoiceVerificationService shared;

    public VoiceVerificationService(Context contextFromCidaas) {
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


    public static VoiceVerificationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new VoiceVerificationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    public void scannedVoice(String baseurl,  ScannedRequestEntity scannedRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                             final Result<ScannedResponseEntity> callback)
    {
        String scannedVoiceUrl="";
        try
        {
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                scannedVoiceUrl=baseurl+ URLHelper.getShared().getScannedVoiceURL();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();

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




            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            scannedRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.scanned(scannedVoiceUrl,headers, scannedRequestEntity).enqueue(new Callback<ScannedResponseEntity>() {
                @Override
                public void onResponse(Call<ScannedResponseEntity> call, Response<ScannedResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        try {

                            // Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);

                            ErrorEntity errorEntity=new ErrorEntity();



                            String errorMessage="";
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                              errorEntity.setCode( ((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE,e.getMessage(), 400,null,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    LogFile.addRecordToLog("acceptConsent Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("Voice Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("Voice Service exception"+e.getMessage());
        }
    }


    //setupVoiceMFA
    public void setupVoiceMFA(String baseurl, String accessToken,  SetupVoiceMFARequestEntity setupVoiceMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<SetupVoiceMFAResponseEntity> callback)
    {
        String setupVoiceMFAUrl="";
        try
        {
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                setupVoiceMFAUrl=baseurl+URLHelper.getShared().getSetupVoiceMFA();
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
            headers.put("verification_api_version","2");
            headers.put("access_token",accessToken);



            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                 deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
 }
            setupVoiceMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupVoiceMFA(setupVoiceMFAUrl,headers, setupVoiceMFARequestEntity).enqueue(new Callback<SetupVoiceMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupVoiceMFAResponseEntity> call, Response<SetupVoiceMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());

                            //Todo Listen For The Push notification to recieve

                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        try {

                            // Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);

                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                              errorEntity.setCode( ((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE,e.getMessage(), 400,null,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<SetupVoiceMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    LogFile.addRecordToLog("acceptConsent Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("Voice exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("Voice Service exception"+e.getMessage());
        }
    }

    public RequestBody StringtoRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    //enrollVoiceMFA
    public void enrollVoice(String baseurl, String accessToken, EnrollVoiceMFARequestEntity enrollVoiceMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<EnrollVoiceMFAResponseEntity> callback)
    {
        String enrollVoiceMFAUrl="";
        try
        {
            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                enrollVoiceMFAUrl=baseurl+URLHelper.getShared().getEnrollVoiceMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            HashMap<String, RequestBody> voiceSetupMap = new HashMap<>();

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
          //  headers.put("Content-Type", URLHelper.contentType);
            headers.put("verification_api_version","2");
            headers.put("access_token",accessToken);


            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {

                //Done Change to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

           }
            enrollVoiceMFARequestEntity.setDeviceInfo(deviceInfoEntity);


            voiceSetupMap.put("statusId", StringtoRequestBody(enrollVoiceMFARequestEntity.getStatusId()));
            voiceSetupMap.put("usage_pass",StringtoRequestBody(enrollVoiceMFARequestEntity.getUsage_pass()));
            voiceSetupMap.put("userDeviceId", StringtoRequestBody(enrollVoiceMFARequestEntity.getUserDeviceId()));
            voiceSetupMap.put("deviceId", StringtoRequestBody(enrollVoiceMFARequestEntity.getDeviceInfo().getDeviceId()));
            voiceSetupMap.put("deviceMake", StringtoRequestBody(enrollVoiceMFARequestEntity.getDeviceInfo().getDeviceMake()));
            voiceSetupMap.put("deviceModel", StringtoRequestBody(enrollVoiceMFARequestEntity.getDeviceInfo().getDeviceModel()));
            voiceSetupMap.put("deviceVersion", StringtoRequestBody(enrollVoiceMFARequestEntity.getDeviceInfo().getDeviceVersion()));
            voiceSetupMap.put("pushNotificationId", StringtoRequestBody(enrollVoiceMFARequestEntity.getDeviceInfo().getPushNotificationId()));


            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            if(enrollVoiceMFARequestEntity.getAudioFile()!=null) {

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), enrollVoiceMFARequestEntity.getAudioFile());
                MultipartBody.Part audioFile = MultipartBody.Part.createFormData("voice", "Audio.fav", requestFile);


                cidaasSDKService.enrollVoiceMFA(enrollVoiceMFAUrl, headers, audioFile, voiceSetupMap).enqueue(new Callback<EnrollVoiceMFAResponseEntity>() {
                    @Override
                    public void onResponse(Call<EnrollVoiceMFAResponseEntity> call, Response<EnrollVoiceMFAResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                callback.success(response.body());
                            } else {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,
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


                                ErrorEntity errorEntity = new ErrorEntity();


                                //Todo Handle Access Token Failure Error
                                String errorMessage = "";
                                if (commonErrorEntity.getError() != null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof String) {
                                    errorMessage = commonErrorEntity.getError().toString();
                                } else {
                                    errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                    errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                    errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                    errorEntity.setMoreInfo(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                    errorEntity.setReferenceNumber(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                    errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                    errorEntity.setType(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                                }


                                //Todo Service call For fetching the Consent details
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,
                                        errorMessage, commonErrorEntity.getStatus(),
                                        commonErrorEntity.getError(), errorEntity));

                            } catch (Exception e) {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE, e.getMessage(), 400, null, null));
                                Timber.e("response" + response.message() + e.getMessage());
                            }
                            Timber.e("response" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<EnrollVoiceMFAResponseEntity> call, Throwable t) {
                        Timber.e("Failure in Login with credentials service call" + t.getMessage());
                        LogFile.addRecordToLog("acceptConsent Service Failure" + t.getMessage());
                        callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE, t.getMessage(), 400, null, null));
                    }
                });
            }
            else
            {
                cidaasSDKService.enrollVoiceWithoutAudioMFA(enrollVoiceMFAUrl, headers,  voiceSetupMap).enqueue(new Callback<EnrollVoiceMFAResponseEntity>() {
                    @Override
                    public void onResponse(Call<EnrollVoiceMFAResponseEntity> call, Response<EnrollVoiceMFAResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                callback.success(response.body());
                            } else {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,
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


                                ErrorEntity errorEntity = new ErrorEntity();


                                //Todo Handle Access Token Failure Error
                                String errorMessage = "";
                                if (commonErrorEntity.getError() != null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof String) {
                                    errorMessage = commonErrorEntity.getError().toString();
                                } else {
                                    errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                    errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                    errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                    errorEntity.setMoreInfo(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                    errorEntity.setReferenceNumber(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                    errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                    errorEntity.setType(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                                }


                                //Todo Service call For fetching the Consent details
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,
                                        errorMessage, commonErrorEntity.getStatus(),
                                        commonErrorEntity.getError(), errorEntity));

                            } catch (Exception e) {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE, e.getMessage(), 400, null, null));
                                Timber.e("response" + response.message() + e.getMessage());
                            }
                            Timber.e("response" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<EnrollVoiceMFAResponseEntity> call, Throwable t) {
                        Timber.e("Failure in Login with credentials service call" + t.getMessage());
                        LogFile.addRecordToLog("acceptConsent Service Failure" + t.getMessage());
                        callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE, t.getMessage(), 400, null, null));
                    }
                });
            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("Voice Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("Voice Service exception"+e.getMessage());
        }
    }


    //initiateVoiceMFA
    public void initiateVoice(String baseurl, InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<InitiateVoiceMFAResponseEntity> callback)
    {
        String initiateVoiceMFAUrl="";
        try
        {
            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                initiateVoiceMFAUrl=baseurl+URLHelper.getShared().getInitiateVoiceMFA();
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
            headers.put("verification_api_version","2");



            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {

                //Done Change to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
                }

            initiateVoiceMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateVoiceMFA(initiateVoiceMFAUrl,headers, initiateVoiceMFARequestEntity).enqueue(new Callback<InitiateVoiceMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiateVoiceMFAResponseEntity> call, Response<InitiateVoiceMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        try {

                            // Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                              errorEntity.setCode( ((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE,e.getMessage(), 400,null,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiateVoiceMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in InitiateSMSMFAResponseEntityservice call"+t.getMessage());
                    LogFile.addRecordToLog("InitiateVoiceMFAResponseEntity Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("InitiateVoiceMFAResponseEntity Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("InitiateVoiceMFAResponseEntity Service exception"+e.getMessage());
        }
    }

    //authenticateVoiceMFA
    public void authenticateVoice(String baseurl, AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,final Result<AuthenticateVoiceResponseEntity> callback)
    {
        String authenticateVoiceMFAUrl="";
        try
        {
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                authenticateVoiceMFAUrl=baseurl+URLHelper.getShared().getAuthenticateVoiceMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            HashMap<String, RequestBody> voiceSetupMap = new HashMap<>();


            // Get Device Information
            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();

            //Done This is only for testing purpose
            if(deviceInfoEntityFromParam==null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromParam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromParam;
            }

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
          //  headers.put("Content-Type", URLHelper.contentType);
            headers.put("verification_api_version","2");


            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {

                //Done Change to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            authenticateVoiceRequestEntity.setDeviceInfo(deviceInfoEntity);



            voiceSetupMap.put("statusId", StringtoRequestBody(authenticateVoiceRequestEntity.getStatusId()));
            voiceSetupMap.put("usage_pass",StringtoRequestBody(authenticateVoiceRequestEntity.getUsage_pass()));
            voiceSetupMap.put("userDeviceId", StringtoRequestBody(authenticateVoiceRequestEntity.getUserDeviceId()));
            voiceSetupMap.put("deviceId", StringtoRequestBody(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceId()));
            voiceSetupMap.put("deviceMake", StringtoRequestBody(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceMake()));
            voiceSetupMap.put("deviceModel", StringtoRequestBody(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceModel()));
            voiceSetupMap.put("deviceVersion", StringtoRequestBody(authenticateVoiceRequestEntity.getDeviceInfo().getDeviceVersion()));
            voiceSetupMap.put("pushNotificationId", StringtoRequestBody(authenticateVoiceRequestEntity.getDeviceInfo().getPushNotificationId()));



            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            if(authenticateVoiceRequestEntity.getVoiceFile()!=null) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), authenticateVoiceRequestEntity.getVoiceFile());
                MultipartBody.Part audioFile = MultipartBody.Part.createFormData("voice", "Audio.fav", requestFile);


                cidaasSDKService.authenticateVoiceMFA(authenticateVoiceMFAUrl, headers, audioFile, voiceSetupMap).enqueue(new Callback<AuthenticateVoiceResponseEntity>() {
                    @Override
                    public void onResponse(Call<AuthenticateVoiceResponseEntity> call, Response<AuthenticateVoiceResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                callback.success(response.body());
                            } else {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,
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
                                    errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                    errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                    errorEntity.setMoreInfo(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                    errorEntity.setReferenceNumber(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                    errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                    errorEntity.setType(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                                }


                                //Todo Service call For fetching the Consent details
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,
                                        errorMessage, commonErrorEntity.getStatus(),
                                        commonErrorEntity.getError(), errorEntity));

                            } catch (Exception e) {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE, e.getMessage(), 400, null, null));
                                Timber.e("response" + response.message() + e.getMessage());
                            }
                            Timber.e("response" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticateVoiceResponseEntity> call, Throwable t) {
                        Timber.e("Failure in AuthenticateVoiceResponseEntity service call" + t.getMessage());
                        LogFile.addRecordToLog("AuthenticateVoiceResponseEntity Service Failure" + t.getMessage());
                        callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE, t.getMessage(), 400, null, null));
                    }
                });
            }
            else
            {
                cidaasSDKService.authenticateVoiceWithoutAudioMFA(authenticateVoiceMFAUrl, headers,  voiceSetupMap).enqueue(new Callback<AuthenticateVoiceResponseEntity>() {
                    @Override
                    public void onResponse(Call<AuthenticateVoiceResponseEntity> call, Response<AuthenticateVoiceResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                callback.success(response.body());
                            } else {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,
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
                                    errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                    errorEntity.setError(((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                    errorEntity.setMoreInfo(((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                    errorEntity.setReferenceNumber(((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                    errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                    errorEntity.setType(((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                                }


                                //Todo Service call For fetching the Consent details
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,
                                        errorMessage, commonErrorEntity.getStatus(),
                                        commonErrorEntity.getError(), errorEntity));

                            } catch (Exception e) {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE, e.getMessage(), 400, null, null));
                                Timber.e("response" + response.message() + e.getMessage());
                            }
                            Timber.e("response" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticateVoiceResponseEntity> call, Throwable t) {
                        Timber.e("Failure in AuthenticateVoiceResponseEntity service call" + t.getMessage());
                        LogFile.addRecordToLog("AuthenticateVoiceResponseEntity Service Failure" + t.getMessage());
                        callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE, t.getMessage(), 400, null, null));
                    }
                });
            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateVoiceMFA Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("authenticateVoiceMFA Service exception"+e.getMessage());
        }
    }

}
