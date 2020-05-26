package de.cidaas.sdk.android.cidaasVerification.domain.Service.AuthenticatedHistory;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.AuthenticatedHistory.AuthenticatedHistoryEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.AuthenticatedHistory.AuthenticatedHistoryResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasVerification.data.Service.ICidaasSDK_V2_Services;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.logger.LogFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticatedHistoryService {
    private Context context;

    public static AuthenticatedHistoryService shared;

    CidaasSDK_V2_Service service;


    public AuthenticatedHistoryService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

    }

    public static AuthenticatedHistoryService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new AuthenticatedHistoryService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("AuthenticatedHistoryService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //call AuthenticatedHistory Service
    public void callAuthenticatedHistoryService(@NonNull String authenticatedHistoryURL, Map<String, String> headers, AuthenticatedHistoryEntity authenticatedHistoryEntity,
                                                final EventResult<AuthenticatedHistoryResponse> authenticatedHistoryCallback) {
        final String methodName = "AuthenticatedHistoryService:-callAuthenticatedHistoryService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.getAuthenticatedHistory(authenticatedHistoryURL, headers, authenticatedHistoryEntity).enqueue(new Callback<AuthenticatedHistoryResponse>() {
                @Override
                public void onResponse(Call<AuthenticatedHistoryResponse> call, Response<AuthenticatedHistoryResponse> response) {
                    if (response.isSuccessful()) {
                        authenticatedHistoryCallback.success(response.body());
                    } else {
                        authenticatedHistoryCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticatedHistoryResponse> call, Throwable t) {
                    authenticatedHistoryCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            authenticatedHistoryCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
