package de.cidaas.sdk.android.service.entity.mfa.EnrollMFA.FIDOKey;

public class APDUError extends Exception {
    private final int code;

    public APDUError(int code) {
        super(String.format("APDU status: %04x", code));
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
