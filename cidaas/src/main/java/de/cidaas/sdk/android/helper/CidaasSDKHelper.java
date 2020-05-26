package de.cidaas.sdk.android.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class CidaasSDKHelper {
    public static String codeChallengeMethod = "S256";
    public static String contentType = "application/x-www-form-urlencoded";


    public static boolean isInternetAvailable(final Context context) {
        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return !((networkInfo == null) || (!networkInfo.isConnected()) || (!networkInfo.isAvailable()));
    }
}
