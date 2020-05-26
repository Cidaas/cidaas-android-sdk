package de.cidaas.sdk.android.consent.Presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import de.cidaas.sdk.android.consent.Domain.Controller.Consent.ConsentController;
import de.cidaas.sdk.android.consent.ConsentDetailsRequestEntity;
import de.cidaas.sdk.android.consent.ConsentEntity;
import de.cidaas.sdk.android.consent.data.Entity.AcceptConsent.AcceptConsentV2Entity;
import de.cidaas.sdk.android.consent.data.Entity.ConsentDetails.ConsentDetailsResponseEntity;
import de.cidaas.sdk.android.entities.LoginCredentialsResponseEntity;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.general.CidaasHelper;


//import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;

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

 /*   public void getConsentDetails(@NonNull final String consentName, finalEventResult<ConsentDetailsResultEntity> consentResult) {
        ConsentController.getShared(context).getConsentDetails(consentName, consentResult);
    }
*/

    public void loginAfterConsent(@NonNull final ConsentEntity consentEntity, final EventResult<LoginCredentialsResponseEntity> loginresult) {
        ConsentController.getShared(context).acceptConsent(consentEntity, loginresult);
    }

    public void getConsentDetails(@NonNull final ConsentDetailsRequestEntity consentDetailsRequestEntity, final EventResult<ConsentDetailsResponseEntity> consentResult) {
        ConsentController.getShared(context).getConsentDetailsV2(consentDetailsRequestEntity, consentResult);
    }

    public void acceptConsent(@NonNull final AcceptConsentV2Entity acceptConsentV2Entity, final EventResult<LoginCredentialsResponseEntity> consentResult) {
        ConsentController.getShared(context).acceptConsentV2(acceptConsentV2Entity, consentResult);
    }

}
