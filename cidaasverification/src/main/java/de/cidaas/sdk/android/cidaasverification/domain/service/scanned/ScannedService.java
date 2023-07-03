package de.cidaas.sdk.android.cidaasverification.domain.service.scanned;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.ScannedEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.ScannedResponse;
import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.SetUpCancelEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.scanned.SetUpCancelResponse;
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

public class ScannedService {
    private Context context;

    public static ScannedService shared;

    CidaasSDK_V2_Service service;


    public ScannedService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

    }

    public static ScannedService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ScannedService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("ScannedService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //call Scanned Service
    public void callScannedService(@NonNull String scannedURL, Map<String, String> headers, ScannedEntity scannedEntity,
                                   final EventResult<ScannedResponse> scannedCallback) {
        final String methodName = "ScannedService:-callScannedService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.scanned(scannedURL, headers, scannedEntity).enqueue(new Callback<ScannedResponse>() {
                @Override
                public void onResponse(Call<ScannedResponse> call, Response<ScannedResponse> response) {
                    if (response.isSuccessful()) {
                        scannedCallback.success(response.body());
                    } else {
                        scannedCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                                response, VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponse> call, Throwable t) {
                    scannedCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                            t.getMessage(), VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            scannedCallback.failure(WebAuthError.getShared(context).methodException(VerificationConstants.ERROR_LOGGING_PREFIX + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
    public void callSetUpCancelService(@NonNull String scannedURL, Map<String, String> headers, SetUpCancelEntity scannedEntity,
                                       final EventResult<SetUpCancelResponse> scannedCallback) {
        final String methodName = "ScannedService:-callScannedService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.setUpCancel(scannedURL, headers, scannedEntity).enqueue(new Callback<SetUpCancelResponse>() {
                @Override
                public void onResponse(Call<SetUpCancelResponse> call, Response<SetUpCancelResponse> response) {
                    if (response.isSuccessful()) {
                        scannedCallback.success(response.body());
                    } else {
                        scannedCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<SetUpCancelResponse> call, Throwable t) {
                    scannedCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            scannedCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.SCANNED_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
