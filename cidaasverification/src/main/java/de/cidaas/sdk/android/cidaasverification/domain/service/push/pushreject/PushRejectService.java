package de.cidaas.sdk.android.cidaasverification.domain.service.push.pushreject;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushreject.PushRejectEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.push.pushreject.PushRejectResponse;
import de.cidaas.sdk.android.cidaasverification.data.service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasverification.data.service.ICidaasSDK_V2_Services;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.logger.LogFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PushRejectService {
    private Context context;

    public static PushRejectService shared;

    CidaasSDK_V2_Service service;


    public PushRejectService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

    }

    public static PushRejectService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PushRejectService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PushRejectService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call pushReject Service
    public void callPushRejectService(@NonNull String pushRejectURL, Map<String, String> headers, PushRejectEntity pushRejectEntity,
                                      final EventResult<PushRejectResponse> pushRejectCallback) {
        final String methodName = "PushRejectService:-callPushRejectService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.pushReject(pushRejectURL, headers, pushRejectEntity).enqueue(new Callback<PushRejectResponse>() {
                @Override
                public void onResponse(Call<PushRejectResponse> call, Response<PushRejectResponse> response) {
                    if (response.isSuccessful()) {
                        pushRejectCallback.success(response.body());
                    } else {
                        pushRejectCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.PUSH_REJECT_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<PushRejectResponse> call, Throwable t) {
                    pushRejectCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PUSH_REJECT_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            pushRejectCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.PUSH_REJECT_FAILURE,
                    e.getMessage()));
        }
    }
}

