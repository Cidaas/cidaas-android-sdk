package widas.cidaassdkv2.cidaasVerificationV2.domain.Service.Push.PushAllow;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Push.PushAllow.PushAllowEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.Push.PushAllow.PushAllowResponse;
import widas.cidaassdkv2.cidaasVerificationV2.data.Service.CidaasSDK_V2_Service;
import widas.cidaassdkv2.cidaasVerificationV2.data.Service.ICidaasSDK_V2_Services;

public class PushAllowService {
    private Context context;

    public static PushAllowService shared;

    CidaasSDK_V2_Service service;


    public PushAllowService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

        //Todo setValue for authenticationType

    }

    public static PushAllowService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new PushAllowService(contextFromCidaas);
            }
        }
        catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("PushAllowService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //call pushAllow Service
    public void callPushAllowService(@NonNull String pushAllowURL, Map<String, String> headers, PushAllowEntity pushAllowEntity,
                                    final Result<PushAllowResponse> pushAllowCallback)
    {
        final String methodName = "PushAllowService:-callPushAllowService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.pushAllow(pushAllowURL, headers, pushAllowEntity).enqueue(new Callback<PushAllowResponse>() {
                @Override
                public void onResponse(Call<PushAllowResponse> call, Response<PushAllowResponse> response) {
                    if(response.isSuccessful())
                    {
                        pushAllowCallback.success(response.body());
                    }
                    else
                    {
                        pushAllowCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.PUSH_ALLOW_FAILURE,
                                response,"Error:- "+methodName));
                    }
                }

                @Override
                public void onFailure(Call<PushAllowResponse> call, Throwable t) {
                    pushAllowCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.PUSH_ALLOW_FAILURE,
                            t.getMessage(),"Error:- "+methodName));
                }
            });
        } catch (Exception e) {
            pushAllowCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.PUSH_ALLOW_FAILURE,
                    e.getMessage()));
        }
    }
}
