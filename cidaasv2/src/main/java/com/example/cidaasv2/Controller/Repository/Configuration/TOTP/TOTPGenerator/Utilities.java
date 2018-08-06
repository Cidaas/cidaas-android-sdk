package com.example.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPGenerator;

/**
 * Created by ganesh on 14/02/18.
 */

public class Utilities {

    public static final String ZXING_MARKET =
            "market://search?q=pname:com.google.zxing.client.android";

    public static final String ZXING_DIRECT =
            "https://zxing.googlecode.com/files/BarcodeScanner3.1.apk";

    public static final int DOWNLOAD_DIALOG = 0;
    public static final int MULTIPLE_ACCOUNTS_DIALOG = 1;
    static final int INVALID_QR_CODE = 3;
    static final int INVALID_SECRET_IN_QR_CODE = 7;

    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = 60 * SECOND_IN_MILLIS;

    private Utilities() { }

    public static final long millisToSeconds(long timeMillis) {
        return timeMillis / 1000;
    }

    public static final long secondsToMillis(long timeSeconds) {
        return timeSeconds * 1000;
    }
}
