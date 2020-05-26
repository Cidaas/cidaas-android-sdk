package de.cidaas.sdk.android.cidaasVerification.domain.Service.Delete;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Map;

import de.cidaas.sdk.android.cidaasVerification.data.Entity.Delete.DeleteEntity;
import de.cidaas.sdk.android.cidaasVerification.data.Entity.Delete.DeleteResponse;
import de.cidaas.sdk.android.cidaasVerification.data.Service.CidaasSDK_V2_Service;
import de.cidaas.sdk.android.cidaasVerification.data.Service.ICidaasSDK_V2_Services;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.Result;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.logger.LogFile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteService {
    private Context context;

    public static DeleteService shared;

    CidaasSDK_V2_Service service;


    public DeleteService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

    }

    public static DeleteService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new DeleteService(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("DeleteService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //call For Delete and Delete All Service
    public void callDeleteService(@NonNull String deleteURL, Map<String, String> headers, DeleteEntity deleteEntity,
                                  final Result<DeleteResponse> deleteCallback) {
        final String methodName = "DeleteService:-callDeleteService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.delete(deleteURL, headers, deleteEntity).enqueue(new Callback<DeleteResponse>() {
                @Override
                public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                    if (response.isSuccessful()) {
                        deleteCallback.success(response.body());
                    } else {
                        deleteCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                                response, "Error:- " + methodName));
                    }
                }

                @Override
                public void onFailure(Call<DeleteResponse> call, Throwable t) {
                    deleteCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                            t.getMessage(), "Error:- " + methodName));
                }
            });
        } catch (Exception e) {
            deleteCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.DELETE_VERIFICATION_FAILURE,
                    e.getMessage()));
        }
    }
}
