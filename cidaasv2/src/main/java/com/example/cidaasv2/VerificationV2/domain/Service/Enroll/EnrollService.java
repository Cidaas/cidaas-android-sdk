package com.example.cidaasv2.VerificationV2.domain.Service.Enroll;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Enroll.EnrollResponse;
import com.example.cidaasv2.VerificationV2.data.Service.CidaasSDK_V2_Service;
import com.example.cidaasv2.VerificationV2.data.Service.ICidaasSDK_V2_Services;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnrollService {
    private Context context;

    public static EnrollService shared;

    CidaasSDK_V2_Service service;


    public EnrollService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

        //Todo setValue for authenticationType

    }

    public static EnrollService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new EnrollService(contextFromCidaas);
            }
        }
        catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("EnrollService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call enroll Service
    public void callEnrollService(@NonNull String enrollURL, Map<String, String> headers, EnrollEntity enrollEntity,
                                  final Result<EnrollResponse> enrollCallback)
    {
        final String methodName = "EnrollService:-callEnrollService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.enroll(enrollURL, headers, enrollEntity).enqueue(new Callback<EnrollResponse>() {
                @Override
                public void onResponse(Call<EnrollResponse> call, Response<EnrollResponse> response) {
                    if(response.isSuccessful())
                    {
                        enrollCallback.success(response.body());
                    }
                    else
                    {
                        enrollCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                                response,"Error:- "+methodName));
                    }
                }

                @Override
                public void onFailure(Call<EnrollResponse> call, Throwable t) {
                    enrollCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                            t.getMessage(),"Error:- "+methodName));
                }
            });
        } catch (Exception e) {
            enrollCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
