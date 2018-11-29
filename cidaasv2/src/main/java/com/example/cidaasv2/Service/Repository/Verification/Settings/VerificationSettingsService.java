package com.example.cidaasv2.Service.Repository.Verification.Settings;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.URLHelper;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.NotificationEntity.DenyNotification.DenyNotificationRequestEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.DenyNotification.DenyNotificationResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.DeleteMFA.DeleteMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.NotificationEntity;
import com.example.cidaasv2.Service.Entity.UserList.ConfiguredMFAListEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.LinkedHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class VerificationSettingsService {

    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;

    private String verificationType;
    private Context context;

    public static VerificationSettingsService shared;

    public  VerificationSettingsService(Context contextFromCidaas) {
        context=contextFromCidaas;


        if(service==null) {
            service=new CidaassdkService();
        }

        //Todo setValue for authenticationType

    }

    String codeVerifier, codeChallenge;
    // Generate Code Challenge and Code verifier
    private void generateChallenge(){
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge= generator.getCodeChallenge(codeVerifier);

    }

    public static  VerificationSettingsService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  VerificationSettingsService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void getmfaList( String baseurl,String sub,String userDeviceID,DeviceInfoEntity deviceInfoEntityFromParam, final Result<MFAListResponseEntity> callback)
    {
        //Local Variables
        String mfalistUrl = "";
        try{

            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                if(sub!=null && sub!=""){
                    //Construct URL For RequestId

                    //mfalistUrl=baseurl+ URLHelper.getShared().getMfa_URL();
                   //TODO change to getMfaList();
                     mfalistUrl=baseurl+ URLHelper.getShared().getMfaList();
                }
                else {
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,context.getString(R.string.MFA_LIST_FAILURE),
                            400,null,null));
                    return;
                }
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,context.getString(R.string.PROPERTY_MISSING),
                        400,null,null));
                return;
            }


            boolean common_configs = true;
            if(userDeviceID.equals("")) {

                DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
                //This is only for testing purpose
                if(deviceInfoEntityFromParam==null) {
                    deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                }
                else if(deviceInfoEntityFromParam!=null)
                {
                    deviceInfoEntity=deviceInfoEntityFromParam;
                }

                userDeviceID=deviceInfoEntity.getDeviceId();
            }
            else {

            }

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getmfaList(mfalistUrl,sub,userDeviceID,common_configs).enqueue(new Callback<MFAListResponseEntity>() {
                @Override
                public void onResponse(Call<MFAListResponseEntity> call, Response<MFAListResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,
                                    "Service failure but successful response" ,response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();

                            CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }



                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,errorMessage,
                                    commonErrorEntity.getStatus(),commonErrorEntity.getError(),errorEntity));
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.MFA_LIST_FAILURE,
                                    "Delete All Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<MFAListResponseEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,t.getMessage(), 400,null,null));

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());



            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }



    public void deleteMFA(String baseurl,  @NonNull final String accessToken,String userDeviceID, String verificationType, DeviceInfoEntity deviceInfoEntityFromParam, final Result<DeleteMFAResponseEntity> callback)
    {
        //Local Variables
        String deleteMFAURL = "";
        try{

            if(baseurl!=null && baseurl!=""){

                //Construct URL For RequestId
                if(userDeviceID!=null && userDeviceID!=""){

                    //Delete MFA
                    deleteMFAURL=baseurl+ URLHelper.getShared().getDeleteMFA(userDeviceID,verificationType);
                }
                else {
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DELETE_MFA_FAILURE,context.getString(R.string.USER_DEVICE_ID_FAILURE),
                            400,null,null));
                    return;
                }
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DELETE_MFA_FAILURE,context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        400,null,null));
                return;
            }


            if(userDeviceID.equals("")) {

                DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
                //This is only for testing purpose
                if(deviceInfoEntityFromParam==null) {
                    deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                }
                else if(deviceInfoEntityFromParam!=null)
                {
                    deviceInfoEntity=deviceInfoEntityFromParam;
                }

                userDeviceID=deviceInfoEntity.getDeviceId();
            }
            else {

            }

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.delete(deleteMFAURL,accessToken).enqueue(new Callback<DeleteMFAResponseEntity>() {
                @Override
                public void onResponse(Call<DeleteMFAResponseEntity> call, Response<DeleteMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DELETE_MFA_FAILURE,
                                    "Service failure but successful response" ,response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();

                            CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }



                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,errorMessage,
                                    commonErrorEntity.getStatus(),commonErrorEntity.getError(),errorEntity));
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.DELETE_MFA_FAILURE,
                                    "Delete  Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<DeleteMFAResponseEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,t.getMessage(), 400,null,null));

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());



            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    //Service call to Delete All MFA

    public void deleteAllMFA(String baseurl,@NonNull final String accessToken,String userDeviceID,DeviceInfoEntity deviceInfoEntityFromParam, final Result<DeleteMFAResponseEntity> callback)
    {
        //Local Variables
        String deleteMFAURL = "";
        try{

            if(baseurl!=null && baseurl!=""){

                //Construct URL For RequestId
                if(userDeviceID!=null && userDeviceID!=""){

                    //Delete MFA
                    deleteMFAURL=baseurl+ URLHelper.getShared().getDeleteAllMFA()+userDeviceID;
                }
                else {
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DELETE_MFA_FAILURE,context.getString(R.string.USER_DEVICE_ID_FAILURE),
                            400,null,null));
                    return;
                }
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DELETE_MFA_FAILURE,context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        400,null,null));
                return;
            }


            if(userDeviceID.equals("")) {

                DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
                //This is only for testing purpose
                if(deviceInfoEntityFromParam==null) {
                    deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
                }
                else if(deviceInfoEntityFromParam!=null)
                {
                    deviceInfoEntity=deviceInfoEntityFromParam;
                }

                userDeviceID=deviceInfoEntity.getDeviceId();
            }
            else {

            }

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.deleteAll(deleteMFAURL,accessToken).enqueue(new Callback<DeleteMFAResponseEntity>() {
                @Override
                public void onResponse(Call<DeleteMFAResponseEntity> call, Response<DeleteMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DELETE_MFA_FAILURE,
                                    "Service failure but successful response" ,response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();

                            CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }



                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DELETE_MFA_FAILURE,errorMessage,
                                    commonErrorEntity.getStatus(),commonErrorEntity.getError(),errorEntity));
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.DELETE_MFA_FAILURE,
                                    "Delete All Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<DeleteMFAResponseEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.MFA_LIST_FAILURE,t.getMessage(), 400,null,null));

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }


    //Service call to deny notification

    public void denyNotification(String baseurl, @NonNull final String accessToken, DenyNotificationRequestEntity denyNotificationRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<DenyNotificationResponseEntity> callback)
    {
        //Local Variables
        String denyNotificationURL = "";
        try{

            if(baseurl!=null && baseurl!="") {

                //Construct URL For
                //Deny Notification MFA
                denyNotificationURL = baseurl + URLHelper.getShared().getDenyNotification();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DENY_NOTIFICATION,context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        400,null,null));
                return;
            }


            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.denyNotificationService(denyNotificationURL,URLHelper.contentTypeJson,accessToken,denyNotificationRequestEntity).enqueue(new Callback<DenyNotificationResponseEntity>() {
                @Override
                public void onResponse(Call<DenyNotificationResponseEntity> call, Response<DenyNotificationResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DENY_NOTIFICATION,
                                    "Service failure but successful response" ,response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();

                            CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }



                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DENY_NOTIFICATION,errorMessage,
                                    commonErrorEntity.getStatus(),commonErrorEntity.getError(),errorEntity));
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.DENY_NOTIFICATION,
                                    "Deny Notification Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<DenyNotificationResponseEntity> call, Throwable t) {
                    Timber.e("Faliure in Deny notification service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DENY_NOTIFICATION,t.getMessage(), 400,null,null));

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }


    //Service call to get pending notification

    public void getPendingNotification(String baseurl, @NonNull final String accessToken, @NonNull final String userDeviceId,DeviceInfoEntity deviceInfoEntityFromParam, final Result<NotificationEntity> callback)
    {
        //Local Variables
        String getPendingNotificationURL = "";
        try{

            if(baseurl!=null && baseurl!="") {

                //Construct URL For
                //Deny Notification MFA
                getPendingNotificationURL = baseurl + URLHelper.getShared().getPendingNotificationURL()+userDeviceId;
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        400,null,null));
                return;
            }


            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getPendingNotification(getPendingNotificationURL,URLHelper.contentTypeJson,accessToken).enqueue(new Callback<NotificationEntity>()
            {
                @Override
                public void onResponse(Call<NotificationEntity> call, Response<NotificationEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                                    "Service failure but successful response" ,response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();

                            CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }



                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,errorMessage,
                                    commonErrorEntity.getStatus(),commonErrorEntity.getError(),errorEntity));
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                                    "Deny Notification Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<NotificationEntity> call, Throwable t) {
                    Timber.e("Faliure in Deny notification service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.DENY_NOTIFICATION,t.getMessage(), 400,null,null));

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }



    //Service call to get Configured MFA List

    public void getConfiguredMFAList(String baseurl, @NonNull final String sub, @NonNull final String userDeviceId,DeviceInfoEntity deviceInfoEntityFromParam, final Result<ConfiguredMFAListEntity> callback)
    {
        //Local Variables
        String ConfiguredMFAListURL = "";
        try{

            if(baseurl!=null && baseurl!="") {

                //Construct URL For
                //Deny Notification MFA
                ConfiguredMFAListURL = baseurl + URLHelper.getShared().getConfiguredMFAListURL();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE,context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        400,null,null));
                return;
            }


            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getConfiguredMFAList(ConfiguredMFAListURL,sub,userDeviceId).enqueue(new Callback<ConfiguredMFAListEntity>()
            {
                @Override
                public void onResponse(Call<ConfiguredMFAListEntity> call, Response<ConfiguredMFAListEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE,
                                    "Service failure but successful response" ,response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();

                            CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }



                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE,errorMessage,
                                    commonErrorEntity.getStatus(),commonErrorEntity.getError(),errorEntity));
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE,
                                    "Deny Notification Exception:"+ e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ConfiguredMFAListEntity> call, Throwable t) {
                    Timber.e("Faliure in Deny notification service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE,t.getMessage(), 400,null,null));

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }


}
