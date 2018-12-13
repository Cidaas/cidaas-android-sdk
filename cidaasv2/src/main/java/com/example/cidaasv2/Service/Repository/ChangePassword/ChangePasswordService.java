package com.example.cidaasv2.Service.Repository.ChangePassword;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordRequestEntity;
import com.example.cidaasv2.Service.Entity.ResetPassword.ChangePassword.ChangePasswordResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

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
            service=new CidaassdkService();
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

    //Reset Password Validate Code
    public void changePassword(ChangePasswordRequestEntity changePasswordRequestEntity,
                               String baseurl, DeviceInfoEntity deviceInfoEntityFromParam,final Result<ChangePasswordResponseEntity> callback)
    {
        //Local Variables
        String changePasswordUrl = "";
        try{

            if(baseurl!=null && baseurl!=""){
                //Construct URL For Change Password
                changePasswordUrl=baseurl+ URLHelper.getShared().getChangePasswordURl();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }

            //Construct Body Parameter for change Password

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
            else
            {
                // deviceInfoEntity=new DeviceInfoEntity();
            }
            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            /* headers.put("Authorization",*//*"Bearer "+*//*changePasswordRequestEntity.getAccess_token());*/
            headers.put("access_token",changePasswordRequestEntity.getAccess_token());
            headers.put("user-agent", "cidaas-android");
            headers.put("device-id", deviceInfoEntity.getDeviceId());
            headers.put("device-make", deviceInfoEntity.getDeviceMake());
            headers.put("device-model", deviceInfoEntity.getDeviceModel());
            headers.put("device-version", deviceInfoEntity.getDeviceVersion());

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.changePassword(changePasswordUrl,headers,changePasswordRequestEntity)
                    .enqueue(new Callback<ChangePasswordResponseEntity>() {
                        @Override
                        public void onResponse(Call<ChangePasswordResponseEntity> call, Response<ChangePasswordResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if(response.code()==200) {
                                    callback.success(response.body());
                                }
                                else {
                                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,
                                            "Service failure but successful response" , 400,null,null));
                                }
                            }
                            else {
                                assert response.errorBody() != null;
                                try {

                                    //Todo Handle proper error message

                                    String errorResponse=response.errorBody().source().readByteString().utf8();

                                    CommonErrorEntity commonErrorEntity;
                                    ErrorEntity errorEntity=new ErrorEntity();
                                    commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);
                                    String errorMessage="";
                                    if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                        errorMessage=commonErrorEntity.getError().toString();
                                    }
                                    else
                                    {
                                        errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                        errorEntity.setCode( ((LinkedHashMap) commonErrorEntity.getError()).get("code").toString());
                                        errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                        errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                        errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                        errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                        errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                                    }


                                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,
                                            errorMessage, commonErrorEntity.getStatus(),commonErrorEntity.getError(),errorEntity));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Timber.e("response"+response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ChangePasswordResponseEntity> call, Throwable t) {
                            Timber.e("Faliure in Request id service call"+t.getMessage());
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CHANGE_PASSWORD_FAILURE,t.getMessage(), 400,null,null));

                        }
                    });

        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }
}
