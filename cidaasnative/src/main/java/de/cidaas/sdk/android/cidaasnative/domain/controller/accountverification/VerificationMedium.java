package de.cidaas.sdk.android.cidaasnative.domain.controller.accountverification;

public class VerificationMedium {
    public static final String EMAIL = "email";
    public static final String SMS = "sms";
    public static final String IVR = "ivr";

    private VerificationMedium() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

}
