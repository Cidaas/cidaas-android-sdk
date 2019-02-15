package com.example.cidaasv2.Service.Repository.Verification.Face;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Face.EnrollFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Face.InitiateFaceMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Face.SetupFaceMFAResponseEntity;
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

public class FaceVerificationService {

    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static FaceVerificationService shared;

    public FaceVerificationService(Context contextFromCidaas) {
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


    public static FaceVerificationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new FaceVerificationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }



    //setupFaceMFA
    public void setupFaceMFA(String baseurl, String accessToken, SetupFaceMFARequestEntity setupFaceMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                             final Result<SetupFaceMFAResponseEntity> callback)
    {
        String setupFaceMFAUrl="";
        try
        {
            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                setupFaceMFAUrl=baseurl+ URLHelper.getShared().getSetupFaceMFA();
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
            setupFaceMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupFaceMFA(setupFaceMFAUrl,headers, setupFaceMFARequestEntity).enqueue(new Callback<SetupFaceMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupFaceMFAResponseEntity> call, Response<SetupFaceMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());

                            //Todo Listen For The Push notification to recieve

                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_FACE_MFA_FAILURE,
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
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") &&
                                    commonErrorEntity.getError() instanceof  String) {
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
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_FACE_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_FACE_MFA_FAILURE,e.getMessage(),
                                    400,null,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<SetupFaceMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    LogFile.addRecordToLog("acceptConsent Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_FACE_MFA_FAILURE,t.getMessage(),
                            400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("acceptConsent Service exception"+e.getMessage());
        }
    }

    public void scannedFace(String baseurl,  ScannedRequestEntity scannedRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                            final Result<ScannedResponseEntity> callback)
    {
        String scannedFaceUrl="";
        try
        {
            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                scannedFaceUrl=baseurl+URLHelper.getShared().getScannedFaceURL();
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
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }


            scannedRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.scanned(scannedFaceUrl,headers, scannedRequestEntity).enqueue(new Callback<ScannedResponseEntity>() {
                @Override
                public void onResponse(Call<ScannedResponseEntity> call, Response<ScannedResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_FACE_MFA_FAILURE,
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
                                errorEntity.setCode(((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_FACE_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_FACE_MFA_FAILURE,e.getMessage(), 400,null,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    LogFile.addRecordToLog("acceptConsent Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SCANNED_FACE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("Face Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("Face Service exception"+e.getMessage());
        }
    }

    public RequestBody StringtoRequestBody(String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }
    //enrollFaceMFA
    public void enrollFace(String baseurl, String accessToken, EnrollFaceMFARequestEntity enrollFaceMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                              final Result<EnrollFaceMFAResponseEntity> callback)
    {
        String enrollFaceMFAUrl="";
        try {
            if (baseurl != null && baseurl != "") {
                //Construct URL For RequestId
                enrollFaceMFAUrl = baseurl + URLHelper.getShared().getEnrollFaceMFA();
            } else {
                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400, null,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();


            HashMap<String, RequestBody> faceSetupMap = new HashMap<>();


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
            if (DBHelper.getShared().getFCMToken() != null && DBHelper.getShared().getFCMToken() != "") {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                 deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

                //deviceInfoEntity.setPushNotificationId("cegfVcqD6xU:APA91bF1UddwL6AoXUwI5g1s9DRKOkz6KEQz6zbcYRHHrcO34tXkQ8ILe4m38jTuT_MuqIvqC9Z0lZjxvAbGtakhUnCN6sHSbWWr0W10sAM436BCU8-jlEEAB8a_BMPzxGOEDBZIrMWTkdHxtIn_VGxBiOPYia7Zbw");
            }
            enrollFaceMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
           // headers.put("Content-Type", URLHelper.contentType);

            headers.put("access_token", accessToken);
            headers.put("verification_api_version","2");

            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            faceSetupMap.put("statusId", StringtoRequestBody(enrollFaceMFARequestEntity.getStatusId()));
            faceSetupMap.put("client_id",StringtoRequestBody(enrollFaceMFARequestEntity.getClient_id()));
            faceSetupMap.put("attempt",StringtoRequestBody(""+enrollFaceMFARequestEntity.getAttempt()+""));
            faceSetupMap.put("usage_pass",StringtoRequestBody(enrollFaceMFARequestEntity.getUsage_pass()));
            faceSetupMap.put("userDeviceId", StringtoRequestBody(enrollFaceMFARequestEntity.getUserDeviceId()));
            faceSetupMap.put("deviceId", StringtoRequestBody(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceId()));
            faceSetupMap.put("deviceMake", StringtoRequestBody(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceMake()));
            faceSetupMap.put("deviceModel", StringtoRequestBody(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceModel()));
            faceSetupMap.put("deviceVersion", StringtoRequestBody(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceVersion()));
            faceSetupMap.put("pushNotificationId", StringtoRequestBody(enrollFaceMFARequestEntity.getDeviceInfo().getPushNotificationId()));

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            if(enrollFaceMFARequestEntity.getImagetoSend() != null) {


                Bitmap finalimg = BitmapFactory.decodeFile(enrollFaceMFARequestEntity.getImagetoSend().getAbsolutePath());

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), enrollFaceMFARequestEntity.getImagetoSend());
                MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", "cidaas.png", requestFile);

                if (enrollFaceMFARequestEntity.getImagetoSend().exists()) {
                    // Toast.makeText(context, "FIle found", Toast.LENGTH_SHORT).show();
                }

                cidaasSDKService.enrollFaceMFA(enrollFaceMFAUrl,headers,photo,faceSetupMap).enqueue(new Callback<EnrollFaceMFAResponseEntity>() {
                    @Override
                    public void onResponse(Call<EnrollFaceMFAResponseEntity> call, Response<EnrollFaceMFAResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if(response.code()==200) {
                                callback.success(response.body());
                            }
                            else {
                                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
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

                                //Todo Handle Access Token Failure Error
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
                                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
                                        errorMessage, commonErrorEntity.getStatus(),
                                        commonErrorEntity.getError(),errorEntity));

                            } catch (Exception e) {
                                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,e.getMessage(), 400,null,null));
                                Timber.e("response"+response.message()+e.getMessage());
                            }
                            Timber.e("response"+response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<EnrollFaceMFAResponseEntity> call, Throwable t) {
                        Timber.e("Failure in Login with credentials service call"+t.getMessage());
                        LogFile.addRecordToLog("acceptConsent Service Failure"+t.getMessage());
                        callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,t.getMessage(), 400,null,null));
                    }
                });
            }
            else
            {

                cidaasSDKService.enrollFaceWithoutPhotoMFA(enrollFaceMFAUrl,headers,faceSetupMap).enqueue(new Callback<EnrollFaceMFAResponseEntity>() {
                    @Override
                    public void onResponse(Call<EnrollFaceMFAResponseEntity> call, Response<EnrollFaceMFAResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if(response.code()==200) {
                                callback.success(response.body());
                            }
                            else {
                                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
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

                                //Todo Handle Access Token Failure Error
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
                                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
                                        errorMessage, commonErrorEntity.getStatus(),
                                        commonErrorEntity.getError(),errorEntity));

                            } catch (Exception e) {
                                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,e.getMessage(), 400,null,null));
                                Timber.e("response"+response.message()+e.getMessage());
                            }
                            Timber.e("response"+response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<EnrollFaceMFAResponseEntity> call, Throwable t) {
                        Timber.e("Failure in Login with credentials service call"+t.getMessage());
                        LogFile.addRecordToLog("acceptConsent Service Failure"+t.getMessage());
                        callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,t.getMessage(), 400,null,null));
                    }
                });
            }

        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("acceptConsent Service exception"+e.getMessage());
        }
    }



    //enrollFaceMFA
    public void enrollFaces(String baseurl, String accessToken, EnrollFaceMFARequestEntity enrollFaceMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                           final Result<EnrollFaceMFAResponseEntity> callback)
    {
        String enrollFaceMFAUrl="";
        try {
            if (baseurl != null && baseurl != "") {
                //Construct URL For RequestId
                enrollFaceMFAUrl = baseurl + URLHelper.getShared().getEnrollFaceMFA();
            } else {
                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400, null,null));
                return;
            }

            Map<String, String> headers = new Hashtable<>();


            HashMap<String, RequestBody> faceSetupMap = new HashMap<>();


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
            if (DBHelper.getShared().getFCMToken() != null && DBHelper.getShared().getFCMToken() != "") {

                //Todo Chaange to FCM acceptence now it is in Authenticator
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());

                //deviceInfoEntity.setPushNotificationId("cegfVcqD6xU:APA91bF1UddwL6AoXUwI5g1s9DRKOkz6KEQz6zbcYRHHrcO34tXkQ8ILe4m38jTuT_MuqIvqC9Z0lZjxvAbGtakhUnCN6sHSbWWr0W10sAM436BCU8-jlEEAB8a_BMPzxGOEDBZIrMWTkdHxtIn_VGxBiOPYia7Zbw");
            }
            enrollFaceMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            // headers.put("Content-Type", URLHelper.contentType);
            headers.put("access_token", accessToken);
            headers.put("verification_api_version","2");

            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            faceSetupMap.put("statusId", StringtoRequestBody(enrollFaceMFARequestEntity.getStatusId()));
            //faceSetupMap.put("",enrollFaceMFARequestEntity.getSub());
            faceSetupMap.put("userDeviceId", StringtoRequestBody(enrollFaceMFARequestEntity.getUserDeviceId()));
            faceSetupMap.put("deviceId", StringtoRequestBody(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceId()));
            faceSetupMap.put("deviceMake", StringtoRequestBody(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceMake()));
            faceSetupMap.put("deviceModel", StringtoRequestBody(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceModel()));
            faceSetupMap.put("deviceVersion", StringtoRequestBody(enrollFaceMFARequestEntity.getDeviceInfo().getDeviceVersion()));
            faceSetupMap.put("pushNotificationId", StringtoRequestBody(enrollFaceMFARequestEntity.getDeviceInfo().getPushNotificationId()));







            Bitmap finalimg = BitmapFactory.decodeFile(enrollFaceMFARequestEntity.getImagetoSend().getAbsolutePath());

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), enrollFaceMFARequestEntity.getImagetoSend());
            MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", "cidaas.png", requestFile);

            if (enrollFaceMFARequestEntity.getImagetoSend().exists())
            {
                // Toast.makeText(context, "FIle found", Toast.LENGTH_SHORT).show();
            }



            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.enrollFaceMFA(enrollFaceMFAUrl,headers,photo,faceSetupMap).enqueue(new Callback<EnrollFaceMFAResponseEntity>() {
                @Override
                public void onResponse(Call<EnrollFaceMFAResponseEntity> call, Response<EnrollFaceMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
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

                            //Todo Handle Access Token Failure Error
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
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,e.getMessage(), 400,null,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<EnrollFaceMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    LogFile.addRecordToLog("acceptConsent Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ENROLL_FACE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("acceptConsent Service exception"+e.getMessage());
        }
    }

    //initiateFaceMFA
    public void initiateFace(String baseurl, InitiateFaceMFARequestEntity initiateFaceMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                                final Result<InitiateFaceMFAResponseEntity> callback){
        String initiateFaceMFAUrl="";
        try
        {
            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                initiateFaceMFAUrl=baseurl+URLHelper.getShared().getInitiateFaceMFA();
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
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            initiateFaceMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateFaceMFA(initiateFaceMFAUrl,headers, initiateFaceMFARequestEntity).enqueue(new Callback<InitiateFaceMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiateFaceMFAResponseEntity> call, Response<InitiateFaceMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_FACE_MFA_FAILURE,
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
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_FACE_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_FACE_MFA_FAILURE,e.getMessage(), 400,null,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<InitiateFaceMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in InitiateSMSMFAResponseEntityservice call"+t.getMessage());
                    LogFile.addRecordToLog("InitiateFaceMFAResponseEntity Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_FACE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("InitiateFaceMFAResponseEntity Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("InitiateFaceMFAResponseEntity Service exception"+e.getMessage());
        }
    }


    //authenticateFaceMFA
    public void authenticateFace(String baseurl, AuthenticateFaceRequestEntity authenticateFaceRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                                    final Result<AuthenticateFaceResponseEntity> callback){
        String authenticateFaceMFAUrl="";
        try
        {
            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                authenticateFaceMFAUrl=baseurl+URLHelper.getShared().getAuthenticateFaceMFA();
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
          //  headers.put("Content-Type", URLHelper.contentTypeJson);
             headers.put("verification_api_version","2");

            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            HashMap<String, RequestBody> faceSetupMap = new HashMap<>();

            authenticateFaceRequestEntity.setDeviceInfo(deviceInfoEntity);


            faceSetupMap.put("statusId", StringtoRequestBody(authenticateFaceRequestEntity.getStatusId()));
            //faceSetupMap.put("",enrollFaceMFARequestEntity.getSub());
            faceSetupMap.put("usage_pass",StringtoRequestBody(authenticateFaceRequestEntity.getUsage_pass()));
            faceSetupMap.put("userDeviceId", StringtoRequestBody(authenticateFaceRequestEntity.getUserDeviceId()));
            faceSetupMap.put("deviceId", StringtoRequestBody(authenticateFaceRequestEntity.getDeviceInfo().getDeviceId()));
            faceSetupMap.put("deviceMake", StringtoRequestBody(authenticateFaceRequestEntity.getDeviceInfo().getDeviceMake()));
            faceSetupMap.put("deviceModel", StringtoRequestBody(authenticateFaceRequestEntity.getDeviceInfo().getDeviceModel()));
            faceSetupMap.put("deviceVersion", StringtoRequestBody(authenticateFaceRequestEntity.getDeviceInfo().getDeviceVersion()));
            faceSetupMap.put("pushNotificationId", StringtoRequestBody(authenticateFaceRequestEntity.getDeviceInfo().getPushNotificationId()));

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            if(authenticateFaceRequestEntity.getImagetoSend() != null) {

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), authenticateFaceRequestEntity.getImagetoSend());
                MultipartBody.Part photo = MultipartBody.Part.createFormData("photo", "cidaas_face.jpg", requestFile);
                cidaasSDKService.authenticateFaceMFA(authenticateFaceMFAUrl, headers, photo, faceSetupMap).enqueue(new Callback<AuthenticateFaceResponseEntity>() {
                    @Override
                    public void onResponse(Call<AuthenticateFaceResponseEntity> call, Response<AuthenticateFaceResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                callback.success(response.body());
                            } else {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
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
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
                                        errorMessage, commonErrorEntity.getStatus(),
                                        commonErrorEntity.getError(), errorEntity));

                            } catch (Exception e) {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE, e.getMessage(), 400, null, null));
                                Timber.e("response" + response.message() + e.getMessage());
                            }
                            Timber.e("response" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticateFaceResponseEntity> call, Throwable t) {
                        Timber.e("Failure in AuthenticateFaceResponseEntity service call" + t.getMessage());
                        LogFile.addRecordToLog("AuthenticateFaceResponseEntity Service Failure" + t.getMessage());
                        callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE, t.getMessage(), 400, null, null));
                    }
                });
            }
            else
            {

                cidaasSDKService.authenticateFaceWithoutPhotoMFA(authenticateFaceMFAUrl, headers,  faceSetupMap).enqueue(new Callback<AuthenticateFaceResponseEntity>() {
                    @Override
                    public void onResponse(Call<AuthenticateFaceResponseEntity> call, Response<AuthenticateFaceResponseEntity> response) {
                        if (response.isSuccessful()) {
                            if (response.code() == 200) {
                                callback.success(response.body());
                            } else {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
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
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
                                        errorMessage, commonErrorEntity.getStatus(),
                                        commonErrorEntity.getError(), errorEntity));

                            } catch (Exception e) {
                                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE, e.getMessage(), 400, null, null));
                                Timber.e("response" + response.message() + e.getMessage());
                            }
                            Timber.e("response" + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenticateFaceResponseEntity> call, Throwable t) {
                        Timber.e("Failure in AuthenticateFaceResponseEntity service call" + t.getMessage());
                        LogFile.addRecordToLog("AuthenticateFaceResponseEntity Service Failure" + t.getMessage());
                        callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE, t.getMessage(), 400, null, null));
                    }
                });
            }


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateFaceMFA Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("authenticateFaceMFA Service exception"+e.getMessage());
        }
    }



    //authenticateFaceMFA
    public void authenticateFaces(String baseurl, AuthenticateFaceRequestEntity authenticateFaceRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam,
                                 final Result<AuthenticateFaceResponseEntity> callback){
        String authenticateFaceMFAUrl="";
        try
        {
            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                authenticateFaceMFAUrl=baseurl+URLHelper.getShared().getAuthenticateFaceMFA();
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
            //  headers.put("Content-Type", URLHelper.contentTypeJson);
             headers.put("verification_api_version","2");

            if(DBHelper.getShared().getFCMToken()!=null && DBHelper.getShared().getFCMToken()!="") {
                deviceInfoEntity.setPushNotificationId(DBHelper.getShared().getFCMToken());
            }

            HashMap<String, RequestBody> faceSetupMap = new HashMap<>();

            authenticateFaceRequestEntity.setDeviceInfo(deviceInfoEntity);


            faceSetupMap.put("statusId", StringtoRequestBody(authenticateFaceRequestEntity.getStatusId()));
            //faceSetupMap.put("",enrollFaceMFARequestEntity.getSub());
            faceSetupMap.put("userDeviceId", StringtoRequestBody(authenticateFaceRequestEntity.getUserDeviceId()));
            faceSetupMap.put("deviceId", StringtoRequestBody(authenticateFaceRequestEntity.getDeviceInfo().getDeviceId()));
            faceSetupMap.put("deviceMake", StringtoRequestBody(authenticateFaceRequestEntity.getDeviceInfo().getDeviceMake()));
            faceSetupMap.put("deviceModel", StringtoRequestBody(authenticateFaceRequestEntity.getDeviceInfo().getDeviceModel()));
            faceSetupMap.put("deviceVersion", StringtoRequestBody(authenticateFaceRequestEntity.getDeviceInfo().getDeviceVersion()));
            faceSetupMap.put("pushNotificationId", StringtoRequestBody(authenticateFaceRequestEntity.getDeviceInfo().getPushNotificationId()));



            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), authenticateFaceRequestEntity.getImagetoSend());
            MultipartBody.Part photo = MultipartBody.Part.createFormData("photo","cidaas_face.jpg",requestFile);




            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticateFaceMFA(authenticateFaceMFAUrl,headers,photo, faceSetupMap).enqueue(new Callback<AuthenticateFaceResponseEntity>() {
                @Override
                public void onResponse(Call<AuthenticateFaceResponseEntity> call, Response<AuthenticateFaceResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
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
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,e.getMessage(), 400,null,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateFaceResponseEntity> call, Throwable t) {
                    Timber.e("Failure in AuthenticateFaceResponseEntity service call"+t.getMessage());
                    LogFile.addRecordToLog("AuthenticateFaceResponseEntity Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_FACE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("authenticateFaceMFA Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
            Timber.e("authenticateFaceMFA Service exception"+e.getMessage());
        }
    }

}
