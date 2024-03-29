package de.cidaas.sdk.android.cidaasverification.domain.service.setup;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.setup.SetupEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.setup.SetupResponse;
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

public class SetupService {
    private Context context;

    public static SetupService shared;

    CidaasSDK_V2_Service service;


    public SetupService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }
    }

    public static SetupService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new SetupService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("SetupService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call callSetup Service
    public void callSetupService(@NonNull String setupURL, Map<String, String> headers, SetupEntity setupEntity,
                                 final EventResult<SetupResponse> setupCallback) {
        final String methodName = "SetupService:-callSetupService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.setup(setupURL, headers, setupEntity).enqueue(new Callback<SetupResponse>() {
                @Override
                public void onResponse(Call<SetupResponse> call, Response<SetupResponse> response) {
                    if (response.isSuccessful()) {
                        setupCallback.success(response.body());
                    } else {
                        setupCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_VERIFICATION_FAILURE,
                                response, VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                    }
                }

                @Override
                public void onFailure(Call<SetupResponse> call, Throwable t) {
                    setupCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SETUP_VERIFICATION_FAILURE,
                            t.getMessage(), VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            setupCallback.failure(WebAuthError.getShared(context).methodException(VerificationConstants.ERROR_LOGGING_PREFIX + methodName, WebAuthErrorCode.SETUP_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
