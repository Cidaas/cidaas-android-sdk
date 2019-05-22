package com.example.cidaasv2.VerificationV2.domain.Service.Settings;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.ConfiguredMFAList.ConfiguredMFAList;
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.ConfiguredMFAList.GetMFAListEntity;
import com.example.cidaasv2.VerificationV2.data.Service.CidaasSDK_V2_Service;
import com.example.cidaasv2.VerificationV2.data.Service.ICidaasSDK_V2_Services;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsService {
    private Context context;

    public static SettingsService shared;

    CidaasSDK_V2_Service service;


    public SettingsService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

        //Todo setValue for authenticationType

    }

    public static SettingsService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new SettingsService(contextFromCidaas);
            }
        }
        catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("SettingsService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //call Settings Service
    public void callSettingsService(@NonNull String settingsURL, Map<String, String> headers, GetMFAListEntity getMFAListEntity,
                                   final Result<ConfiguredMFAList> configuredMFAListResult)
    {
        final String methodName = "SettingsService:-callSettingsService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.getConfiguredMFAList(settingsURL, headers, getMFAListEntity).enqueue(new Callback<ConfiguredMFAList>() {
                @Override
                public void onResponse(Call<ConfiguredMFAList> call, Response<ConfiguredMFAList> response) {
                    if(response.isSuccessful())
                    {
                        configuredMFAListResult.success(response.body());
                    }
                    else
                    {
                        configuredMFAListResult.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE,
                                response,"Error:- "+methodName));
                    }
                }

                @Override
                public void onFailure(Call<ConfiguredMFAList> call, Throwable t) {
                    configuredMFAListResult.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE,
                            t.getMessage(),"Error:- "+methodName));
                }
            });
        } catch (Exception e) {
            configuredMFAListResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }
}
