package widas.raja.cidaasconsentv2.Presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.cidaasv2.Controller.Cidaas;

import widas.raja.cidaasconsentv2.Domain.Controller.Consent.ConsentController;
import widas.raja.cidaasconsentv2.data.Entity.v1.ConsentDetailsV2RequestEntity;
import widas.raja.cidaasconsentv2.data.Entity.v1.ConsentEntity;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.CidaasHelper;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Entity.ConsentManagement.ConsentDetailsResultEntity;

import widas.raja.cidaasconsentv2.data.Entity.v2.AcceptConsent.AcceptConsentV2Entity;
import widas.raja.cidaasconsentv2.data.Entity.v2.ConsentDetails.ConsentDetailsV2ResponseEntity;
import com.example.cidaasv2.Service.Entity.LoginCredentialsEntity.LoginCredentialsResponseEntity;

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

    public void getConsentDetails(@NonNull final String consentName, final Result<ConsentDetailsResultEntity> consentResult) {
        ConsentController.getShared(context).getConsentDetails(consentName, consentResult);
    }


    public void loginAfterConsent(@NonNull final ConsentEntity consentEntity, final Result<LoginCredentialsResponseEntity> loginresult) {
        ConsentController.getShared(context).acceptConsent(consentEntity,loginresult);
    }



    public void getConsentDetailsV2(@NonNull final ConsentDetailsV2RequestEntity consentDetailsV2RequestEntity, final Result<ConsentDetailsV2ResponseEntity> consentResult) {
        if(consentDetailsV2RequestEntity.getRequestId()!=null && !consentDetailsV2RequestEntity.getRequestId().equals("")) {

            Cidaas.getInstance(context).getRequestId(new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity result) {
                    consentDetailsV2RequestEntity.setRequestId(result.getData().getRequestId());
                    ConsentController.getShared(context).getConsentDetailsV2(consentDetailsV2RequestEntity, consentResult);
                }

                @Override
                public void failure(WebAuthError error) {
                    consentResult.failure(error);
                }
            });
        }
        else
        {
            ConsentController.getShared(context).getConsentDetailsV2(consentDetailsV2RequestEntity, consentResult);
        }
    }

    public void acceptConsentV2(@NonNull final AcceptConsentV2Entity acceptConsentV2Entity, final Result<LoginCredentialsResponseEntity> consentResult) {
        ConsentController.getShared(context).acceptConsentV2(acceptConsentV2Entity, consentResult);
    }



}
