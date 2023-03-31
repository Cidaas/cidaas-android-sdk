package de.cidaas.sdk.android.cidaasverification.domain.service.authenticatedhistory;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory.AuthenticatedHistoryEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory.AuthenticatedHistoryResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory.AuthenticatedHistoryResponseNew;
import de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory.UserAuthenticatedHistoryDataEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.authenticatedhistory.UserAuthenticatedHistoryResponse;
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

    public void callAuthenticatedHistoryServiceNew(@NonNull String authenticatedHistoryURL, Map<String, String> headers, AuthenticatedHistoryEntity authenticatedHistoryEntity,
                                                   final EventResult<AuthenticatedHistoryResponseNew> authenticatedHistoryCallback) {
        final String methodName = VerificationConstants.METHOD_AUTHENTICATED_HISTORY_SERVICE;
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.getAuthenticatedHistoryNew(authenticatedHistoryURL, headers, authenticatedHistoryEntity).enqueue(new Callback<AuthenticatedHistoryResponseNew>() {
                @Override
                public void onResponse(Call<AuthenticatedHistoryResponseNew> call, Response<AuthenticatedHistoryResponseNew> response) {
                    if (response.isSuccessful()) {
                        authenticatedHistoryCallback.success(response.body());
                    } else {
                        authenticatedHistoryCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                                response, VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticatedHistoryResponseNew> call, Throwable t) {
                    authenticatedHistoryCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                            t.getMessage(), VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            authenticatedHistoryCallback.failure(WebAuthError.getShared(context).methodException(VerificationConstants.ERROR_LOGGING_PREFIX + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
    //call AuthenticatedHistory Service
    public void callAuthenticatedHistoryService(@NonNull String authenticatedHistoryURL, Map<String, String> headers, AuthenticatedHistoryEntity authenticatedHistoryEntity,
                                                final EventResult<AuthenticatedHistoryResponse> authenticatedHistoryCallback) {
        final String methodName = VerificationConstants.METHOD_AUTHENTICATED_HISTORY_SERVICE;
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
                                response, VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticatedHistoryResponse> call, Throwable t) {
                    authenticatedHistoryCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                            t.getMessage(), VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            authenticatedHistoryCallback.failure(WebAuthError.getShared(context).methodException(VerificationConstants.ERROR_LOGGING_PREFIX + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    public void callAuthenticatedHistoryServiceDetail(String authenticatedHistoryUrl, Map<String, String> headers,
                                                      UserAuthenticatedHistoryDataEntity authenticatedHistoryEntity,
                                                      final EventResult<UserAuthenticatedHistoryResponse> authenticatedHistoryCallback) {
        final String methodName = VerificationConstants.METHOD_AUTHENTICATED_HISTORY_SERVICE;
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.getAuthenticatedHistoryDetail(authenticatedHistoryUrl, headers, authenticatedHistoryEntity).enqueue(new Callback<UserAuthenticatedHistoryResponse>() {
                @Override
                public void onResponse(Call<UserAuthenticatedHistoryResponse> call,
                                       Response<UserAuthenticatedHistoryResponse> response) {
                    if (response.isSuccessful()) {
                        authenticatedHistoryCallback.success(response.body());
                    } else {
                        authenticatedHistoryCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                                response, VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                    }
                }

                @Override
                public void onFailure(Call<UserAuthenticatedHistoryResponse> call, Throwable t) {
                    authenticatedHistoryCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                            t.getMessage(), VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            authenticatedHistoryCallback.failure(WebAuthError.getShared(context).methodException(VerificationConstants.ERROR_LOGGING_PREFIX + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
