package de.cidaas.sdk.android.cidaasnative.domain.controller.client;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.Dictionary;

import de.cidaas.sdk.android.cidaasnative.data.entity.clientinfo.ClientInfoEntity;
import de.cidaas.sdk.android.cidaasnative.domain.service.Client.ClientService;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;
import de.cidaas.sdk.android.helper.pkce.OAuthChallengeGenerator;
import de.cidaas.sdk.android.properties.CidaasProperties;


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
    public void getClientInfo(@NonNull final String requestId, final EventResult<ClientInfoEntity> clientInfoEntityResult) {
        final String methodName = "Client Controller :getClientInfo()";
        try {
            LogFile.getShared(context).addInfoLog("Info " + methodName, " Info requestId:-" + requestId);
            if (CidaasHelper.baseurl != null && !CidaasHelper.baseurl.equals("") && requestId != null && !requestId.equals("")) {

                CidaasProperties.getShared(context).checkCidaasProperties(new EventResult<Dictionary<String, String>>() {
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
                clientInfoEntityResult.failure(WebAuthError.getShared(context).cidaasPropertyMissingException("", "Error :" + methodName));
            }

        } catch (Exception e) {
            clientInfoEntityResult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.CLIENT_INFO_FAILURE,
                    e.getMessage()));
        }
    }


}
