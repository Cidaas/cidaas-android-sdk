package de.cidaas.sdk.android.cidaasverification.domain.service.enroll;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.enroll.EnrollEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.enroll.EnrollResponse;
import de.cidaas.sdk.android.cidaasverification.data.service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasverification.data.service.ICidaasSDK_V2_Services;
import de.cidaas.sdk.android.cidaasverification.util.VerificationConstants;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.logger.LogFile;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
                                  final EventResult<EnrollResponse> enrollCallback) {
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
                                response, VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                    }
                }

                @Override
                public void onFailure(Call<EnrollResponse> call, Throwable t) {
                    enrollCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                            t.getMessage(), VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            enrollCallback.failure(WebAuthError.getShared(context).methodException(VerificationConstants.ERROR_LOGGING_PREFIX + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }


    //call enroll Service
    public void callEnrollServiceForFaceOrVoice(MultipartBody.Part fileToSend, @NonNull String enrollURL, Map<String, String> headers, HashMap<String, RequestBody> enrollHashmap,
                                                final EventResult<EnrollResponse> enrollCallback) {
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
                                response, VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                    }
                }

                @Override
                public void onFailure(Call<EnrollResponse> call, Throwable t) {
                    enrollCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                            t.getMessage(), VerificationConstants.ERROR_LOGGING_PREFIX + methodName));
                }
            });
        } catch (Exception e) {
            enrollCallback.failure(WebAuthError.getShared(context).methodException(VerificationConstants.ERROR_LOGGING_PREFIX + methodName, WebAuthErrorCode.ENROLL_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
