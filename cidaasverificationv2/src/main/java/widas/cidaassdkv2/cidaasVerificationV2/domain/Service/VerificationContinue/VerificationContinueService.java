package widas.cidaassdkv2.cidaasVerificationV2.domain.Service.VerificationContinue;

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
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.VerificationContinue.VerificationContinue;
import widas.cidaassdkv2.cidaasVerificationV2.data.Entity.VerificationContinue.VerificationContinueResponseEntity;
import widas.cidaassdkv2.cidaasVerificationV2.data.Service.CidaasSDK_V2_Service;
import widas.cidaassdkv2.cidaasVerificationV2.data.Service.ICidaasSDK_V2_Services;

public class VerificationContinueService {

    private Context context;

    public static VerificationContinueService shared;

    CidaasSDK_V2_Service service;


    public VerificationContinueService(Context contextFromCidaas) {
        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasSDK_V2_Service();
        }

        //Todo setValue for authenticationType

    }

    public static VerificationContinueService getShared(@NonNull Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new VerificationContinueService(contextFromCidaas);
            }
        }
        catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("VerificationContinueService instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }


    //call VerificationContinue Service
    public void callVerificationContinueService(@NonNull String verificationContinueURL, Map<String, String> headers, VerificationContinue verificationContinueEntity,
                                       final Result<VerificationContinueResponseEntity> verificationContinueCallback)
    {
        final String methodName = "VerificationContinueService:-callVerificationContinueService()";
        try {
            //call service
            ICidaasSDK_V2_Services cidaasSDK_v2_services = service.getInstance();
            cidaasSDK_v2_services.verificationContinue(verificationContinueURL, headers, verificationContinueEntity).enqueue(new Callback<VerificationContinueResponseEntity>() {
                @Override
                public void onResponse(Call<VerificationContinueResponseEntity> call, Response<VerificationContinueResponseEntity> response) {
                    if(response.isSuccessful())
                    {
                        verificationContinueCallback.success(response.body());
                    }
                    else
                    {
                        verificationContinueCallback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                                response,"Error:- "+methodName));
                    }
                }

                @Override
                public void onFailure(Call<VerificationContinueResponseEntity> call, Throwable t) {
                    verificationContinueCallback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                            t.getMessage(),"Error:- "+methodName));
                }
            });
        } catch (Exception e) {
            verificationContinueCallback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                    e.getMessage()));
        }
    }
}