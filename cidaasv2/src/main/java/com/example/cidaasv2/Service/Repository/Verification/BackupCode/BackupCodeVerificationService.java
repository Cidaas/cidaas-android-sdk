package com.example.cidaasv2.Service.Repository.Verification.BackupCode;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Library.LocationLibrary.LocationDetails;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.BackupCode.AuthenticateBackupCodeResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.BackupCode.InitiateBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.BackupCode.SetupBackupCodeRequestEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class  BackupCodeVerificationService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static  BackupCodeVerificationService shared;

    public  BackupCodeVerificationService(Context contextFromCidaas) {
        sub="";
        statusId="";
        verificationType="";
        context=contextFromCidaas;
        authenticationType="";
        //Todo setValue for authenticationType
        if(service==null) {
            service=new CidaassdkService();
        }
    }



    public static  BackupCodeVerificationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new  BackupCodeVerificationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //setupBackupCodeMFA
    public void setupBackupCodeMFA(String baseurl, String accessToken,DeviceInfoEntity deviceInfoEntityFromparam, final Result<SetupBackupCodeMFAResponseEntity> callback){
        String setupBackupCodeMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                setupBackupCodeMFAUrl=baseurl+ URLHelper.getShared().getSetupBackupCodeMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Error :BackupCodeVerificationService :initiateBackupCodeMFA()"));
                return;
            }

            Map<String, String> headers = new Hashtable<>();

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
            headers.put("access_token",accessToken);
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            SetupBackupCodeRequestEntity setupBackupCodeRequestEntity=new SetupBackupCodeRequestEntity();
            setupBackupCodeRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupBackupCodeMFA(setupBackupCodeMFAUrl,headers, setupBackupCodeRequestEntity).enqueue(new Callback<SetupBackupCodeMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupBackupCodeMFAResponseEntity> call, Response<SetupBackupCodeMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SETUP_BACKUPCODE_MFA_FAILURE,
                                    response.code(),"Error :BackupCodeVerificationService :initiateBackupCodeMFA()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_BACKUPCODE_MFA_FAILURE,
                                response,"Error :BackupCodeVerificationService :initiateBackupCodeMFA()"));
                    }
                }

                @Override
                public void onFailure(Call<SetupBackupCodeMFAResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SETUP_BACKUPCODE_MFA_FAILURE,
                            t.getMessage(), "Error :BackupCodeVerificationService :initiateBackupCodeMFA()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Error :BackupCodeVerificationService :setupBackupCodeMFA()",
                    WebAuthErrorCode.SETUP_BACKUPCODE_MFA_FAILURE,e.getMessage()));
        }
    }


    //initiateBackupCodeMFA
    public void initiateBackupCodeMFA(String baseurl, InitiateBackupCodeMFARequestEntity initiateBackupCodeMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromparam,
                                      final Result<InitiateBackupCodeMFAResponseEntity> callback){
        String initiateBackupCodeMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                initiateBackupCodeMFAUrl=baseurl+URLHelper.getShared().getInitiateBackupCodeMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Error :BackupCodeVerificationService :initiateBackupCodeMFA()"));
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            initiateBackupCodeMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateBackupCodeMFA(initiateBackupCodeMFAUrl,headers, initiateBackupCodeMFARequestEntity).enqueue(new Callback<InitiateBackupCodeMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiateBackupCodeMFAResponseEntity> call, Response<InitiateBackupCodeMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.INITIATE_BACKUPCODE_MFA_FAILURE,
                                    response.code(),"Error :BackupCodeVerificationService :initiateBackupCodeMFA()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_BACKUPCODE_MFA_FAILURE,
                                response,"Error :BackupCodeVerificationService :initiateBackupCodeMFA()"));
                    }
                }

                @Override
                public void onFailure(Call<InitiateBackupCodeMFAResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_BACKUPCODE_MFA_FAILURE,t.getMessage(),
                            "Error :BackupCodeVerificationService :initiateBackupCodeMFA()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :BackupCodeVerificationService :initiateBackupCodeMFA()",
                    WebAuthErrorCode.INITIATE_BACKUPCODE_MFA_FAILURE,e.getMessage()));
        }
    }


    //authenticateBackupCodeMFA
    public void authenticateBackupCodeMFA(String baseurl, AuthenticateBackupCodeRequestEntity authenticateBackupCodeRequestEntity,DeviceInfoEntity deviceInfoEntityFromparam,
                                          final Result<AuthenticateBackupCodeResponseEntity> callback)
    {
        String authenticateBackupCodeMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                authenticateBackupCodeMFAUrl=baseurl+URLHelper.getShared().getAuthenticateBackupCodeMFA();
            }
            else {
                WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Error :BackupCodeVerificationService :authenticateBackupCodeMFA()");
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            authenticateBackupCodeRequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticateBackupCodeMFA(authenticateBackupCodeMFAUrl,headers, authenticateBackupCodeRequestEntity).enqueue(new Callback<AuthenticateBackupCodeResponseEntity>() {
                @Override
                public void onResponse(Call<AuthenticateBackupCodeResponseEntity> call, Response<AuthenticateBackupCodeResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE,
                                    response.code(),"Error :BackupCodeVerificationService :authenticateBackupCodeMFA()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE,response
                                ,"Error :BackupCodeVerificationService :authenticateBackupCodeMFA()"));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateBackupCodeResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE,
                            t.getMessage(), "Error :BackupCodeVerificationService :authenticateBackupCodeMFA()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :BackupCodeVerificationService :authenticateBackupCodeMFA()",WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE,e.getMessage()));
        }
    }
}
