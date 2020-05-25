package de.cidaas.sdk.android.cidaasconsentv2.Domain.Service.Consent;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.CommonError.CommonError;

import de.cidaas.sdk.android.cidaasconsentv2.Domain.Service.CidaasConsentSDKService;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v1.ConsentDetailsV2RequestEntity;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement.ConsentManagementAcceptResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement.ConsentManagementAcceptedRequestEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.ResumeConsent.ResumeConsentEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.ResumeConsent.ResumeConsentResponseEntity;

import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.AcceptConsent.AcceptConsentV2Entity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.AcceptConsent.AcceptConsentV2ResponseEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.ConsentDetails.ConsentDetailsV2ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;
import de.cidaas.sdk.android.cidaasconsentv2.data.Service.ICidaasConsentSDKService;

public class ConsentService {


    private Context context;

    public static ConsentService shared;

    CidaasConsentSDKService service;

    public ConsentService(Context contextFromCidaas) {

        context = contextFromCidaas;
        if (service == null) {
            service = new CidaasConsentSDKService(context);
        }


    }


    public ObjectMapper objectMapper = new ObjectMapper();

    public static ConsentService getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ConsentService(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //---------------------------------------------------------------get Consent String Details--------------------------------------------------------
    public void getConsentDetails(String consentstringDetailsUrl, Map<String, String> headers, final Result<ConsentDetailsResultEntity> callback) {
        final String methodName = "Consent Service  :getConsentDetails()";
        try {
            //Call Service-getRequestId
            ICidaasConsentSDKService cidaasConsentSDKService = service.getInstance();
            cidaasConsentSDKService.getConsentStringDetails(consentstringDetailsUrl, headers).enqueue(new Callback<ConsentDetailsResultEntity>() {
                @Override
                public void onResponse(Call<ConsentDetailsResultEntity> call, Response<ConsentDetailsResultEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.CONSENT_STRING_FAILURE, response.code(),
                                    "Error :" + methodName));
                        }
                    } else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.CONSENT_STRING_FAILURE, response,
                                "Error :" + methodName));
                    }
                }

                @Override
                public void onFailure(Call<ConsentDetailsResultEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.CONSENT_STRING_FAILURE, t.getMessage(),
                            "Error :" + methodName));

                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.CONSENT_STRING_FAILURE, e.getMessage()));
        }
    }


    //---------------------------------------------------------------get ConsentDetailsV2--------------------------------------------------------
    public void getConsentDetailsV2(String consentDetailsUrl, ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity, Map<String, String> headers,
                                    final Result<ConsentDetailsV2ResponseEntity> callback) {
        final String methodName = "ConsentService:getConsentDetailsV2()";
        try {

            //Call Service-getRequestId
            ICidaasConsentSDKService cidaasConsentSDKService = service.getInstance();
            cidaasConsentSDKService.getConsentDetailsV2(consentDetailsUrl, headers, consentDetailsV2RequestEntity).enqueue(new Callback<ConsentDetailsV2ResponseEntity>() {
                @Override
                public void onResponse(Call<ConsentDetailsV2ResponseEntity> call, Response<ConsentDetailsV2ResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.CONSENT_STRING_FAILURE, response.code(),
                                    "Error :" + methodName));
                        }
                    } else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.CONSENT_STRING_FAILURE, response,
                                "Error :" + methodName));
                    }
                }

                @Override
                public void onFailure(Call<ConsentDetailsV2ResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.CONSENT_STRING_FAILURE, t.getMessage(),
                            "Error :" + methodName));
                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.CONSENT_STRING_FAILURE, e.getMessage()));
        }
    }

    //---------------------------------------------------------------------AcceptConsentV2----------------------------------------------------------------

    public void acceptConsentV2(String consentAcceptUrl, AcceptConsentV2Entity acceptConsentV2Entity, Map<String, String> headers,
                                final Result<AcceptConsentV2ResponseEntity> callback) {
        final String methodName = "Consent Service :serviceCallForAcceptConsent()";
        try {
            //Call Service-getRequestId
            ICidaasConsentSDKService cidaasConsentSDKService = service.getInstance();
            cidaasConsentSDKService.acceptConsentV2(consentAcceptUrl, headers, acceptConsentV2Entity).enqueue(
                    new Callback<AcceptConsentV2ResponseEntity>() {
                        @Override
                        public void onResponse(Call<AcceptConsentV2ResponseEntity> call, Response<AcceptConsentV2ResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, response.code(),
                                            "Error :" + methodName));
                                }
                            } else {
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, response,
                                        "Error :" + methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<AcceptConsentV2ResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, t.getMessage(),
                                    "Error :" + methodName));
                        }
                    });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, e.getMessage()));
        }
    }



 /*   public void acceptConsent(String baseurl, ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity,
                              final Result<ConsentManagementAcceptResponseEntity> callback)
    {
        String methodName="Consent Service :acceptConsent()";
        try
        {
            if(baseurl!=null && !baseurl.equals("")){

                //Construct URL For RequestId
                String consentAcceptUrl=baseurl+ VerificationURLHelper.getShared().getAcceptConsent();

                //Header Generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

                //Service call to accept consent
                serviceCallForAcceptConsent(consentAcceptUrl, consentManagementAcceptedRequestEntity, headers, callback);

            }
            else {
                callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE)
                        ,"Error :"+methodName));
                return;
            }

        }
        catch (Exception e)
        {
           callback.failure( WebAuthError.getShared(context).methodException("Exception :"+methodName,
                   WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,e.getMessage()));
        }
    }*/


    public void acceptConsent(String consentAcceptUrl, ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity,
                              Map<String, String> headers, final Result<ConsentManagementAcceptResponseEntity> callback) {
        final String methodName = "Consent Service :serviceCallForAcceptConsent()";
        try {
            //Call Service-getRequestId
            ICidaasConsentSDKService cidaasConsentSDKService = service.getInstance();
            cidaasConsentSDKService.acceptConsent(consentAcceptUrl, headers, consentManagementAcceptedRequestEntity).enqueue(
                    new Callback<ConsentManagementAcceptResponseEntity>() {
                        @Override
                        public void onResponse(Call<ConsentManagementAcceptResponseEntity> call, Response<ConsentManagementAcceptResponseEntity> response) {
                            if (response.isSuccessful()) {
                                if (response.code() == 200) {
                                    callback.success(response.body());
                                } else {
                                    callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, response.code(),
                                            "Error :" + methodName));
                                }
                            } else {
                                callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, response,
                                        "Error :" + methodName));
                            }
                        }

                        @Override
                        public void onFailure(Call<ConsentManagementAcceptResponseEntity> call, Throwable t) {
                            callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, t.getMessage(),
                                    "Error :" + methodName));
                        }
                    });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.ACCEPT_CONSENT_FAILURE, e.getMessage()));
        }
    }

  /*  //------------------------------------------------------------------------Resume Consent-------------------------------------------------------------
    public void resumeConsent(final String baseurl, final ResumeConsentEntity resumeConsentEntity, final Result<ResumeConsentResponseEntity> callback)
    {
        //Local Variables
        String methodName = "Consent Service :resumeConsent()";
        try{

            if(baseurl!=null && !baseurl.equals("")){

                //Construct URL For RequestId
                String resumeConsentUrl=baseurl+URLHelper.getShared().getResumeConsentURL()+ resumeConsentEntity.getTrack_id();

                //Header generation
                Map<String, String> headers = Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

                //service call resume consent
                serviceCallForResumeConsent(resumeConsentEntity,  resumeConsentUrl, headers,callback);
            }
            else
            {
              callback.failure( WebAuthError.getShared(context).propertyMissingException(context.getString(R.string.EMPTY_BASE_URL_SERVICE), "Error :"+methodName));
              return;
            }

        }
        catch (Exception e)
        {
            callback.failure( WebAuthError.getShared(context).methodException("Exception :"+methodName, WebAuthErrorCode.RESUME_CONSENT_FAILURE,e.getMessage()));
        }
    }*/

    //----------------------------------------------------------------ServiceCallForResumeConsent--------------------------------------------------------
    public void resumeConsent(String resumeConsentUrl, ResumeConsentEntity resumeConsentEntity, Map<String, String> headers,
                              final Result<ResumeConsentResponseEntity> callback) {
        final String methodName = "Consent Service :serviceCallForResumeConsent()";
        try {
            //Call Service-getRequestId
            ICidaasConsentSDKService cidaasConsentSDKService = service.getInstance();
            cidaasConsentSDKService.resumeConsent(resumeConsentUrl, headers, resumeConsentEntity).enqueue(new Callback<ResumeConsentResponseEntity>() {
                @Override
                public void onResponse(Call<ResumeConsentResponseEntity> call, Response<ResumeConsentResponseEntity> response) {
                    if (response.isSuccessful()) {
                        if (response.code() == 200) {
                            callback.success(response.body());
                        } else {
                            callback.failure(WebAuthError.getShared(context).emptyResponseException(WebAuthErrorCode.RESUME_CONSENT_FAILURE,
                                    response.code(), "Error :" + methodName));
                        }
                    } else {
                        callback.failure(CommonError.getShared(context).generateCommonErrorEntity(WebAuthErrorCode.RESUME_CONSENT_FAILURE, response,
                                "Error :" + methodName));
                    }
                }

                @Override
                public void onFailure(Call<ResumeConsentResponseEntity> call, Throwable t) {
                    callback.failure(WebAuthError.getShared(context).serviceCallFailureException(WebAuthErrorCode.RESUME_CONSENT_FAILURE, t.getMessage(),
                            "Error :" + methodName));
                }
            });
        } catch (Exception e) {
            callback.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.RESUME_CONSENT_FAILURE, e.getMessage()));
        }
    }
}




