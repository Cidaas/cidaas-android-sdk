package com.example.cidaasv2.Helper.CidaasProperties;

import android.content.Context;

import com.example.cidaasv2.Controller.Cidaas;
import com.example.cidaasv2.Helper.Enums.Result;
import com.example.cidaasv2.Helper.Enums.WebAuthErrorCode;
import com.example.cidaasv2.Helper.Extension.WebAuthError;
import com.example.cidaasv2.Helper.Genral.DBHelper;
import com.example.cidaasv2.Helper.Genral.FileHelper;
import com.example.cidaasv2.Helper.Logger.LogFile;

import java.util.Dictionary;
import java.util.Hashtable;

import static com.example.cidaasv2.Controller.Cidaas.ENABLE_PKCE;

public class CidaasProperties {
    public static CidaasProperties shared;
    private Context context;


    public  CidaasProperties(Context contextFromCidaas) {
        context=contextFromCidaas;
    }


    public static CidaasProperties getShared(Context contextFromCidaas )
    {
            if (shared == null) {
                shared = new  CidaasProperties(contextFromCidaas);
            }

        return shared;
    }


    public void saveCidaasProperties(final Result<Dictionary<String,String>> result)
    {
            readFromFile("Cidaas.xml",result);
    }

    public void checkCidaasProperties(final Result<Dictionary<String, String>> result)
    {
        try
        {
            if(Cidaas.baseurl!=null && !Cidaas.baseurl.equals("")){

                final Dictionary<String, String> loginProperties = DBHelper.getShared().getLoginProperties(Cidaas.baseurl);
                if (loginProperties != null && !loginProperties.isEmpty() && loginProperties.size() > 0) {
                    //check here for already saved properties
                    if (checkNotnull(result, loginProperties,"Check cidaas saved properties failure : ")) {
                        return;
                    }
                    else {
                        result.success(loginProperties);
                    }
                } else {
                    //Read File from asset to get URL
                    readFromFile("Cidaas.xml",result);
                }
            }
            else
            {
                readFromFile("Cidaas.xml",result);
               // result.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL must not be null"));
            }
        }
        catch (Exception e)
        {
            result.failure(WebAuthError.getShared(context).methodException("Exception :CidaasProperties :checkCidaasProperties()",WebAuthErrorCode.READ_PROPERTIES_ERROR,e.getMessage()));
        }
    }



    private void readFromFile(String fileName,final Result<Dictionary<String, String>> loginPropertiesResult) {
        try {
            FileHelper.getShared(context).readProperties(context.getAssets(), fileName, new Result<Dictionary<String, String>>() {
                @Override
                public void success(final Dictionary<String, String> loginProperties) {
                    //on successfully completion of file reading add it to LocalDB(shared Preference) and call requestIdByloginProperties

                    checkPKCEFlow(loginProperties, loginPropertiesResult);

                }

                @Override
                public void failure(WebAuthError error) {
                    //Return File Reading Error
                    String loggerMessage = "Read From File failure : "
                            + "Error Code - " + error.getErrorCode() + ", Error Message - " + error.getErrorMessage() + ", Status Code - " + error.getStatusCode();
                    LogFile.getShared(context).addFailureLog(loggerMessage);
                    loginPropertiesResult.failure(error);
                }
            });
        }
        catch (Exception e)
        {
          loginPropertiesResult.failure(WebAuthError.getShared(context).methodException("Exception :CidaasProperties :readFromFile()",WebAuthErrorCode.READ_PROPERTIES_ERROR,e.getMessage()));
        }
    }


    private void checkPKCEFlow(Dictionary<String, String> loginProperties, Result<Dictionary<String, String>> savedResult) {
        try {


            if (checkNotnull(savedResult, loginProperties,"Check PKCE Flow readProperties failure : ")) {
                return;
            }
            else {
                //Saved in Shared preference
                Cidaas.baseurl = loginProperties.get("DomainURL");
                DBHelper.getShared().addLoginProperties(loginProperties);
                savedResult.success(loginProperties);
            }

        } catch (Exception e) {
            savedResult.failure(WebAuthError.getShared(context).methodException("Exception :CidaasProperties :checkPKCEFlow()",WebAuthErrorCode.READ_PROPERTIES_ERROR,e.getMessage()));
        }
    }

    //Get WebAuth error
    public WebAuthError getAuthError(String errorMessage, String methodName) {
        String loggerMessage = methodName + "Error Code - " + WebAuthErrorCode.READ_PROPERTIES_ERROR + ", Error Message - " + errorMessage;
        LogFile.getShared(context).addFailureLog(loggerMessage);
        return WebAuthError.getShared(context).propertyMissingException(errorMessage,methodName);
    }


    private boolean checkNotnull(Result<Dictionary<String, String>> result, Dictionary<String, String> loginProperties,String title) {

        if (loginProperties.get("DomainURL") == null || loginProperties.get("DomainURL").equals("")|| loginProperties == null
                || !((Hashtable) loginProperties).containsKey("DomainURL")) {
            result.failure(getAuthError("Domain URL must not be empty", title));
            return true;
        }
        if (loginProperties.get("RedirectURL").equals("") || loginProperties.get("RedirectURL") == null || loginProperties == null
                || !((Hashtable) loginProperties).containsKey("RedirectURL")) {
            result.failure( getAuthError("RedirectURL must not be empty", title));
            return true;
        }
        if (loginProperties.get("ClientId").equals("") || loginProperties.get("ClientId") == null || loginProperties == null
                || !((Hashtable) loginProperties).containsKey("ClientId")) {
            result.failure(  getAuthError("ClientId must not be empty", title));
            return true;
        }

        ENABLE_PKCE = DBHelper.getShared().getEnablePKCE();
        if (!ENABLE_PKCE) {
            if (loginProperties.get("ClientSecret") == null || loginProperties.get("ClientSecret").equals("") || loginProperties == null
                    || !((Hashtable) loginProperties).containsKey("ClientSecret")) {
                result.failure( getAuthError("PKCE is disabled,ClientSecret must not be empty",title));
                return true;
            }
        }

        return false;
    }
}


