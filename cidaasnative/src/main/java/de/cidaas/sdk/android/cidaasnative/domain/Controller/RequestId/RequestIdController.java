package de.cidaas.sdk.android.cidaasnative.domain.Controller.RequestId;

import android.content.Context;

import androidx.annotation.Nullable;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import de.cidaas.sdk.android.cidaasnative.data.Entity.AuthRequest.AuthRequestResponseEntity;
import de.cidaas.sdk.android.cidaasnative.domain.Service.RequestId.RequestIdService;
import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.pkce.OAuthChallengeGenerator;
import de.cidaas.sdk.android.properties.CidaasProperties;
import timber.log.Timber;


public class RequestIdController {

    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static RequestIdController shared;

    public RequestIdController(Context contextFromCidaas) {
        sub = "";
        statusId = "";
        verificationType = "";
        authenticationType = "";
        context = contextFromCidaas;

    }

    String codeVerifier = "";
    String codeChallenge = "";
    String clientSecret = "";

    // Generate Code Challenge and Code verifier
    public void generateChallenge() {

        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        Dictionary<String, String> savedProperties = new Hashtable<>();

        codeVerifier = generator.getCodeVerifier();
        codeChallenge = generator.getCodeChallenge(codeVerifier);

        savedProperties.put("Verifier", codeVerifier);
        savedProperties.put("Challenge", codeChallenge);
        savedProperties.put("Method", generator.codeChallengeMethod);

      /*  if(!ENABLE_PKCE && !clientSecret.equals("") && clientSecret!=null)
        {
            savedProperties.put("ClientSecret",clientSecret);
        }
*/
        DBHelper.getShared().addChallengeProperties(savedProperties);
    }

    public static RequestIdController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new RequestIdController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //Service call for RequestID
    public void getRequestId(final Dictionary<String, String> loginproperties, final EventResult<AuthRequestResponseEntity> Primaryresult, @Nullable HashMap<String, String>... extraParams) {
        String methodName = "RequestIdController :getRequestId()";
        try {

            //Check all the login Properties are Correct
            if (checkNotnull(loginproperties, Primaryresult)) {
                return;
            }

            //Check for Code challenge and code Verifier
            if (codeChallenge.equals("") && codeVerifier.equals("")) {  //&& Cidaas.ENABLE_PKCE
                generateChallenge();
            }

            CidaasHelper.baseurl = loginproperties.get("DomainURL");
            DBHelper.getShared().addLoginProperties(loginproperties);

            //Service call
            RequestIdService.getShared(context).getRequestID(loginproperties, Primaryresult, extraParams);
        } catch (Exception e) {
            Primaryresult.failure(WebAuthError.getShared(context).methodException("Exception :" + methodName, WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,
                    e.getMessage()));
        }
    }

    private boolean checkNotnull(Dictionary<String, String> loginproperties, EventResult<AuthRequestResponseEntity> Primaryresult) {
        String methodName = "RequestIdController:checkNotnull() ";
        if (loginproperties.get("DomainURL") == null || loginproperties.get("DomainURL").equals("")
                || !((Hashtable) loginproperties).containsKey("DomainURL")) {
            Primaryresult.failure(CidaasProperties.getShared(context).getAuthError("DomainURL must not be null", "Error:" + methodName));
            return true;
        }
        if (loginproperties.get("ClientId").equals(null) || loginproperties.get("ClientId").equals("")
                || !((Hashtable) loginproperties).containsKey("ClientId")) {
            Primaryresult.failure(CidaasProperties.getShared(context).getAuthError("ClientId must not be null", "Error:" + methodName));
            return true;
        }
        if (!((Hashtable) loginproperties).containsKey("RedirectURL") || loginproperties.get("RedirectURL").equals(null)
                || loginproperties.get("RedirectURL").equals("")) {
            Primaryresult.failure(CidaasProperties.getShared(context).getAuthError("RedirectURL must not be null", "Error:" + methodName));
            return true;
        }

        CidaasHelper.ENABLE_PKCE = DBHelper.getShared().getEnablePKCE();
        if (!CidaasHelper.ENABLE_PKCE) {
            if (loginproperties.get("ClientSecret") == null || loginproperties.get("ClientSecret").equals("") || loginproperties == null
                    || !((Hashtable) loginproperties).containsKey("ClientSecret")) {
                Primaryresult.failure(CidaasProperties.getShared(context).getAuthError("PKCE flow is disabled ,ClientSecret must not be null",
                        "Error:" + methodName));
                return true;
            } else {
                clientSecret = loginproperties.get("ClientSecret");
            }
        }
        return false;
    }
}
