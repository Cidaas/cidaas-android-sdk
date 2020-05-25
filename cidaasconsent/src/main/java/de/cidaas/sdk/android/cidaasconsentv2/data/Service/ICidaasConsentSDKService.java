package de.cidaas.sdk.android.cidaasconsentv2.data.Service;

import de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement.ConsentManagementAcceptResponseEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement.ConsentManagementAcceptedRequestEntity;
import de.cidaas.sdk.android.cidaas.Service.Entity.ConsentManagement.ConsentManagementResponseEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.ResumeConsent.ResumeConsentEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.ResumeConsent.ResumeConsentResponseEntity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Url;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v1.ConsentDetailsV2RequestEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.AcceptConsent.AcceptConsentV2Entity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.AcceptConsent.AcceptConsentV2ResponseEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.ConsentDetails.ConsentDetailsV2ResponseEntity;

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
