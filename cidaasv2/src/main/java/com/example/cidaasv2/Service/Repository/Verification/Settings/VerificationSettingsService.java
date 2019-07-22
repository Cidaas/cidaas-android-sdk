package com.example.cidaasv2.Service.Repository.Verification.Settings;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.MFA.DeleteMFA.DeleteMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.MFAList.MFAListResponseEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.DenyNotification.DenyNotificationRequestEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.DenyNotification.DenyNotificationResponseEntity;
import com.example.cidaasv2.Service.Entity.NotificationEntity.GetPendingNotification.NotificationEntity;
import com.example.cidaasv2.Service.Entity.UserList.ConfiguredMFAListEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

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
            service=new CidaassdkService(context);
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

    //----------------------------------------------------------GetMFAList-----------------------------------------------------------------------
    public void getmfaList( String baseurl,String sub,String userDeviceID, final Result<MFAListResponseEntity> callback)
    {
        //Local Variables
        String methodName = "VerificationSettingsService :getmfaList()";
        try{

            if(baseurl!=null && !baseurl.equals("") && sub!=null && !sub.equals(""))
            {

               String mfalistUrl=baseurl+ URLHelper.getShared().getMfaList();

               if(userDeviceID.equals(""))
               {

                DeviceInfoEntity deviceInfoEntity=DBHelper.getShared().getDeviceInfo();
                userDeviceID=deviceInfoEntity.getDeviceId();
               }
               else
               {

               }

               Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,null);
               serviceForGetMFAList(mfalistUrl, sub, userDeviceID, headers, callback);


            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Sub or Baseurl must not be empty","Error :"+methodName));
                return;
            }
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.MFA_LIST_FAILURE,e.getMessage()));
        }
    }

    public void serviceForGetMFAList(String mfalistUrl, String sub, String userDeviceID, Map<String, String> headers,
                                     final Result<MFAListResponseEntity> callback)
    {
        final String methodName = "VerificationSettingsService :serviceForGetMFAList()";
        boolean common_configs = true;
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getmfaList(mfalistUrl, headers, sub, userDeviceID, common_configs).enqueue(new Callback<MFAListResponseEntity>() {
                @Override
                public void onResponse(Call<MFAListResponseEntity> call, Response<MFAListResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else if (response.code() == 204) {
                            MFAListResponseEntity mfaListResponseEntity = new MFAListResponseEntity();
                            mfaListResponseEntity.setStatus(response.code());
                            mfaListResponseEntity.setSuccess(response.isSuccessful());
                            callback.success(mfaListResponseEntity);
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.MFA_LIST_FAILURE,
                                    response.code(), "Error :" + methodName));
                        }
                    } else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.MFA_LIST_FAILURE, response,
                                "Error :" + methodName));
                    }
                }

                @Override
                public void onFailure(Call<MFAListResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.MFA_LIST_FAILURE, t.getMessage(),
                            "Error :" + methodName));

                }
            });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception:"+methodName,WebAuthErrorCode.MFA_LIST_FAILURE,e.getMessage()));
        }
    }

    //----------------------------------------------------------deleteMFA-----------------------------------------------------------------------
    public void deleteMFA(String baseurl, @NonNull final String accessToken, String userDeviceID, String verificationType,
                          final Result<DeleteMFAResponseEntity> callback)
    {
        //Local Variables
        String methodName = "VerificationSettingsService :deleteMFA()";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                String deleteMFAURL="";
                if(userDeviceID.equals("") && userDeviceID==null) {

                DeviceInfoEntity deviceInfoEntity=DBHelper.getShared().getDeviceInfo();
                userDeviceID=deviceInfoEntity.getDeviceId();

                }
                //Delete MFA
                deleteMFAURL =baseurl+ URLHelper.getShared().getDeleteMFA(userDeviceID,verificationType);

                Map<String, String> headers = Headers.getShared(context).getHeaders(accessToken,false,null);
                serviceForDeleteMFA(accessToken, deleteMFAURL, headers, callback);

            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.USER_DEVICE_ID_FAILURE)+
                                context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :"+methodName));
                return;
            }
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.DELETE_MFA_FAILURE,e.getMessage()));
        }
    }

    public void serviceForDeleteMFA(@NonNull String accessToken, String deleteMFAURL, Map<String, String> headers,
                                    final Result<DeleteMFAResponseEntity> callback)
    {
        final String methodName="VerificationSettingsService :deleteMFA()";
     try {
         //Call Service-deleteMFA
         ICidaasSDKService cidaasSDKService = service.getInstance();
         cidaasSDKService.delete(deleteMFAURL, headers, accessToken).enqueue(new Callback<DeleteMFAResponseEntity>() {
             @Override
             public void onResponse(Call<DeleteMFAResponseEntity> call, Response<DeleteMFAResponseEntity> response) {
                 if (response.isSuccessful()) {
                     if (response.code() == 200) {
                         callback.success(response.body());
                     } else {
                         callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.DELETE_MFA_FAILURE, response.code(),
                                 "Error :"+methodName));
                     }
                 }
                 else {

                     callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.DELETE_MFA_FAILURE, response
                             , "Error :"+methodName));
                 }
             }

             @Override
             public void onFailure(Call<DeleteMFAResponseEntity> call, Throwable t) {
              callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.DELETE_MFA_FAILURE, t.getMessage(),
                      "Error :"+methodName));

             }
         });
     }
     catch (Exception e)
     {
         callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.DELETE_MFA_FAILURE,e.getMessage()));
     }
    }

    //------------------------------------------------------Service call to Delete All MFA-------------------------------------------------------

    public void deleteAllMFA(String baseurl,@NonNull final String accessToken,String userDeviceID,DeviceInfoEntity deviceInfoEntityFromParam,
                             final Result<DeleteMFAResponseEntity> callback)
    {
        //Local Variables
        String methodName = "VerificationSettingsService :deleteAllMFA()";
        try{

            if(baseurl!=null && !baseurl.equals("")){

            if(userDeviceID.equals("")) {

                DeviceInfoEntity deviceInfoEntity=DBHelper.getShared().getDeviceInfo();

                userDeviceID=deviceInfoEntity.getDeviceId();
            }
                //Delete MFA
                String deleteMFAURL=baseurl+ URLHelper.getShared().getDeleteAllMFA()+userDeviceID;
                Map<String, String> headers = Headers.getShared(context).getHeaders(accessToken,false,null);
                serviceForDeleteAllMFA(deleteMFAURL, accessToken, headers, callback);

            }
            else {
              callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :"+methodName));
                return;
            }
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.DELETE_MFA_FAILURE,e.getMessage()));
        }
    }

    public void serviceForDeleteAllMFA(String deleteMFAURL, @NonNull String accessToken,Map<String, String> headers,
                                       final Result<DeleteMFAResponseEntity> callback)
    {
        final String methodName="";
        //Call Service-getRequestId
        ICidaasSDKService cidaasSDKService = service.getInstance();
        cidaasSDKService.deleteAll(deleteMFAURL,headers,accessToken).enqueue(new Callback<DeleteMFAResponseEntity>() {
            @Override
            public void onResponse(Call<DeleteMFAResponseEntity> call, Response<DeleteMFAResponseEntity> response) {
                if (response.isSuccessful()) {
                    if(response.code()==200) {
                        callback.success(response.body());
                    }
                    else {
                        callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.DELETE_MFA_FAILURE, response.code(),
                                "Error :"+methodName));
                    }
                }
                else {
                    assert response.errorBody() != null;
                    callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.DELETE_MFA_FAILURE, response,
                            "Error :"+methodName));
                }
            }

            @Override
            public void onFailure(Call<DeleteMFAResponseEntity> call, Throwable t) {
                callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.DELETE_MFA_FAILURE,t.getMessage(),
                        "Error :"+methodName));

            }
        });
    }


    //-------------------------------------------------------Service call to deny notification-------------------------------------------------

    public void denyNotification(String baseurl, @NonNull final String accessToken, DenyNotificationRequestEntity denyNotificationRequestEntity,
                                  final Result<DenyNotificationResponseEntity> callback)
    {
        //Local Variables
        String methodName = "VerificationSettingsService :denyNotification()";
        try{

            if(baseurl!=null && !baseurl.equals("")) {

                String denyNotificationURL = baseurl + URLHelper.getShared().getDenyNotification();

                Map<String, String> headers = Headers.getShared(context).getHeaders(accessToken, false, null);
                serviceForDenyNotification(accessToken, denyNotificationURL, denyNotificationRequestEntity, headers, callback);
            }
            else
            {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Exception :" + methodName));
                return;
            }
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.DENY_NOTIFICATION,e.getMessage()));
        }
    }

    public void serviceForDenyNotification(@NonNull String accessToken,String denyNotificationURL,DenyNotificationRequestEntity denyNotificationRequestEntity
                                    , Map<String, String> headers, final Result<DenyNotificationResponseEntity> callback)
    {
        final String methodName="VerificationSettingsService :denyNotification()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.denyNotificationService(denyNotificationURL, URLHelper.contentTypeJson, accessToken, headers, denyNotificationRequestEntity).
                    enqueue(new Callback<DenyNotificationResponseEntity>() {
                        @Override
                        public void onResponse(Call<DenyNotificationResponseEntity> call, Response<DenyNotificationResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200 || response.code() == 204) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.DENY_NOTIFICATION,
                                            response.code(), "Error:"+methodName));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.DENY_NOTIFICATION, response,
                                        "Error:"+methodName));
                            }
                        }


                        @Override
                        public void onFailure(Call<DenyNotificationResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.DENY_NOTIFICATION, t.getMessage(),
                                    "Error :"+methodName));

                        }
                    });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.DENY_NOTIFICATION,e.getMessage()));
        }
    }


    //------------------------------------------------------Service call to get pending notification-------------------------------------------

    public void getPendingNotification(String baseurl,@NonNull final String accessToken,@NonNull final String userDeviceId,
                                       final Result<NotificationEntity> callback)
    {
        //Local Variables
        String methodName = "VerificationSettingsService :getPendingNotification()";
        try{

            if(baseurl!=null && !baseurl.equals("") ) {

                //URL
                String getPendingNotificationURL = baseurl + URLHelper.getShared().getPendingNotificationURL()+userDeviceId;

                //Header generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,null);

                //Service For getPending Notification
                serviceForGetPendingNotification(accessToken, getPendingNotificationURL, headers, callback);

            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE)
                        ,"Error :"+methodName));
                return;
            }
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                    e.getMessage()));
        }
    }

    public void serviceForGetPendingNotification(@NonNull String accessToken, String getPendingNotificationURL, Map<String, String> headers,
                                                 final Result<NotificationEntity> callback)
    {
        //Local Variables
        final String methodName = "VerificationSettingsService :serviceForGetPendingNotification()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getPendingNotification(getPendingNotificationURL, URLHelper.contentTypeJson, accessToken, headers).
                    enqueue(new Callback<NotificationEntity>() {
                        @Override
                        public void onResponse(Call<NotificationEntity> call, Response<NotificationEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else if (response.code() == 204) {
                                    NotificationEntity notificationEntity = new NotificationEntity();

                                    notificationEntity.setStatus(response.code());
                                    notificationEntity.setSuccess(response.isSuccessful());
                                    callback.success(notificationEntity);
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                                            response.code(), "Error :" + methodName));
                                }
                            } else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                                        response, "Error :" + methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<NotificationEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                                    t.getMessage(), "Error :" + methodName));

                        }
                    });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //-------------------------------------------------------Service call to get pending notification-------------------------------------------

    public void updateFCMToken(String baseurl, @NonNull final String accessToken, @NonNull final String FCMToken, final Result<Object> callback)
    {
        //Local Variables
        String methodName = "VerificationSettingsService :updateFCMToken()";
        try{

            if(baseurl!=null && !baseurl.equals("") && DBHelper.getShared().getFCMToken()!=null && !DBHelper.getShared().getFCMToken().equals("")) {

                //Deny Notification MFA
               String fcmTokenURL = baseurl + URLHelper.getShared().getUpdateFCMTokenURL();

               //DeviceInfoEntity
               DeviceInfoEntity deviceInfoEntity=DBHelper.getShared().getDeviceInfo();
               deviceInfoEntity.setPushNotificationId(FCMToken);

               //Headers Generation
               Map<String, String> headers =Headers.getShared(context).getHeaders(accessToken,false,null);
               serviceForUpdateFCMToken(accessToken, fcmTokenURL, headers, deviceInfoEntity, callback);

            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Error :"+methodName));
                return;
            }
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.DENY_NOTIFICATION,e.getMessage()));
        }
    }

    public void serviceForUpdateFCMToken(@NonNull String accessToken, String fcmTokenURL, Map<String, String> headers, DeviceInfoEntity deviceInfoEntity,
                                         final Result<Object> callback)
    {
        final String methodName="VerificationSettingsService :updateFCMToken()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.updateFCMToken(fcmTokenURL, accessToken, headers, deviceInfoEntity).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.UPDATE_FCM_TOKEN,
                                   response.code(), "Error :"+methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.DENY_NOTIFICATION, response
                                , "Error :"+methodName));
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.DENY_NOTIFICATION, t.getMessage(),
                            "Error :"+methodName));

                }
            });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.DENY_NOTIFICATION,e.getMessage()));
        }
    }


    //--------------------------------------------------------Service call to get Configured MFA List---------------------------------------

    public void getConfiguredMFAList(String baseurl,@NonNull final String sub,@NonNull final String userDeviceId,
                                     final Result<ConfiguredMFAListEntity> callback)
    {
        //Local Variables
        String methodName = "VerificationSettingsService :getConfiguredMFAList()";
        try{

            if(baseurl!=null && !baseurl.equals("")) {

              String  ConfiguredMFAListURL = baseurl + URLHelper.getShared().getConfiguredMFAListURL();

              Map<String, String> headers = Headers.getShared(context).getHeaders(null,false,null);
                serviceForGetConfiguredList(sub, userDeviceId, ConfiguredMFAListURL, headers, callback);

            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException("Baseurl must not be empty", "Error :"+methodName));
                return;
            }
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE,
                    e.getMessage()));
        }
    }

    public void serviceForGetConfiguredList(@NonNull String sub, @NonNull String userDeviceId, String configuredMFAListURL, Map<String, String> headers,
                                            final Result<ConfiguredMFAListEntity> callback) {
        final String methodName = "VerificationSettingsService :getConfiguredMFAList()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            final String Sendedurl = "ConfiguredMFAListURL :" + configuredMFAListURL + " sub:-" + sub + "  userDeviceId:-" + userDeviceId;

            cidaasSDKService.getConfiguredMFAList(configuredMFAListURL, headers, sub, userDeviceId).enqueue(new Callback<ConfiguredMFAListEntity>() {
                @Override
                public void onResponse(Call<ConfiguredMFAListEntity> call, Response<ConfiguredMFAListEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else if (response.code() == 204) {
                            ConfiguredMFAListEntity configuredMFAListEntity = new ConfiguredMFAListEntity();

                            configuredMFAListEntity.setStatus(response.code());
                            configuredMFAListEntity.setSuccess(response.isSuccessful());
                            configuredMFAListEntity.setSendedURL(Sendedurl);

                            LogFile.getShared(context).addFailureLog(Sendedurl);

                            callback.success(configuredMFAListEntity);

                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE,
                                   response.code(), "Error :"+methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE, response
                                , "Error :"+methodName));
                    }
                }

                @Override
                public void onFailure(Call<ConfiguredMFAListEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE, t.getMessage(),
                             "Error :"+methodName));

                }
            });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.CONFIGURED_LIST_MFA_FAILURE,
                    e.getMessage()));
        }
    }



}
