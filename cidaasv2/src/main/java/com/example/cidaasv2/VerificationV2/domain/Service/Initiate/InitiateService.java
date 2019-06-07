package com.example.cidaasv2.VerificationV2.domain.Service.Initiate;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.VerificationV2.data.Entity.Initiate.InitiateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Initiate.InitiateResponse;
import com.example.cidaasv2.VerificationV2.data.Service.CidaasSDK_V2_Service;
import com.example.cidaasv2.VerificationV2.data.Service.ICidaasSDK_V2_Services;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitiateService {
    private Context context;

    public static InitiateService shared;

    CidaasSDK_V2_Service service;


    public InitiateService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

        //Todo setValue for authenticationType

    }

    public static InitiateService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new InitiateService(contextFromCidaas);
            }
        }
        catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("InitiateService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call initiate Service
    public void callInitiateService(@NonNull String initiateURL, Map<String, String> headers, InitiateEntity initiateEntity,
                                  final Result<InitiateResponse> initiateCallback)
    {
        final String methodName = "InitiateService:-callInitiateService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.initiate(initiateURL, headers, initiateEntity).enqueue(new Callback<InitiateResponse>() {
                @Override
                public void onResponse(Call<InitiateResponse> call, Response<InitiateResponse> response) {
                    if(response.isSuccessful())
                    {
                        initiateCallback.success(response.body());
                    }
                    else
                    {
                        initiateCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_VERIFICATION_FAILURE,
                                response,"Error:- "+methodName));
                    }
                }

                @Override
                public void onFailure(Call<InitiateResponse> call, Throwable t) {
                    initiateCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_VERIFICATION_FAILURE,
                            t.getMessage(),"Error:- "+methodName));
                }
            });
        } catch (Exception e) {
            initiateCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.INITIATE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
