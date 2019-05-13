package com.example.cidaasv2.VerificationV2.domain.Service.Setup;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Setup.SetupResponse;
import com.example.cidaasv2.VerificationV2.data.Service.CidaasSDK_V2_Service;
import com.example.cidaasv2.VerificationV2.data.Service.ICidaasSDK_V2_Services;
import com.example.cidaasv2.VerificationV2.domain.Service.Setup.SetupService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SetupService {
    private Context context;

    public static SetupService shared;

    CidaasSDK_V2_Service service;


    public SetupService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

        //Todo setValue for authenticationType

    }

    public static SetupService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new SetupService(contextFromCidaas);
            }
        }
        catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("SetupService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call callSetup Service
    public void callSetupService(@NonNull String setupURL, Map<String, String> headers, SetupEntity setupEntity,
                                   final Result<SetupResponse> setupCallback)
    {
        final String methodName = "SetupService:-callSetupService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.setup(setupURL, headers, setupEntity).enqueue(new Callback<SetupResponse>() {
                @Override
                public void onResponse(Call<SetupResponse> call, Response<SetupResponse> response) {
                    if(response.isSuccessful())
                    {
                        setupCallback.success(response.body());
                    }
                    else
                    {
                        setupCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SCANNED_FAILURE,
                                response,"Error:- "+methodName));
                    }
                }

                @Override
                public void onFailure(Call<SetupResponse> call, Throwable t) {
                    setupCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SCANNED_FAILURE,
                            t.getMessage(),"Error:- "+methodName));
                }
            });
        } catch (Exception e) {
            setupCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.SCANNED_FAILURE,
                    e.getMessage()));
        }
    }
}
