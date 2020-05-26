package de.cidaas.sdk.android.Service.Entity.MFA.AuthenticateMFA.FIDOKey;

import org.junit.Test;

import de.cidaas.sdk.android.service.entity.mfa.AuthenticateMFA.FIDOKey.AuthenticateFIDORequestDevice;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateFIDORequestDeviceTest {
    @Test
    public void getDeviceId() {

        AuthenticateFIDORequestDevice authenticateFIDORequestDevice = new AuthenticateFIDORequestDevice();
        authenticateFIDORequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateFIDORequestDevice.getDeviceId() == "Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme