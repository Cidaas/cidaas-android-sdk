package com.example.cidaasv2.Controller.Repository.RequestId;

import android.content.Context;

import androidx.annotation.Nullable;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.CidaasProperties.CidaasProperties;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Repository.RequestId.RequestIdService;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import timber.log.Timber;

import static com.example.cidaasv2.Helper.Genral.CidaasHelper.ENABLE_PKCE;

public class RequestIdController {

    private String statusId;
    private String authenticationType;
    private String sub;
    private String verificationType;
    private Context context;

    public static RequestIdController shared;

    public RequestIdController(Context contextFromCidaas) {
        sub="";
        statusId="";
        verificationType="";
        authenticationType="";
        context=contextFromCidaas;
        //Todo setValue for authenticationType

    }

    String codeVerifier="";
        String codeChallenge="";
        String clientSecret="";
    // Generate Code Challenge and Code verifier
    public void generateChallenge(){

        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        Dictionary<String,String> savedProperties=new Hashtable<>();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge=generator.getCodeChallenge(codeVerifier);

        savedProperties.put("Verifier", codeVerifier);
        savedProperties.put("Challenge", codeChallenge);
        savedProperties.put("Method", generator.codeChallengeMethod);

        if(!ENABLE_PKCE && !clientSecret.equals("") && clientSecret!=null)
        {
            savedProperties.put("ClientSecret",clientSecret);
        }

        DBHelper.getShared().addChallengeProperties(savedProperties);


    }

    public static RequestIdController getShared(Context contextFromCidaas )
    {
        try {

            if (shared == null) {
                shared = new RequestIdController(contextFromCidaas);
            }
        }
        catch (Exception e)
        {
            Timber.i(e.getMessage());
        }
        return shared;
    }

    //Service call for RequestID
    public void getRequestId(final Dictionary<String,String> loginproperties,  final Result<AuthRequestResponseEntity> Primaryresult,@Nullable HashMap<String, String>... extraParams )
    {
        String methodName="RequestIdController :getRequestId()";
        try {

            //Check all the login Properties are Correct
            if (checkNotnull(loginproperties, Primaryresult)) {return;}

            //Todo Check for Code challenge and code Verifier
            if(codeChallenge.equals("") && codeVerifier.equals("") ) {  //&& Cidaas.ENABLE_PKCE
                generateChallenge();
            }
            //TODO Service call
            Cidaas.baseurl = loginproperties.get("DomainURL");

            DBHelper.getShared().addLoginProperties(loginproperties);

            RequestIdService.getShared(context).getRequestID(loginproperties, Primaryresult,extraParams);
        }
        catch (Exception e)
        {
            Primaryresult.failure(WebAuthError.getShared(context).methodException("Exception :"+methodName,WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE,
                    e.getMessage()));
        }
    }

    private boolean checkNotnull(Dictionary<String, String> loginproperties, Result<AuthRequestResponseEntity> Primaryresult)
    {
     String methodName="RequestIdController:checkNotnull() ";
        if (loginproperties.get("DomainURL") == null || loginproperties.get("DomainURL").equals("")
                || !((Hashtable) loginproperties).containsKey("DomainURL")) {
            Primaryresult.failure(CidaasProperties.getShared(context).getAuthError("DomainURL must not be null","Error:"+methodName));
            return true;
        }
        if (loginproperties.get("ClientId").equals(null) || loginproperties.get("ClientId").equals("")
                || !((Hashtable) loginproperties).containsKey("ClientId")) {
            Primaryresult.failure(CidaasProperties.getShared(context).getAuthError("ClientId must not be null","Error:"+methodName));
            return true;
        }
        if (!((Hashtable) loginproperties).containsKey("RedirectURL") || loginproperties.get("RedirectURL").equals(null)
                || loginproperties.get("RedirectURL").equals("")) {
            Primaryresult.failure(CidaasProperties.getShared(context).getAuthError("RedirectURL must not be null","Error:"+methodName));
            return true;
        }

        ENABLE_PKCE = DBHelper.getShared().getEnablePKCE();
        if (!ENABLE_PKCE) {
            if (loginproperties.get("ClientSecret") == null || loginproperties.get("ClientSecret").equals("") || loginproperties == null
                    || !((Hashtable) loginproperties).containsKey("ClientSecret")) {
                Primaryresult.failure(CidaasProperties.getShared(context).getAuthError("PKCE flow is disabled ,ClientSecret must not be null",
                        "Error:"+methodName));
                return true;
            }
            else
            {
                clientSecret=loginproperties.get("ClientSecret");
            }
        }
        return false;
    }
}
