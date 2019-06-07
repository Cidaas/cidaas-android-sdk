package com.example.cidaasv2.VerificationV2.domain.Service.Push.PushReject;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushReject.PushRejectEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Push.PushReject.PushRejectResponse;
import com.example.cidaasv2.VerificationV2.data.Service.CidaasSDK_V2_Service;
import com.example.cidaasv2.VerificationV2.data.Service.ICidaasSDK_V2_Services;

import java.util.Map;

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

        //Todo setValue for authenticationType

    }

    public static PushRejectService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PushRejectService(contextFromCidaas);
            }
        }
        catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PushRejectService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call pushReject Service
    public void callPushRejectService(@NonNull String pushRejectURL, Map<String, String> headers, PushRejectEntity pushRejectEntity,
                                    final Result<PushRejectResponse> pushRejectCallback)
    {
        final String methodName = "PushRejectService:-callPushRejectService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.pushReject(pushRejectURL, headers, pushRejectEntity).enqueue(new Callback<PushRejectResponse>() {
                @Override
                public void onResponse(Call<PushRejectResponse> call, Response<PushRejectResponse> response) {
                    if(response.isSuccessful())
                    {
                        pushRejectCallback.success(response.body());
                    }
                    else
                    {
                        pushRejectCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.PUSH_REJECT_FAILURE,
                                response,"Error:- "+methodName));
                    }
                }

                @Override
                public void onFailure(Call<PushRejectResponse> call, Throwable t) {
                    pushRejectCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PUSH_REJECT_FAILURE,
                            t.getMessage(),"Error:- "+methodName));
                }
            });
        } catch (Exception e) {
            pushRejectCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.PUSH_REJECT_FAILURE,
                    e.getMessage()));
        }
    }
}

