package com.example.cidaasv2.VerificationV2.domain.Service.ResumeLogin;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.ResumeLogin.ResumeLoginEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.ResumeLogin.ResumeLoginResponse;
import com.example.cidaasv2.VerificationV2.data.Service.CidaasSDK_V2_Service;
import com.example.cidaasv2.VerificationV2.data.Service.ICidaasSDK_V2_Services;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeLoginService {
    private Context context;

    public static ResumeLoginService shared;

    CidaasSDK_V2_Service service;


    public ResumeLoginService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

        //Todo setValue for authenticationType

    }

    public static ResumeLoginService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ResumeLoginService(contextFromCidaas);
            }
        }
        catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("ResumeLoginService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //call ResumeLogin Service
    public void callResumeLoginService(@NonNull String resumeLoginURL, Map<String, String> headers, ResumeLoginEntity resumeLoginEntity,
                                       final Result<ResumeLoginResponseEntity> resumeLoginCallback)
    {
        final String methodName = "ResumeLoginService:-callResumeLoginService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.resumeLogin(resumeLoginURL, headers, resumeLoginEntity).enqueue(new Callback<ResumeLoginResponseEntity>() {
                @Override
                public void onResponse(Call<ResumeLoginResponseEntity> call, Response<ResumeLoginResponseEntity> response) {
                    if(response.isSuccessful())
                    {
                        resumeLoginCallback.success(response.body());
                    }
                    else
                    {
                        resumeLoginCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.PASSWORDLESS_SERVICE_FAILURE,
                                response,"Error:- "+methodName));
                    }
                }

                @Override
                public void onFailure(Call<ResumeLoginResponseEntity> call, Throwable t) {
                    resumeLoginCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PASSWORDLESS_SERVICE_FAILURE,
                            t.getMessage(),"Error:- "+methodName));
                }
            });
        } catch (Exception e) {
            resumeLoginCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.PASSWORDLESS_SERVICE_FAILURE,
                    e.getMessage()));
        }
    }
}
