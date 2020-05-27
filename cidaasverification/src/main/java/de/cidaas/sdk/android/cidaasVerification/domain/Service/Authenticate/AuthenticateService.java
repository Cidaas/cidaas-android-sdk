package de.cidaas.sdk.android.cidaasVerification.domain.Service.Authenticate;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.Authenticate.AuthenticateEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Authenticate.AuthenticateResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasVerification.data.Service.ICidaasSDK_V2_Services;
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
import timber.log.Timber;

public class AuthenticateService {
    private Context context;

    public static AuthenticateService shared;

    CidaasSDK_V2_Service service;


    public AuthenticateService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

    }

    public static AuthenticateService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new AuthenticateService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getInstance(contextFromCidaas).addFailureLog("AuthenticateService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call authenticate Service
    public void callAuthenticateService(@NonNull String authenticateURL, Map<String, String> headers, AuthenticateEntity authenticateEntity,
                                        final EventResult<AuthenticateResponse> authenticateCallback) {
        final String methodName = "AuthenticateService:-callAuthenticateService()";
        try {

            LogFile.getInstance(context).addInfoLog(methodName, " AuthenticateURL:- " + authenticateURL +
                    " ExchangeId:- " + authenticateEntity.getExchange_id() + " PassCode:- " + authenticateEntity.getPass_code());

            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.authenticate(authenticateURL, headers, authenticateEntity).enqueue(new Callback<AuthenticateResponse>() {
                @Override
                public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {
                    if (response.isSuccessful()) {
                        authenticateCallback.success(response.body());

                        LogFile.getInstance(context).addSuccessLog(methodName, " Sub:- " + response.body().getData().getSub() +
                                " Status id:- " + response.body().getData().getStatus_id() + " ExchangeId:- " + response.body().getData().getExchange_id());
                    } else {
                        authenticateCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                    authenticateCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            authenticateCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //call authenticate Service
    public void callAuthenticateServiceForFaceOrVoice(MultipartBody.Part fileToSend, @NonNull String authenticateURL, Map<String, String> headers,
                                                      HashMap<String, RequestBody> authenticateHashmap, final EventResult<AuthenticateResponse> authenticateCallback) {
        final String methodName = "AuthenticateService:-callAuthenticateServiceForFaceOrVoice()";
        try {
            //call service

            LogFile.getInstance(context).addInfoLog(methodName, " AuthenticateURL:- " + authenticateURL +
                    " ExchangeId:- " + authenticateHashmap.get("exchange_id"));

            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.authenticateWithMultipart(authenticateURL, headers, fileToSend, authenticateHashmap).enqueue(new Callback<AuthenticateResponse>() {
                @Override
                public void onResponse(Call<AuthenticateResponse> call, Response<AuthenticateResponse> response) {
                    if (response.isSuccessful()) {

                        authenticateCallback.success(response.body());

                        LogFile.getInstance(context).addSuccessLog(methodName, " Sub:- " + response.body().getData().getSub() +
                                " Status id:- " + response.body().getData().getStatus_id() + " ExchangeId:- " + response.body().getData().getExchange_id());
                    } else {
                        authenticateCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(
                                WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, response, "Error:- " + methodName));
                        // Handle proper error message
                        String errorResponse = "NO message";
                        try {
                            errorResponse = response.errorBody().source().readByteString().utf8();
                        } catch (Exception e) {
                            Timber.d(e.getMessage() + errorResponse + e.getMessage());
                        }

                        LogFile.getInstance(context).addFailureLog(errorResponse);
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateResponse> call, Throwable t) {
                    authenticateCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            authenticateCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.AUTHENTICATE_VERIFICATION_FAILURE, e.getMessage()));
        }
    }
}
