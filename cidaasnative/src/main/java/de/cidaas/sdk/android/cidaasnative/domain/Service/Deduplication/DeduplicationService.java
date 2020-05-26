package de.cidaas.sdk.android.cidaasnative.domain.Service.Deduplication;

import android.content.Context;

import com.example.cidaasv2.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import de.cidaas.sdk.android.cidaasnative.data.Entity.Deduplication.DeduplicationResponseEntity;
import de.cidaas.sdk.android.cidaasnative.data.Entity.Deduplication.RegisterDeduplication.RegisterDeduplicationEntity;
import de.cidaas.sdk.android.cidaasnative.data.Service.CidaasNativeService;
import de.cidaas.sdk.android.cidaasnative.data.Service.Helper.NativeURLHelper;
import de.cidaas.sdk.android.cidaasnative.data.Service.ICidaasNativeService;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class DeduplicationService {

    CidaasNativeService service;
    private ObjectMapper objectMapper = new ObjectMapper();
    //Local variables

    private Context context;

    public static DeduplicationService shared;

    public DeduplicationService(Context contextFromCidaas) {

        context = contextFromCidaas;


        if (service == null) {
            service = new CidaasNativeService(context);
        }


    }

    String codeVerifier, codeChallenge;


    public static DeduplicationService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new DeduplicationService(contextFromCidaas);

            }

        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //----------------------------------------------------Get Deduplication info--------------------------------------------------
    public void getDeduplicationList(String baseurl, String trackId, final EventResult<DeduplicationResponseEntity> callback) {
        //Local Variables
        String methodName = "DeduplicationService :getDeduplicationList()";
        try {

            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId

                // Change URL Global wise
                String DeduplicationUrl = baseurl + NativeURLHelper.getShared().getDeduplicationList() + trackId;

                //Header Generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, null);

                //Service call For DeduplicationList
                serviceCallForDeduplicationList(callback, DeduplicationUrl, headers);

            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :" + methodName));
                return;
            }
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE, e.getMessage()));
        }
    }

    private void serviceCallForDeduplicationList(final EventResult<DeduplicationResponseEntity> callback, String deduplicationUrl, Map<String, String> headers) {
        final String methodName = "DeduplicationService :serviceCallForDeduplicationList()";
        try {
            //Call Service-getRequestId
            ICidaasNativeService cidaasNativeService = service.getInstance();
            cidaasNativeService.getDeduplicationList(deduplicationUrl, headers).enqueue(new Callback<DeduplicationResponseEntity>() {
                @Override
                public void onResponse(Call<DeduplicationResponseEntity> call, Response<DeduplicationResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE,
                                    response.code(), "Error :" + methodName));
                        }
                    } else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE, response
                                , "Error :"));
                    }
                }

                @Override
                public void onFailure(Call<DeduplicationResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE,
                            t.getMessage(), "Error :" + methodName));

                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE, e.getMessage()));
        }
    }

    //--------------------------------------------------Register Deduplication info------------------------------------------------
    public void registerDeduplication(String baseurl, String trackId, final EventResult<RegisterDeduplicationEntity> callback) {
        //Local Variables
        String methodName = "DeduplicationService :registerDeduplication()";
        try {

            if (baseurl != null && !baseurl.equals("")) {
                //Construct URL For RequestId

                //Change URL Global wise
                String registerDeduplicationUrl = baseurl + NativeURLHelper.getShared().getRegisterdeduplication() + trackId;

                //Header Generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, NativeURLHelper.contentTypeJson);

                //ServiceCall
                serviceCallForRegisterDeduplication(registerDeduplicationUrl, headers, callback);
            } else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :" + methodName));
                return;
            }
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE, e.getMessage()));
        }
    }

    private void serviceCallForRegisterDeduplication(String registerDeduplicationUrl, Map<String, String> headers, final EventResult<RegisterDeduplicationEntity> callback) {
        final String methodName = "";
        try {
            //Call Service-getRequestId
            ICidaasNativeService cidaasNativeService = service.getInstance();
            cidaasNativeService.registerDeduplication(registerDeduplicationUrl, headers).enqueue(new Callback<RegisterDeduplicationEntity>() {
                @Override
                public void onResponse(Call<RegisterDeduplicationEntity> call, Response<RegisterDeduplicationEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE,
                                    response.code(), "Error :" + methodName));
                        }
                    } else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE,
                                response, "Error :" + methodName));
                    }
                }

                @Override
                public void onFailure(Call<RegisterDeduplicationEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE,
                            t.getMessage(), "Error :" + methodName));

                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.DEDUPLICATION_LIST_FAILURE, e.getMessage()));
        }
    }

}
