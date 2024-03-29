package de.cidaas.sdk.android.cidaasverification.domain.service.push.pushacknowledge;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushacknowledge.PushAcknowledgeEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushacknowledge.PushAcknowledgeResponse;
import de.cidaas.sdk.android.cidaasverification.data.service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasverification.data.service.ICidaasSDK_V2_Services;
import de.cidaas.sdk.android.cidaasverification.util.VerificationConstants;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.logger.LogFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushAcknowledgeService {
    private Context context;

    public static PushAcknowledgeService shared;

    CidaasSDK_V2_Service service;


    public PushAcknowledgeService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

    }

    public static PushAcknowledgeService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PushAcknowledgeService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PushAcknowledgeService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call pushAcknowledge Service
    public void callPushAcknowledgeService(@NonNull String pushAcknowledgeURL, Map<String, String> headers, PushAcknowledgeEntity pushAcknowledgeEntity,
                                           final EventResult<PushAcknowledgeResponse> pushAcknowledgeCallback) {
        final String methodName = "PushAcknowledgeService:-callPushAcknowledgeService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.pushAcknowledge(pushAcknowledgeURL, headers, pushAcknowledgeEntity).enqueue(new Callback<PushAcknowledgeResponse>() {
                @Override
                public void onResponse(Call<PushAcknowledgeResponse> call, Response<PushAcknowledgeResponse> response) {
                    if (response.isSuccessful()) {
                        pushAcknowledgeCallback.success(response.body());
                    } else {
                        pushAcknowledgeCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.PUSH_ACKNOWLEDGE_FAILURE,
                                response, VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                    }
                }

                @Override
                public void onFailure(Call<PushAcknowledgeResponse> call, Throwable t) {
                    pushAcknowledgeCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PUSH_ACKNOWLEDGE_FAILURE,
                            t.getMessage(), VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            pushAcknowledgeCallback.failure(WebAuthError.getShared(context).methodException(VerificationConstants.ERROR_LOGGING_PREFIX + methodName,
                    WebAuthErrorCode.PUSH_ACKNOWLEDGE_FAILURE, e.getMessage()));
        }
    }
}
