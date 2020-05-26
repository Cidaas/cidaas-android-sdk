package de.cidaas.cidaasv2.Service.Entity.MFA.AuthenticateMFA.Face;

import org.junit.Test;

import de.cidaas.sdk.android.cidaas.Service.Entity.MFA.AuthenticateMFA.Face.AuthenticateFaceRequestDevice;

import static junit.framework.TestCase.assertTrue;

public class AuthenticateFaceRequestDeviceTest {
    @Test
    public void getDeviceId() {

        AuthenticateFaceRequestDevice authenticateFaceRequestDevice = new AuthenticateFaceRequestDevice();
        authenticateFaceRequestDevice.setDeviceId("Device ID");
        assertTrue(authenticateFaceRequestDevice.getDeviceId() == "Device ID");

    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme