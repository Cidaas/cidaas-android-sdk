package de.cidaas.sdk.android.cidaasconsentv2.data.Service;

import java.util.Map;

import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.ResumeConsent.ResumeConsentEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.ResumeConsent.ResumeConsentResponseEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v1.ConsentDetailsV2RequestEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.AcceptConsent.AcceptConsentV2Entity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.AcceptConsent.AcceptConsentV2ResponseEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.ConsentDetails.ConsentDetailsV2ResponseEntity;
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
    Call<ConsentDetailsV2ResponseEntity> getConsentDetailsV2(@Url String url, @HeaderMap Map<String, String> headers, @Body ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity);

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
