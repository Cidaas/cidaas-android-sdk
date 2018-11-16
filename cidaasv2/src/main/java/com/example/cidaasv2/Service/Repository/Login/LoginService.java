package com.example.cidaasv2.Service.Repository.Login;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.RequestId.RequestIdController;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.URLHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseErrorEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginRequestEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.ResumeLogin.ResumeLoginResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoginService {


    private Context context;

    public static LoginService shared;

    CidaassdkService service;
    public ObjectMapper objectMapper=new ObjectMapper();

    public LoginService(Context contextFromCidaas) {
        if(service==null) {
            service=new CidaassdkService();
        }

        context=contextFromCidaas;
        //Todo setValue for authenticationType

    }

    public static LoginService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new LoginService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //Login With Credentials
    public void loginWithCredentials(final String baseurl, final LoginCredentialsRequestEntity loginCredentialsRequestEntity, DeviceInfoEntity deviceInfoEntityFromparam,final Result<LoginCredentialsResponseEntity> callback)
    {
        //Local Variables

        String loginUrl = "";
        try{

            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                loginUrl=baseurl+ URLHelper.getShared().getLoginWithCredentials();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }


            Map<String, String> headers = new Hashtable<>();
            // Get Device Information


            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromparam==null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromparam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromparam;
            }




            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("deviceId", deviceInfoEntity.getDeviceId());
            headers.put("deviceMake", deviceInfoEntity.getDeviceMake());
            headers.put("deviceModel", deviceInfoEntity.getDeviceModel());
            headers.put("deviceVersion", deviceInfoEntity.getDeviceVersion());


            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.loginWithCredentials(loginUrl,headers, loginCredentialsRequestEntity).enqueue(new Callback<LoginCredentialsResponseEntity>() {
                @Override
                public void onResponse(Call<LoginCredentialsResponseEntity> call, Response<LoginCredentialsResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            // Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();
                            final LoginCredentialsResponseErrorEntity loginCredentialsResponseErrorEntity;
                            loginCredentialsResponseErrorEntity=objectMapper.readValue(errorResponse,LoginCredentialsResponseErrorEntity.class);


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,
                                    loginCredentialsResponseErrorEntity.getError().getError(), loginCredentialsResponseErrorEntity.getStatus(),
                                    loginCredentialsResponseErrorEntity.getError(),null));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,e.getMessage(), 400,null,null));
                           // Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<LoginCredentialsResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.LOGINWITH_CREDENTIALS_FAILURE,t.getMessage(), 400,null,null));
                }
            });
        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("LoginWithCredentials Service exception"+e.getMessage());
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    //Resume Login After MFA
    public void continueMFA(final String baseurl, final ResumeLoginRequestEntity resumeLoginRequestEntity, DeviceInfoEntity deviceInfoEntityFromparam,final Result<ResumeLoginResponseEntity> callback)
    {
        //Local Variables

        String resumeloginUrl = "";
        try{

            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                resumeloginUrl=baseurl+URLHelper.getShared().getResumeLoginURL()+resumeLoginRequestEntity.getTrack_id();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }


            Map<String, String> headers = new Hashtable<>();
            // Get Device Information


            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromparam==null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromparam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromparam;
            }


            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("deviceId", deviceInfoEntity.getDeviceId());
            headers.put("deviceMake", deviceInfoEntity.getDeviceMake());
            headers.put("deviceModel", deviceInfoEntity.getDeviceModel());
            headers.put("deviceVersion", deviceInfoEntity.getDeviceVersion());

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.resumeLogin(resumeloginUrl,headers,resumeLoginRequestEntity).enqueue(new Callback<ResumeLoginResponseEntity>() {
                @Override
                public void onResponse(Call<ResumeLoginResponseEntity> call, Response<ResumeLoginResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            // Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);

                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage(), 400,null,null));
                            //Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ResumeLoginResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,t.getMessage(), 400,null,null));
                }
            });
        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("LoginWithCredentials Service exception"+e.getMessage());
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

    //Resume Login
    public void continuePasswordless(final String baseurl, final ResumeLoginRequestEntity resumeLoginRequestEntity, DeviceInfoEntity deviceInfoEntityFromparam,final Result<ResumeLoginResponseEntity> callback)
    {
        //Local Variables

        String continuePasswordlessUrl = "";
        try{

            if(baseurl!=null && baseurl!=""){
                //Construct URL For RequestId
                continuePasswordlessUrl=baseurl+URLHelper.getShared().getPasswordlessContinueUrl()+resumeLoginRequestEntity.getTrack_id();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
                return;
            }


            Map<String, String> headers = new Hashtable<>();
            // Get Device Information


            DeviceInfoEntity deviceInfoEntity=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromparam==null) {
                deviceInfoEntity = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromparam!=null)
            {
                deviceInfoEntity=deviceInfoEntityFromparam;
            }

            //Done check Construct Headers pending,Null Checking Pending
            //Add headers
            headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("user-agent", "cidaas-android");
            headers.put("deviceId", deviceInfoEntity.getDeviceId());
            headers.put("deviceMake", deviceInfoEntity.getDeviceMake());
            headers.put("deviceModel", deviceInfoEntity.getDeviceModel());
            headers.put("deviceVersion", deviceInfoEntity.getDeviceVersion());

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.resumeLogin(continuePasswordlessUrl,headers,resumeLoginRequestEntity).enqueue(new Callback<ResumeLoginResponseEntity>() {
                @Override
                public void onResponse(Call<ResumeLoginResponseEntity> call, Response<ResumeLoginResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            // Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);

                            String errorMessage="";
                            ErrorEntity errorEntity=new ErrorEntity();
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                if(commonErrorEntity.getError_description()!=null && !commonErrorEntity.getError_description().equals(""))
                                {
                                    errorMessage=commonErrorEntity.getError_description();
                                }
                                else {
                                    errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                                errorEntity.setCode((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("code"));
                                errorEntity.setError( ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString());
                                errorEntity.setMoreInfo( ((LinkedHashMap) commonErrorEntity.getError()).get("moreInfo").toString());
                                errorEntity.setReferenceNumber( ((LinkedHashMap) commonErrorEntity.getError()).get("referenceNumber").toString());
                                errorEntity.setStatus((Integer) ((LinkedHashMap) commonErrorEntity.getError()).get("status"));
                                errorEntity.setType( ((LinkedHashMap) commonErrorEntity.getError()).get("type").toString());
                                }
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError(),errorEntity));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage(), 400,null,null));
                           // Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ResumeLoginResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,t.getMessage(), 400,null,null));
                }
            });
        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("LoginWithCredentials Service exception"+e.getMessage());
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).propertyMissingException());
        }
    }

}
