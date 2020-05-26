package de.cidaas.sdk.android.cidaas.Controller.Repository.Login;

import android.content.Context;

import de.cidaas.sdk.android.cidaas.Helper.Extension.WebAuthError;
import de.cidaas.sdk.android.cidaas.Helper.Enums.Result;
import de.cidaas.sdk.android.cidaas.Helper.Enums.WebAuthErrorCode;
import de.cidaas.sdk.android.cidaas.Helper.URLHelper.URLHelper;

import androidx.annotation.NonNull;

import timber.log.Timber;

public class LogoutController {
    private Context context;

    public static LogoutController shared;

    public LogoutController(Context contextFromCidaas) {

        context = contextFromCidaas;

    }

    public static LogoutController getShared(Context contextFromCidaas) {
        try {

            if (shared == null) {
                shared = new LogoutController(contextFromCidaas);
            }
        } catch (Exception e) {
            Timber.i(e.getMessage());
        }
        return shared;
    }
/*

    public void logout(@NonNull String baseurl, String access_token_hint, Result<LogoutResponseEntity> callback){
       try
       {
           LoginService.getShared(context).logoutForEmbeddedBrowser(baseurl,access_token_hint,callback);
       }
       catch (Exception e)
       {
           callback.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.LOGOUT_ERROR,
                   e.getMessage(), HttpStatusCode.EXPECTATION_FAILED));
       }
    }
*/


    public void getLogoutURL(@NonNull String baseurl, String access_token_hint, String post_logout_redirect_uri, Result<String> result) {
        try {
            String LogoutURL = "";
            if (access_token_hint != null && !access_token_hint.equals("")) {
                if (post_logout_redirect_uri != null && !post_logout_redirect_uri.equals("")) {
                    LogoutURL = baseurl + URLHelper.getShared().getLogoutURLForEmbeddedBrowser() + "?access_token_hint=" + access_token_hint + "&post_logout_redirect_uri=" + post_logout_redirect_uri;
                    result.success(LogoutURL);
                } else {
                    LogoutURL = baseurl + URLHelper.getShared().getLogoutURLForEmbeddedBrowser() + "?access_token_hint=" + access_token_hint;
                    result.success(LogoutURL);
                }
            } else {
                result.failure(WebAuthError.getShared(context).customException(WebAuthErrorCode.LOGOUT_ERROR,
                        "AccessToken must not be null", "Error :LogoutController :getLogoutURL()"));
            }
        } catch (Exception e) {
            result.failure(WebAuthError.getShared(context).methodException("Exception :LogoutController :getLogoutURL()", WebAuthErrorCode.LOGOUT_ERROR,
                    e.getMessage()));
        }
    }

}
