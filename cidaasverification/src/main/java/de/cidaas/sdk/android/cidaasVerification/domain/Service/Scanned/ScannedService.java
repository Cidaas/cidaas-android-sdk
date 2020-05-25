package de.cidaas.sdk.android.cidaasVerification.domain.Service.Scanned;

import android.content.Context;

import androidx.annotation.NonNull;

import de.cidaas.sdk.android.cidaas.Helper.CommonError.CommonError;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Helper.Logger.LogFile;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Scanned.ScannedEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Scanned.ScannedResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasVerification.data.Service.ICidaasSDK_V2_Services;

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
                                   final Result<ScannedResponse> scannedCallback) {
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
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<ScannedResponse> call, Throwable t) {
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
