package com.example.cidaasv2.Service.Repository.Verification.Voice;

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
            if(baseurl!=null || !baseurl.equals("")){
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
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());



            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {
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
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Scanned Voice service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Scanned Voice Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Voice Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SCANNED_VOICE_MFA_FAILURE));
            Timber.e("Voice Service exception"+e.getMessage());
        }
    }


    //setupVoiceMFA
    public void setupVoiceMFA(String baseurl, String accessToken,  SetupVoiceMFARequestEntity setupVoiceMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<SetupVoiceMFAResponseEntity> callback)
    {
        String setupVoiceMFAUrl="";
        try
        {
            if(baseurl!=null || !baseurl.equals("")){
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());



            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {

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
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<SetupVoiceMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Setup Voice service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("setup voice Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Voice exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SETUP_VOICE_MFA_FAILURE));
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
            if(baseurl!=null && !baseurl.equals("")){
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {

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
                            callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,response));
                        }
                    }

                    @Override
                    public void onFailure(Call<EnrollVoiceMFAResponseEntity> call, Throwable t) {
                        Timber.e("Failure in Enroll Voice service call" + t.getMessage());
                        LogFile.getShared(context).addRecordToLog(" Enroll Voice Service Failure" + t.getMessage());
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
                            callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE,response));
                        }
                    }

                    @Override
                    public void onFailure(Call<EnrollVoiceMFAResponseEntity> call, Throwable t) {
                        Timber.e("Failure in  Enroll Voice service call" + t.getMessage());
                        LogFile.getShared(context).addRecordToLog(" Enroll Voice Service Failure" + t.getMessage());
                        callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE, t.getMessage(), 400, null, null));
                    }
                });
            }

        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Voice Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.ENROLL_VOICE_MFA_FAILURE));
            Timber.e("Voice Service exception"+e.getMessage());
        }
    }


    //initiateVoiceMFA
    public void initiateVoice(String baseurl, InitiateVoiceMFARequestEntity initiateVoiceMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<InitiateVoiceMFAResponseEntity> callback)
    {
        String initiateVoiceMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());



            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {

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
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<InitiateVoiceMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in  Inititate Voice call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Initiate Voice MFA Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Initiate Voice MFA Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_VOICE_MFA_FAILURE));
            Timber.e("Initiate Voice MFA Service exception"+e.getMessage());
        }
    }

    //authenticateVoiceMFA
    public void authenticateVoice(String baseurl, AuthenticateVoiceRequestEntity authenticateVoiceRequestEntity, DeviceInfoEntity deviceInfoEntityFromParam,final Result<AuthenticateVoiceResponseEntity> callback)
    {
        String authenticateVoiceMFAUrl="";
        try
        {
            if(baseurl!=null || !baseurl.equals("")){
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());


            if(DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {

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
                            callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE, response));
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticateVoiceResponseEntity> call, Throwable t) {
                        Timber.e("Failure in Authenticate Voice  service call" + t.getMessage());
                        LogFile.getShared(context).addRecordToLog("Authenticate Voice Service Failure" + t.getMessage());
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
                            // Handle proper error message

                            callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE,response));
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticateVoiceResponseEntity> call, Throwable t) {
                        Timber.e("Failure in Authenticate Voice service call" + t.getMessage());
                        LogFile.getShared(context).addRecordToLog("Authenticate Voice Service Failure" + t.getMessage());
                        callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE, t.getMessage(), 400, null, null));
                    }
                });
            }

        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Authenticate Voice MFA Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.AUTHENTICATE_VOICE_MFA_FAILURE));
            Timber.e("Authenticate Voice MFA Service exception"+e.getMessage());
        }
    }

}
