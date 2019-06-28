package com.example.cidaasv2.Controller.Repository.Consent;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.ResumeLogin.ResumeLogin;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.ConsentDetailsV2RequestEntity;
import com.example.cidaasv2.Helper.Entity.ConsentEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.URLHelper.URLHelper;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptedRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.v2.ConsentDetailsV2ResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.HelperForService.Headers.Headers;
import com.example.cidaasv2.Service.Repository.Consent.ConsentService;
import com.example.cidaasv2.VerificationV2.data.Service.Helper.VerificationURLHelper;

import java.util.Dictionary;
import java.util.Map;

import androidx.annotation.NonNull;
import timber.log.Timber;

public class ConsentController {

    private Context context;
    private String ConsentName;
    private String ConsentVersion;

    public static ConsentController shared;

    public ConsentController(Context contextFromCidaas) {

        context = contextFromCidaas;
        //Todo setValue for authenticationType

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
    public void getConsentURL(@NonNull String consentName, @NonNull String consentVersion, @NonNull final Result<String> result) {

    }

    //Todo Log

    //-------------------------------------------------------------getConsentDetails----------------------------------------------------------------
    public void getConsentDetails(@NonNull final String consentName, @NonNull final Result<ConsentDetailsResultEntity> consentresult)
    {
        final String methodName="ConsentController :getConsentDetails()";
        try {
        CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
            @Override
            public void success(Dictionary<String, String> result) {
                String baseurl = result.get("DomainURL");
                String clientId = result.get("ClientId");

                if (consentName != null && !consentName.equals("")) {
                    //Call getString Details
                    ConsentService.getShared(context).getConsentDetails(baseurl, consentName, consentresult);
                } else {
                    consentresult.failure(WebAuthError.getShared(context).propertyMissingException("Consent Name must not be null",methodName));
                    return;
                }
            }

            @Override
            public void failure(WebAuthError error) {
                consentresult.failure(error);
            }
        });

        } catch (Exception e) {
            consentresult.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,WebAuthErrorCode.CONSENT_URL_FAILURE,e.getMessage()));
        }
    }


    //----------------------------------------------------Service call To AcceptConsent--------------------------------------------------------------
    public void acceptConsent(@NonNull final ConsentEntity consentEntity, final Result<LoginCredentialsResponseEntity> loginresult)
    {
      String methodName="ConsentController :acceptConsent()";
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    final String baseurl = result.get("DomainURL");
                    final String clientId = result.get("ClientId");

                    ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity=
                            getConsentManagementAcceptRequestEntity(clientId,consentEntity,loginresult);

                    serviceCallForAcceptConsent(baseurl, consentManagementAcceptedRequestEntity,loginresult);

                }


                @Override
                public void failure(WebAuthError error) {
                  loginresult.failure(error);
                }
            });


        } catch (Exception e) {
             loginresult.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,e.getMessage()));
        }
    }

    private void serviceCallForAcceptConsent(final String baseurl,  final ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity,
                                             final Result<LoginCredentialsResponseEntity> loginresult)
    {
       final String methodName="ConsentController :serviceCallForAcceptConsent()";
       try {
           ConsentService.getShared(context).acceptConsent(baseurl, consentManagementAcceptedRequestEntity, new Result<ConsentManagementAcceptResponseEntity>() {
                       @Override
                       public void success(ConsentManagementAcceptResponseEntity serviceresult) {
                           ResumeLogin.getShared(context).resumeLoginAfterConsent(baseurl, consentManagementAcceptedRequestEntity, loginresult);
                       }

                       @Override
                       public void failure(WebAuthError error) {
                           loginresult.failure(error);
                       }
                   });
       }
       catch (Exception e)
       {
           loginresult.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,e.getMessage()));
       }
    }

    //Todo Log

    private ConsentManagementAcceptedRequestEntity getConsentManagementAcceptRequestEntity(String clientId,final ConsentEntity consentEntity,
                                                                                           final Result<LoginCredentialsResponseEntity> loginresult)
    {
     String methodName="ConsentController :getConsentManagementAcceptRequestEntity()";
        if (consentEntity.getSub() != null && !consentEntity.getSub().equals("") && consentEntity.getConsentName() != null
                && !consentEntity.getConsentName().equals("") && consentEntity.getConsentVersion() != null
                && !consentEntity.getConsentVersion().equals("") && consentEntity.isAccepted() != false) {

            ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity = new ConsentManagementAcceptedRequestEntity();
            consentManagementAcceptedRequestEntity.setAccepted(consentEntity.isAccepted());
            consentManagementAcceptedRequestEntity.setSub(consentEntity.getSub());
            consentManagementAcceptedRequestEntity.setClient_id(clientId);
            consentManagementAcceptedRequestEntity.setName(consentEntity.getConsentName());
            consentManagementAcceptedRequestEntity.setTrackId(consentEntity.getTrackId());
            consentManagementAcceptedRequestEntity.setVersion(consentEntity.getConsentVersion());

            return consentManagementAcceptedRequestEntity;

        } else {
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException("Sub or ConsentName or ConsentVersion must not be null",
                    methodName));
            return null;
        }
    }


    //Consent V2

    public void getConsentDetailsV2(@NonNull ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity, Result<ConsentDetailsV2ResponseEntity> consentDetails)
    {
        try
        {
            checkProperties(consentDetailsV2RequestEntity, consentDetails);
        }
        catch (Exception e)
        {
            consentDetails.failure(WebAuthError.getShared(context).methodException("ConsentController:getConsentDetailsV2",
                    WebAuthErrorCode.CONSENT_DETAILS_FAILURE,e.getMessage()));
        }
    }

    private void checkProperties(ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity, Result<ConsentDetailsV2ResponseEntity> consentDetails) {
       String methodName="ConsentController:checkProperties";
        try
        {
            if(!consentDetailsV2RequestEntity.getConsent_id().equals("") && consentDetailsV2RequestEntity.getConsent_version_id()!=null &&
            !consentDetailsV2RequestEntity.getConsent_version_id().equals("") && consentDetailsV2RequestEntity.getConsent_id()!=null)
            {
                if(consentDetailsV2RequestEntity.getSub()!=null && !consentDetailsV2RequestEntity.getSub().equals("") &&
                        consentDetailsV2RequestEntity.getTrack_id()!=null && !consentDetailsV2RequestEntity.getTrack_id().equals(""))
                {
                    addProperties(consentDetailsV2RequestEntity,consentDetails);
                }
                else
                {
                    consentDetails.failure(WebAuthError.getShared(context).propertyMissingException("Track id or sub must not be null",methodName));
                }
            }
            else
            {
                consentDetails.failure(WebAuthError.getShared(context).propertyMissingException("Consent id or Consent Version id must not be null",methodName));
            }
        }
        catch (Exception e)
        {
            consentDetails.failure(WebAuthError.getShared(context).methodException(methodName, WebAuthErrorCode.CONSENT_DETAILS_FAILURE,e.getMessage()));
        }
    }

    private void addProperties(final ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity, final Result<ConsentDetailsV2ResponseEntity> consentDetails) {
        String methodName="ConsentController:addProperties";
        try
        {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    if(consentDetailsV2RequestEntity.getClient_id()!=null && !consentDetailsV2RequestEntity.getClient_id().equals(""))
                    {

                        consentDetailsV2RequestEntity.setClient_id(result.get("ClientId"));
                        serviceForGetConsentDetailsV2(result.get("DomainURL"),consentDetailsV2RequestEntity,consentDetails);
                    }
                    else
                    {
                        serviceForGetConsentDetailsV2(result.get("DomainURL"),consentDetailsV2RequestEntity,consentDetails);
                    }
                }

                @Override
                public void failure(WebAuthError error) {
                    consentDetails.failure(error);
                }
            });



        }
        catch (Exception e)
        {
            consentDetails.failure(WebAuthError.getShared(context).methodException(methodName,WebAuthErrorCode.CONSENT_DETAILS_FAILURE,e.getMessage()));
        }
    }

    private void serviceForGetConsentDetailsV2(String baseurl,ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity, Result<ConsentDetailsV2ResponseEntity> consentDetails) {
     String methodName="ConsentController:serviceForGetConsentDetailsV2";
     try
     {
         String authenticateUrl= URLHelper.getShared().getConsentDetailsV2(baseurl);

         //headers Generation
         Map<String,String> headers= Headers.getShared(context).getHeaders(null,false, URLHelper.contentTypeJson);

         //Servoec
         ConsentService.getShared(context).getConsentDetailsV2(authenticateUrl,consentDetailsV2RequestEntity,headers,consentDetails);
     }
     catch (Exception e)
     {
         consentDetails.failure(WebAuthError.getShared(context).methodException(methodName,WebAuthErrorCode.CONSENT_DETAILS_FAILURE,e.getMessage()));
     }

    }

}
