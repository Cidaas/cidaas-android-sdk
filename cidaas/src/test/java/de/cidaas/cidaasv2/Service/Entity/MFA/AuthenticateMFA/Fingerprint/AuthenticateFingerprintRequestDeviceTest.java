package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Fingerprint;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Fingerprint.AuthenticateFingerprintRequestDevice;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateFingerprintRequestDeviceTest {
    @Test
    public void getDeviceId() {

        AuthenticateFingerprintRequestDevice authenticateFingerprintRequestDevice = new AuthenticateFingerprintRequestDevice();
        authenticateFingerprintRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateFingerprintRequestDevice.getDeviceId() == "Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme