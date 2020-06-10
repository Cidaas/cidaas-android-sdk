package de.cidaas.sdk.android.consent.domain.service.consent;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import de.cidaas.sdk.android.consent.ConsentDetailsRequestEntity;
import de.cidaas.sdk.android.consent.data.entity.acceptconsent.AcceptConsentV2Entity;
import de.cidaas.sdk.android.consent.data.entity.acceptconsent.AcceptConsentV2ResponseEntity;
import de.cidaas.sdk.android.consent.data.entity.consentdetails.ConsentDetailsResponseEntity;
import de.cidaas.sdk.android.consent.data.entity.resumeconsent.ResumeConsentEntity;
import de.cidaas.sdk.android.consent.data.entity.resumeconsent.ResumeConsentResponseEntity;
import de.cidaas.sdk.android.consent.data.service.ICidaasConsentSDKService;
import de.cidaas.sdk.android.consent.domain.service.CidaasConsentSDKService;
import de.cidaas.sdk.android.helper.commonerror.CommonError;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentDetailsResultEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptResponseEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptedRequestEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

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
    public void getConsentDetails(String consentstringDetailsUrl, Map<String, String> headers, final EventResult<ConsentDetailsResultEntity> callback) {
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
    public void getConsentDetailsV2(String consentDetailsUrl, ConsentDetailsRequestEntity consentDetailsRequestEntity, Map<String, String> headers,
                                    final EventResult<ConsentDetailsResponseEntity> callback) {
        final String methodName = "ConsentService:getConsentDetailsV2()";
        try {

            //Call Service-getRequestId
            ICidaasConsentSDKService cidaasConsentSDKService = service.getInstance();
            cidaasConsentSDKService.getConsentDetailsV2(consentDetailsUrl, headers, consentDetailsRequestEntity).enqueue(new Callback<ConsentDetailsResponseEntity>() {
                @Override
                public void onResponse(Call<ConsentDetailsResponseEntity> call, Response<ConsentDetailsResponseEntity> response) {
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
                public void onFailure(Call<ConsentDetailsResponseEntity> call, Throwable t) {
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
                                final EventResult<AcceptConsentV2ResponseEntity> callback) {
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

    public void acceptConsent(String consentAcceptUrl, ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity,
                              Map<String, String> headers, final EventResult<ConsentManagementAcceptResponseEntity> callback) {
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

    //----------------------------------------------------------------ServiceCallForResumeConsent--------------------------------------------------------
    public void resumeConsent(String resumeConsentUrl, ResumeConsentEntity resumeConsentEntity, Map<String, String> headers,
                              final EventResult<ResumeConsentResponseEntity> callback) {
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




