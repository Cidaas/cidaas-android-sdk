package com.example.cidaasv2.Controller.Repository.Configuration.TOTP.TOTPGenerator;

/**
 * Created by ganesh on 14/02/18.
 */

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

import java.security.SecureRandom;

public class GoogleAuthenticator {
    public static String getRandomSecretKey()
    {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);
        Base32 base32 = new Base32();
        String secretKey = base32.encodeToString(bytes);
        return secretKey.toLowerCase().replaceAll("(.{4})(?=.{4})", "$1 ");
    }

    public static String getTOTPCode(String secretKey)
    {
        String normalizedBase32Key = secretKey.replace(" ", "").toUpperCase();
        Base32 base32 = new Base32();
        byte[] b = normalizedBase32Key.getBytes();
        byte[] bytes = base32.decode(normalizedBase32Key);
        char[] hexKey = Hex.encodeHex(bytes);
        //String key = Hex.encodeHexString(bytes);
        long time = (System.currentTimeMillis() / 1000) / 30;
        String hexTime = Long.toHexString(time);
        return TOTP.generateTOTP(String.valueOf(hexKey), hexTime, "6");
    }
}
