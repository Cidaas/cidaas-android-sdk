package de.cidaas.sdk.android.cidaas.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ganesh on 29/12/17.
 */

public class CidaasSDKHelper {
    public static String responseType = "code";
    public static String viewType = "login";
    public static String grantType = "authorization_code";
    public static String codeChallengeMethod = "S256";
    public static String contentType = "application/x-www-form-urlencoded";

    /*public static String constructSocialServiceURl(String tokenOrCode, String provider, String givenType) {

        return CidaasSDKEntity.cidaasSDKEntityInstance.getSocialURL() + "?codeOrToken=" + tokenOrCode + "&provider=" + provider + "&clientId=" + CidaasSDKEntity.cidaasSDKEntityInstance.getClientId() + "&givenType=" + givenType + "&responseType=" + CidaasSDKHelper.responseType + "&redirectUrl=" + CidaasSDKEntity.cidaasSDKEntityInstance.getRedirectURI() + "&viewtype=" + CidaasSDKHelper.viewType;
    }

    public static String constructLoginURL(String codeChallenge) {

        Uri.Builder builder = new Uri.Builder();

        String[] authority = CidaasSDKEntity.cidaasSDKEntityInstance.getAuthorizationURL().split("//");

        builder.scheme("https")
                .encodedAuthority(authority[1])
                .appendQueryParameter("redirect_uri", CidaasSDKEntity.cidaasSDKEntityInstance.getRedirectURI())
                .appendQueryParameter("response_type", responseType)
                .appendQueryParameter("view_type", viewType)
                .appendQueryParameter("client_id", CidaasSDKEntity.cidaasSDKEntityInstance.getClientId())
                .appendQueryParameter("code_challenge", codeChallenge)
                .appendQueryParameter("code_challenge_method", codeChallengeMethod);

        for(Map.Entry<String,String> entry : CidaasSDK.extraParams.entrySet()) {
            builder.appendQueryParameter(entry.getKey(),entry.getValue());
        }

         return builder.build().toString();

    }

    public static String constructUserProfileURL(String accessToken) {
        Uri.Builder builder = new Uri.Builder();
        String[] authority = CidaasSDKEntity.cidaasSDKEntityInstance.getBaseURL().split("//");
        builder.scheme("https")
                .encodedAuthority(authority[1] + "/user-profile/editprofile")
                .appendQueryParameter("access_token", accessToken);

        for(Map.Entry<String,String> entry : CidaasSDK.extraParams.entrySet()) {
            builder.appendQueryParameter(entry.getKey(),entry.getValue());
        }
        return builder.build().toString();
    }*/

    public static boolean isInternetAvailable(final Context context) {
        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return !((networkInfo == null) || (!networkInfo.isConnected()) || (!networkInfo.isAvailable()));
    }
}
