package de.cidaas.sdk.android.cidaasverification.domain.service.settings;

import android.content.Context;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import de.cidaas.sdk.android.cidaasverification.data.entity.settings.configuredmfalist.ConfiguredMFAList;
import de.cidaas.sdk.android.cidaasverification.data.entity.settings.configuredmfalist.GetMFAListEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.updatefcmtoken.UpdateFCMTokenEntity;
import de.cidaas.sdk.android.cidaasverification.data.entity.updatefcmtoken.UpdateFCMTokenResponseEntity;
import de.cidaas.sdk.android.cidaasverification.data.service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasverification.data.service.ICidaasSDK_V2_Services;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.logger.LogFile;
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

    }

    public static SettingsService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new SettingsService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("SettingsService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //call Settings Service
    public void getConfigurationList(@NonNull String settingsURL, Map<String, String> headers, final GetMFAListEntity getMFAListEntity,
                                     final EventResult<ConfiguredMFAList> configuredMFAListResult) {
        final String methodName = "SettingsService:-getConfigurationList()";
        try {

            LogFile.getShared(context).addInfoLog(methodName, getMFAListEntity.getSub());
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.getConfiguredMFAList(settingsURL, headers, getMFAListEntity).enqueue(new Callback<ConfiguredMFAList>() {
                @Override
                public void onResponse(Call<ConfiguredMFAList> call, Response<ConfiguredMFAList> response) {
                    if (response.isSuccessful()) {

                        LogFile.getShared(context).addSuccessLog(methodName, response.toString() + "Sub:-" + getMFAListEntity.getSub()
                        );
                        if (response.code() == 200) {
                            configuredMFAListResult.success(response.body());
                        } else {

                            ConfiguredMFAList configuredMFAList = new ConfiguredMFAList();
                            configuredMFAList.setStatus(response.code());
                            configuredMFAList.setSuccess(true);
                            configuredMFAListResult.success(configuredMFAList);
                        }
                    } else {
                        configuredMFAListResult.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<ConfiguredMFAList> call, Throwable t) {
                    configuredMFAListResult.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            configuredMFAListResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }

    //call update FCM Token
    public void updateFCMToken(@NonNull String updateFCMTOkenURL, Map<String, String> headers, UpdateFCMTokenEntity updateFCMTokenEntity,
                               final EventResult<UpdateFCMTokenResponseEntity> result) {
        final String methodName = "SettingsService:-updateFCMToken()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.updateFCMToken(updateFCMTOkenURL, headers, updateFCMTokenEntity).enqueue(new Callback<UpdateFCMTokenResponseEntity>() {
                @Override
                public void onResponse(Call<UpdateFCMTokenResponseEntity> call, Response<UpdateFCMTokenResponseEntity> response) {
                    if (response.isSuccessful()) {
                        result.success(response.body());
                    } else {
                        result.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.UPDATE_FCM_TOKEN,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<UpdateFCMTokenResponseEntity> call, Throwable t) {
                    result.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.UPDATE_FCM_TOKEN,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.UPDATE_FCM_TOKEN, e.getMessage()));
        }
    }


    public void getConfigurationListupdated(String settingsURL, Map<String, String> headers, String mfalistentity, EventResult<ConfiguredMFAList> configuredMFAListResult) {
        final String methodName = "SettingsService:-getConfigurationList()";
        try {
            JSONObject jsonObject = new JSONObject(mfalistentity);

            LogFile.getShared(context).addInfoLog(methodName, jsonObject.get("sub").toString());
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.getConfiguredMFAListupdated(settingsURL, headers, mfalistentity).enqueue(new Callback<ConfiguredMFAList>() {
                @Override
                public void onResponse(Call<ConfiguredMFAList> call, Response<ConfiguredMFAList> response) {
                    if (response.isSuccessful()) {

                        try {
                            LogFile.getShared(context).addSuccessLog(methodName, response.toString() + "Sub:-" +jsonObject.get("sub").toString()/* getMFAListEntity.getSub()*/
                            );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (response.code() == 200) {
                            configuredMFAListResult.success(response.body());
                        } else {

                            ConfiguredMFAList configuredMFAList = new ConfiguredMFAList();
                            configuredMFAList.setStatus(response.code());
                            configuredMFAList.setSuccess(true);
                            configuredMFAListResult.success(configuredMFAList);
                        }
                    } else {
                        configuredMFAListResult.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<ConfiguredMFAList> call, Throwable t) {
                    configuredMFAListResult.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            configuredMFAListResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName,
                    WebAuthErrorCode.MFA_LIST_VERIFICATION_FAILURE, e.getMessage()));
        }
    }
}
