package com.example.cidaasv2.Controller.Repository.RequestId;

import android.content.Context;

import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;
import com.example.cidaasv2.Helper.pkce.OAuthChallengeGenerator;
import com.example.cidaasv2.Service.Entity.AuthRequest.AuthRequestResponseEntity;
import com.example.cidaasv2.Service.Repository.RequestId.RequestIdService;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

import androidx.annotation.Nullable;
import timber.log.Timber;

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
    // Generate Code Challenge and Code verifier
    public void generateChallenge(){

        OAuthChallengeGenerator generator = new OAuthChallengeGenerator();

        Dictionary<String,String> savedProperties=new Hashtable<>();

        codeVerifier=generator.getCodeVerifier();
        codeChallenge=generator.getCodeChallenge(codeVerifier);

        savedProperties.put("Verifier", codeVerifier);
        savedProperties.put("Challenge", codeChallenge);
        savedProperties.put("Method", generator.codeChallengeMethod);

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
        WebAuthError webAuthError = null;

        try {
            //WebError Code instance Creation
          webAuthError= WebAuthError.getShared(context);
            // Global Checking
            //Check all the login Properties are Correct
            if (loginproperties.get("DomainURL") == null || loginproperties.get("DomainURL").equals("")
                    || !((Hashtable) loginproperties).containsKey("DomainURL")) {
                webAuthError = webAuthError.propertyMissingException("Domain URL must not be null");
                String loggerMessage = "Request-Id readProperties failure : " + "Error Code - "
                        +webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage + ", Status Code - " +  webAuthError.statusCode;
                LogFile.getShared(context).addRecordToLog(loggerMessage);
                Primaryresult.failure(webAuthError);

                return;
            }
            if (loginproperties.get("ClientId").equals(null) || loginproperties.get("ClientId").equals("")
                    || !((Hashtable) loginproperties).containsKey("ClientId")) {
                webAuthError = webAuthError.propertyMissingException("ClientId must not be null");
                String loggerMessage = "Request-Id readProperties failure : " + "Error Code - "
                        +webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage + ", Status Code - " +  webAuthError.statusCode;
                LogFile.getShared(context).addRecordToLog(loggerMessage);
                Primaryresult.failure(webAuthError);return;
            }
            if (!((Hashtable) loginproperties).containsKey("RedirectURL") || loginproperties.get("RedirectURL").equals(null)
                    || loginproperties.get("RedirectURL").equals("")) {
                webAuthError = webAuthError.propertyMissingException("Redirect URL must not be null");
                String loggerMessage = "Request-Id readProperties failure : " + "Error Code - "
                        +webAuthError.errorCode + ", Error Message - " + webAuthError.ErrorMessage + ", Status Code - " +  webAuthError.statusCode;
                LogFile.getShared(context).addRecordToLog(loggerMessage);
                Primaryresult.failure(webAuthError);return;
            }

            //Todo Check for Code challenge and code Verifier
            if(codeChallenge=="" && codeVerifier=="") {
                generateChallenge();
            }
            //TODO Service call

            DBHelper.getShared().addLoginProperties(loginproperties);

            RequestIdService.getShared(context).getRequestID(loginproperties, null,null,
                    new Result<AuthRequestResponseEntity>() {
                @Override
                public void success(AuthRequestResponseEntity authRequestResponseEntity) {
                    Primaryresult.success(authRequestResponseEntity);
                }

                @Override
                public void failure(WebAuthError error) {
                    Primaryresult.failure(error);
                    String loggerMessage = "Request-Id service failure : " +
                            "Error Code - " +error.errorCode + ", Error Message - " + error.ErrorMessage + ", Status Code - " +  error.statusCode;
                    LogFile.getShared(context).addRecordToLog(loggerMessage);
                }
            },extraParams);
        }
        catch (Exception e)
        {
            String mess=e.toString();
            Primaryresult.failure(WebAuthError.getShared(context).serviceException(WebAuthErrorCode.REQUEST_ID_SERVICE_FAILURE));
            String loggerMessage = "Request-Id Controller failure : Error Message - " + e.getMessage();
            LogFile.getShared(context).addRecordToLog(loggerMessage);
        }
    }
}
