package de.cidaas.sdk.android.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public final class CidaasSDKHelper {
    public static String codeChallengeMethod = "S256";
    public static String contentType = "application/x-www-form-urlencoded";

    private CidaasSDKHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }


    public static boolean isInternetAvailable(final Context context) {
        ConnectivityManager conManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return !((networkInfo == null) || (!networkInfo.isConnected()) || (!networkInfo.isAvailable()));
    }
}
