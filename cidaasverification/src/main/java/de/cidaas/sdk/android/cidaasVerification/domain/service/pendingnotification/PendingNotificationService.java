package de.cidaas.sdk.android.cidaasVerification.domain.service.pendingnotification;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;

import de.cidaas.sdk.android.cidaasVerification.data.entity.settings.pendingnotification.PendingNotificationEntity;
import de.cidaas.sdk.android.cidaasVerification.data.entity.settings.pendingnotification.PendingNotificationResponse;
import de.cidaas.sdk.android.cidaasVerification.data.service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasVerification.data.service.ICidaasSDK_V2_Services;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.logger.LogFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingNotificationService {
    private Context context;

    public static PendingNotificationService shared;

    CidaasSDK_V2_Service service;


    public PendingNotificationService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

    }

    public static PendingNotificationService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PendingNotificationService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PendingNotificationService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //call PendingNotification Service
    public void callPendingNotificationService(@NonNull String pendingNotificationURL, Map<String, String> headers, PendingNotificationEntity pendingNotificationEntity,
                                               final EventResult<PendingNotificationResponse> pendingNotificationCallback) {
        final String methodName = "PendingNotificationService:-callPendingNotificationService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.getPendingNotification(pendingNotificationURL, headers, pendingNotificationEntity).enqueue(new Callback<PendingNotificationResponse>() {
                @Override
                public void onResponse(Call<PendingNotificationResponse> call, Response<PendingNotificationResponse> response) {
                    if (response.isSuccessful()) {
                        pendingNotificationCallback.success(response.body());
                    } else {
                        pendingNotificationCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<PendingNotificationResponse> call, Throwable t) {
                    pendingNotificationCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            pendingNotificationCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.PENDING_NOTIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
