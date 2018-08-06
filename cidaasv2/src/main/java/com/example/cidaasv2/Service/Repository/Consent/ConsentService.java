package com.example.cidaasv2.Service.Repository.Consent;

import android.content.Context;

import com.example.cidaasv2.Helper.Entity.CommonErrorEntity;
import com.example.cidaasv2.Helper.Entity.DeviceInfoEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.URLHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.R;
import com.example.cidaasv2.Service.CidaassdkService;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultDataEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptedRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentResponseEntity;
import com.example.cidaasv2.Service.ICidaasSDKService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ConsentService {


    private Context context;

    public static ConsentService shared;

    public ConsentService(Context contextFromCidaas) {

        context=contextFromCidaas;
        if(service==null) {
            service=new CidaassdkService();
        }

        //Todo setValue for authenticationType

    }

    CidaassdkService service;
    public ObjectMapper objectMapper=new ObjectMapper();

    public static ConsentService getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new ConsentService(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //get ConsentUrl
    public void getConsentUrl(final String baseurl,String consentName,String consentVersion,final Result<String> callback) {
        String ConsentcallUrl;
        try {

            if (baseurl != null || baseurl != "") {
                //Construct URL For RequestId
                ConsentcallUrl = baseurl + URLHelper.getShared().getConsent_url(consentName,consentVersion);
            } else {
                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400, null));
                return;
            }

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getConsentInfo(ConsentcallUrl).enqueue(new Callback<ConsentManagementResponseEntity>() {
                @Override
                public void onResponse(Call<ConsentManagementResponseEntity> call, Response<ConsentManagementResponseEntity> response) {
                    if(response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body().getData());
                        }
                        else if(response.code()==204)
                        {
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONSENT_URL_FAILURE, "No data found", response.code(), null));
                        }
                        else
                        {
                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONSENT_URL_FAILURE,
                                    "Service failure but successful response", response.code(), null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse = response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                            String errorMessage="";
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }




                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONSENT_URL_FAILURE, errorMessage, commonErrorEntity.getStatus()
                                    , commonErrorEntity.getError()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Timber.e("response" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ConsentManagementResponseEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call" + t.getMessage());
                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONSENT_URL_FAILURE, t.getMessage(), 400, null));

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONSENT_URL_FAILURE, e.getMessage(), 400, null));

        }
    }


    //get Consent String Details
    public void getConsentStringDetails(final String baseurl,String consentName,final Result<ConsentDetailsResultEntity> callback) {
        String ConsentstringDetailsUrl;
        try {

            if (baseurl != null || baseurl != "") {
                //Construct URL For RequestId
                ConsentstringDetailsUrl = baseurl +URLHelper.getShared().getConsent_string_details(consentName);
            } else {
                callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400, null));
                return;
            }

            //Call Service-getRequestId
            ICidaasSDKService cidaasSDKService = service.getInstance();
            cidaasSDKService.getConsentStringDetails(ConsentstringDetailsUrl).enqueue(new Callback<ConsentDetailsResultEntity>() {
                @Override
                public void onResponse(Call<ConsentDetailsResultEntity> call, Response<ConsentDetailsResultEntity> response) {
                    if(response.isSuccessful()) {

                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONSENT_STRING_FAILURE,
                                    "Service failure but successful response" , 400,null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        try {

                            //Todo Handle proper error message
                            String errorResponse = response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);


                            String errorMessage="";
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }



                            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONSENT_STRING_FAILURE, errorMessage, commonErrorEntity.getStatus()
                                    , commonErrorEntity.getError()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Timber.e("response" + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ConsentDetailsResultEntity> call, Throwable t) {
                    Timber.e("Faliure in Request id service call" + t.getMessage());
                    callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONSENT_STRING_FAILURE, t.getMessage(), 400, null));

                }
            });
        }
        catch (Exception e)
        {
            Timber.d(e.getMessage());
            callback.failure(WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.CONSENT_STRING_FAILURE, e.getMessage(), 400, null));

        }
    }


    //AcceptConsent
    public void acceptConsent(String baseurl, ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity, final Result<ConsentManagementAcceptResponseEntity> callback)
    {
        String consentAcceptUrl="";
        try
        {
            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                consentAcceptUrl=baseurl+URLHelper.getShared().getAcceptConsent();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null));
                return;
            }

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


            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.acceptConsent(consentAcceptUrl,headers, consentManagementAcceptedRequestEntity).enqueue(new Callback<ConsentManagementAcceptResponseEntity>() {
                @Override
                public void onResponse(Call<ConsentManagementAcceptResponseEntity> call, Response<ConsentManagementAcceptResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,
                                    "Service failure but successful response" , response.code(),null));
                        }
                    }
                    else {
                        assert response.errorBody() != null;
                        //Todo Check The error if it is not recieved
                        try {

                            // Handle proper error message
                            String errorResponse=response.errorBody().source().readByteString().utf8();
                            final CommonErrorEntity commonErrorEntity;
                            commonErrorEntity=objectMapper.readValue(errorResponse,CommonErrorEntity.class);

                            String errorMessage="";
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError()));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,e.getMessage(), 400,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ConsentManagementAcceptResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    LogFile.addRecordToLog("acceptConsent Service Failure"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,t.getMessage(), 400,null));
                }
            });


        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("acceptConsent Service exception"+e.getMessage());
            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,e.getMessage(), 400,null));

            Timber.e("acceptConsent Service exception"+e.getMessage());
        }
    }

    //Resume Consent
    public void resumeConsent(final String baseurl, final ResumeConsentRequestEntity resumeConsentRequestEntity, final Result<ResumeConsentResponseEntity> callback)
    {
        //Local Variables

        String resumeConsentUrl = "";
        try{

            if(baseurl!=null || baseurl!=""){
                //Construct URL For RequestId
                resumeConsentUrl=baseurl+URLHelper.getShared().getResumeConsentURL()+resumeConsentRequestEntity.getTrack_id();
            }
            else {
                callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.PROPERTY_MISSING,
                        context.getString(R.string.PROPERTY_MISSING), 400,null));
                return;
            }


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

            //Call Service-getRequestId
            final ICidaasSDKService cidaasSDKService = service.getInstance();

            cidaasSDKService.resumeConsent(resumeConsentUrl,headers,resumeConsentRequestEntity).enqueue(new Callback<ResumeConsentResponseEntity>() {
                @Override
                public void onResponse(Call<ResumeConsentResponseEntity> call, Response<ResumeConsentResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if(response.code()==200) {
                            callback.success(response.body());
                        }
                        else {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_CONSENT_FAILURE,
                                    "Service failure but successful response" , response.code(),null));
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
                            if(commonErrorEntity.getError()!=null && !commonErrorEntity.getError().toString().equals("") && commonErrorEntity.getError() instanceof  String) {
                                errorMessage=commonErrorEntity.getError().toString();
                            }
                            else
                            {
                                errorMessage = ((LinkedHashMap) commonErrorEntity.getError()).get("error").toString();
                            }


                            //Todo Service call For fetching the Consent details
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_CONSENT_FAILURE,
                                    errorMessage, commonErrorEntity.getStatus(),
                                    commonErrorEntity.getError()));

                        } catch (Exception e) {
                            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_CONSENT_FAILURE,e.getMessage(), 400,null));
                            Timber.e("response"+response.message()+e.getMessage());
                        }
                        Timber.e("response"+response.message());
                    }
                }

                @Override
                public void onFailure(Call<ResumeConsentResponseEntity> call, Throwable t) {
                    Timber.e("Failure in Login with credentials service call"+t.getMessage());
                    callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,t.getMessage(), 400,null));
                }
            });
        }
        catch (Exception e)
        {
            LogFile.addRecordToLog("LoginWithCredentials Service exception"+e.getMessage());
            Timber.d(e.getMessage());
            callback.failure( WebAuthError.getShared(context).serviceFailureException(WebAuthErrorCode.RESUME_LOGIN_FAILURE,e.getMessage(), 400,null));

        }
    }
}
