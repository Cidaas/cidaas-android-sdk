package com.example.cidaasv2.Controller.Repository.Consent;

import android.content.Context;

import com.example.cidaasv2.Controller.Repository.AccessToken.AccessTokenController;
import com.example.cidaasv2.Controller.Repository.ResumeLogin.ResumeLogin;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Entity.ConsentEntity;
import com.example.cidaasv2.Helper.Enums.HttpStatusCode;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Service.Entity.AccessTokenEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentManagementAcceptedRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentRequestEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ResumeConsent.ResumeConsentResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;
import com.example.cidaasv2.Service.Repository.Consent.ConsentService;

import java.util.Dictionary;

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

    //Service call For ConsentManagementService
    public void getConsentDetails(@NonNull final String consentName, @NonNull final Result<ConsentDetailsResultEntity> consentresult) {
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
                    consentresult.failure(WebAuthError.getShared(context).propertyMissingException("Consent Name must not be null"));
                    return;
                }
            }

            @Override
            public void failure(WebAuthError error) {
                consentresult.failure(error);
            }
        });


       // ConsentName = consentName;

        } catch (Exception e) {
            Timber.e("Get Consent URL Controller" + e.getMessage());
            LogFile.getShared(context).addRecordToLog("Get Consent URL Service"+e.getMessage()+ WebAuthErrorCode.CONSENT_URL_FAILURE);
            consentresult.failure(WebAuthError.getShared(context).serviceException("Exception :ConsentController :getConsentDetails()",WebAuthErrorCode.CONSENT_URL_FAILURE,e.getMessage()));
        }
    }


    //Service call To AcceptConsent
    public void acceptConsent(@NonNull final ConsentEntity consentEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        try {

            CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                @Override
                public void success(Dictionary<String, String> result) {
                    final String baseurl = result.get("DomainURL");
                    final String clientId = result.get("ClientId");

                    ConsentManagementAcceptedRequestEntity consentManagementAcceptedRequestEntity=getConsentManagementAcceptRequestEntity(clientId,consentEntity,loginresult);

                    if(consentManagementAcceptedRequestEntity!=null) {
                        ConsentService.getShared(context).acceptConsent(baseurl, consentManagementAcceptedRequestEntity, null,
                                new Result<ConsentManagementAcceptResponseEntity>() {
                                    @Override
                                    public void success(ConsentManagementAcceptResponseEntity serviceresult) {

                                        ResumeLogin.getShared(context).resumeLoginAfterConsent(baseurl,consentEntity.getSub(), consentEntity.getTrackId(), consentEntity.getConsentName()
                                                , consentEntity.getConsentVersion(), clientId,loginresult);

                                    }

                                    @Override
                                    public void failure(WebAuthError error) {
                                        loginresult.failure(error);
                                    }
                                });
                    }

                }



                @Override
                public void failure(WebAuthError error) {
                  loginresult.failure(error);
                }
            });


        } catch (Exception e) {
            Timber.e(e.getMessage());
            LogFile.getShared(context).addRecordToLog(e.getMessage()+ WebAuthErrorCode.ACCEPT_CONSENT_FAILURE);
            loginresult.failure(WebAuthError.getShared(context).serviceException("Exception :ConsentController :acceptConsent()",WebAuthErrorCode.ACCEPT_CONSENT_FAILURE,e.getMessage()));
        }
    }


    private ConsentManagementAcceptedRequestEntity getConsentManagementAcceptRequestEntity(String clientId,final ConsentEntity consentEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
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
            loginresult.failure(WebAuthError.getShared(context).propertyMissingException("Sub or ConsentName or ConsentVersion must not be null"));
            return null;
        }
    }

}
