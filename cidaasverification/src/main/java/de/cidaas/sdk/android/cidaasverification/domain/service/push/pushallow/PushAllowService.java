package de.cidaas.sdk.android.cidaasverification.domain.service.push.pushallow;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushallow.PushAllowEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushallow.PushAllowResponse;
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

public class PushAllowService {
    private Context context;

    public static PushAllowService shared;

    CidaasSDK_V2_Service service;


    public PushAllowService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }


    }

    public static PushAllowService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PushAllowService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PushAllowService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call pushAllow Service
    public void callPushAllowService(@NonNull String pushAllowURL, Map<String, String> headers, PushAllowEntity pushAllowEntity,
                                     final EventResult<PushAllowResponse> pushAllowCallback) {
        final String methodName = "PushAllowService:-callPushAllowService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.pushAllow(pushAllowURL, headers, pushAllowEntity).enqueue(new Callback<PushAllowResponse>() {
                @Override
                public void onResponse(Call<PushAllowResponse> call, Response<PushAllowResponse> response) {
                    if (response.isSuccessful()) {
                        pushAllowCallback.success(response.body());
                    } else {
                        pushAllowCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.PUSH_ALLOW_FAILURE,
                                response, VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                    }
                }

                @Override
                public void onFailure(Call<PushAllowResponse> call, Throwable t) {
                    pushAllowCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PUSH_ALLOW_FAILURE,
                            t.getMessage(), VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            pushAllowCallback.failure(WebAuthError.getShared(context).methodException(VerificationConstants.ERROR_LOGGING_PREFIX + methodName, WebAuthErrorCode.PUSH_ALLOW_FAILURE,
                    e.getMessage()));
        }
    }
}
