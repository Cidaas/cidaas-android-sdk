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
import com.example.cidaasv2.VerificationV2.data.Entity.Settings.Others.UpdateFCMTokenEntity;
import com.example.cidaasv2.VerificationV2.data.Entity.UpdateFCMToken.UpdateFCMTokenResponseEntity;
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
    public void getConfigurationList(@NonNull String settingsURL, Map<String, String> headers, final GetMFAListEntity getMFAListEntity,
                                     final Result<ConfiguredMFAList> configuredMFAListResult)
    {
        final String methodName = "SettingsService:-getConfigurationList()";
        try {

            LogFile.getShared(context).addInfoLog(methodName,getMFAListEntity.getSub());
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.getConfiguredMFAList(settingsURL, headers, getMFAListEntity).enqueue(new Callback<ConfiguredMFAList>() {
                @Override
                public void onResponse(Call<ConfiguredMFAList> call, Response<ConfiguredMFAList> response) {
                    if(response.isSuccessful())
                    {

                        LogFile.getShared(context).addSuccessLog(methodName,response.toString()+"Sub:-"+getMFAListEntity.getSub()
                        );
                       if(response.code()==200) {
                           configuredMFAListResult.success(response.body());
                       }
                       else
                       {

                           ConfiguredMFAList configuredMFAList=new ConfiguredMFAList();
                           configuredMFAList.setStatus(response.code());
                           configuredMFAList.setSuccess(true);
                           configuredMFAListResult.success(configuredMFAList);
                       }
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

    //call update FCM Token
    public void updateFCMToken(@NonNull String updateFCMTOkenURL, Map<String, String> headers, UpdateFCMTokenEntity updateFCMTokenEntity,
                                     final Result<UpdateFCMTokenResponseEntity> result)
    {
        final String methodName = "SettingsService:-updateFCMToken()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.updateFCMToken(updateFCMTOkenURL, headers, updateFCMTokenEntity).enqueue(new Callback<UpdateFCMTokenResponseEntity>() {
                @Override
                public void onResponse(Call<UpdateFCMTokenResponseEntity> call, Response<UpdateFCMTokenResponseEntity> response) {
                    if(response.isSuccessful())
                    {
                        result.success(response.body());
                    }
                    else
                    {
                        result.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.UPDATE_FCM_TOKEN,
                                response,"Error:- "+methodName));
                    }
                }

                @Override
                public void onFailure(Call<UpdateFCMTokenResponseEntity> call, Throwable t) {
                    result.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.UPDATE_FCM_TOKEN,
                            t.getMessage(),"Error:- "+methodName));
                }
            });
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.UPDATE_FCM_TOKEN, e.getMessage()));
        }
    }


}
