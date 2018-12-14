package com.example.cidaasv2.Service.Entity.MFA.EnrollMFA.FIDOKey;

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
