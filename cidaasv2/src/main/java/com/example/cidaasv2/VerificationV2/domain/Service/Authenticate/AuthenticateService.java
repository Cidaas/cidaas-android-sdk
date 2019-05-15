package com.example.cidaasv2.VerificationV2.domain.Service.Authenticate;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.Authenticate.AuthenticateResponse;
import com.example.cidaasv2.VerificationV2.data.Service.CidaasSDK_V2_Service;
import com.example.cidaasv2.VerificationV2.data.Service.ICidaasSDK_V2_Services;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticateService {
    private Context context;

    public static AuthenticateService shared;

    CidaasSDK_V2_Service service;


    public AuthenticateService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

        //Todo setValue for authenticationType

    }

    public static AuthenticateService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new AuthenticateService(contextFromCidaas);
            }
        }
        catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("AuthenticateService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call authenticate Service
    public void callAuthenticateService(@NonNull String authenticateURL, Map<String, String> headers, AuthenticateEntity authenticateEntity,
                                    final Result<AuthenticateResponse> authenticateCallback)
    {
        final String methodName = "AuthenticateService:-callAuthenticateService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.authenticate(authenticateURL, headers, authenticateEntity).enqueue(new Callback<AuthenticateResponse>() {
                @Override
                public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {
                    if(response.isSuccessful())
                    {
                        authenticateCallback.success(response.body());
                    }
                    else
                    {authenticateCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE,
                                response,"Error:- "+methodName));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                authenticateCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE,
                            t.getMessage(),"Error:- "+methodName));
                }
            });
        }
        catch (Exception e) {
        authenticateCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }
}
