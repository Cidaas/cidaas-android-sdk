package de.cidaas.sdk.android.consent.domain.controller.consent;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Dictionary;
import java.util.Map;

import de.cidaas.sdk.android.consent.ConsentDetailsRequestEntity;
import de.cidaas.sdk.android.consent.ConsentEntity;
import de.cidaas.sdk.android.consent.data.entity.acceptconsent.AcceptConsentV2Entity;
import de.cidaas.sdk.android.consent.data.entity.acceptconsent.AcceptConsentV2ResponseEntity;
import de.cidaas.sdk.android.consent.data.entity.consentdetails.ConsentDetailsResponseEntity;
import de.cidaas.sdk.android.consent.data.entity.resumeconsent.ResumeConsentEntity;
import de.cidaas.sdk.android.consent.data.entity.resumeconsent.ResumeConsentResponseEntity;
import de.cidaas.sdk.android.consent.domain.service.consent.ConsentService;
import de.cidaas.sdk.android.consent.helper.ConsentConstants;
import de.cidaas.sdk.android.consent.helper.ConsentURLHelper;
import de.cidaas.sdk.android.controller.AccessTokenController;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.urlhelper.URLHelper;
import de.cidaas.sdk.android.properties.CidaasProperties;
import de.cidaas.sdk.android.service.entity.accesstoken.AccessTokenEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentDetailsResultEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptResponseEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptedRequestEntity;
import de.cidaas.sdk.android.service.helperforservice.Headers.Headers;
import timber.log.Timber;

public class ConsentController {

    private Context context;
    private String ConsentName = "";
    private String ConsentVersion = "";

    public static ConsentController shared;

    public ConsentController(Context contextFromCidaas) {

        context = contextFromCidaas;

    }

    public static ConsentController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ConsentController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }


    //Get Consent URL
    public void getConsentURL(@NonNull String consentName, @NonNull String consentVersion, @NonNull final EventResult<String> result) {

    }

    //-------------------------------------------------------------getConsentDetails----------------------------------------------------------------
    public void getConsentDetails(@NonNull final String consentName, @NonNull final EventResult<ConsentDetailsResultEntity> consentresult) {
        checkConsentDetailsProperties(consentName, consentresult);
    }

    private void checkConsentDetailsProperties(@NonNull final String consentName, @NonNull final EventResult<ConsentDetailsResultEntity> consentresult) {
        String methodName = "ConsentController :checkConsentDetailsProperties()";
        try {
            if (consentName != null && !consentName.equals("")) {
                //Call getString Details
                serviceForConsentDetails(consentName, consentresult);
            } else {
                consentresult.failure(WebAuthError.getShared(context).propertyMissingException("Consent Name must not be null", methodName));
                return;
            }
        } catch (Exception e) {
            consentresult.failure(WebAuthError.getShared(context).methodException(ConsentConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForConsentDetails(@NonNull final String consentName, @NonNull final EventResult<ConsentDetailsResultEntity> consentresult) {
        final String methodName = "ConsentController :serviceForConsentDetails()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get(ConsentConstants.DOMAIN_URL);
                    String clientId = result.get(ConsentConstants.CLIENT_ID);

                    String consentDetailsURL = ConsentURLHelper.getShared().getConsent_details(baseurl, consentName);

                    //Headers generation
                    Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, null);

                    //service call For consent details
                    ConsentService.getShared(context).getConsentDetails(consentDetailsURL, headers, consentresult);

                }

                @Override
                public void failure(WebAuthError error) {
                    consentresult.failure(error);
                }
            });

        } catch (Exception e) {
            consentresult.failure(WebAuthError.getShared(context).methodException(ConsentConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE, e.getMessage()));
        }
    }


    //----------------------------------------------------Service call To AcceptConsent--------------------------------------------------------------
    public void acceptConsent(@NonNull final ConsentEntity consentEntity, final EventResult<LoginCredentialsResponseEntity> loginresult) {
        checkAcceptConsentProperties(consentEntity, loginresult);
    }

    private void checkAcceptConsentProperties(@NonNull final ConsentEntity consentEntity, final EventResult<LoginCredentialsResponseEntity> loginresult) {
        String methodName = "ConsentController :checkAcceptConsentProperties()";
        try {
            if (consentEntity.getSub() != null && !consentEntity.getSub().equals("") && consentEntity.getConsentName() != null
                    && !consentEntity.getConsentName().equals("") && consentEntity.getConsentVersion() != null
                    && !consentEntity.getConsentVersion().equals("") && consentEntity.isAccepted()) {

                addAcceptConsentProperties(consentEntity, loginresult);
            } else {
                loginresult.failure(WebAuthError.getShared(context).propertyMissingException(
                        "Sub or ConsentName or ConsentVersion must not be null and isAccept must be true", methodName));
            }
        } catch (Exception e) {
            loginresult.failure(WebAuthError.getShared(context).methodException(ConsentConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE,
                    e.getMessage()));
        }
    }

    private void serviceForAcceptConsent(@NonNull final ConsentManagementAcceptedRequestEntity consentEntity,
                                         @NonNull final EventResult<LoginCredentialsResponseEntity> loginResult) {
        final String methodName = "ConsentController :serviceForConsentDetails()";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    String baseurl = result.get("DomainURL");
                    String clientId = result.get("ClientId");

                    consentEntity.setClient_id(clientId);

                    String acceptConsentURL = ConsentURLHelper.getShared().getAcceptConsent(baseurl);

                    serviceCallForAcceptConsent(acceptConsentURL, consentEntity, loginResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    loginResult.failure(error);
                }
            });

        } catch (Exception e) {
            loginResult.failure(WebAuthError.getShared(context).methodException(ConsentConstants.EXCEPTION_LOGGING_PREFIX+ methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE,
                    e.getMessage()));
        }
    }


    private void serviceCallForAcceptConsent(final String acceptConsentURL, final ConsentManagementAcceptedRequestEntity consentEntity,
                                             final EventResult<LoginCredentialsResponseEntity> loginresult) {
        final String methodName = "ConsentController :serviceCallForAcceptConsent()";
        try {

            //Headers generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, null);

            //service call For consent details
            ConsentService.getShared(context).acceptConsent(acceptConsentURL, consentEntity, headers, new EventResult<ConsentManagementAcceptResponseEntity>() {
                @Override
                public void success(ConsentManagementAcceptResponseEntity result) {

                    // Call Resume call
                }

                @Override
                public void failure(WebAuthError error) {
                    loginresult.failure(error);
                }
            });
        } catch (Exception e) {
            loginresult.failure(WebAuthError.getShared(context).methodException(ConsentConstants.EXCEPTION_LOGGING_PREFIX + methodName, WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,
                    e.getMessage()));
        }
    }

    // Log

    private void addAcceptConsentProperties(final ConsentEntity consentEntity, final EventResult<LoginCredentialsResponseEntity> loginresult) {
        //For Naming and Client ID use seperate entity
        ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity = new ConsentManagementAcceptedRequestEntity();
        consentManagementAcceptedRequestEntity.setAccepted(consentEntity.isAccepted());
        consentManagementAcceptedRequestEntity.setSub(consentEntity.getSub());
        consentManagementAcceptedRequestEntity.setName(consentEntity.getConsentName());
        consentManagementAcceptedRequestEntity.setTrackId(consentEntity.getTrackId());
        consentManagementAcceptedRequestEntity.setVersion(consentEntity.getConsentVersion());

        serviceForAcceptConsent(consentManagementAcceptedRequestEntity, loginresult);
    }


    //------------------------------------------------------Consent V2------------------------------------------------------------------------

    public void getConsentDetailsV2(@NonNull ConsentDetailsRequestEntity consentDetailsV2Entity, EventResult<ConsentDetailsResponseEntity> consentDetails) {
        checkProperties(consentDetailsV2Entity, consentDetails);
    }

    private void checkProperties(ConsentDetailsRequestEntity consentDetailsRequestEntity, EventResult<ConsentDetailsResponseEntity> consentDetails) {
        String methodName = "ConsentController:checkProperties";
        try {
            if (!consentDetailsRequestEntity.getConsent_id().equals("") && consentDetailsRequestEntity.getConsent_version_id() != null &&
                    !consentDetailsRequestEntity.getConsent_version_id().equals("") && consentDetailsRequestEntity.getConsent_id() != null) {
                if (consentDetailsRequestEntity.getSub() != null && !consentDetailsRequestEntity.getSub().equals("") &&
                        consentDetailsRequestEntity.getTrack_id() != null && !consentDetailsRequestEntity.getTrack_id().equals("")) {
                    if (consentDetailsRequestEntity.getRequestId() != null && !consentDetailsRequestEntity.getRequestId().equals("")) {
                        addProperties(consentDetailsRequestEntity, consentDetails);
                    } else {
                        consentDetails.failure(WebAuthError.getShared(context).propertyMissingException("RequestId must not be null", methodName));
                    }
                } else {
                    consentDetails.failure(WebAuthError.getShared(context).propertyMissingException("Track id or sub must not be null", methodName));
                }
            } else {
                consentDetails.failure(WebAuthError.getShared(context).propertyMissingException("Consent id or Consent Version id must not be null", methodName));
            }
        } catch (Exception e) {
            consentDetails.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE, e.getMessage()));
        }
    }

    private void addProperties(final ConsentDetailsRequestEntity consentDetailsV2Entity, final EventResult<ConsentDetailsResponseEntity> consentDetails) {
        String methodName = "ConsentController:addProperties";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    if (consentDetailsV2Entity.getClient_id() == null || consentDetailsV2Entity.getClient_id().equals("")) {
                        consentDetailsV2Entity.setClient_id(result.get(ConsentConstants.CLIENT_ID));
                        serviceForGetConsentDetailsV2(result.get(ConsentConstants.DOMAIN_URL), consentDetailsV2Entity, consentDetails);
                    } else {
                        serviceForGetConsentDetailsV2(result.get(ConsentConstants.DOMAIN_URL), consentDetailsV2Entity, consentDetails);
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    consentDetails.failure(error);
                }
            });
        } catch (Exception e) {
            consentDetails.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE, e.getMessage()));
        }
    }

    private void serviceForGetConsentDetailsV2(String baseurl, ConsentDetailsRequestEntity consentDetailsRequestEntity, EventResult<ConsentDetailsResponseEntity> consentDetails) {
        String methodName = "ConsentController:serviceForGetConsentDetailsV2";
        try {
            String consentDetailsUrl = ConsentURLHelper.getShared().getConsentDetailsV2(baseurl);

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Service
            ConsentService.getShared(context).getConsentDetailsV2(consentDetailsUrl, consentDetailsRequestEntity, headers, consentDetails);
        } catch (Exception e) {
            consentDetails.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE, e.getMessage()));
        }

    }

//-----------------------------------------------Accept Consent V2-------------------------------------------------------------------------

    public void acceptConsentV2(@NonNull AcceptConsentV2Entity acceptConsentV2Entity, EventResult<LoginCredentialsResponseEntity> consentDetails) {
        checkPropertiesForAcceptConsentV2(acceptConsentV2Entity, consentDetails);
    }

    private void checkPropertiesForAcceptConsentV2(AcceptConsentV2Entity acceptConsentV2Entity, EventResult<LoginCredentialsResponseEntity> consentDetails) {
        String methodName = "ConsentController:checkPropertiesForAcceptConsentV2";
        try {
            if (!acceptConsentV2Entity.getConsent_id().equals("") && acceptConsentV2Entity.getConsent_version_id() != null &&
                    !acceptConsentV2Entity.getConsent_version_id().equals("") && acceptConsentV2Entity.getConsent_id() != null) {
                if (acceptConsentV2Entity.getSub() != null && !acceptConsentV2Entity.getSub().equals("") &&
                        acceptConsentV2Entity.getTrackId() != null && !acceptConsentV2Entity.getTrackId().equals("")) {

                    addPropertiesForAcceptConsentV2(acceptConsentV2Entity, consentDetails);
                } else {
                    consentDetails.failure(WebAuthError.getShared(context).propertyMissingException("Track id or sub must not be null", methodName));
                }
            } else {
                consentDetails.failure(WebAuthError.getShared(context).propertyMissingException("Consent id or Consent Version id must not be null", methodName));
            }
        } catch (Exception e) {
            consentDetails.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE, e.getMessage()));
        }
    }

    private void addPropertiesForAcceptConsentV2(final AcceptConsentV2Entity acceptConsentV2Entity, final EventResult<LoginCredentialsResponseEntity> consentDetails) {
        String methodName = "ConsentController:addProperties";
        try {
            CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    if (acceptConsentV2Entity.getClient_id() == null || acceptConsentV2Entity.getClient_id().equals("")) {
                        acceptConsentV2Entity.setClient_id(result.get(ConsentConstants.CLIENT_ID));
                        serviceForAcceptConsentV2(result.get(ConsentConstants.DOMAIN_URL), acceptConsentV2Entity, consentDetails);
                    } else {
                        serviceForAcceptConsentV2(result.get(ConsentConstants.DOMAIN_URL), acceptConsentV2Entity, consentDetails);
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    consentDetails.failure(error);
                }
            });
        } catch (Exception e) {
            consentDetails.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE, e.getMessage()));
        }
    }

    private void serviceForAcceptConsentV2(final String baseurl, final AcceptConsentV2Entity acceptConsentV2, final EventResult<LoginCredentialsResponseEntity> loginResult) {
        String methodName = "ConsentController:serviceForAcceptConsentV2";
        try {
            String consentDetailsUrl = ConsentURLHelper.getShared().getAcceptConsentV2(baseurl);

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Service
            ConsentService.getShared(context).acceptConsentV2(consentDetailsUrl, acceptConsentV2, headers, new EventResult<AcceptConsentV2ResponseEntity>() {
                @Override
                public void success(AcceptConsentV2ResponseEntity result) {
                    // call Resume Call
                    ResumeConsentEntity resumeConsentEntity = new ResumeConsentEntity(acceptConsentV2.getSub(), acceptConsentV2.getTrackId()
                            , acceptConsentV2.getConsent_id(), acceptConsentV2.getConsent_version_id(), acceptConsentV2.getClient_id());


                    callResumeConsent(baseurl, resumeConsentEntity, loginResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    loginResult.failure(error);
                }
            });
        } catch (Exception e) {
            loginResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE, e.getMessage()));
        }
    }

    private void callResumeConsent(String baseurl, ResumeConsentEntity resumeConsentEntity, final EventResult<LoginCredentialsResponseEntity> loginResult) {
        String methodName = "ConsentController:callResumeConsent";
        try {
            String resumeConsentUrl = ConsentURLHelper.getShared().getResumeConsentURL(baseurl);

            //headers Generation
            Map<String, String> headers = Headers.getShared(context).getHeaders(null, false, URLHelper.contentTypeJson);

            //Service
            ConsentService.getShared(context).resumeConsent(resumeConsentUrl, resumeConsentEntity, headers, new EventResult<ResumeConsentResponseEntity>() {
                @Override
                public void success(ResumeConsentResponseEntity resumeConsentresult) {
                    getTokenByCode(resumeConsentresult, loginResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    loginResult.failure(error);
                }
            });
        } catch (Exception e) {
            loginResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.RESUME_CONSENT_FAILURE, e.getMessage()));
        }
    }

    public void getTokenByCode(ResumeConsentResponseEntity resumeConsentresult, final EventResult<LoginCredentialsResponseEntity> loginResult) {
        String methodName = "ConsentController:getTokenByCode()";
        try {
            AccessTokenController.getShared(context).getAccessTokenByCode(resumeConsentresult.getData().getCode(), new EventResult<AccessTokenEntity>() {
                @Override
                public void success(AccessTokenEntity accessTokenresult) {
                    LoginCredentialsResponseEntity loginCredentialsResponseEntity = new LoginCredentialsResponseEntity();
                    loginCredentialsResponseEntity.setData(accessTokenresult);
                    loginCredentialsResponseEntity.setStatus(200);
                    loginCredentialsResponseEntity.setSuccess(true);
                    loginResult.success(loginCredentialsResponseEntity);
                }

                @Override
                public void failure(WebAuthError error) {
                    loginResult.failure(error);
                }
            });
        } catch (Exception e) {
            loginResult.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.RESUME_CONSENT_FAILURE, e.getMessage()));
        }
    }
}
