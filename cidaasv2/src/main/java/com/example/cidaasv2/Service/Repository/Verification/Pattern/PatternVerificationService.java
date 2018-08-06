package com.example.cidaasv2.Service.Repository.Verification.Pattern;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.Configuration.Pattern.PatternConfigurationController;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.URLHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
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
    public ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static PatternVerificationService shared;

    public PatternVerificationService(Context contextFromCidaas) {
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


    public static PatternVerificationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new PatternVerificationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

//Todo setup Pattern Webservice
    // 1. Check For NotNull Values
    // 2.  Create header and Scanned Request entity
    // 3.  Call Webservice and return the result
    // 4.  Maintain logs based on flags


    //setupPatternMFA
    public void setupPattern(String baseurl, String accessToken, String codeChallenge, SetupPatternMFARequestEntity setupPatternMFARequestEntity,
                                final Result<SetupPatternMFAResponseEntity> callback)
    {
        String setupPatternMFAUrl="";
        try
        {
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                setupPatternMFAUrl=baseurl+URLHelper.getShared().getSetupPatternMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null));
                return;
            }





            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("verification_api_version","2");
            headers.put("access_token",accessToken);
            headers.put("access_challenge",codeChallenge);
            headers.put("access_challenge_method","S256");


            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                /// deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

                deviceInfoEntity.setPushNotificationId("cegfVcqD6xU:APA91bF1UddwL6AoXUwI5g1s9DRKOkz6KEQz6zbcYRHHrcO34tXkQ8ILe4m38jTuT_Muq" +
                        "IvqC9Z0lZjxvAbGtakhUnCN6sHSbWWr0W10sAM436BCU8-jlEEAB8a_BMPzxGOEDBZIrMWTkdHxtIn_VGxBiOPYia7Zbw");
            }
            setupPatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupPatternMFA(setupPatternMFAUrl,headers, setupPatternMFARequestEntity)
                    .enqueue(new Callback<SetupPatternMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupPatternMFAResponseEntity> call, Response<SetupPatternMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());

                            //Todo Listen For The Push notification to recieve

                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null));
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
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("")
                                    && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError()));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,
                                    e.getMessage(), 400,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<SetupPatternMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    LogFile.addRecordToLog("acceptConsent Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,
                            t.getMessage(), 400,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent Service exception"+e.getMessage());
            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_PATTERN_MFA_FAILURE,e.getMessage(), 400,null));
            Timber.e("acceptConsent Service exception"+e.getMessage());
        }
    }

//Todo scanned Pattern Webservice
    // 1. Done Check For NotNull Values
    // 2. Done Create header and Scanned Request entity
    // 3. done Call Webservice and return the result
    // 4. done  Maintain logs based on flags

    //Scanned Pattern
    public void scannedPattern(String baseurl, String usagePass,String statusId,String AccessToken,
                               final Result<ScannedResponseEntity> callback)
    {
        String scannedPatternUrl="";
        try
        {
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                scannedPatternUrl=baseurl+ URLHelper.getShared().getScannedPatternURL();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("verification_api_version","2");
            headers.put("access_token",AccessToken);



            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            ScannedRequestEntity scannedRequestEntity=new ScannedRequestEntity();
            scannedRequestEntity.setUsage_pass(usagePass);
            scannedRequestEntity.setStatusId(statusId);
            scannedRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.scanned(scannedPatternUrl,headers, scannedRequestEntity).enqueue(new Callback<ScannedResponseEntity>() {
                @Override
                public void onResponse(Call<ScannedResponseEntity> call, Response<ScannedResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null));
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
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("")
                                    && commonErrorEntity.getError() instanceof  String) {

                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError()));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,
                                    e.getMessage(), 400,null));

                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    LogFile.addRecordToLog("Pattern verification Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,
                            t.getMessage(), 400,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("Pattern verification Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_PATTERN_MFA_FAILURE,
                    e.getMessage(), 400,null));
            Timber.e("Pattern verification Service exception"+e.getMessage());
        }
    }



    //enrollPatternMFA
    public void enrollPattern(String baseurl, String accessToken, EnrollPatternMFARequestEntity enrollPatternMFARequestEntity,
                                 final Result<EnrollPatternMFAResponseEntity> callback)
    {
        String enrollPatternMFAUrl="";
        try
        {
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                enrollPatternMFAUrl=baseurl+URLHelper.getShared().getEnrollPatternMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("access_token",accessToken);
            headers.put("verification_api_version","2");

            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                /// deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

                deviceInfoEntity.setPushNotificationId("cegfVcqD6xU:APA91bF1UddwL6AoXUwI5g1s9DRKOkz6KEQz6zbcYRHHrcO34tXkQ8ILe4m38jTuT_MuqIvqC9Z0l" +
                        "ZjxvAbGtakhUnCN6sHSbWWr0W10sAM436BCU8-jlEEAB8a_BMPzxGOEDBZIrMWTkdHxtIn_VGxBiOPYia7Zbw");
            }

            enrollPatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.enrollPatternMFA(enrollPatternMFAUrl,headers, enrollPatternMFARequestEntity)
                    .enqueue(new Callback<EnrollPatternMFAResponseEntity>() {
                @Override
                public void onResponse(Call<EnrollPatternMFAResponseEntity> call, Response<EnrollPatternMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null));
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

                            //Todo Handle Access Token Failure Error
                            String errorMessage="";
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("")
                                    && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError()));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                                    e.getMessage(), 400,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<EnrollPatternMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with pattern service call"+t.getMessage());
                    LogFile.addRecordToLog("Login pattern Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                            t.getMessage(), 400,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("pattern Login Service exception"+e.getMessage());
            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_PATTERN_MFA_FAILURE,
                    e.getMessage(), 400,null));

            Timber.e("pattern Login Service exception"+e.getMessage());
        }
    }



    //initiatePatternMFA
    public void initiatePattern(String baseurl, String codeChallenge,InitiatePatternMFARequestEntity initiatePatternMFARequestEntity,
                                   final Result<InitiatePatternMFAResponseEntity> callback)
    {
        String initiatePatternMFAUrl="";
        try
        {
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                initiatePatternMFAUrl=baseurl+URLHelper.getShared().getInitiatePatternMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("verification_api_version","2");
            headers.put("access_challenge",codeChallenge);
            headers.put("access_challenge_method","S256");

            initiatePatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiatePatternMFA(initiatePatternMFAUrl,headers, initiatePatternMFARequestEntity)
                    .enqueue(new Callback<InitiatePatternMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiatePatternMFAResponseEntity> call, Response<InitiatePatternMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null));
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
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("")
                                    && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError()));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                    e.getMessage(), 400,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiatePatternMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in InitiatePatternMFAResponseEntityservice call"+t.getMessage());
                    LogFile.addRecordToLog("InitiatePatternMFAResponseEntity Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                            t.getMessage(), 400,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("InitiatePatternMFAResponseEntity Service exception"+e.getMessage());
            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                    e.getMessage(), 400,null));
            Timber.e("InitiatePatternMFAResponseEntity Service exception"+e.getMessage());
        }
    }

/*
    //initiatePatternMFA
    public void initiatePatternWithUsagePass(String baseurl, InitiatePatternMFARequestEntity initiatePatternMFARequestEntity,
                                final Result<InitiatePatternMFAResponseEntity> callback)
    {
        String initiatePatternWithUsagePassMFAUrl="";
        try
        {
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                initiatePatternWithUsagePassMFAUrl=baseurl+URLHelper.getShared().getInitiatePatternMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("verification_api_version","2");
            headers.put("access_challenge",);
            headers.put("access_challenge_method","S256");


            initiatePatternMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiatePatternMFA(initiatePatternMFAUrl,headers, initiatePatternMFARequestEntity)
                    .enqueue(new Callback<InitiatePatternMFAResponseEntity>() {
                        @Override
                        public void onResponse(Call<InitiatePatternMFAResponseEntity> call, Response<InitiatePatternMFAResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if(response.code()==200) {
                                    callback.success(response.body());
                                }
                                else {
                                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                            "Service failure but successful response" , response.code(),null));
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
                                    if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("")
                                            && commonErrorEntity.getError() instanceof  String) {
                                        errorMessage=commonErrorEntity.getError().toString();
                                    }
                                    else
                                    {
                                        errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                    }


                                    //Todo Service call For fetching the Consent details
                                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                            errorMessage, commonErrorEntity.getStatus(),
                                            commonErrorEntity.getError()));

                                } catch (Exception e) {
                                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                            e.getMessage(), 400,null));
                                    Timber.e("response"+response.message()+e.getMessage());
                                }
                                Timber.e("response"+response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<InitiatePatternMFAResponseEntity> call, Throwable t) {
                            Timber.e("Failure in InitiatePatternMFAResponseEntityservice call"+t.getMessage());
                            LogFile.addRecordToLog("InitiatePatternMFAResponseEntity Service Failure"+t.getMessage());
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                                    t.getMessage(), 400,null));
                        }
                    });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("InitiatePatternMFAResponseEntity Service exception"+e.getMessage());
            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_PATTERN_MFA_FAILURE,
                    e.getMessage(), 400,null));
            Timber.e("InitiatePatternMFAResponseEntity Service exception"+e.getMessage());
        }
    }*/

    //authenticatePatternMFA
    public void authenticatePattern(String baseurl, AuthenticatePatternRequestEntity authenticatePatternRequestEntity,
                                       final Result<AuthenticatePatternResponseEntity> callback)
    {
        String authenticatePatternMFAUrl="";
        try
        {
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                authenticatePatternMFAUrl=baseurl+URLHelper.getShared().getAuthenticatePatternMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("verification_api_version","2");

            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                /// deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

                deviceInfoEntity.setPushNotificationId("cegfVcqD6xU:APA91bF1UddwL6AoXUwI5g1s9DRKOkz6KEQz6zbcYRHHrcO" +
                        "34tXkQ8ILe4m38jTuT_MuqIvqC9Z0lZjxvAbGtakhUnCN6sHSbWWr0W10sAM436BCU8-jlEEAB8a_BMPzxGOEDBZIrMWTkdHxtIn_VGxBiOPYia7Zbw");
            }

            authenticatePatternRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticatePatternMFA(authenticatePatternMFAUrl,headers, authenticatePatternRequestEntity)
                    .enqueue(new Callback<AuthenticatePatternResponseEntity>() {
                @Override
                public void onResponse(Call<AuthenticatePatternResponseEntity> call, Response<AuthenticatePatternResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null));
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
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("")
                                    && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError()));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                                    e.getMessage(), 400,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<AuthenticatePatternResponseEntity> call, Throwable t) {
                    Timber.e("Failure in AuthenticatePatternResponseEntity service call"+t.getMessage());
                    LogFile.addRecordToLog("AuthenticatePatternResponseEntity Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                            t.getMessage(), 400,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticatePatternMFA Service exception"+e.getMessage());
            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_PATTERN_MFA_FAILURE,
                    e.getMessage(), 400,null));
            Timber.e("authenticatePatternMFA Service exception"+e.getMessage());
        }
    }

}
