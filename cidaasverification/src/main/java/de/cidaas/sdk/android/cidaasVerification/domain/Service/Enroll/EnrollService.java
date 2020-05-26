package de.cidaas.sdk.android.cidaasVerification.domain.Service.Enroll;

import android.content.Context;

import androidx.annotation.NonNull;

import de.cidaas.sdk.android.cidaas.Helper.CommonError.CommonError;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Helper.Logger.LogFile;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Enroll.EnrollEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Enroll.EnrollResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasVerification.data.Service.ICidaasSDK_V2_Services;

public class EnrollService {
    private Context context;

    public static EnrollService shared;

    CidaasSDK_V2_Service service;


    public EnrollService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

    }

    public static EnrollService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new EnrollService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("EnrollService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call enroll Service
    public void callEnrollService(@NonNull String enrollURL, Map<String, String> headers, EnrollEntity enrollEntity,
                                  final Result<EnrollResponse> enrollCallback) {
        final String methodName = "EnrollService:-callEnrollService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.enroll(enrollURL, headers, enrollEntity).enqueue(new Callback<EnrollResponse>() {
                @Override
                public void onResponse(Call<EnrollResponse> call, Response<EnrollResponse> response) {
                    if (response.isSuccessful()) {
                        enrollCallback.success(response.body());
                    } else {
                        enrollCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<EnrollResponse> call, Throwable t) {
                    enrollCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            enrollCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //call enroll Service
    public void callEnrollServiceForFaceOrVoice(MultipartBody.Part fileToSend, @NonNull String enrollURL, Map<String, String> headers, HashMap<String, RequestBody> enrollHashmap,
                                                final Result<EnrollResponse> enrollCallback) {
        final String methodName = "EnrollService:-callEnrollServiceForFaceOrVoice()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.enrollWithMultipart(enrollURL, headers, fileToSend, enrollHashmap).enqueue(new Callback<EnrollResponse>() {
                @Override
                public void onResponse(Call<EnrollResponse> call, Response<EnrollResponse> response) {
                    if (response.isSuccessful()) {
                        enrollCallback.success(response.body());
                    } else {
                        enrollCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<EnrollResponse> call, Throwable t) {
                    enrollCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            enrollCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
