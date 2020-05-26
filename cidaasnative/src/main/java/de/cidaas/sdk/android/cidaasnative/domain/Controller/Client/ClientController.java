package de.cidaas.sdk.android.cidaasnative.domain.Controller.Client;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.CidaasProperties.CidaasProperties;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Helper.Genral.CidaasHelper;
import de.cidaas.sdk.android.cidaas.Helper.Logger.LogFile;
import de.cidaas.sdk.android.cidaas.Helper.pkce.OAuthChallengeGenerator;

import de.cidaas.sdk.android.cidaasnative.data.Entity.ClientInfo.ClientInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.Client.ClientService;

import java.util.Dictionary;

import androidx.annotation.NonNull;

public class ClientController {

    private Context context;

    public static ClientController shared;

    public ClientController(Context contextFromCidaas) {
        context = contextFromCidaas;

    }

    String codeVerifier, codeChallenge;

    // Generate Code Challenge and Code verifier
    public void generateChallenge() {
        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        codeVerifier = generator.getCodeVerifier();
        codeChallenge = generator.getCodeChallenge(codeVerifier);

    }

    public static ClientController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new ClientController(contextFromCidaas);
            }
        } catch (Exception e) {
            LogFile.getShared(contextFromCidaas).addFailureLog("ClientController instance Creation Exception:-" + e.getMessage());
        }
        return shared;
    }

    //Service call to Get Client info
    public void getClientInfo(@NonNull final String requestId, final Result<ClientInfoEntity> clientInfoEntityResult) {
        final String methodName = "Client Controller :getClientInfo()";
        try {
            LogFile.getShared(context).addInfoLog("Info " + methodName, " Info requestId:-" + requestId);
            if (CidaasHelper.baseurl != null && !CidaasHelper.baseurl.equals("") && requestId != null && !requestId.equals("")) {

                CidaasProperties.getShared(context).checkCidaasProperties(new Result<Dictionary<String, String>>() {
                    @Override
                    public void success(Dictionary<String, String> stringresult) {
                        ClientService.getShared(context).getClientInfo(requestId, stringresult.get("DomainURL"), clientInfoEntityResult);
                    }

                    @Override
                    public void failure(WebAuthError error) {
                        clientInfoEntityResult.failure(error);
                    }
                });
            } else {
                clientInfoEntityResult.failure(WebAuthError.getShared(context).CidaaspropertyMissingException("", "Error :" + methodName));
            }

        } catch (Exception e) {
            clientInfoEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.CLIENT_INFO_FAILURE,
                    e.getMessage()));
        }
    }


}
