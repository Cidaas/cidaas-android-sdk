package com.example.cidaasv2.Service.Repository.ChangePassword;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static  ChangePasswordService shared;

    public  ChangePasswordService(Context contextFromCidaas) {
        sub="";
        statusId="";
        verificationType="";
        context=contextFromCidaas;
        authenticationType="";

        if(service==null) {
            service=new CidaassdkService(context);
        }
        //Todo setValue for authenticationType

    }



    public static  ChangePasswordService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  ChangePasswordService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            //Timber.i(e.getMessage());
        }
        return shared;
    }

    //------------------------------------------------------------------------------Reset Password Validate Code
    public void changePassword(ChangePasswordRequestEntity changePasswordRequestEntity,
                               String baseurl, DeviceInfoEntity deviceInfoEntityFromParam,final Result<ChangePasswordResponseEntity> callback)
    {
        //Local Variables
        final String methodName = "ChangePassword Service :changePassword()";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For Change Password
                String changePasswordUrl=baseurl+ URLHelper.getShared().getChangePasswordURl();


            //Construct header
            Map<String, String> headers = Headers.getShared(context).getHeaders(changePasswordRequestEntity.getAccess_token(),false,
                    URLHelper.contentTypeJson);
                serviceForChangePassword(changePasswordRequestEntity, changePasswordUrl, headers,callback);


            }
            else {
                callback.failure(WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),"Error :"+methodName));
                return;
            }

        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,e.getMessage()));
        }
    }

    private void serviceForChangePassword(ChangePasswordRequestEntity changePasswordRequestEntity, String changePasswordUrl,
                                          Map<String, String> headers,final Result<ChangePasswordResponseEntity> callback)
    {
        final String methodName = "ChangePassword Service :serviceForChangePassword()";
        try {
            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.changePassword(changePasswordUrl, headers, changePasswordRequestEntity)
                    .enqueue(new Callback<ChangePasswordResponseEntity>() {
                        @Override
                        public void onResponse(Call<ChangePasswordResponseEntity> call, Response<ChangePasswordResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,
                                        response.code(), "Error :" + methodName));
                                }
                            } else {
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE, response,
                                        "Error :" + methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE, t.getMessage(),
                                    "Error :" + methodName));

                        }
                    });
        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,e.getMessage()));
        }
    }
}
