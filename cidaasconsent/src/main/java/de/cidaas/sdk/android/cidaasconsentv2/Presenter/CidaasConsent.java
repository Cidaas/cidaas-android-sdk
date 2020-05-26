package de.cidaas.sdk.android.cidaasconsentv2.Presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import de.cidaas.sdk.android.cidaas.Helper.Entity.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Genral.CidaasHelper;
//import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;

import de.cidaas.sdk.android.cidaasconsentv2.Domain.Controller.Consent.ConsentController;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v1.ConsentDetailsV2RequestEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v1.ConsentEntity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.AcceptConsent.AcceptConsentV2Entity;
import de.cidaas.sdk.android.cidaasconsentv2.data.Entity.v2.ConsentDetails.ConsentDetailsV2ResponseEntity;

public class CidaasConsent {

    private Context context;
    private static CidaasConsent cidaasConsentInstance;

    public static CidaasConsent getInstance(Context YourActivitycontext) {
        if (cidaasConsentInstance == null) {
            cidaasConsentInstance = new CidaasConsent(YourActivitycontext);
        }

        return cidaasConsentInstance;
    }

    private CidaasConsent(Context yourActivityContext) {
        this.context = yourActivityContext;
        CidaasHelper.getShared(yourActivityContext).initialiseObject();
    }

    // -----------------------------------------------------***** CONSENT MANAGEMENT *****---------------------------------------------------------------

 /*   public void getConsentDetails(@NonNull final String consentName, final Result<ConsentDetailsResultEntity> consentResult) {
        ConsentController.getShared(context).getConsentDetails(consentName, consentResult);
    }
*/

    public void loginAfterConsent(@NonNull final ConsentEntity consentEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        ConsentController.getShared(context).acceptConsent(consentEntity, loginresult);
    }

    public void getConsentDetails(@NonNull final ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity, final Result<ConsentDetailsV2ResponseEntity> consentResult) {
        ConsentController.getShared(context).getConsentDetailsV2(consentDetailsV2RequestEntity, consentResult);
    }

    public void acceptConsent(@NonNull final AcceptConsentV2Entity acceptConsentV2Entity, final Result<LoginCredentialsResponseEntity> consentResult) {
        ConsentController.getShared(context).acceptConsentV2(acceptConsentV2Entity, consentResult);
    }

}
