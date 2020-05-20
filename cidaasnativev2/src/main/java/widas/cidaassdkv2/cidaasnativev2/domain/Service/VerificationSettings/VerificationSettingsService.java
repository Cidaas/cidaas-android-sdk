/*
package widas.cidaassdkv2.cidaasnativev2.domain.Service.VerificationSettings;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.NotificationEntity.DenyNotification.DenyNotificationRequestEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

import widas.cidaassdkv2.cidaasnativev2.data.Service.CidaasNativeService;
import widas.cidaassdkv2.cidaasnativev2.data.Service.ICidaasNativeService;

public class VerificationSettingsService {

    CidaasNativeService service;
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
            service=new CidaasNativeService(context);
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
           // Timber.i(e.getMessage());
        }
        return shared;
    }

    //----------------------------------------------------------deleteMFA-----------------------------------------------------------------------

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
            ICidaasNativeService cidaasNativeService = service.getInstance();
            cidaasNativeService.denyNotificationService(denyNotificationURL, URLHelper.contentTypeJson, accessToken, headers, denyNotificationRequestEntity).
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
            ICidaasNativeService cidaasNativeService = service.getInstance();
            cidaasNativeService.getPendingNotification(getPendingNotificationURL, URLHelper.contentTypeJson, accessToken, headers).
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
            ICidaasNativeService cidaasNativeService = service.getInstance();
            cidaasNativeService.updateFCMToken(fcmTokenURL, accessToken, headers, deviceInfoEntity).enqueue(new Callback<Object>() {
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
            ICidaasNativeService cidaasNativeService = service.getInstance();
            final String Sendedurl = "ConfiguredMFAListURL :" + configuredMFAListURL + " sub:-" + sub + "  userDeviceId:-" + userDeviceId;

            cidaasNativeService.getConfiguredMFAList(configuredMFAListURL, headers, sub, userDeviceId).enqueue(new Callback<ConfiguredMFAListEntity>() {
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



}*/
