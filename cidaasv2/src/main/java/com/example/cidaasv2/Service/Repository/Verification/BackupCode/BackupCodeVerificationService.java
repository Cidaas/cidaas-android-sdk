package com.example.cidaasv2.Service.Repository.Verification.BackupCode;

import android.content.Context;

import com.example.cidaasv2.Helper.CommonError.CommonError;
import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Entity.ErrorEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
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
import java.util.LinkedHashMap;
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
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null,null));
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
            headers.put("long",LocationDetails.getShared(context).getLongitude());

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
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_BACKUPCODE_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_BACKUPCODE_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<SetupBackupCodeMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Setup BackupCode service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Setup BackupCode Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.SETUP_BACKUPCODE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Setup BackupCode Service exception"+e.getMessage());
            Timber.e("Setup BackupCode Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.SETUP_BACKUPCODE_MFA_FAILURE));
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("long",LocationDetails.getShared(context).getLongitude());

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
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_BACKUPCODE_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_BACKUPCODE_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<InitiateBackupCodeMFAResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Initiate BackUpCode MFA  call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Initiate BackUpCode MFA Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.INITIATE_BACKUPCODE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Initiate BackUpCode MFA Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.INITIATE_BACKUPCODE_MFA_FAILURE));
            Timber.e("Initiate BackUpCode MFA Service exception"+e.getMessage());
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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("long",LocationDetails.getShared(context).getLongitude());

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
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE,
                                    "Service failure but successful response" , response.code(),null,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE,response));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateBackupCodeResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Authenticate Face service call"+t.getMessage());
                    LogFile.getShared(context).addRecordToLog("Authenticate Face Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE,t.getMessage(), 400,null,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.getShared(context).addRecordToLog("Authenticate BackupCode MFA Service exception"+e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.AUTHENTICATE_BACKUPCODE_MFA_FAILURE));
            Timber.e("Authenticate BackupCode MFA Service exception"+e.getMessage());
        }
    }
}
