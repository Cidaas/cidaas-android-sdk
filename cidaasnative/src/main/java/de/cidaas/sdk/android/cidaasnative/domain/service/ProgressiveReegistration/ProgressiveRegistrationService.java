package de.cidaas.sdk.android.cidaasnative.domain.service.ProgressiveReegistration;

import android.content.Context;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Map;

import de.cidaas.sdk.android.cidaasnative.R;
import de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration.ProgressiveRegistrationResponseDataEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration.ProgressiveRegistrationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration.ProgressiveUpdateRequestEntity;
import de.cidaas.sdk.android.cidaasnative.data.entity.progressiveregistration.ProgressiveUpdateResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.service.CidaasNativeService;
import de.cidaas.sdk.android.cidaasnative.data.service.ICidaasNativeService;
import de.cidaas.sdk.android.cidaasnative.data.service.helper.NativeURLHelper;
import de.cidaas.sdk.android.cidaasnative.util.NativeConstants;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ProgressiveRegistrationService {
        private Context context;

        public static ProgressiveRegistrationService shared;

    CidaasNativeService service;
        public ObjectMapper objectMapper = new ObjectMapper();

        public ProgressiveRegistrationService(Context contextFromCidaas) {
            if (service == null) {
                service = new CidaasNativeService(contextFromCidaas);
            }

            context = contextFromCidaas;

        }

        public static ProgressiveRegistrationService getShared(Context contextFromCidaas) {
            try {

                if (shared == null) {
                    shared = new ProgressiveRegistrationService(contextFromCidaas);
                }
            } catch (Exception e) {
                Timber.i(e.getMessage());
            }
            return shared;
        }

        public void preLoginCheck(@NonNull final String baseurl,@NonNull String trackId, @NonNull EventResult<ProgressiveRegistrationResponseEntity> callback){
            String methodName = "ProgressiveRegistrationService :preLoginCheck()";
            try{
                if (baseurl != null && !baseurl.equals("") && trackId!=null && !trackId.equals("")) {
                    //Construct URL For RequestId
                    String preLoginUrl = baseurl + NativeURLHelper.getShared().getPreLoginCheckUrl(trackId);

                    Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, NativeURLHelper.contentTypeJson);

                    //Call Service-getRequestId
                    serviceForPreLoginCheck(preLoginUrl, callback);

                } else {
                    callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                    return;
                }
            } catch (Exception e) {
                callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.PRE_LOGIN_ERROR, e.getMessage()));
            }



        }

    private void serviceForPreLoginCheck(String preLoginUrl, EventResult<ProgressiveRegistrationResponseEntity> callback) {
        String methodName = "ProgressiveRegistrationService :serviceForPreLoginCheck()";
        try {
            final ICidaasNativeService cidaasNativeService = service.getInstance();

            cidaasNativeService.preLoginCheck(preLoginUrl).enqueue(new Callback<ProgressiveRegistrationResponseEntity>() {
                @Override
                public void onResponse(Call<ProgressiveRegistrationResponseEntity> call, Response<ProgressiveRegistrationResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.PRE_LOGIN_ERROR,
                                    response.code(), "Error" + methodName));
                        }
                    } else {

                        try {

                            // Handle proper error message
                            callback.failure(CommonError.getShared(context).generateProperErrorEntity(WebAuthErrorCode.PROGRESSIVE_UPDATE_USER, response,
                                    NativeConstants.ERROR_LOGGING_PREFIX + methodName));

                        } catch (Exception e) {
                            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.PRE_LOGIN_ERROR,
                                    e.getMessage()));
                            // Timber.e("response"+response.message()+e.getMessage());
                        }

                    }
                }

                @Override
                public void onFailure(Call<ProgressiveRegistrationResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PRE_LOGIN_ERROR, t.getMessage(),
                            NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.PRE_LOGIN_ERROR, e.getMessage()));
        }
    }


    public void progressiveUpdate(@NonNull final String baseurl, @NonNull String trackId,@NonNull String requestId, @NonNull ProgressiveUpdateRequestEntity progressiveUpdateRequest,@NonNull EventResult<ProgressiveUpdateResponseEntity> callback){
        String methodName = "ProgressiveRegistrationService :progressiveUpdate()";
        try {
            if (baseurl != null && !baseurl.equals("") && trackId!=null && !trackId.equals("")) {

                //Construct URL For RequestId
                String preLoginUrl = baseurl + NativeURLHelper.getShared().getProgressiveUpdateURL();

                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, NativeURLHelper.contentTypeJson,requestId);

                if (trackId != null && !trackId.equals("")) {
                    headers.put("trackId", trackId); // Assuming only one trackId is provided
                }



                //Call Service-getRequestId
                serviceForProgressiveUpdate(preLoginUrl,headers,progressiveUpdateRequest, callback);
            }
        } catch (Exception e){
            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.PRE_LOGIN_ERROR, e.getMessage()));
        }

    }

    private void serviceForProgressiveUpdate(String progressiveUpdateUrl,Map<String, String> headers,ProgressiveUpdateRequestEntity progressiveUpdateRequest, EventResult<ProgressiveUpdateResponseEntity> callback){
        String methodName = "ProgressiveRegistrationService :serviceForPreLoginCheck()";
        try {
            final ICidaasNativeService cidaasNativeService = service.getInstance();

            cidaasNativeService.progressiveUpdate(progressiveUpdateUrl,headers,progressiveUpdateRequest).enqueue(new Callback<ProgressiveUpdateResponseEntity>() {
                @Override
                public void onResponse(Call<ProgressiveUpdateResponseEntity> call, Response<ProgressiveUpdateResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.PROGRESSIVE_UPDATE_USER,
                                    response.code(), "Error" + methodName));
                        }
                    } else {
                        assert response.errorBody() != null;
                        try {

                            callback.failure(CommonError.getShared(context).generateStandardErrorEntity(WebAuthErrorCode.PROGRESSIVE_UPDATE_USER, response,
                                    NativeConstants.ERROR_LOGGING_PREFIX + methodName));


                        } catch (Exception e) {
                            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.PROGRESSIVE_UPDATE_USER,
                                    e.getMessage()));
                            // Timber.e("response"+response.message()+e.getMessage());
                        }

                    }
                }

                @Override
                public void onFailure(Call<ProgressiveUpdateResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PROGRESSIVE_UPDATE_USER, t.getMessage(),
                            NativeConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        }
        catch (Exception e){
            callback.failure(WebAuthError.getShared(context).methodException(NativeConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.PROGRESSIVE_UPDATE_USER, e.getMessage()));
        }
    }
}
