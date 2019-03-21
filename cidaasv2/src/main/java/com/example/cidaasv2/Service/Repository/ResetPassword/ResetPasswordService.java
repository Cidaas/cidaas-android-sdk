package com.example.cidaasv2.Service.Repository.ResetPassword;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetNewPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetNewPassword.ResetPasswordEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordResponseEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ResetPasswordValidateCode.ResetPasswordValidateCodeResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ResetPasswordService {
    //Reset Password
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static  ResetPasswordService shared;

    public  ResetPasswordService(Context contextFromCidaas) {
        sub="";
        statusId="";
        verificationType="";
        context=contextFromCidaas;
        authenticationType="";
        //Todo setValue for authenticationType

    }


    public static  ResetPasswordService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  ResetPasswordService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    public void initiateresetPassword(ResetPasswordRequestEntity resetPasswordRequestEntity, String baseurl,DeviceInfoEntity deviceInfoEntityFromParam, final Result<ResetPasswordResponseEntity> callback)
    {
        //Local Variables
        String resetpasswordUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                resetpasswordUrl=baseurl+ URLHelper.getShared().getInitiateResetPassword();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }

            //Construct Body Parameter for Reset Password

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromParam==null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromParam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromParam;
            }
            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.initiateresetPassword(resetpasswordUrl,headers,resetPasswordRequestEntity).enqueue(new Callback<ResetPasswordResponseEntity>() {
                @Override
                public void onResponse(Call<ResetPasswordResponseEntity> call, Response<ResetPasswordResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE,
                                    "Service failure but successful response" , 400,null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<ResetPasswordResponseEntity> call, Throwable t) {
                    Timber.e("Faliure in Reset Password service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE,t.getMessage(), 400,null,null));

                }
            });

        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            LogFile.getShared(context).addRecordToLog(e.getMessage()+WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE);
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_RESET_PASSWORD_FAILURE));
        }
    }


    //Reset Password Validate Code
    public void resetPasswordValidateCode(ResetPasswordValidateCodeRequestEntity resetPasswordValidateCodeRequestEntity,
                                          String baseurl, final Result<ResetPasswordValidateCodeResponseEntity> callback)
    {
        //Local Variables
        String resetpasswordValidateCodeUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                resetpasswordValidateCodeUrl=baseurl+URLHelper.getShared().getResetPasswordValidateCode();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }

            //Construct Body Parameter for Reset Password

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.resetPasswordValidateCode(resetpasswordValidateCodeUrl,headers,resetPasswordValidateCodeRequestEntity)
                    .enqueue(new Callback<ResetPasswordValidateCodeResponseEntity>() {
                        @Override
                        public void onResponse(Call<ResetPasswordValidateCodeResponseEntity> call, Response<ResetPasswordValidateCodeResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if(response.code()==200) {
                                    callback.success(response.body());
                                }
                                else {
                                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE,
                                            "Service failure but successful response" , 400,null,null));
                                }
                            }
                            else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE,response));
                            }
                        }

                        @Override
                        public void onFailure(Call<ResetPasswordValidateCodeResponseEntity> call, Throwable t) {
                            Timber.e("Faliure in Request id service call"+t.getMessage());
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE,t.getMessage(), 400,null,null));

                        }
                    });

        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
             LogFile.getShared(context).addRecordToLog(WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.RESET_PASSWORD_VALIDATE_CODE_FAILURE));
        }
    }





    //Reset Password Validate Code
    public void resetNewPassword(ResetPasswordEntity resetPasswordEntity,
                                 String baseurl, final Result<ResetNewPasswordResponseEntity> callback)
    {
        //Local Variables
        String ResetNewPasswordUrl = "";
        try{

            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For Change Password
                ResetNewPasswordUrl=baseurl+URLHelper.getShared().getChangePasswordURl();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }

            //Construct Body Parameter for Reset Password

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfoEntity = DBHelper.getShared().getDeviceInfo();

            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.ResetNewPassword(ResetNewPasswordUrl,headers, resetPasswordEntity)
                    .enqueue(new Callback<ResetNewPasswordResponseEntity>() {
                        @Override
                        public void onResponse(Call<ResetNewPasswordResponseEntity> call, Response<ResetNewPasswordResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if(response.code()==200) {
                                    callback.success(response.body());
                                }
                                else {
                                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE,
                                            "Service failure but successful response" , 400,null,null));
                                }
                            }
                            else {
                                assert response.errorBody() != null;
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE,response));
                            }
                        }

                        @Override
                        public void onFailure(Call<ResetNewPasswordResponseEntity> call, Throwable t) {
                            Timber.e("Faliure in Request id service call"+t.getMessage());
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE,t.getMessage(), 400,null,null));

                        }
                    });

        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            LogFile.getShared(context).addRecordToLog(e.getMessage()+WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE);
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.RESET_NEWPASSWORD_FAILURE));
        }
    }

}
