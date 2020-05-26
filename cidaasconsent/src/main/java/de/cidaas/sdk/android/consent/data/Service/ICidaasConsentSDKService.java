package de.cidaas.sdk.android.consent.data.Service;

import java.util.Map;

import de.cidaas.sdk.android.consent.data.Entity.ResumeConsent.ResumeConsentEntity;
import de.cidaas.sdk.android.consent.data.Entity.ResumeConsent.ResumeConsentResponseEntity;
import de.cidaas.sdk.android.consent.ConsentDetailsRequestEntity;
import de.cidaas.sdk.android.consent.data.Entity.AcceptConsent.AcceptConsentV2Entity;
import de.cidaas.sdk.android.consent.data.Entity.AcceptConsent.AcceptConsentV2ResponseEntity;
import de.cidaas.sdk.android.consent.data.Entity.ConsentDetails.ConsentDetailsResponseEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentDetailsResultEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptResponseEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementAcceptedRequestEntity;
import de.cidaas.sdk.android.service.entity.consentmanagement.ConsentManagementResponseEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ICidaasConsentSDKService {


    //consent Accept
    @POST
    Call<ConsentManagementAcceptResponseEntity> acceptConsent(@Url String url, @HeaderMap Map<String, String> headers, @Body ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity);


    //Resume Login
    @POST
    Call<ResumeConsentResponseEntity> resumeConsent(@Url String url, @HeaderMap Map<String, String> headers, @Body ResumeConsentEntity resumeConsentEntity);

    //XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX-----V2---------XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    //Get Consent String Details
    @POST
    Call<ConsentDetailsResponseEntity> getConsentDetailsV2(@Url String url, @HeaderMap Map<String, String> headers, @Body ConsentDetailsRequestEntity consentDetailsRequestEntity);

    //consent Accept
    @POST
    Call<AcceptConsentV2ResponseEntity> acceptConsentV2(@Url String url, @HeaderMap Map<String, String> headers, @Body AcceptConsentV2Entity consentV2Entity);

    //-------------------------------------------------GET Call-----------------------------------------------

    //Get ConsentInfo
    @GET
    Call<ConsentManagementResponseEntity> getConsentInfo(@Url String url, @HeaderMap Map<String, String> headers);

    //Get Consent String Details
    @GET
    Call<ConsentDetailsResultEntity> getConsentStringDetails(@Url String url, @HeaderMap Map<String, String> headers);

}
