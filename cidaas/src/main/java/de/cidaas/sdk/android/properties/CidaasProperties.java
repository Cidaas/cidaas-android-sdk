package de.cidaas.sdk.android.properties;

import android.content.Context;

import java.util.Dictionary;
import java.util.Hashtable;

import de.cidaas.sdk.android.helper.enums.EventResult;
import de.cidaas.sdk.android.helper.enums.WebAuthErrorCode;
import de.cidaas.sdk.android.helper.extension.WebAuthError;
import de.cidaas.sdk.android.helper.general.CidaasConstants;
import de.cidaas.sdk.android.helper.general.CidaasHelper;
import de.cidaas.sdk.android.helper.general.DBHelper;
import de.cidaas.sdk.android.helper.general.FileHelper;
import de.cidaas.sdk.android.helper.logger.LogFile;

public class CidaasProperties {
    public static CidaasProperties shared;
    private Context context;


    public CidaasProperties(Context contextFromCidaas) {
        context = contextFromCidaas;
    }


    public static CidaasProperties getShared(Context contextFromCidaas) {
        if (shared == null) {
            shared = new CidaasProperties(contextFromCidaas);
        }

        return shared;
    }


    public void saveCidaasProperties(final EventResult<Dictionary<String, String>> result) {

        if (CidaasHelper.IS_SETURL_CALLED) {
            //From Authenticator app
            if (!CidaasHelper.baseurl.equals("") && CidaasHelper.baseurl != null) {
                result.success(DBHelper.getShared().getLoginProperties(CidaasHelper.baseurl));
            } else {
                result.failure(WebAuthError.getShared(context).cidaasPropertyMissingException("CidaasHelper.Baseurl must not be empty", "CidaasProperties:saveCidaasProperties()"));
            }

        } else {
            readFromFile("cidaas.xml", result);
        }

    }

    public void checkCidaasProperties(final EventResult<Dictionary<String, String>> result) {
        try {
            if (CidaasHelper.baseurl != null && !CidaasHelper.baseurl.equals("")) {

                final Dictionary<String, String> loginProperties = DBHelper.getShared().getLoginProperties(CidaasHelper.baseurl);
                if (loginProperties != null && !loginProperties.isEmpty() && loginProperties.size() > 0) {
                    //check here for already saved properties
                    if (checkNotnull(result, loginProperties, "Check de.cidaas saved properties failure : ")) {
                        return;
                    } else {
                        result.success(loginProperties);
                    }
                } else {
                    //Read File from asset to get URL
                    readFromFile("cidaas.xml", result);
                }
            } else {
                readFromFile("cidaas.xml", result);
                // result.failure(WebAuthError.getShared(context).propertyMissingException("DomainURL must not be null"));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :CidaasProperties :checkCidaasProperties()", WebAuthErrorCode.READ_PROPERTIES_ERROR, e.getMessage()));
        }
    }


    private void readFromFile(String fileName, final EventResult<Dictionary<String, String>> loginPropertiesResult) {
        try {
            FileHelper.getShared(context).readProperties(context.getAssets(), fileName, new EventResult<Dictionary<String, String>>() {
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
        } catch (Exception e) {
            loginPropertiesResult.failure(WebAuthError.getShared(context).methodException("Exception :CidaasProperties :readFromFile()", WebAuthErrorCode.READ_PROPERTIES_ERROR, e.getMessage()));
        }
    }


    private void checkPKCEFlow(Dictionary<String, String> loginProperties, EventResult<Dictionary<String, String>> savedResult) {
        try {


            if (checkNotnull(savedResult, loginProperties, "Check PKCE Flow readProperties failure : ")) {
                return;
            } else {
                //Saved in Shared preference
                CidaasHelper.baseurl = loginProperties.get(CidaasConstants.DOMAIN_URL);
                DBHelper.getShared().addLoginProperties(loginProperties);
                savedResult.success(loginProperties);
            }

        } catch (Exception e) {
            savedResult.failure(WebAuthError.getShared(context).methodException("Exception :CidaasProperties :checkPKCEFlow()", WebAuthErrorCode.READ_PROPERTIES_ERROR, e.getMessage()));
        }
    }

    //Get WebAuth error
    public WebAuthError getAuthError(String errorMessage, String methodName) {
        String loggerMessage = methodName + "Error Code - " + WebAuthErrorCode.READ_PROPERTIES_ERROR + ", Error Message - " + errorMessage;
        LogFile.getShared(context).addFailureLog(loggerMessage);
        return WebAuthError.getShared(context).propertyMissingException(errorMessage, methodName);
    }

    //Check the Conditions If Condition fails return true otherwise return false
    private boolean checkNotnull(EventResult<Dictionary<String, String>> result, Dictionary<String, String> loginProperties, String title) {

        if (loginProperties.get(CidaasConstants.DOMAIN_URL) == null || loginProperties.get(CidaasConstants.DOMAIN_URL).equals("") || loginProperties == null
                || !((Hashtable) loginProperties).containsKey(CidaasConstants.DOMAIN_URL)) {
            result.failure(getAuthError("Domain URL must not be empty", title));
            return true;
        }
        if (loginProperties.get("RedirectURL").equals("") || loginProperties.get("RedirectURL") == null || loginProperties == null
                || !((Hashtable) loginProperties).containsKey("RedirectURL")) {
            result.failure(getAuthError("RedirectURL must not be empty", title));
            return true;
        }
        if (loginProperties.get("ClientId").equals("") || loginProperties.get("ClientId") == null || loginProperties == null
                || !((Hashtable) loginProperties).containsKey("ClientId")) {
            result.failure(getAuthError("ClientId must not be empty", title));
            return true;
        }

        CidaasHelper.ENABLE_PKCE = DBHelper.getShared().getEnablePKCE();
        if (!CidaasHelper.ENABLE_PKCE) {
            if (loginProperties.get("ClientSecret") == null || loginProperties.get("ClientSecret").equals("") || loginProperties == null
                    || !((Hashtable) loginProperties).containsKey("ClientSecret")) {
                result.failure(getAuthError("PKCE is disabled,ClientSecret must not be empty", title));
                return true;
            }
        }

        return false;
    }
}


