package com.example.cidaasv2.Service.Repository.Verification.Email;

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
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailRequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Email.AuthenticateEmailResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.Email.EnrollEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.InitiateMFA.Email.InitiateEmailMFAResponseEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFARequestEntity;
import com.example.cidaasv2.Service.Entity.MFA.SetupMFA.Email.SetupEmailMFAResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class EmailVerificationService {
    CidaassdkService service;
    private ObjectMapper objectMapper=new ObjectMapper();
    //Local variables
    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static EmailVerificationService shared;

    public EmailVerificationService(Context contextFromCidaas) {
        sub="";
        statusId="";
        verificationType="";
        context=contextFromCidaas;
        authenticationType="";
        //Todo setValue for authenticationType

        if(service==null) {
            service=new CidaassdkService(context);
        }
    }



    public static EmailVerificationService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new EmailVerificationService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //setupemailMFA
    public void setupEmailMFA(String baseurl, String accessToken,DeviceInfoEntity deviceInfoEntityFromParam, final Result<SetupEmailMFAResponseEntity> callback){
       final String methodName="EmailVerificationService :setupEmailMFA()";
        String setupEmailMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                setupEmailMFAUrl=baseurl+ URLHelper.getShared().getSetupEmailMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE)
                        ,"Error :"+methodName));
                return;
            }

            Map<String, String> headers = new Hashtable<>();
            // Get Device Information
            DeviceInfoEntity deviceInfo=new DeviceInfoEntity();
            //This is only for testing purpose
            if(deviceInfoEntityFromParam==null) {
                deviceInfo = DBHelper.getShared().getDeviceInfo();
            }
            else if(deviceInfoEntityFromParam!=null)
            {
                deviceInfo=deviceInfoEntityFromParam;
            }
            //Todo - check Construct Headers pending,Null Checking Pending
            //Add headers
          //  headers.put("Content-Type", URLHelper.contentTypeJson);
            headers.put("lat", LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());
            headers.put("access_token",accessToken);

            SetupEmailMFARequestEntity setupEmailMFARequestEntity =new SetupEmailMFARequestEntity();
            setupEmailMFARequestEntity.setDeviceInfo(deviceInfo);



            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.setupEmailMFA(setupEmailMFAUrl,headers, setupEmailMFARequestEntity).enqueue(new Callback<SetupEmailMFAResponseEntity>() {
                @Override
                public void onResponse(Call<SetupEmailMFAResponseEntity> call, Response<SetupEmailMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.SETUP_EMAIL_MFA_FAILURE,
                                    response.code(),"Error :"+methodName));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.SETUP_EMAIL_MFA_FAILURE,response,
                                "Error :"+methodName));
                    }
                }

                @Override
                public void onFailure(Call<SetupEmailMFAResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.SETUP_EMAIL_MFA_FAILURE,
                            t.getMessage(), "Error :"+methodName));
                }
            });


        }
        catch (Exception e)
        {
           callback.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,WebAuthErrorCode.SETUP_EMAIL_MFA_FAILURE,e.getMessage()));

        }
    }

    //enrollemailMFA
    public void enrollEmailMFA(String baseurl, String accessToken, EnrollEmailMFARequestEntity enrollEmailMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<EnrollEmailMFAResponseEntity> callback){
        String enrollEmailMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                enrollEmailMFAUrl=baseurl+URLHelper.getShared().getEnrollEmailMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE)
                        ,"Error :EmailVerificationService :enrollEmailMFA()"));
                return;
            }

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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());
            headers.put("access_token",accessToken);


            enrollEmailMFARequestEntity.setDeviceInfo(deviceInfoEntity);

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.enrollEmailMFA(enrollEmailMFAUrl,headers, enrollEmailMFARequestEntity).enqueue(new Callback<EnrollEmailMFAResponseEntity>() {
                @Override
                public void onResponse(Call<EnrollEmailMFAResponseEntity> call, Response<EnrollEmailMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.ENROLL_EMAIL_MFA_FAILURE,
                                    response.code(),"Error :EmailVerificationService :enrollEmailMFA()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ENROLL_EMAIL_MFA_FAILURE,response
                                ,"Error :EmailVerificationService :enrollEmailMFA()"));
                    }
                }

                @Override
                public void onFailure(Call<EnrollEmailMFAResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ENROLL_EMAIL_MFA_FAILURE,t.getMessage(),
                            "Error :EmailVerificationService :enrollEmailMFA()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :EmailVerificationService :enrollEmailMFA()",
                    WebAuthErrorCode.ENROLL_EMAIL_MFA_FAILURE,e.getMessage()));

        }
    }


    //initiateemailMFA
    public void initiateEmailMFA(String baseurl, InitiateEmailMFARequestEntity initiateEmailMFARequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<InitiateEmailMFAResponseEntity> callback){
        String initiateEmailMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                initiateEmailMFAUrl=baseurl+URLHelper.getShared().getInitiateemailMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE),
                        "Error :EmailVerificationService :initiateEmailMFA()"));
                return;
            }

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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());

            initiateEmailMFARequestEntity.setDeviceInfo(deviceInfoEntity);


            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.initiateEmailMFA(initiateEmailMFAUrl,headers, initiateEmailMFARequestEntity).enqueue(new Callback<InitiateEmailMFAResponseEntity>() {
                @Override
                public void onResponse(Call<InitiateEmailMFAResponseEntity> call, Response<InitiateEmailMFAResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.INITIATE_EMAIL_MFA_FAILURE,
                                    response.code(),"Error :EmailVerificationService :initiateEmailMFA()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.INITIATE_EMAIL_MFA_FAILURE,response
                                ,"Error :EmailVerificationService :initiateEmailMFA()"));
                    }
                }

                @Override
                public void onFailure(Call<InitiateEmailMFAResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.INITIATE_EMAIL_MFA_FAILURE,t.getMessage(),
                            "Error :EmailVerificationService :initiateEmailMFA()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :EmailVerificationService :initiateEmailMFA()",
                    WebAuthErrorCode.INITIATE_EMAIL_MFA_FAILURE,e.getMessage()));

        }
    }


    //authenticateemailMFA
    public void authenticateEmailMFA(String baseurl, AuthenticateEmailRequestEntity authenticateEmailRequestEntity,DeviceInfoEntity deviceInfoEntityFromParam, final Result<AuthenticateEmailResponseEntity> callback){
        String authenticateemailMFAUrl="";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){
                //Construct URL For RequestId
                authenticateemailMFAUrl=baseurl+URLHelper.getShared().getAuthenticateemailMFA();
            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE)
                        ,"Error :EmailVerificationService :authenticateEmailMFA()"));
                return;
            }

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
            headers.put("lat",LocationDetails.getShared(context).getLatitude());
            headers.put("lon",LocationDetails.getShared(context).getLongitude());
            authenticateEmailRequestEntity.setDeviceInfo(deviceInfoEntity);


            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.authenticateEmailMFA(authenticateemailMFAUrl,headers, authenticateEmailRequestEntity).enqueue(new Callback<AuthenticateEmailResponseEntity>() {
                @Override
                public void onResponse(Call<AuthenticateEmailResponseEntity> call, Response<AuthenticateEmailResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.AUTHENTICATE_EMAIL_MFA_FAILURE,
                                    response.code(),"Error :EmailVerificationService :authenticateEmailMFA()"));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.AUTHENTICATE_EMAIL_MFA_FAILURE,response
                                ,"Error :EmailVerificationService :authenticateEmailMFA()"));
                    }
                }

                @Override
                public void onFailure(Call<AuthenticateEmailResponseEntity> call, Throwable t) {
                    callback.failure( WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.AUTHENTICATE_EMAIL_MFA_FAILURE,t.getMessage(),
                            "Error :EmailVerificationService :authenticateEmailMFA()"));
                }
            });


        }
        catch (Exception e)
        {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :EmailVerificationService :authenticateEmailMFA()"
                    ,WebAuthErrorCode.AUTHENTICATE_EMAIL_MFA_FAILURE,e.getMessage()));
        }
    }

}
